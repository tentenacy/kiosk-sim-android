package com.tenutz.kiosksim.utils.validation.err.base

open class ValidationException(val errorCode: ValidationErrorCode): RuntimeException()