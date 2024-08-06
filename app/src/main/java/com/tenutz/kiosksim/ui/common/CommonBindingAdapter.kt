package com.tenutz.kiosksim.ui.common

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.utils.ext.toPx
import com.tenutz.kiosksim.utils.type.SocialType
import com.tenutz.kiosksim.utils.watcher.BusinessNumberTextWatcher
import com.tenutz.kiosksim.utils.watcher.HourTextWatcher
import com.tenutz.kiosksim.utils.watcher.MinuteTextWatcher


object CommonBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:showImage", "bind:imageRadius", requireAll = false)
    fun showImage(imageView: ImageView, imageUrl: String?, radius: Int?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(imageUrl)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        *listOfNotNull(
                            CenterCrop(),
                            radius?.let {
                                it.takeIf { it > 0 }?.let {
                                    RoundedCorners(it.toPx.toInt())
                                }
                            } ?: RoundedCorners(8.toPx.toInt()),
                        ).toTypedArray()
                    )
                )
            )
            .placeholder(R.drawable.placeholder_logo)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:packagingCode")
    fun setPackagingCode(textView: TextView, code: String?) {
        code?.let {
            textView.text = when (it) {
                "02" -> {
                    "매장 전용"
                }
                "03" -> {
                    "포장 전용"
                }
                else -> {
                    "매장,포장"
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bind:highlightType")
    fun setHighlightType(textView: TextView, code: String?) {
        textView.apply {
            code?.let {
                when (it) {
                    "01" -> {
                        text = "신규"
                        setTextColor(Color.parseColor("#FFD59E00"))
                        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFAEFCD"))
                        visibility = View.VISIBLE
                    }
                    "02" -> {
                        text = "추천"
                        setTextColor(Color.parseColor("#FF9E01DC"))
                        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFEDCDFA"))
                        visibility = View.VISIBLE
                    }
                    "03" -> {
                        text = "인기"
                        setTextColor(Color.parseColor("#FFDB3A00"))
                        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFAD9CD"))
                        visibility = View.VISIBLE
                    }
                    "04" -> {
                        text = "행사"
                        setTextColor(Color.parseColor("#FF21DB00"))
                        backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFD4FACD"))
                        visibility = View.VISIBLE
                    }
                    else -> {
                        visibility = View.GONE
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bind:weekDayIn")
    fun weekDayIn(checkBox: CheckBox, weekDaysText: String?) {
        weekDaysText?.split(",")?.takeIf { it.isNotEmpty() }?.let { weekDays ->
            checkBox.isChecked = weekDays.any { it == checkBox.text.toString() }
        } ?: kotlin.run { checkBox.isChecked = false }
    }

    @JvmStatic
    @BindingAdapter("bind:timeFormat")
    fun setInputFormat(editText: EditText, format: String?) {
        when (format) {
            "hour" -> {
                editText.addTextChangedListener(HourTextWatcher(editText))
            }
            "minute" -> {
                editText.addTextChangedListener(MinuteTextWatcher(editText))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bind:businessNumberFormat")
    fun setBusinessNumberFormat(editText: EditText, ignored: Any?) {
        editText.addTextChangedListener(BusinessNumberTextWatcher(editText))
    }

    @JvmStatic
    @BindingAdapter("bind:rating")
    fun setRating(imageView: ImageView, rating: Int?) {
        when(rating) {
            1 -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_star_1))
            2 -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_star_2))
            3 -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_star_3))
            4 -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_star_4))
            5 -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_star_5))
        }
    }

    @JvmStatic
    @BindingAdapter("bind:socialType")
    fun setSocialType(imageView: ImageView, socialTypeName: String?) {
        if(socialTypeName == null) {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.placeholder_logo))
            return
        }

        when(SocialType.of(socialTypeName)) {
            SocialType.KAKAO -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_kakao))
            SocialType.GOOGLE -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_google))
            SocialType.FACEBOOK -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_facebook))
            SocialType.NAVER -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_naver))
            else -> imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.placeholder_logo))
        }
    }

    @JvmStatic
    @BindingAdapter("bind:fullAddress")
    fun setFullAddress(textView :TextView, fullAddress: String?) {
        textView.text = fullAddress?.split("|")?.joinToString(" ")?.ifBlank { "주소가 등록되지 않음" } ?: "주소가 등록되지 않음"
        textView.setTextColor(ContextCompat.getColor(textView.context, if(fullAddress?.replace("|", "").isNullOrBlank()) R.color.rect_4480 else R.color.rect_4474))
    }
}