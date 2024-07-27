package ir.hadiagdamapps.peyvand.tools

import ir.hadiagdamapps.peyvand.tools.Constants.Companion.TARGET_SERVER

class TextValidator {

    companion object {
        fun validateName(text: String): String? {
//            TODO("return null if it cannot be validated")
            return text
        }

        fun validateTel(text: String): String? {
//            TODO("return null if it cannot be validated")
            return text
        }

        fun validateBio(text: String): String? {
//            TODO("return null if it cannot be validated")
            return text
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

            return false
        }

    }
}