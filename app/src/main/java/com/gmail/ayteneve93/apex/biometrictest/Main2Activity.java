package com.gmail.ayteneve93.apex.biometrictest;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.gmail.ayteneve93.apex.MyApplication;
import com.gmail.ayteneve93.apex.biometrictest.authentication.biometric.BiometricManager;

import javax.inject.Inject;

public class Main2Activity extends AppCompatActivity {

    @Inject BiometricManager mBiometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ((MyApplication)getApplication()).getBiometricManagerComponent().inject(this);

        mBiometricManager.requestFingerprintAuthentication(this, (biometricRequestResult, errorCode, errString) -> {
            switch (biometricRequestResult) {
                case UNSUPPORTED:
                    // 아직 지원되지 않는 인증 메소드의 경우
                    Toast.makeText(this, R.string.txt_fingerprint_authentication_request_unsupported, Toast.LENGTH_LONG).show();
                    break;
                case ERROR :
                    Toast.makeText(this, getString(R.string.txt_fingerprint_authentication_request_error, errorCode, errString), Toast.LENGTH_LONG).show();
                    break;
                case SUCCEED:
                    Toast.makeText(this, R.string.txt_fingerprint_authentication_request_succeed, Toast.LENGTH_LONG).show();
                    break;
                case FAILURE:
                    Toast.makeText(this, R.string.txt_fingerprint_authentication_request_failure, Toast.LENGTH_LONG).show();
                    break;
            }
            finish();
        });

    }
}
