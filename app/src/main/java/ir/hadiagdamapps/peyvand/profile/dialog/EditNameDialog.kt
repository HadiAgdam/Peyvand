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
import ir.hadiagdamapps.peyvand.tools.Name

class EditNameDialog(
    private val name: Name,
    private val setName: (Name) -> Unit
) :
    BottomSheetDialogFragment(R.layout.edit_name_dialog) {

    private lateinit var nameInput: EditText
    private lateinit var saveButton: View
    private lateinit var cancelButton: View
    private lateinit var errorText: TextView

    private fun cancelClick() = dismiss()


    private fun saveClick() {
        val name = Name.parse(nameInput.text.toString())
        if (name == null)
            errorText.text = getString(R.string.invalid_name)
        else {
            setName(name)
            dismiss()
        }

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

        nameInput.setText(name.toString())

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

}