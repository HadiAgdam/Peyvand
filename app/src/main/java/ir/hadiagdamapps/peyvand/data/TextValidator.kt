package ir.hadiagdamapps.peyvand.data

import ir.hadiagdamapps.peyvand.data.network.Api
import ir.hadiagdamapps.peyvand.data.Key.*

class TextValidator {

    companion object {
        fun validateName(text: String): String? {
            return text.ifBlank { null }

        }

        fun validateTel(text: String): String? {
            return if (text.isNotBlank() &&
                text.all { it.isDigit() } &&
                text.length == 11 &&
                text[0] == '0' &&
                text[1] == '9'
            ) text
            else null
        }

        fun validateBio(text: String): String? {
            return if (text.isNotBlank() && text.length <= 250) text
            else null
        }

        fun isValidQrString(text: String): Boolean {

            if (!text.startsWith(Api.BASE_URL))
                return false

            val args = text.removeRange(0, Api.BASE_URL.length + 1).split("&")

            val headers = arrayListOf(NAME, PICTURE, TEL, BIO, PUBLIC_KEY)

            args.forEach { arg ->
                arg.split("=")[0].let { Key.fromString(it) }?.apply {

                    if (!headers.contains(this)) return false

                    headers.remove(this)
                } ?: return false
            }

            return headers.size == 0
        }

    }
}