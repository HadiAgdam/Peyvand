package ir.hadiagdamapps.peyvand.data

import org.json.JSONObject

enum class Key {
    PUBLIC_KEY,
    PRIVATE_KEY,
    NAME,
    PICTURE,
    BIO,
    TEL,
    ;

    override fun toString() = this.name.lowercase()

    companion object {
        private val map = entries.associateBy { it.toString() }

        fun fromString(key: String): Key? = map[key]
    }
}

fun JSONObject.put(key: Key, value: Any?): JSONObject = put(key.toString(), value)

fun JSONObject.getString(key: Key): String = getString(key.toString())
