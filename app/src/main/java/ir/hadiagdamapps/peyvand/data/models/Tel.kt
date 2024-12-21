package ir.hadiagdamapps.peyvand.tools

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Tel private constructor(private val tell: String) : Parcelable {

    override fun toString() = tell

    companion object {
        fun parse(text: String?): Tel? {
            return Tel(TextValidator.validateTel(text ?: return null) ?: return null)
        }
    }

}