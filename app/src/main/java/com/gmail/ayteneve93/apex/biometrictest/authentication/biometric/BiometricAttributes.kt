package com.gmail.ayteneve93.apex.biometrictest.authentication.biometric

import androidx.biometric.BiometricPrompt

/**
 * 요청의 다양한 결과값을 처리할 수 있는 4가지 고차함수를 포함한 클래스입니다.
 */
class BiometricRequestResultCallback {
    private var onUnsupportedHandler : (() -> Unit)? = null
    fun onUnsupported(onUnsupportedHandler : () -> Unit) : BiometricRequestResultCallback {
        this.onUnsupportedHandler = onUnsupportedHandler
        return this
    }
    fun runOnUnsupported() { onUnsupportedHandler?.let { it() }}

    private var onSucceedHandler : ((result: BiometricPrompt.AuthenticationResult?) -> Unit)? = null
    fun onSucceed(onSucceedHandler : (result: BiometricPrompt.AuthenticationResult?) -> Unit) : BiometricRequestResultCallback{
        this.onSucceedHandler = onSucceedHandler
        return this
    }
    fun runOnSucceed(result: BiometricPrompt.AuthenticationResult?) { onSucceedHandler?.let { it(result) } }

    private var onErrorHandler : ((errorCode: Int?, errString: CharSequence?) -> Unit)? = null
    fun onError(onErrorHandler : (errorCode: Int?, errString: CharSequence?) -> Unit) : BiometricRequestResultCallback{
        this.onErrorHandler = onErrorHandler
        return this
    }
    fun runOnError(errorCode: Int?, errString: CharSequence?) { onErrorHandler?.let { it(errorCode, errString) } }

    private var onFailureHandler : (() -> Unit)? = null
    fun onFailure(onFailureHandler : () -> Unit) : BiometricRequestResultCallback{
        this.onFailureHandler = onFailureHandler
        return this
    }
    fun runOnFailure() { onFailureHandler?.let { it() } }
}

@FunctionalInterface
interface BiometricRequestResultListener {
    fun onBiometricRequestResult(
        biometricRequestResult : BiometricRequestResult,
        errorCode              : Int?,
        errString              : String?
    )
}
typealias BiometricRequestResultCallbackMap = HashMap<Int, BiometricRequestResultCallback>
typealias BiometricRequestResultListenerMap = HashMap<Int, BiometricRequestResultListener>

/**
 * 패키지 내에서 사용하는 각종 상수 및 속성 정보들입니다.
 */
enum class BiometricRequestResult { UNSUPPORTED, ERROR, SUCCEED, FAILURE }
object BiometricPreference {
    const val TAG : String = "Biometric"
    object RequestIntent {
        const val INTENT_ACTION : String = "BiometricPreference#RequestIntent#INTENT_ACTION"
        const val INTENT_CODE   : String = "BiometricPreference#RequestIntent#INTENT_CODE"
        const val DEFAULT_INTENT_CODE : Int = -1
        enum class IntentAction { FINGERPRINT, IRIS, FACIAL_RECOGNITION; }
        object Fingerprint {
            const val TITLE                : String = "BiometricPreference#RequestIntent#Fingerprint#TITLE"
            const val DESCRIPTION          : String = "BiometricPreference#RequestIntent#Fingerprint#DESCRIPTION"
            const val SUBTITLE             : String = "BiometricPreference#RequestIntent#Fingerprint#SUBTITLE"
            const val NEGATIVE_BUTTON_TEXT : String = "BiometricPreference#RequestIntent#Fingerprint#NEGATIVE_BUTTON_TEXT"
        }
    }
}