package ir.hadiagdamapps.peyvand.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Bio

abstract class EditBioDialog
    : BottomSheetDialogFragment(R.layout.edit_bio_dialog) {

    abstract fun setBio(bio: Bio)

    private lateinit var bioInout: EditText
    private lateinit var saveButton: View
    private lateinit var cancelButton: View
    private lateinit var errorText: TextView

    private fun cancelClick() {
        dismiss()
    }

    private fun saveClick() {
        val bio = Bio.parse(bioInout.text.toString())
        if (bio == null)
            errorText.text = getString(R.string.invalid_bio)
        else setBio(bio)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_bio_dialog, container, false)

        bioInout = view.findViewById(R.id.bioInput)
        saveButton = view.findViewById(R.id.saveBioButton)
        cancelButton = view.findViewById(R.id.cancelBioButton)
        errorText = view.findViewById(R.id.errorText)

        cancelButton.setOnClickListener { cancelClick() }
        saveButton.setOnClickListener { saveClick() }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}




