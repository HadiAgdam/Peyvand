package ir.hadiagdamapps.peyvand.data.models.social_media

import ir.hadiagdamapps.peyvand.data.getStringOrNull
import org.json.JSONObject

data class LinkedSocialMedias(
//    val map: Map<SocialMedia, String> = EnumMap(SocialMedia::class.java),
    val instagram: String? = null,
    val telegram: String? = null,
    val twitter: String? = null,
    val whatsapp: String? = null
) {


    companion object {
        fun fromJson(json: JSONObject): LinkedSocialMedias {
            return LinkedSocialMedias(
                instagram = json.getStringOrNull(SocialMedia.INSTAGRAM.toString()),
                telegram = json.getStringOrNull(SocialMedia.TELEGRAM.toString()),
                whatsapp = json.getStringOrNull(SocialMedia.WHATSAPP.toString())
            )
        }
    }


    fun toJson(): JSONObject {
        return JSONObject().apply {
            instagram?.let { put(SocialMedia.INSTAGRAM.toString(), it) }
            telegram?.let { put(SocialMedia.TELEGRAM.toString(), it) }
            whatsapp?.let { put(SocialMedia.WHATSAPP.toString(), it) }
        }
    }

}


/*

    {
        "instagram": "hadiagdam0",
        "telegram": "HadiAqdam",
        "twitter" : "twitter",
        "whatsapp": "whatsapp"
    }

 */
