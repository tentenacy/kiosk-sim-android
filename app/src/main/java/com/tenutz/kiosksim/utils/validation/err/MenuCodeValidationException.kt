package com.tenutz.kiosksim.utils.validation.err

import com.tenutz.kiosksim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.kiosksim.utils.validation.err.base.ValidationException

class MenuCodeValidationException: ValidationException(ValidationErrorCode.INVALID_MENU_CODE)