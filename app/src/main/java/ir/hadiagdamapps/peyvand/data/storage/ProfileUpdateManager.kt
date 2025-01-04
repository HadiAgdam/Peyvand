package ir.hadiagdamapps.peyvand.data.storage

import android.content.Context
import ir.hadiagdamapps.peyvand.data.Key
import ir.hadiagdamapps.peyvand.data.models.profile.SyncProfile
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.network.ApiSingleton
import ir.hadiagdamapps.peyvand.data.network.UserApi
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.data.ProfileHelper

class ProfileUpdateManager(private val context: Context) {

    private val profileHelper = ProfileHelper(context)
    private val keyManager = KeyManager(context)
    private val sharedPreferences = context.getSharedPreferences("update_manager", 0)
    private val api: UserApi by lazy { UserApi(ApiSingleton.getInstance(context).getRequestQueue()) }


    private fun put(profile: SyncProfile) {
        sharedPreferences.edit().apply {

            putString(Key.NAME, profile.name.toString())
            profile.picture?.apply { putString(Key.PICTURE, this) }
            putString(Key.BIO, profile.bio.toString())

            apply()
        }
    }

    private fun getSyncProfile(): SyncProfile? {
        return SyncProfile(
            name = sharedPreferences.getString(Key.NAME.toString(), null)?.let { Name.parse(it) }
                ?: return null,
            picture = sharedPreferences.getString(Key.PICTURE.toString(), null) ?: return null,
            bio = sharedPreferences.getString(Key.BIO.toString(), null)?.let { Bio.parse(it) }
                ?: return null
        )
    }

    private fun push(login: KeySet, profile: SyncProfile) {
        api.update(
            login = login,
            name = profile.name,
            pictureUrl = profile.picture,
            bio = profile.bio,
            success = {
                put(profile)
            }
        )
    }

    fun sync() {

        val login = keyManager.getKeySet()
        val profile = profileHelper.getProfile() ?: return // throw Error.NULL_PROFILE_EXCEPTION


        if (login == null) {
            api.register(
                profile = profile,
                success = {
                    keyManager.put(it)
                    sync()
                },
                failed = {}
            )
            return
        }

        val syncProfile = getSyncProfile()

        if (syncProfile == null ||
            (syncProfile.name.toString() != profile.name.toString()) ||
            (syncProfile.bio.toString() != profile.bio.toString()) ||
            (syncProfile.picture != profile.picture?.urlString)
        ) {
            push(
                login = login,
                SyncProfile(
                    name = profile.name,
                    picture = profile.picture?.urlString,
                    bio = profile.bio
                )
            )
        }

    }


}