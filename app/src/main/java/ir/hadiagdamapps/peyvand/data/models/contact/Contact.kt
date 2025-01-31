package ir.hadiagdamapps.peyvand.data.models.contact

import android.os.Parcelable
import ir.hadiagdamapps.peyvand.data.Key
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel
import kotlinx.parcelize.Parcelize
import java.net.URI
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import ir.hadiagdamapps.peyvand.data.Key.*
import ir.hadiagdamapps.peyvand.data.models.social_media.LinkedSocialMedias
import org.json.JSONObject


@Parcelize
class Contact(
    val id: Int = -1,
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio,
    val publicKey: String? = null,
    val socialMedias: LinkedSocialMedias
) : Parcelable {


    companion object {

        private fun parseFromHashmap(map: Map<Key?, String>): Contact? {
            return Contact(
                id = -1,
                name = Name.parse(map[NAME] ?: return null) ?: return null,
                picture = Picture.parse(map[PICTURE] ?: ""),
                tel = Tel.parse(map[TEL]) ?: return null,
                bio = Bio.parse(map[BIO]) ?: return null,
                publicKey = map[PUBLIC_KEY],
                socialMedias = LinkedSocialMedias.fromJson(map[SOCIAL_MEDIA]?.let { JSONObject(it) })
            )
        }

        fun parseFromURL(url: String): Contact? =
            parseFromHashmap(URI(url).query.split("&").associate { param ->
                param.split("=").let {
                    Key.fromString(it[0]) to URLDecoder.decode(it[1], StandardCharsets.UTF_8.name())
                }
            })

    }

}