package com.tenutz.kiosksim.utils.watcher

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding4.widget.textChanges
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit


class CouponNumberTextWatcher(private val editText: EditText) : TextWatcher {

    private val compositeDisposable = CompositeDisposable()
    private var isBack = false
    private var lastSelectionStart = -1

    init {
        //BACK BUTTON LONG CLICK 이벤트에 의해 발생하는 QUICK DELETE에 대비한 UI 업데이트
        editText.textChanges()
            .delay(10, TimeUnit.MILLISECONDS)
            .filter {
                //지우는 중에 뷰의 SELECTION과 OnTextChanged에서 갱신한 SELECTION 값을 비교할 때 다르면 QUICK DELETE로 간주한다.
                isBack && editText.selectionStart == 0 && lastSelectionStart != editText.selectionStart
            }
            .map { charSequence ->
                charSequence.toString()
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s ->

                val spannableString = editText.text

                //애니메이션을 수행할 Spannable 구간을 탐색한다.
                val targetColor = ContextCompat.getColor(editText.context, R.color.rect_4474)
                val spans = spannableString.getSpans(0, spannableString.length, ForegroundColorSpan::class.java)

                var spanStart: Int = 0
                var spanEnd: Int = 19

                for (span in spans) {
                    val spanColor = span.foregroundColor
                    if (spanColor == targetColor) {
                        spanStart = spannableString.getSpanStart(span)
                        spanEnd = spannableString.getSpanEnd(span)
                        break
                    }
                }

                //찾지 못하면 애니메이션을 수행할 필요가 없다.
                if(spanStart == 0 && spanEnd == 19) return@subscribe

                //찾은 Spannable COLOR로부터 애니메이션을 수행한다.
                val endColor = ContextCompat.getColor(editText.context, R.color.rect_4481)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), targetColor, endColor)
                colorAnimation.duration = 500
                colorAnimation.addUpdateListener { animator ->
                    //애니메이션 하기 직전, 입력에 의해 selection이 변경되어, 애니메이션을 중지한다.
                    if(editText.selectionStart != 0) return@addUpdateListener
                    val animatedValue = animator.animatedValue as Int
                    editText.text.setSpan(
                        ForegroundColorSpan(animatedValue),
                        spanStart,
                        spanEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                colorAnimation.start()
            }.addTo(compositeDisposable)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    //QUICK DELETE가 발생하는 경우 Text Changed를 감지하지 못한다.
    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.toString().length > 20) return
        editText.removeTextChangedListener(this)

        isBack = before > count

        val replace = s.toString().replace("-", "")
        //나머지 구간을 0으로 채워 16자리로 고정한다.
        val take = replace.take(16) + "0".repeat(16 - replace.take(16).length)

        val formatted = StringBuilder()

        //'-'을 포함한 형식을 갖춘 문자열을 생성한다.
        for (i in 0..15) {
            if (i > 0 && i % 4 == 0) {
                formatted.append("-")
            }
            formatted.append(take[i])
        }

        //문자를 추가하는 경우와 제거하는 경우를 나누어 EditText의 다음 SELECTION을 계산한다.
        val x = if (before < count) 1 else 0
        val newIndex =
            Math.min(19, (start + x) + (if (before < count) 1 else -1) * (if ((start + x) % 5 == 0) 1 else 0))

        lastSelectionStart = if(newIndex != -1) newIndex else before
        Logger.d("${lastSelectionStart}")

        if (newIndex != -1) {
            editText.setText(
                formatted.take(newIndex).toString() + formatted.substring(newIndex).replace("[0-9]".toRegex(), "0")
            )
            //4474는 기본 컬러이지만 위 애니메이션 동작에서 Spannable을 찾기 위해 의도적으로 Span으로 설정한다.
            editText.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(editText.context, R.color.rect_4474)),
                0,
                newIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(editText.context, R.color.rect_4481)),
                newIndex,
                19,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            editText.setText(
                formatted.take(before).toString() + formatted.substring(before).replace("[0-9]".toRegex(), "0")
            )
            editText.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(editText.context, R.color.rect_4474)),
                0,
                before-1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(editText.context, R.color.rect_4481)),
                before-1,
                19,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        editText.setSelection(if (newIndex == -1) 0 else newIndex)
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
    }
}