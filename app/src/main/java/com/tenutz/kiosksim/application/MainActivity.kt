package com.tenutz.kiosksim.application

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tenutz.kiosksim.core.result.ActivityResultFactory
import com.tenutz.kiosksim.databinding.ActivityMainBinding
import com.tenutz.kiosksim.utils.MyToast
import com.tenutz.kiosksim.utils.ext.dismissAllDialogs
import com.tenutz.kiosksim.utils.ext.navigateToLoginFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    lateinit var binding: ActivityMainBinding

    private val compositeDisposable = CompositeDisposable()

    val vm: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    GlobalViewModel.EVENT_GLOBAL_NAVIGATE_TO_LOGIN -> {
                        /*
                        if (currentDestinationId() != R.id.loginFragment) {
                            val navController = findNavController(R.id.container)
                            val navHostFragment: NavHostFragment =
                                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
                            val inflater = navHostFragment.navController.navInflater
                            val graph = inflater.inflate(R.navigation.navigation_main)
                            graph.startDestination = R.id.loginFragment

                            navController.graph = graph
                        }
*/
                        val refreshTokenExpired = it.second as Boolean

                        if (refreshTokenExpired) {
                            MyToast.create(this, "장시간 이용하지 않아 자동 로그아웃 되었습니다.", 200)?.show()
                            Single.timer(1, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { _ ->
                                    supportFragmentManager.dismissAllDialogs()
                                    navigateToLoginFragment()
                                }.addTo(compositeDisposable)
                        } else {
                            navigateToLoginFragment()
                        }
                    }
                }
            }
        }
    }
}