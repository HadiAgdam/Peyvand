package ir.hadiagdamapps.peyvand.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import ir.hadiagdamapps.peyvand.data.models.KeySet
import ir.hadiagdamapps.peyvand.data.models.PrivateKey
import ir.hadiagdamapps.peyvand.data.models.PublicKey

class KeyManager(context: Context) {

    companion object {
        private const val PUBLIC_KEY = "public_key"
        private const val PRIVATE_KEY = "private_key"
    }

    private val sharedPreferences = context.getSharedPreferences("Key_preferences", 0)
    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        "encrypted_key_preferences", // File name
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), // Master key
        context,        // Context
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    private fun getPublicKey(): PublicKey? {
        return PublicKey.parse(sharedPreferences.getString(PUBLIC_KEY, null) ?: return null)
    }

    private fun getPrivateKey(): PrivateKey? {
        return PrivateKey.parse(
            encryptedSharedPreferences.getString(PRIVATE_KEY, null) ?: return null
        )
    }

    private fun setPublicKey(publicKey: PublicKey) {
        sharedPreferences.edit().apply {
            putString(PUBLIC_KEY, publicKey.toString())
            apply()
        }
    }

    private fun setPrivateKey(privateKey: PrivateKey) {
        encryptedSharedPreferences.edit().apply {
            putString(PRIVATE_KEY, privateKey.toString())
            apply()
        }
    }


    fun getKeySet(): KeySet? {
        return KeySet(
            public = getPublicKey() ?: return null,
            private = getPrivateKey() ?: return null
        )
    }


    fun put(set: KeySet) {
        setPublicKey(set.public)
        setPrivateKey(set.private)
    }


}