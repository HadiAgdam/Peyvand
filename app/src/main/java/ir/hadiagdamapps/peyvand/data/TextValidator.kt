package ir.hadiagdamapps.peyvand.data

import ir.hadiagdamapps.peyvand.data.Key.*
import ir.hadiagdamapps.peyvand.data.models.social_media.SocialMedia

object TextValidator {

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

        if (!text.startsWith(Constants.TARGET_URL))
            return false

        val args = text.removeRange(0, Constants.TARGET_URL.length + 1).split("&")

        val headers = arrayListOf(NAME, PICTURE, TEL, BIO, PUBLIC_KEY, SOCIAL_MEDIA)

        args.forEach { arg ->
            arg.split("=")[0].let { Key.fromString(it) }?.apply {

                if (!headers.contains(this)) return false

                headers.remove(this)
            } ?: return false
        }

        return headers.size == 0
    }

    fun isValidSocialMedia(socialMedia: SocialMedia, text: String): Boolean =
        when (socialMedia) {

            SocialMedia.INSTAGRAM ->
                Regex("^[a-zA-Z0-9._]{1,30}\$").matches(text)


            SocialMedia.WHATSAPP ->
                Regex("^[a-zA-Z0-9._]{5,20}\$").matches(text)


            SocialMedia.TELEGRAM ->
                Regex("^[a-zA-Z][a-zA-Z0-9_]{4,31}\$").matches(text)

        }


}