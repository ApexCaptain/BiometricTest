package com.gmail.ayteneve93.apex.biometrictest.authentication.biometric

import com.gmail.ayteneve93.apex.biometrictest.Main2Activity
import com.gmail.ayteneve93.apex.biometrictest.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * 의존성 주입을 위한 컴퍼넌트 인터페이스입니다.
 */
@Singleton
@Component(modules = [BiometricManagerModule::class])
interface BiometricManagerComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(main2Activity: Main2Activity)
    fun inject(biometricActivity: BiometricActivity)
}