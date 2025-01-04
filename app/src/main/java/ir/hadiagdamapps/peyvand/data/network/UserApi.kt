package ir.hadiagdamapps.peyvand.data.network

import android.util.Log
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.models.key.PrivateKey
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import org.json.JSONObject
import ir.hadiagdamapps.peyvand.data.Key.*
import ir.hadiagdamapps.peyvand.data.models.profile.SyncProfile
import ir.hadiagdamapps.peyvand.data.put

class UserApi(private val queue: RequestQueue) : Api() {

    companion object {
        private const val BASE_URL = "${Api.BASE_URL}/users"
    }

    fun register(
        profile: SyncProfile,
        success: (KeySet) -> Unit,
        failed: (ApiError?) -> Unit,
        finally: () -> Unit = {}
    ) {
        Log.e("UserApi register name", profile.name.toString())

        queue.add(
            JsonObjectRequest(Method.POST, BASE_URL, JSONObject().apply {

                put(NAME, profile.name.toString())
                profile.picture?.let { put(PICTURE, it) }
                put(BIO, profile.bio.toString())

            }, {

                success(
                    KeySet(
                        public = PublicKey.parse(it.getString("public_key"))!!,
                        private = PrivateKey.parse(it.getString("private_key"))!!
                    )
                )
                finally()
            }, {
                Log.e("UserApi register", it.toString())
                failed(it.toApiError());
                finally()
            })
        )

    }


    fun update(
        login: KeySet,
        name: Name? = null,
        pictureUrl: String? = null,
        bio: Bio? = null,
        failed: (ApiError?) -> Unit = {},
        success: () -> Unit,
        finally: () -> Unit = {}
    ) {
        queue.add(
            JsonObjectRequest(Method.PUT, "$BASE_URL/${login.public}", JSONObject().apply {

                put(PRIVATE_KEY, login.private.toString())

                name?.let { put(NAME, it.toString()) }
                pictureUrl?.let { put(PICTURE, it) }
                bio?.let { put(BIO, it.toString()) }

            }, {
                success()
                finally()
            }, { failed(it.toApiError()); finally() })
        )
    }

}