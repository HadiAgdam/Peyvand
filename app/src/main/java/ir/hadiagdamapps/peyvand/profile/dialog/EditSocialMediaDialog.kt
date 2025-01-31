package ir.hadiagdamapps.peyvand.profile.dialog

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.TextValidator

class EditSocialMediaDialog(
    private val manager: FragmentManager,
    private val save: (text: String) -> Unit
) : BottomSheetDialogFragment(R.layout.social_media_dialog) {

    private lateinit var textInput: EditText
    private lateinit var errorText: TextView

    private fun cancelClick() = dismiss()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("position", "onCreate")
    }

    private fun saveAction() {
        save(textInput.text.toString())
    }

    fun setError(error: String) {
        errorText.text = error
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.social_media_dialog, container, false).apply {
        textInput = findViewById<EditText?>(R.id.textInput).apply {
            setOnEditorActionListener { v, actionId, event ->
                saveAction()
                true
            }
        }
        errorText = findViewById(R.id.errorText)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun show(text: String?) {
        super.show(manager, null)
        errorText.text = ""
        Handler(Looper.myLooper()!!).postDelayed({
            textInput.setText(text ?: "")
        }, 10)
    }
}