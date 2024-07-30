package ir.hadiagdamapps.peyvand.tools

import ir.hadiagdamapps.peyvand.tools.Constants.Companion.TARGET_SERVER

class TextValidator {

    companion object {
        fun validateName(text: String): String? {
            return if (text.isNotBlank() && text.all { it.isLetter() }) text
            else null

        }

        fun validateTel(text: String): String? {

            return if (text.isNotBlank() && text.all { it.isDigit() } && text.length == 11 && text[0] == '0') text
            else null
        }

        fun validateBio(text: String): String? {
            return if (text.isNotBlank() && text.length <= 250) text
            else null
        }

        fun isValidQrString(text: String): Boolean {

            if (!text.startsWith(TARGET_SERVER))
                return false

            val args = text.removeRange(0, TARGET_SERVER.length + 1).split("&")

            val headers = ArrayList("name picture tel bio".split(" "))
            for (a in args) {
                if (!headers.contains(a.split("=")[0])) return false
                headers.remove(a.split("=")[0])
            }
            if (headers.size != 0) return false

            return true
        }

    }
}