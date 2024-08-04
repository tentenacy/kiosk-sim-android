package com.tenutz.kiosksim.utils.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.application.MainActivity

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

val MainActivity.navController get() = run { binding.container.getFragment<NavHostFragment>().navController }

fun Fragment.isOnBackStack(@IdRes id: Int): Boolean = try {
    findNavController().getBackStackEntry(id); true
} catch (e: Throwable) {
    false
}

fun MainActivity.navigateToLoginFragment() {
    if (currentDestinationId() == R.id.loginFragment) {
    } else {
//        currentFragment()?.findNavController()?.popBackStack(R.id.loginFragment, true)
//        currentFragment()?.findNavController()?.navigate(R.id.action_global_to_loginFragment)

        currentFragment()?.findNavController()?.run {
            navigate(
                R.id.navigation_main,
                null,
                NavOptions.Builder().setPopUpTo(R.id.loginFragment, false).build()
            )
        }
    }
}

fun MainActivity.navigateToMainFragment() {
    currentFragment()?.findNavController()?.run {
        navigate(
            R.id.action_global_mainFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.mainFragment, true)
                .setEnterAnim(0)
                .setExitAnim(0)
                .setPopEnterAnim(0)
                .setPopExitAnim(0)
                .build()
        )
    }
}