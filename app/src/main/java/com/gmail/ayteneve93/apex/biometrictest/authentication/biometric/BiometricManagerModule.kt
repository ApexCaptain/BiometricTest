package com.gmail.ayteneve93.apex.biometrictest.authentication.biometric

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 의존성 주입을 위한 모듈입니다.
 */
@Module
class BiometricManagerModule {
    @Provides
    @Singleton
    fun provideBiometricManager() : BiometricManager {
        return BiometricManager(HashMap(), HashMap())
    }
}