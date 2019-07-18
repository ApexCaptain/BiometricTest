package com.gmail.ayteneve93.apex.biometrictest;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.gmail.ayteneve93.apex.MyApplication;
import com.gmail.ayteneve93.apex.biometrictest.authentication.biometric.BiometricManager;
import com.gmail.ayteneve93.apex.biometrictest.databinding.ActivityMain2Binding;

import javax.inject.Inject;

@SuppressWarnings("FieldCanBeLocal")
public class Main2Activity extends AppCompatActivity {

    @Inject BiometricManager mBiometricManager;
    private ActivityMain2Binding mBindingViews = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBindingViews = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        setContentView(R.layout.activity_main2);

        ((MyApplication)getApplication()).getBiometricManagerComponent().inject(this);
        
        mBindingViews.fingerPrintRequestButton2.setOnClickListener((v) ->
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
            })
        );

    }
}
