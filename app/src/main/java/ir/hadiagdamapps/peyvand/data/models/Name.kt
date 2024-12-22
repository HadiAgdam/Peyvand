package ir.hadiagdamapps.peyvand.tools

import android.os.Parcelable
import ir.hadiagdamapps.peyvand.data.TextValidator
import kotlinx.parcelize.Parcelize

@Parcelize
class Name private constructor(private val name: String) : Parcelable {

    companion object {
        fun parse(text: String): Name? {
            return Name(TextValidator.validateName(text) ?: return null)
        }
    }

    override fun toString() = name

}