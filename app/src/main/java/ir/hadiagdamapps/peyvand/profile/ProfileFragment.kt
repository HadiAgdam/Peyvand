package ir.hadiagdamapps.peyvand.profile

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.register.ChoosePictureDialogFragment
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.MyFragment
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Profile
import ir.hadiagdamapps.peyvand.tools.ProfileHelper
import ir.hadiagdamapps.peyvand.tools.Tel


class ProfileFragment : MyFragment(R.layout.fragment_profile) {

    private var picture: Picture? = null
    private val helper by lazy { ProfileHelper(requireContext()) }
    private lateinit var profile: Profile

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it == null) return@registerForActivityResult
        picture = Picture.parse(requireContext().contentResolver, it)
        image.setImageBitmap(picture!!.toBitmap())
        helper.setProfile(profile)
    }

    private val chooseDialog: ChoosePictureDialogFragment by lazy {
        ChoosePictureDialogFragment(requireActivity().supportFragmentManager,
            { // camera
                TODO("implement")
            },
            { // gallery
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            { // unSelect
                picture = null
                image.setImageURI(Uri.parse(""))
            })
    }

    private val nameDialog = object : EditNameDialog() {
        override fun setName(name: Name) {
            profile.name = name
            helper.setProfile(profile)
        }
    }

    private val telDialog = object : EditTelDialog() {
        override fun setTel(tel: Tel) {
            profile.tel = tel
            helper.setProfile(profile)
        }
    }

    private val bioDialog = object : EditBioDialog() {
        override fun setBio(bio: Bio) {
            profile.bio = bio
            helper.setProfile(profile)
        }
    }

    private lateinit var image: ImageView
    private lateinit var imageContainer: View
    private lateinit var smallImageIcon: View

    private lateinit var nameText: TextView
    private lateinit var nameContainer: View

    private lateinit var bioText: TextView
    private lateinit var bioContainer: View

    private lateinit var telText: TextView
    private lateinit var telContainer: View

    private lateinit var overlay: View

    private fun editName() {
        nameDialog.show(requireActivity().supportFragmentManager, null)
    }

    private fun editBio() {
        bioDialog.show(requireActivity().supportFragmentManager, null)
    }

    private fun editTel() {
        telDialog.show(requireActivity().supportFragmentManager, null)
    }

    override fun initViews(view: View) {
        overlay = view.findViewById(R.id.overlay)

        image = view.findViewById(R.id.image)
        imageContainer = view.findViewById(R.id.imageContainer)
        smallImageIcon = view.findViewById(R.id.smallImageIcon)

        nameText = view.findViewById(R.id.nameText)
        nameContainer = view.findViewById(R.id.nameContainer)

        bioText = view.findViewById(R.id.bioText)
        bioContainer = view.findViewById(R.id.bioContainer)

        telText = view.findViewById(R.id.telText)
        telContainer = view.findViewById(R.id.telContainer)

        nameContainer.setOnClickListener { editName() }
        bioContainer.setOnClickListener { editBio() }
        telContainer.setOnClickListener { editTel() }
    }

    override fun main() {
        image.setOnClickListener { chooseDialog.showDialog(picture != null) }
        smallImageIcon.setOnClickListener { image.performClick() }
        val p = helper.getProfile()
        if (p != null)
            profile = p


        val picture = profile.picture
        if (picture != null)
            image.setImageBitmap(picture.toBitmap())
        else image.setImageBitmap(Picture.getPlaceHolder(requireContext()))
        nameText.text = profile.name.toString()
        bioText.text = profile.bio.toString()
        telText.text = profile.tel.toString()
    }
}