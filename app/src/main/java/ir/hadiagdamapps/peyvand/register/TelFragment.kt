package ir.hadiagdamapps.peyvand.register

import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.MyFragment
import ir.hadiagdamapps.peyvand.tools.Tel
import ir.hadiagdamapps.peyvand.tools.TextValidator

class TelFragment : MyFragment(R.layout.fragment_tel) {

    var tel: Tel? = null

    private lateinit var telInput: TextInputEditText
    private lateinit var telContainer: TextInputLayout


    private fun clearError() {
        telContainer.error = null
    }
    fun displayError() {
        if (tel == null) telContainer.error = getString(R.string.invalid_tel)
    }

    override fun initViews(view: View) {
        telInput = view.findViewById(R.id.telInput)
        telContainer = view.findViewById(R.id.telContainer)

        telInput.doOnTextChanged { text, _, _, _ ->
            tel = Tel.parse(text.toString())

            if (tel != null) clearError()
        }
    }

    override fun main() {

    }
}