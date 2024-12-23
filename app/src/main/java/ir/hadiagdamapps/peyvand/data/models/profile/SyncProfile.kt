package ir.hadiagdamapps.peyvand.data.models.profile

import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name

data class SyncProfile(
    val name: Name,
    val picture: String?,
    val bio: Bio
)