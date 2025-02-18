package ir.hadiagdamapps.peyvand.data.models.social_media

import android.os.Parcelable
import ir.hadiagdamapps.peyvand.data.getNullString
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class LinkedSocialMedias(
//    val map: Map<SocialMedia, String> = EnumMap(SocialMedia::class.java),
    var instagram: String? = null,
    var telegram: String? = null,
    var twitter: String? = null,
    var x: String? = null
) : Parcelable{


    companion object {
        fun fromJson(json: JSONObject?): LinkedSocialMedias {

            return LinkedSocialMedias(
                instagram = json?.getNullString(SocialMedia.INSTAGRAM.toString()),
                telegram = json?.getNullString(SocialMedia.TELEGRAM.toString()),
                x = json?.getNullString(SocialMedia.X.toString())
            )
        }
    }


    fun toJson(): JSONObject {
        return JSONObject().apply {
            instagram?.let { put(SocialMedia.INSTAGRAM.toString(), it) }
            telegram?.let { put(SocialMedia.TELEGRAM.toString(), it) }
            x?.let { put(SocialMedia.X.toString(), it) }
        }
    }


    fun set(socialMedia: SocialMedia, value: String) {
        value.takeIf { it != "" }.let {
            when (socialMedia) {

                SocialMedia.INSTAGRAM -> instagram = it
                SocialMedia.X -> x = it
                SocialMedia.TELEGRAM -> telegram = it

            }
        }
    }

    override fun toString(): String = toJson().toString()
}


/*

    {
        "instagram": "hadiagdam0",
        "telegram": "HadiAqdam",
        "twitter" : "twitter",
        "whatsapp": "whatsapp"
    }

 */
