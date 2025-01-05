package ir.hadiagdamapps.peyvand.data

import android.content.Context
import android.util.Log
import ir.hadiagdamapps.peyvand.data.db.ContactsDatabaseHelper
import ir.hadiagdamapps.peyvand.data.models.contact.ContactUpdate
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.data.network.ApiSingleton
import ir.hadiagdamapps.peyvand.data.network.ContactApi
import ir.hadiagdamapps.peyvand.data.storage.KeyManager

abstract class ContactUpdateManager(context: Context) {

    private val api = ContactApi(ApiSingleton.getInstance(context).getRequestQueue())
    private val database = ContactsDatabaseHelper(context)
    private val keyManager = KeyManager(context)

    fun sync() {
        val login = keyManager.getKeySet()

        api.getContactsByPublicKey(
            login = login,
            contacts = database.getAll()
                .mapNotNull { PublicKey.parse(it.publicKey ?: return@mapNotNull null) },
            success = {
                for (contactUpdate in it)
                    database.update(contactUpdate)
                updateSuccess(it)
            },
            failed = {
                Log.e("get contacts failed", it.toString())
            }
        )
    }

    abstract fun updateSuccess(contacts: List<ContactUpdate>)
}