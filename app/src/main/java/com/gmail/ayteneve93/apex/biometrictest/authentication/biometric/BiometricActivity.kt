package com.gmail.ayteneve93.apex.biometrictest.authentication.biometric

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.gmail.ayteneve93.apex.MyApplication
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * xml UI 가 따로 존재하지 않는 FragmentActivity 입니다.
 * BiometricManager 에 의해 간접적으로 받은 생체인증 요청을 처리하고
 * 바로 종료됩니다.
 */
class BiometricActivity : FragmentActivity() {

    @Inject
    lateinit var mBiometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).biometricManagerComponent.inject(this)
        when(intent.getSerializableExtra(BiometricPreference.RequestIntent.INTENT_ACTION) as BiometricPreference.RequestIntent.IntentAction) {
            BiometricPreference.RequestIntent.IntentAction.FINGERPRINT -> {
                // 지문 인식
                BiometricPrompt(this, object : Executor {
                    private val handler = Handler(Looper.getMainLooper())
                    override fun execute(runnable : Runnable) {
                        handler.post(runnable)
                    }
                }, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        this@BiometricActivity.senRequestResult(BiometricRequestResult.ERROR, null, errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(resultInfo: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(resultInfo)
                        this@BiometricActivity.senRequestResult(BiometricRequestResult.SUCCEED, resultInfo)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        this@BiometricActivity.senRequestResult(BiometricRequestResult.FAILURE)
                    }
                }).authenticate(
                    // BiometricPrompt 의 빌드 정보
                    BiometricPrompt.PromptInfo.Builder()
                        .setTitle(intent.getStringExtra(BiometricPreference.RequestIntent.Fingerprint.TITLE))
                        .setDescription(intent.getStringExtra(BiometricPreference.RequestIntent.Fingerprint.DESCRIPTION))
                        .setSubtitle(intent.getStringExtra(BiometricPreference.RequestIntent.Fingerprint.SUBTITLE))
                        .setNegativeButtonText(intent.getStringExtra(BiometricPreference.RequestIntent.Fingerprint.NEGATIVE_BUTTON_TEXT))
                        .build()
                )
            }
            BiometricPreference.RequestIntent.IntentAction.IRIS -> {
                // 홍채 인식 - Biometric 모듈에서 아직 지원되지 않음
                senRequestResult(BiometricRequestResult.UNSUPPORTED)
            }
            BiometricPreference.RequestIntent.IntentAction.FACIAL_RECOGNITION -> {
                // 안면 인식 - Biometric 모듈에서 아직 지원되지 않음
                senRequestResult(BiometricRequestResult.UNSUPPORTED)
            }
        }
    }

    /**
     * 인증 결과를 취합해 BiometricManager 로 전송합니다.
     * @param biometricRequestResult : 요청 결과입니다. 결과는 지원하지 않음, 에러발생, 성공, 실패 총 4가지입니다.
     * @param resultInfo             : 요청 성공시에만 받아옵니다. BiometricPrompt 에서 제공받습니다.
     * @param errorCode              : 에러 발생시에만 받아옵니다. 에러 코드입니다.
     * @param errString              : 에러 발생시에만 받아옵니다. 에러 내용입니다.
     */
    @Synchronized private fun senRequestResult(biometricRequestResult : BiometricRequestResult, resultInfo: BiometricPrompt.AuthenticationResult? = null, errorCode: Int? = null, errString: CharSequence? = null) {
        val requestIntentCode = intent.getIntExtra(BiometricPreference.RequestIntent.INTENT_CODE, BiometricPreference.RequestIntent.DEFAULT_INTENT_CODE)
        mBiometricManager.onBiometricAuthenticationRequestResult(biometricRequestResult, requestIntentCode, resultInfo, errorCode, errString)
        finish()
    }


}
