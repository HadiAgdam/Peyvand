package ir.hadiagdamapps.peyvand.tools

import org.json.JSONObject

data class Profile(
    var name: String,
    var picture: Picture?,
    var tel: Tel,
    var bio: String
) {
    fun toJson(): JSONObject {


        return JSONObject()
    }
}

// TODO add location on map maybe


class Tel(private val tell: String) {

    override fun toString(): String {
        return tell
    }

}
