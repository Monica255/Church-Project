package com.example.churchproject.core.injection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CustomBaseUrl