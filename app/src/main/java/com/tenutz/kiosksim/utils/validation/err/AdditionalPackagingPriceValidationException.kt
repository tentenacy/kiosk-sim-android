package com.tenutz.kiosksim.utils.validation.err

import com.tenutz.kiosksim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.kiosksim.utils.validation.err.base.ValidationException

class AdditionalPackagingPriceValidationException: ValidationException(ValidationErrorCode.INVALID_ADDITIONAL_PACKAGING_PRICE)