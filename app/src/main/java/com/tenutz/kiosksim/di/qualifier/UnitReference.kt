package com.tenutz.kiosksim.di.qualifier

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UnitReference(val value: Units)
