package com.tenutz.kiosksim.utils.validation.err

import com.tenutz.kiosksim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.kiosksim.utils.validation.err.base.ValidationException

class CategoryNameValidationException: ValidationException(ValidationErrorCode.INVALID_CATEGORY_NAME)