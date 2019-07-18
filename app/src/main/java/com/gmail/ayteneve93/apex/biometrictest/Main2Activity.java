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
                    Toast.makeText(this, "지원되지 않는 인증방식입니다.", Toast.LENGTH_LONG).show();
                    break;
                case ERROR :
                    Toast.makeText(this, "인증 도중 에러가 발생했습니다." +
                            "\n에러코드 : " + errorCode + ", 에러 메시지 : " + errString, Toast.LENGTH_LONG).show();
                    break;
                case SUCCEED:
                    Toast.makeText(this, "지문 인증에 성공하였습니다!", Toast.LENGTH_LONG).show();
                    break;
                case FAILURE:
                    Toast.makeText(this, "지문 인증에 실패하였습니다.", Toast.LENGTH_LONG).show();
                    break;
            }
            finish();
        });

    }
}
