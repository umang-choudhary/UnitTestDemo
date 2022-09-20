package com.example.unittestdemo.utils

import com.example.unittestdemo.R
import com.example.unittestdemo.model.ValidationError
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationManagerTest {

    /* Test cases for Name validation */
    @Test
    fun `When Name is Valid`() {
        val name = "Umang"
        val result = validateUserName(name)
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `When Name is Blank`() {
        val name = ""
        val result = validateUserName(name)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_empty_user_name_error))
    }

    @Test
    fun `When Name is Invalid`() {
        val name = "Nek"
        val result = validateUserName(name)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_invalid_user_name_error))
    }

    /* Test cases for Email validation */
    @Test
    fun `When Email is Valid`() {
        val email = "umang.ch@gmail.com"
        val result = validateEmail(email)
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `When Email is Blank`() {
        val email = "  "
        val result = validateEmail(email)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_empty_email_error))
    }

    @Test
    fun `When Email does not contain name`() {
        val email = "@gmail.com"
        val result = validateEmail(email)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_invalid_email_error))
    }

    @Test
    fun `When Email does not contain domain`() {
        val email = "umang.ch@"
        val result = validateEmail(email)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_invalid_email_error))
    }

    @Test
    fun `When Email does not contain at symbol`() {
        val email = "umang.chgmail.com"
        val result = validateEmail(email)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_invalid_email_error))
    }

    /* Test cases for Pin Code validation */
    @Test
    fun `When Pin Code is Valid`() {
        val pin = "201003"
        val result = validatePinCode(pin)
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `When Pin Code is Blank`() {
        val pin = "  "
        val result = validatePinCode(pin)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_empty_pin_code_error))
    }

    @Test
    fun `When Pin Code is Small`() {
        val pin = "20100"
        val result = validatePinCode(pin)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_invalid_pin_code_error))
    }

    @Test
    fun `When Pin Code is Large`() {
        val pin = "2010034"
        val result = validatePinCode(pin)
        assertThat(result).isEqualTo(ValidationError(R.string.msg_invalid_pin_code_error))
    }

}