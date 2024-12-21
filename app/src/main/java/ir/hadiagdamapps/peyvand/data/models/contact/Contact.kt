package ir.hadiagdamapps.peyvand.data.models.contact

import android.os.Parcelable
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Constants
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel
import kotlinx.parcelize.Parcelize
import java.net.URLDecoder

@Parcelize
class Contact(
    val id: Int = -1,
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio,
) : Parcelable {


    companion object {

        private fun parseFromHashmap(map: HashMap<String, String>): Contact? {
            return Contact(
                -1,
                Name.parse(map["name"] ?: return null) ?: return null,
                Picture.parse(map["picture"] ?: ""),
                Tel.parse(map["tel"]) ?: return null,
                Bio.parse(map["bio"]) ?: return null
            )
        }

        fun parseFromURL(text: String): Contact? {
            val result = HashMap<String, String>()

            for (i in text.removeRange(0, Constants.TARGET_SERVER.length + 1).split("&"))
                result[URLDecoder.decode(i.split("=")[0], "UTF-8")] =
                    URLDecoder.decode(i.split("=")[1], "UTF-8")

            return parseFromHashmap(result)
        }
    }

}