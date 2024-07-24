package ir.hadiagdamapps.peyvand.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.ProfileHelper

abstract class EditNameDialog
    : BottomSheetDialogFragment(R.layout.edit_name_dialog) {

    abstract fun setName(name: Name)

    private lateinit var nameInput: EditText
    private lateinit var saveButton: View
    private lateinit var cancelButton: View
    private lateinit var errorText: TextView

    private fun cancelClick() {
        dismiss()
    }

    private fun saveClick() {
        val name = Name.parse(nameInput.text.toString())
        if (name == null)
            errorText.text = getString(R.string.invalid_name)
        else setName(name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_name_dialog, container, false)

        nameInput = view.findViewById(R.id.nameInput)
        saveButton = view.findViewById(R.id.saveNameButton)
        cancelButton = view.findViewById(R.id.cancelNameButton)
        errorText = view.findViewById(R.id.errorText)

        cancelButton.setOnClickListener { cancelClick() }
        saveButton.setOnClickListener { saveClick() }

        return view
    }

}