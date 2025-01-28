package ir.hadiagdamapps.peyvand.profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Bio

class EditBioDialog(
    private val bio: Bio,
    private val setBio: (bio: Bio) -> Unit
) : BottomSheetDialogFragment(R.layout.edit_bio_dialog) {

    private lateinit var bioInput: EditText
    private lateinit var saveButton: View
    private lateinit var cancelButton: View
    private lateinit var errorText: TextView

    private fun cancelClick() {
        dismiss()
    }

    private fun saveClick() {
        val bio = Bio.parse(bioInput.text.toString())
        if (bio == null)
            errorText.text = getString(R.string.invalid_bio)
        else {
            setBio(bio); dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_bio_dialog, container, false)

        bioInput = view.findViewById(R.id.editBioInput)
        saveButton = view.findViewById(R.id.saveBioButton)
        cancelButton = view.findViewById(R.id.cancelBioButton)
        errorText = view.findViewById(R.id.errorText)

        bioInput.setText(bio.toString())

        cancelButton.setOnClickListener { cancelClick() }
        saveButton.setOnClickListener { saveClick() }


        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

}




