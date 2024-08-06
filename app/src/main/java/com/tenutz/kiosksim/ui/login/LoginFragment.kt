package com.tenutz.kiosksim.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.sharedpref.Settings
import com.tenutz.kiosksim.data.datasource.sharedpref.Token
import com.tenutz.kiosksim.data.datasource.sharedpref.User
import com.tenutz.kiosksim.databinding.FragmentLoginBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.login.handler.FacebookOAuthLoginHandler
import com.tenutz.kiosksim.ui.login.handler.GoogleOAuthLoginHandler
import com.tenutz.kiosksim.ui.login.handler.KakaoOAuthLoginHandler
import com.tenutz.kiosksim.ui.login.handler.NaverOAuthLoginCallback
import com.tenutz.kiosksim.utils.constant.SocialScopeConstant
import com.tenutz.kiosksim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding: FragmentLoginBinding get() = _binding!!


    private val vm by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    @Inject
    lateinit var naverOAuthLoginCallback: NaverOAuthLoginCallback

    @Inject
    lateinit var facebookOAuthLoginHandler: FacebookOAuthLoginHandler

    @Inject
    lateinit var kakaoOAuthLoginHandler: KakaoOAuthLoginHandler

    @Inject
    lateinit var googleOAuthLoginHandler: GoogleOAuthLoginHandler



    @Inject
    lateinit var googleCallbackManager: GoogleSignInClient

    @Inject
    lateinit var facebookCallbackManager: CallbackManager

    private val naverLoginOnClickListener: (View?) -> Unit = {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Logger.d("token: ${token}")
            User.fcmToken = token

            NaverIdLoginSDK.authenticate(mainActivity(), naverOAuthLoginCallback)
        })
    }

    private val kakaoLoginOnClickListener: (View?) -> Unit = {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Logger.d("token: ${token}")
            User.fcmToken = token

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(
                    requireContext(),
                    callback = kakaoOAuthLoginHandler
                )
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    requireContext(),
                    callback = kakaoOAuthLoginHandler
                )
            }
        })

    }

    private val googleLoginOnClickListener: (View?) -> Unit = {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Logger.d("token: ${token}")
            User.fcmToken = token

            mainActivity().activityResultFactory.launch(
                googleCallbackManager.signInIntent,
                googleOAuthLoginHandler
            )
        })
    }

    private val facebookLoginOnClickListener: (View?) -> Unit = {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Logger.d("token: ${token}")
            User.fcmToken = token

            LoginManager.getInstance().run {
                logInWithReadPermissions(
                    this@LoginFragment,
                    listOf(
                        SocialScopeConstant.FACEBOOK_SCOPE_EMAIL,
                        SocialScopeConstant.FACEBOOK_SCOPE_PUBLIC_PROFILE
                    )
                )
                registerCallback(facebookCallbackManager, facebookOAuthLoginHandler)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //자동 로그인
        if(Settings.autoLoggedIn && !Settings.autoEntered && Token.accessToken.isNotBlank()) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAccessFragment())
        }
        if(Settings.autoLoggedIn && Settings.autoEntered && Token.accessToken.isNotBlank()) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                when (it.first) {
                    LoginViewModel.EVENT_NAVIGATE_TO_ACCESS -> {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAccessFragment())
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageLoginKakao.setOnClickListener(kakaoLoginOnClickListener)
        binding.imageLoginGoogle.setOnClickListener(googleLoginOnClickListener)
        binding.imageLoginFacebook.setOnClickListener(facebookLoginOnClickListener)
        binding.imageLoginNaver.setOnClickListener(naverLoginOnClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}