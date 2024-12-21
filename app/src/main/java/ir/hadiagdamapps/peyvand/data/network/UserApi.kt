package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import ir.hadiagdamapps.peyvand.data.models.KeySet
import ir.hadiagdamapps.peyvand.data.models.PrivateKey
import ir.hadiagdamapps.peyvand.data.models.PublicKey
import ir.hadiagdamapps.peyvand.tools.Profile
import org.json.JSONObject

class UserApi(private val queue: RequestQueue) : Api() {

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

                put("name", profile.name)
                profile.picture?.urlString?.let { put("picture", it) }
                put("bio", profile.bio.toString())

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

    }

}