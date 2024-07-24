package ir.hadiagdamapps.peyvand.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Tel

abstract class EditTelDialog : BottomSheetDialogFragment(R.layout.edit_tel_dialog) {

    abstract fun setTel(tel: Tel)

    private lateinit var telInput: EditText
    private lateinit var saveButton: View
    private lateinit var cancelButton: View
    private lateinit var errorText: TextView

    private fun cancelClick() {
        dismiss()
    }

    private fun saveClick() {
        val tel = Tel.parse(telInput.text.toString())
        if (tel == null)
            errorText.text = getString(R.string.invalid_tel)
        else setTel(tel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_tel_dialog, container, false)

        telInput = view.findViewById(R.id.telInput)
        saveButton = view.findViewById(R.id.saveTelButton)
        cancelButton = view.findViewById(R.id.cancelTelButton)
        errorText = view.findViewById(R.id.errorText)


        cancelButton.setOnClickListener { cancelClick() }
        saveButton.setOnClickListener { saveClick() }

        return view
    }


}