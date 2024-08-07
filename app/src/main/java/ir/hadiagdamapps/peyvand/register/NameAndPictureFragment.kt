package ir.hadiagdamapps.peyvand.register

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.MyFragment
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture


class NameAndPictureFragment(private val fragmentManager: FragmentManager) :
    MyFragment(R.layout.fragment_name_and_picture) {

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it == null) return@registerForActivityResult
        picture = Picture.parse(requireContext().contentResolver, it)
        imageView.setImageBitmap(picture!!.toBitmap())
    }

    private val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            if (granted) {
                startActivityForResult(cameraIntent, 0)
            } else {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(R.string.permission_dialog_title)
                    setMessage(R.string.permission_dialog_text_camera)

                    setNeutralButton(R.string.dismiss, null)

                    setPositiveButton(R.string.ok) { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 1)
                    }

                }.show()
            }
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && data != null) {
            picture = Picture.parse(data.extras?.get("data") as Bitmap)
            imageView.setImageBitmap(picture!!.toBitmap())
        }
        if (requestCode == 1 && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(cameraIntent, 0)
        }
    }

    private val chooseDialog: ChoosePictureDialogFragment by lazy {
        ChoosePictureDialogFragment(fragmentManager,
            { // camera
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            { // gallery
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            { // unSelect
                picture = null
                imageView.setImageURI(Uri.parse(""))
            })
    }

    private lateinit var nameInput: TextInputEditText
    private lateinit var nameContainer: TextInputLayout

    private lateinit var imageContainer: View
    private lateinit var imageView: ImageView
    private lateinit var imageContainerIcon: ImageView

    var picture: Picture? = null
    var name: Name? = null

    private fun clearError() {
        nameContainer.error = null
    }

    fun displayError() {
        if (name == null) nameContainer.error = getString(R.string.invalid_name)
    }


    override fun initViews(view: View) {
        nameInput = view.findViewById(R.id.nameInput)
        nameContainer = view.findViewById(R.id.nameContainer)

        nameInput.doOnTextChanged { text, _, _, _ ->
            name = Name.parse(text.toString())

            if (name != null) clearError()
        }

        imageContainer = view.findViewById(R.id.imageContainer)
        imageView = view.findViewById(R.id.placeHolderImage)
        imageContainerIcon = view.findViewById(R.id.imageContainerIcon)

        imageContainer.setOnClickListener {
            chooseDialog.showDialog(picture != null)
        }
    }

    override fun main() {

    }

}

class ChoosePictureDialogFragment(
    private val fragmentManager: FragmentManager,
    private val optionCamera: () -> Unit,
    private val optionGallery: () -> Unit,
    private val unselect: () -> Unit
) : BottomSheetDialogFragment(R.layout.choose_picture_dialog) {

    private lateinit var unselectItem: View
    private lateinit var div2: View
    private lateinit var self: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        self = inflater.inflate(R.layout.choose_picture_dialog, container, false)
        self.findViewById<View>(R.id.cameraItem).setOnClickListener { dismiss(); optionCamera() }
        self.findViewById<View>(R.id.galleryItem).setOnClickListener { dismiss(); optionGallery() }
        unselectItem = self.findViewById(R.id.unselectItem)
        div2 = self.findViewById(R.id.div2)

        unselectItem.setOnClickListener { dismiss(); unselect(); }
        return self
    }

    fun showDialog(showUnSelect: Boolean) {
        // WTF ?
        Handler().postDelayed(
            {
                if (showUnSelect) {
                    unselectItem.visibility = View.VISIBLE
                    div2.visibility = View.VISIBLE
                } else {
                    unselectItem.visibility = View.GONE
                    div2.visibility = View.GONE
                }

            }, 10
        )

        show(fragmentManager, null)
    }

}