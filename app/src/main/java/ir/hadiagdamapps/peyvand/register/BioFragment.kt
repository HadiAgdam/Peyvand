package ir.hadiagdamapps.peyvand.register

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.MyFragment


class BioFragment : MyFragment(R.layout.fragment_bio) {

    var bio: Bio? = null

    private lateinit var bioContainer: TextInputLayout
    private lateinit var bioInput: TextInputEditText

    override fun initViews(view: View) {
        bioContainer = view.findViewById(R.id.bioContainer)
        bioInput = view.findViewById(R.id.bioInput)

        bioInput.doOnTextChanged { text, _, _, _ ->
            bio = Bio.parse(text.toString())
            if (bio == null) bioContainer.error = getString(R.string.invalid_bio)
        }
    }

    override fun main() {
    }
}