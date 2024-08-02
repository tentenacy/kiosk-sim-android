package com.tenutz.kiosksim.utils.validation.err

import com.tenutz.kiosksim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.kiosksim.utils.validation.err.base.ValidationException

class RequiredInputValidationException: ValidationException(ValidationErrorCode.INPUT_REQUIRED)