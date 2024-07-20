package ir.hadiagdamapps.peyvand.register

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.hadiagdamapps.peyvand.Manifest
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.MyFragment
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.TextValidator


class NameAndPictureFragment() : MyFragment(R.layout.fragment_name_and_picture) {

    private lateinit var nameInput: TextInputEditText
    private lateinit var nameContainer: TextInputLayout

    private lateinit var imageContainer: View
    private lateinit var image: ImageView

    var picture: Picture? = null
    var name: String? = null

    // TODO if name is null, means this is not in correct format
    // TODO if image is null, means image not selected

    private fun pickImage() {

    }

    override fun initViews(view: View) {
        nameInput = view.findViewById(R.id.nameInput)
        nameContainer = view.findViewById(R.id.nameContainer)

        nameInput.doOnTextChanged { text, _, _, _ ->
            val name = TextValidator.validateName(text.toString())
            if (name != null) this.name = name
            else nameContainer.error = getString(R.string.invalid_name)
        }

        imageContainer = view.findViewById(R.id.imageContainer)
        image = view.findViewById(R.id.placeHolderImage)

        imageContainer.setOnClickListener { pickImage() }
    }

    override fun main() {

    }

}