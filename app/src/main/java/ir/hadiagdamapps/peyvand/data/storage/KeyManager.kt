package ir.hadiagdamapps.peyvand.data.storage

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import ir.hadiagdamapps.peyvand.data.Key
import ir.hadiagdamapps.peyvand.data.models.key.KeySet
import ir.hadiagdamapps.peyvand.data.models.key.PrivateKey
import ir.hadiagdamapps.peyvand.data.models.key.PublicKey
import ir.hadiagdamapps.peyvand.data.Key.PUBLIC_KEY
import ir.hadiagdamapps.peyvand.data.Key.PRIVATE_KEY

class KeyManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("Key_preferences", 0)
    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        "encrypted_key_preferences", // File name
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), // Master key
        context,        // Context
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    fun getPublicKey(): PublicKey? {
        return PublicKey.parse(sharedPreferences.getString(PUBLIC_KEY) ?: return null)
    }

    private fun getPrivateKey(): PrivateKey? {
        return PrivateKey.parse(
            encryptedSharedPreferences.getString(PRIVATE_KEY) ?: return null
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

    fun clear() {
        sharedPreferences.edit().apply {
            putString(PUBLIC_KEY, null)
            putString(PRIVATE_KEY, null)
            apply()
        }
    }


}

fun Editor.putString(key: Key, value: String?) {
    this.putString(key.toString(), value)
}

fun SharedPreferences.getString(key: Key) = this.getString(key.toString(), null)

fun SharedPreferences.Editor.putString(key: Key, value: String): Editor? = this.putString(key.toString(), value)