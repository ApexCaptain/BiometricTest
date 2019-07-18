package com.gmail.ayteneve93.apex.biometrictest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gmail.ayteneve93.apex.MyApplication
import com.gmail.ayteneve93.apex.biometrictest.authentication.biometric.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mBiometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).biometricManagerComponent.inject(this)
        finger_print_request_button.setOnClickListener {
            mBiometricManager.requestIrisAuthentication(this) // 홍채인식 미지원으로 Deprecate 처리
            mBiometricManager.requestFingerprintAuthentication(this)
                .onUnsupported {
                    // 아직 지원되지 않는 인증 메소드의 경우
                    Toast.makeText(this, R.string.txt_fingerprint_authentication_request_unsupported, Toast.LENGTH_LONG).show()
                }
                .onError {
                    // 인증 도중 에러가 발생한 경우
                    errorCode, errString ->
                    Toast.makeText(this, getString(R.string.txt_fingerprint_authentication_request_error, errorCode, errString), Toast.LENGTH_LONG).show()
                }
                .onSucceed {
                    // 인증에 성공한 경우
                    Toast.makeText(this, R.string.txt_fingerprint_authentication_request_succeed, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Main2Activity::class.java)) // 성공시 다음 Activity 로 화면 전환
                }
                .onFailure {
                    // 인증에 실패한 경우
                    Toast.makeText(this, R.string.txt_fingerprint_authentication_request_failure, Toast.LENGTH_LONG).show()
                }
        }

    }
}



