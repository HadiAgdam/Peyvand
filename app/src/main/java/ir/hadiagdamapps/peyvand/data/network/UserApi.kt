package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.RequestQueue
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.models.key.PrivateKey
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import org.json.JSONObject
import ir.hadiagdamapps.peyvand.data.Key.*
import ir.hadiagdamapps.peyvand.data.models.profile.SyncProfile
import ir.hadiagdamapps.peyvand.data.put

class UserApi(queue: RequestQueue) : Api(queue) {

    companion object {
        private const val BASE_URL = "${Api.BASE_URL}/users"
    }

    fun register(
        profile: SyncProfile,
        success: (KeySet) -> Unit,
        failed: (ApiError?) -> Unit,
        finally: () -> Unit = {}
    ) {
        newRequest(
            url = BASE_URL,
            method = Method.POST,
            json = JSONObject().apply {
                put(NAME, profile.name.toString())
                profile.picture?.let { put(PICTURE, it) }
                put(BIO, profile.bio.toString())
            },
            onSuccess = {
                success(
                    KeySet(
                        public = PublicKey.parse(it.getString("public_key"))!!,
                        private = PrivateKey.parse(it.getString("private_key"))!!
                    )
                )
            },
            onFailed = failed,
            finally = finally
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
        newRequest(
            url = "$BASE_URL/${login.public}",
            method = Method.PUT,
            json = JSONObject().apply {
                put(PRIVATE_KEY, login.private.toString())

                name?.let { put(NAME, it.toString()) }
                pictureUrl?.let { put(PICTURE, it) }
                bio?.let { put(BIO, it.toString()) }
            },
            onSuccess = { success() },
            onFailed = failed,
            finally = finally,
            label = "User Api update",
        )
    }

}