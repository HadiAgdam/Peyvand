package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import ir.hadiagdamapps.peyvand.data.Key
import ir.hadiagdamapps.peyvand.data.models.contact.ContactUpdate
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.models.key.PrivateKey
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.data.models.profile.Profile
import org.json.JSONObject
import ir.hadiagdamapps.peyvand.data.Key.*
import org.json.JSONArray

class UserApi(private val queue: RequestQueue) : Api() {


    private fun JSONObject.put(key: Key, value: Any?) = put(key.name, value)

    private fun JSONObject.getString(key: Key): String = getString(key.name)


    companion object {
        private const val BASE_URL = "${Api.BASE_URL}/users"
    }

    fun register(
        profile: Profile,
        success: (KeySet) -> Unit,
        failed: (ApiError?) -> Unit
    ) {
        queue.add(
            JsonObjectRequest(Method.POST, BASE_URL, JSONObject().apply {

                put(PUBLIC_KEY, profile.name.toString())
                profile.picture?.urlString?.let { put(PRIVATE_KEY, it) }
                put(BIO, profile.bio.toString())

            }, {

                success(
                    KeySet(
                        public = PublicKey.parse(it.getString("public_key"))!!,
                        private = PrivateKey.parse(it.getString("private_key"))!!
                    )
                )

            }, { failed(it.toApiError()) })
        )

    }


    fun update(
        login: KeySet,
        name: Name? = null,
        pictureUrl: String? = null,
        bio: Bio? = null,
        failed: (ApiError?) -> Unit = {},
        success: () -> Unit
    ) {
        queue.add(
            JsonObjectRequest(Method.PUT, "$BASE_URL/users/${login.public}", JSONObject().apply {
                put(NAME, name?.toString())
                put(PICTURE, pictureUrl)
                put(BIO, bio.toString())
            }, {
                success()
            }, { failed(it.toApiError()) })
        )
    }

    fun getContactByPublicKey(
        login: KeySet,
        contactPublicKey: PublicKey,
        success: (ContactUpdate) -> Unit,
        failed: (ApiError?) -> Unit
    ) {
        queue.add(
            JsonObjectRequest(Method.GET, "$BASE_URL/$contactPublicKey", JSONObject().apply {
                put(PUBLIC_KEY, login.public.toString())
                put(PRIVATE_KEY, login.private.toString())
            }, {

                try {

                    success(
                        ContactUpdate(
                            publicKey = contactPublicKey,
                            name = Name.parse(it.getString(NAME))!!,
                            pictureUrl = it.getString(PICTURE),
                            bio = Bio.parse(it.getString(BIO))!!
                        )
                    )


                } catch (ex: Exception) {
                    failed(ApiError.JSON_CONVERT_ERROR)
                }

            }, { failed(it.toApiError()) })
        )
    }

    fun updateContacts(
        login: KeySet,
        contacts: List<PublicKey>,
        success: (List<ContactUpdate>) -> Unit,
        failed: (ApiError?) -> Unit
    ) {
        queue.add(
            JsonObjectRequest(Method.GET, "$BASE_URL/users", JSONObject().apply {
                put(PUBLIC_KEY, login.public.toString())
                put(PRIVATE_KEY, login.private.toString())
                put("contacts", JSONArray().apply {
                    for (c in contacts)
                        this.put(c.toString())
                })
            }, {
                try {

                    val fetchedContacts = it.getJSONArray("contacts")
                    success(ArrayList<ContactUpdate>().apply {

                        for (i in 0 until fetchedContacts.length())
                            fetchedContacts.getJSONObject(i).apply {
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

            }, { failed(it.toApiError()) })
        )
    }

}