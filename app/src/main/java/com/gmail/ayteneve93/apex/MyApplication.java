package com.gmail.ayteneve93.apex;

import android.app.Application;
import com.gmail.ayteneve93.apex.biometrictest.authentication.biometric.BiometricManagerComponent;
import com.gmail.ayteneve93.apex.biometrictest.authentication.biometric.BiometricManagerModule;
import com.gmail.ayteneve93.apex.biometrictest.authentication.biometric.DaggerBiometricManagerComponent;

public class MyApplication extends Application {

    private BiometricManagerComponent biometricManagerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        biometricManagerComponent = DaggerBiometricManagerComponent.builder().biometricManagerModule(new BiometricManagerModule()).build();
    }

    public BiometricManagerComponent getBiometricManagerComponent() {
        return biometricManagerComponent;
    }
}
