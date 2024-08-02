package com.tenutz.kiosksim.utils.validation.err

import com.tenutz.kiosksim.utils.validation.err.base.ValidationErrorCode
import com.tenutz.kiosksim.utils.validation.err.base.ValidationException

class OptionGroupNameValidationException: ValidationException(ValidationErrorCode.INVALID_OPTION_GROUP_NAME)