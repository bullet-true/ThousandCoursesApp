package ru.ifedorov.auth.validator

internal object LoginInputValidator {

    fun isLoginInputValid(email: CharSequence, password: CharSequence): Boolean =
        isEmailValid(email = email) && password.isNotBlank()

    fun isEmailValid(email: CharSequence): Boolean {
        val atIndex = email.indexOf('@')
        val dotIndex = email.lastIndexOf('.')

        return atIndex > 0 &&
            email.count { char -> char == '@' } == 1 &&
            dotIndex > atIndex + 1 &&
            dotIndex < email.lastIndex &&
            !email.hasCyrillicLetters() &&
            email.none { char -> char.isWhitespace() }
    }

    fun containsCyrillic(input: CharSequence): Boolean = input.hasCyrillicLetters()

    private fun CharSequence.hasCyrillicLetters(): Boolean = any { char ->
        char in 'А'..'я' || char == 'Ё' || char == 'ё'
    }
}
