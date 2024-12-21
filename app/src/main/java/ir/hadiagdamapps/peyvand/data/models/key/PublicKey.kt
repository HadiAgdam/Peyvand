package ir.hadiagdamapps.peyvand.data.models.key

class PublicKey private constructor(private val text: String) {

    companion object {

        fun parse(text: String): PublicKey? {
            return PublicKey(text).takeIf { isValid(text) }
        }


        private fun isValid(text: String): Boolean {
            return true // could be implemented
        }

    }

    override fun toString(): String = text

}