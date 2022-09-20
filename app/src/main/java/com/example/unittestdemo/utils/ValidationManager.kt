@file:JvmName("ValidationManager")

package com.example.unittestdemo.utils

import com.example.unittestdemo.R
import com.example.unittestdemo.model.ValidationError

/**
 * Validate name.
 *
 * @param name The name
 * @return ValidationError
 */
fun validateUserName(name: String): ValidationError? {
    var validationError: ValidationError? = null

    if (name.isBlank()) {
        validationError = ValidationError(R.string.msg_empty_user_name_error)
    } else if (name.length < 4) {
        validationError = ValidationError(R.string.msg_invalid_user_name_error)
    }
    return validationError
}

const val EMAIL_PATTERN = "[a-zA-Z0-9+._%\\-]{1,256}" +
        "@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

/**
 * @param email The email
 * @return ValidationError
 */
fun validateEmail(email: String): ValidationError? {
    var validationError: ValidationError? = null

    if (email.isBlank()) {
        validationError = ValidationError(R.string.msg_empty_email_error)
    } else if (!email.matches(EMAIL_PATTERN.toRegex())) {
        validationError = ValidationError(R.string.msg_invalid_email_error)
    }
    return validationError
}

/**
 * Validate Pin Code.
 *
 * @param pinCode The pin code
 * @return ValidationError
 */
fun validatePinCode(pinCode: String): ValidationError? {
    var validationError: ValidationError? = null

    if (pinCode.isBlank()) {
        validationError = ValidationError(R.string.msg_empty_pin_code_error)
    } else if (pinCode.length < 6 || pinCode.length > 6) {
        validationError = ValidationError(R.string.msg_invalid_pin_code_error)
    }
    return validationError
}

