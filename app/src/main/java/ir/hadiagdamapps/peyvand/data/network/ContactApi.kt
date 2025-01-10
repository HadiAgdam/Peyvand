package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.RequestQueue
import ir.hadiagdamapps.peyvand.data.Key.BIO
import ir.hadiagdamapps.peyvand.data.Key.NAME
import ir.hadiagdamapps.peyvand.data.Key.PICTURE
import ir.hadiagdamapps.peyvand.data.Key.PUBLIC_KEY
import ir.hadiagdamapps.peyvand.data.getString
import ir.hadiagdamapps.peyvand.data.models.contact.ContactUpdate
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
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
            url = BASE_URL,
            method = Method.GET,
            json = JSONObject().apply {
                put("contacts", JSONArray().apply {
                    for (c in contacts) this.put(c.toString())
                })
            },
            onSuccess = {
                try {
                    val fetchedContacts = it.getJSONArray("contacts")
                    success(ArrayList<ContactUpdate>().apply {

                        for (i in 0 until fetchedContacts.length()) fetchedContacts.getJSONObject(i)
                            .apply {
                                add(
                                    ContactUpdate(
                                        publicKey = PublicKey.parse(it.getString(PUBLIC_KEY))!!,
                                        name = Name.parse(it.getString(NAME))!!,
                                        pictureUrl = it.getString(PICTURE),
                                        bio = Bio.parse(it.getString(BIO))!!
                                    )
                                )
                            }
                    })

                } catch (ex: Exception) {
                    failed(ApiError.JSON_CONVERT_ERROR)
                }
            },
            onFailed = failed
        )

    }
}