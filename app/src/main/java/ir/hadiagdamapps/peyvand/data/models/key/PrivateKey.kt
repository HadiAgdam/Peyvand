package ir.hadiagdamapps.peyvand.data.models.key

class PrivateKey private constructor(private val text: String) {

    companion object {

        fun parse(text: String): PrivateKey? {
            return PrivateKey(text).takeIf { isValid(text) }
        }


        private fun isValid(text: String): Boolean {
            return true // could be implemented
        }

    }

    override fun toString(): String = text
}