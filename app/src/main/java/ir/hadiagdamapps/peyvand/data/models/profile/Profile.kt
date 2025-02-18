package ir.hadiagdamapps.peyvand.data.models.profile

import ir.hadiagdamapps.peyvand.data.models.social_media.LinkedSocialMedias
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel

data class Profile(
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio,
    var linkedSocialMedias: LinkedSocialMedias? = null
) {
    fun toSyncProfile(): SyncProfile =
        SyncProfile(
            name = name,
            picture = picture?.urlString,
            bio = bio
        )

}