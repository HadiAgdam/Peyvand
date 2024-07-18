package ir.hadiagdamapps.peyvand.tools

import android.net.Uri
import androidx.core.net.toUri
import org.json.JSONObject
import java.io.File

data class Profile(
    var name: String,
    var picture: Picture,
    var tell: Tell,
    var bio: String
) {
    fun toJson(): JSONObject {


        return JSONObject()
    }
}




class Tell(private val tell: String) {

    override fun toString(): String {
        return tell
    }

}
