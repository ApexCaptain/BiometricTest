package com.gmail.ayteneve93.apex.biometrictest.authentication.biometric

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.gmail.ayteneve93.apex.biometrictest.R

/**
 * 지문인식, 홍채인식 등 안드로이드 기기 사용자의 생체 정보를 바탕으로 인증을 수행하는 모듈입니다.
 * androidx.biometric.BiometricPrompt 를 기반으로 하여 작성되었으며 현재 해당 라이브러리에서
 * 지문인식 외 홍채나 안면인식 등의 기능을 아직 제공하지 않으므로, 지문인식만 구현되어있습니다.
 *
 * @author SangHun Lee
 * @see androidx.biometric.BiometricPrompt
 */
class BiometricManager constructor(
    private val mBiometricRequestResultCallbackMap: BiometricRequestResultCallbackMap,
    private val mBiometricRequestResultListenerMap: BiometricRequestResultListenerMap){

    private var mIncrementalRequestCode : Int = 0

    /**
     * BiometricActivity 를 거쳐 지문 인식을 요청합니다. 별도의 Callback 클래스 인스턴스를 통해
     * 결과값을 호출자에게 반환합니다.
     *
     * @param sourceActivity     : 인증을 요청한 액티비티 인스턴스입니다.
     * @param title              : 인증 Prompt 의 타이틀입니다. string.xml 에서 기본값을 사용합니다.
     * @param description        : 인증 Prompt 의 설명입니다. string.xml 에서 기본값을 사용합니다.
     * @param subtitle           : 인증 Prompt 의 서브 타이틀입니다. string.xml 에서 기본값을 사용합니다.
     * @param negativeButtonText : 인증 Prompt 의 취소 버튼 텍스트입니다. string.xml 에서 기본값을 사용합니다.
     * @return biometricRequestResultCallback
     */
    fun requestFingerprintAuthentication(
        sourceActivity     : AppCompatActivity,
        title              : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_title),
        description        : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_description),
        subtitle           : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_subtitle),
        negativeButtonText : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_negativeButtonText)) : BiometricRequestResultCallback {

        val biometricRequestResultCallback = BiometricRequestResultCallback()
        requestBiometricAuthenticationRequest(
            sourceActivity,
            BiometricPreference.RequestIntent.IntentAction.FINGERPRINT,
            title, description, subtitle, negativeButtonText,
            biometricRequestResultCallback)
        return biometricRequestResultCallback
    }

    @JvmOverloads
    fun requestFingerprintAuthentication(
        sourceActivity: AppCompatActivity,
        title              : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_title),
        description        : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_description),
        subtitle           : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_subtitle),
        negativeButtonText : String = sourceActivity.getString(R.string.txt_dft_biometric_fingerprint_negativeButtonText),
        onRequestResult    : BiometricRequestResultListener) {

        requestBiometricAuthenticationRequest(
            sourceActivity,
            BiometricPreference.RequestIntent.IntentAction.FINGERPRINT,
            title, description, subtitle, negativeButtonText,
            onRequestResult
        )
    }

    @Deprecated("홍채 인식 - Biometric 모듈에서 아직 지원되지 않음")
    fun requestIrisAuthentication(
        sourceActivity     : AppCompatActivity,
        title              : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_title),
        description        : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_description),
        subtitle           : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_subtitle),
        negativeButtonText : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_negativeButtonText)) : BiometricRequestResultCallback {

        val biometricRequestResultCallback = BiometricRequestResultCallback()
        requestBiometricAuthenticationRequest(sourceActivity, BiometricPreference.RequestIntent.IntentAction.IRIS, title, description, subtitle, negativeButtonText, biometricRequestResultCallback)
        return biometricRequestResultCallback
    }

    @JvmOverloads
    fun requestIrisAuthentication(
        sourceActivity: AppCompatActivity,
        title              : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_title),
        description        : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_description),
        subtitle           : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_subtitle),
        negativeButtonText : String = sourceActivity.getString(R.string.txt_dft_biometric_iris_negativeButtonText),
        onRequestResult    : BiometricRequestResultListener) {

        requestBiometricAuthenticationRequest(
            sourceActivity,
            BiometricPreference.RequestIntent.IntentAction.IRIS,
            title, description, subtitle, negativeButtonText,
            onRequestResult
        )
    }

    @Deprecated("안면 인식 - Biometric 모듈에서 아직 지원되지 않음")
    fun requestFacialRecognitionAuthentication(
        sourceActivity     : AppCompatActivity,
        title              : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_title),
        description        : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_description),
        subtitle           : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_subtitle),
        negativeButtonText : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_negativeButtonText)) : BiometricRequestResultCallback {

        val biometricRequestResultCallback = BiometricRequestResultCallback()
        requestBiometricAuthenticationRequest(sourceActivity, BiometricPreference.RequestIntent.IntentAction.FACIAL_RECOGNITION, title, description, subtitle, negativeButtonText, biometricRequestResultCallback)
        return biometricRequestResultCallback
    }

    @JvmOverloads
    fun requestFacialRecognitionAuthentication(
        sourceActivity: AppCompatActivity,
        title              : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_title),
        description        : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_description),
        subtitle           : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_subtitle),
        negativeButtonText : String = sourceActivity.getString(R.string.txt_dft_biometric_facial_recognition_negativeButtonText),
        onRequestResult    : BiometricRequestResultListener) {

        requestBiometricAuthenticationRequest(
            sourceActivity,
            BiometricPreference.RequestIntent.IntentAction.FACIAL_RECOGNITION,
            title, description, subtitle, negativeButtonText,
            onRequestResult
        )
    }

    /**
     * 생체 인증 요청을 취합하여 BiometricActivity 를 호출하는 메소드입니다.
     * 데이터는 Intent 를 거쳐서 전송됩니다.
     *
     * @param sourceActivity     : 인증을 요청한 액티비티 인스턴스입니다.
     * @param intentAction       : 생체 요청의 종류입니다. BiometricAttributes 에 enum 으로 정의되어 있습니다.
     * @param title              : 인증 요청 Dialog 의 타이틀입니다.
     * @param description        : 인증 요청 Dialog 의 설명입니다.
     * @param subtitle           : 인증 요청 Dialog 의 서브 타이틀입니다.
     * @param negativeButtonText : 인증 요청 Dialog 의 취소 버튼 텍스트입니다.
     */
    @Synchronized
    private fun requestBiometricAuthenticationRequest(
        sourceActivity     : AppCompatActivity,
        intentAction       : BiometricPreference.RequestIntent.IntentAction,
        title              : String,
        description        : String,
        subtitle           : String,
        negativeButtonText : String,
        onRequestResult    : BiometricRequestResultCallback) {

        val biometricRequestIntent = Intent(sourceActivity, BiometricActivity::class.java)
            .putExtra(BiometricPreference.RequestIntent.INTENT_ACTION, intentAction)
            .putExtra(BiometricPreference.RequestIntent.INTENT_CODE, mIncrementalRequestCode)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.TITLE, title)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.DESCRIPTION, description)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.SUBTITLE, subtitle)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.NEGATIVE_BUTTON_TEXT, negativeButtonText)

        mBiometricRequestResultCallbackMap[mIncrementalRequestCode++] = onRequestResult
        sourceActivity.startActivity(biometricRequestIntent)
    }

    @Synchronized
    private fun requestBiometricAuthenticationRequest(
        sourceActivity     : AppCompatActivity,
        intentAction       : BiometricPreference.RequestIntent.IntentAction,
        title              : String,
        description        : String,
        subtitle           : String,
        negativeButtonText : String,
        onRequestResult    : BiometricRequestResultListener) {

        val biometricRequestIntent = Intent(sourceActivity, BiometricActivity::class.java)
            .putExtra(BiometricPreference.RequestIntent.INTENT_ACTION, intentAction)
            .putExtra(BiometricPreference.RequestIntent.INTENT_CODE, mIncrementalRequestCode)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.TITLE, title)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.DESCRIPTION, description)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.SUBTITLE, subtitle)
            .putExtra(BiometricPreference.RequestIntent.Fingerprint.NEGATIVE_BUTTON_TEXT, negativeButtonText)

        mBiometricRequestResultListenerMap[mIncrementalRequestCode++] = onRequestResult
        sourceActivity.startActivity(biometricRequestIntent)
    }

    /**
     * 생체 인증의 결과를 Callback 혹은 Listener 로 처리합니다.
     *
     * @param biometricRequestResult : 요청 결과입니다. 결과는 지원하지 않음, 에러발생, 성공, 실패 총 4가지입니다.
     * @param requestIntentCode      : 요청 결과와 Callback 을 매핑해주는 키 코드입니다.
     * @param resultInfo             : 요청 성공시에만 받아옵니다. BiometricPrompt 에서 제공받습니다.
     * @param errorCode              : 에러 발생시에만 받아옵니다. 에러 코드입니다.
     * @param errString              : 에러 발생시에만 받아옵니다. 에러 내용입니다.
     */
    @Synchronized
    fun onBiometricAuthenticationRequestResult(
        biometricRequestResult : BiometricRequestResult,
        requestIntentCode : Int,
        resultInfo: BiometricPrompt.AuthenticationResult?,
        errorCode: Int?, errString: CharSequence?) {

        mBiometricRequestResultCallbackMap.remove(requestIntentCode)?.let {
            when(biometricRequestResult) {
                BiometricRequestResult.UNSUPPORTED -> it.runOnUnsupported()
                BiometricRequestResult.ERROR -> it.runOnError(errorCode, errString)
                BiometricRequestResult.SUCCEED -> it.runOnSucceed(resultInfo)
                BiometricRequestResult.FAILURE -> it.runOnFailure()
            }
            return
        }
        mBiometricRequestResultListenerMap.remove(requestIntentCode)?.onBiometricRequestResult(biometricRequestResult, errorCode, errString.toString() )
    }


}