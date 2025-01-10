package ir.hadiagdamapps.peyvand.data.models.contact

import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name


data class ContactUpdate(
    val publicKey: PublicKey,
    val name: Name,
    val pictureUrl: String?,
    val bio: Bio
)
