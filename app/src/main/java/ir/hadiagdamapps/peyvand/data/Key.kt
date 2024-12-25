package ir.hadiagdamapps.peyvand.data

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

