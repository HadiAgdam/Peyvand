package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.RequestQueue
import ir.hadiagdamapps.peyvand.data.Key.*
import ir.hadiagdamapps.peyvand.data.getString
import ir.hadiagdamapps.peyvand.data.getNullString
import ir.hadiagdamapps.peyvand.data.models.contact.ContactUpdate
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.data.models.social_media.LinkedSocialMedias
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import org.json.JSONArray
import org.json.JSONObject

class ContactApi(queue: RequestQueue) : Api(queue) {

    companion object {
        private const val BASE_URL = "${Api.BASE_URL}/users"
    }


    fun getContactsByPublicKey(
        login: KeySet?,
        contacts: List<PublicKey>,
        success: (List<ContactUpdate>) -> Unit,
        failed: (ApiError?) -> Unit
    ) {
        newRequest(
            url = "$BASE_URL/get",
            method = Method.POST,
            json = JSONObject().apply {
                put("contacts", JSONArray().apply {
                    for (c in contacts) this.put(c.toString())
                })
            },
            onSuccess = { response ->
                try {
                    val fetchedContacts = response.getJSONArray("contacts")
                    success(ArrayList<ContactUpdate>().apply {

                        for (i in 0 until fetchedContacts.length()) fetchedContacts.getJSONObject(i)
                            .apply {
                                add(
                                    ContactUpdate(
                                        publicKey = PublicKey.parse(getString(PUBLIC_KEY))!!,
                                        name = Name.parse(getString(NAME))!!,
                                        pictureUrl = getString(PICTURE).takeIf { it != "null" },
                                        bio = Bio.parse(getString(BIO))!!,
                                        socialMedias = LinkedSocialMedias.fromJson(getNullString(SOCIAL_MEDIA)?.let { JSONObject(it) })
                                    )
                                )
                            }
                    })

                } catch (ex: Exception) {
                    failed(ApiError.JSON_CONVERT_ERROR)
                }
            },
            onFailed = failed,
        )
    }
}