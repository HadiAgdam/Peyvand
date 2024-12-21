package ir.hadiagdamapps.peyvand.data.models.profile

import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Constants
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.ProfileHelper
import ir.hadiagdamapps.peyvand.tools.Tel
import java.net.URLEncoder

data class Profile(
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio
) {
    fun toQrString(profileHelper: ProfileHelper): String {
        val nameEncoded = URLEncoder.encode(name.toString(), "UTF-8")
        val pictureEncoded = URLEncoder.encode(profileHelper.getPictureUrl(), "UTF-8")
        val telEncoded = URLEncoder.encode(tel.toString(), "UTF-8")
        val bioEncoded = URLEncoder.encode(bio.toString(), "UTF-8")

        val result =
            "${Constants.TARGET_SERVER}?name=$nameEncoded&picture=$pictureEncoded&tel=$telEncoded&bio=$bioEncoded"

        return result
    }
}