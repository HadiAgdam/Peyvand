package ir.hadiagdamapps.peyvand.profile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.register.ChoosePictureDialogFragment
import ir.hadiagdamapps.peyvand.data.MyFragment
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.data.models.profile.Profile
import ir.hadiagdamapps.peyvand.data.ProfileHelper
import ir.hadiagdamapps.peyvand.data.TextValidator
import ir.hadiagdamapps.peyvand.data.models.social_media.SocialMedia
import ir.hadiagdamapps.peyvand.profile.dialog.EditBioDialog
import ir.hadiagdamapps.peyvand.profile.dialog.EditNameDialog
import ir.hadiagdamapps.peyvand.profile.dialog.EditSocialMediaDialog
import ir.hadiagdamapps.peyvand.profile.dialog.EditTelDialog


class ProfileFragment : MyFragment(R.layout.fragment_profile) {

    private var picture: Picture? = null
    private val helper by lazy { ProfileHelper(requireContext()) }
    private lateinit var profile: Profile
    private var selectedSocialMedia: SocialMedia? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it == null) return@registerForActivityResult
        val picture =
            Picture.parse(requireContext().contentResolver, it) ?: return@registerForActivityResult
        helper.setPicture(picture)
        image.setImageBitmap(picture.toBitmap())
        profile.picture = picture
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

            helper.setPicture(picture!!)
            image.setImageBitmap(picture!!.toBitmap())
            profile.picture = picture

        }
    }

    private val chooseDialog: ChoosePictureDialogFragment by lazy {
        ChoosePictureDialogFragment(requireActivity().supportFragmentManager,
            { // camera
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            { // gallery
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            { // unSelect
                picture = null
                image.setImageURI(Uri.parse(""))
                helper.deletePicture()
            })
    }

    private val nameDialog by lazy {
        EditNameDialog(profile.name) { name ->
            profile.name = name
            helper.setName(name)
            nameText.text = name.toString()
        }
    }

    private val telDialog by lazy {
        EditTelDialog(profile.tel) { tel ->
            profile.tel = tel
            helper.setTel(tel)
            telText.text = tel.toString()
        }
    }

    private val bioDialog by lazy {
        Log.e("position", "bio dialog lazy")
        EditBioDialog(profile.bio) { bio ->
            profile.bio = bio
            helper.setBio(bio)
            bioText.text = bio.toString()
        }
    }

    private val editSocialMediaDialog: EditSocialMediaDialog by lazy {
        EditSocialMediaDialog(requireActivity().supportFragmentManager) {

            when (selectedSocialMedia!!) {

                SocialMedia.INSTAGRAM -> {
                    if (it == "") clearInstagramButton()
                    else if (TextValidator.isValidSocialMedia(selectedSocialMedia!!, it))
                        fillInstagramButton(it)
                    else {
                        editSocialMediaDialog.setError(getString(R.string.invalid_social_media))
                        return@EditSocialMediaDialog
                    }
                }

                SocialMedia.TELEGRAM -> {
                    if (it == "") clearTelegramButton()
                    else if (TextValidator.isValidSocialMedia(selectedSocialMedia!!, it))
                        fillTelegramButton(it)
                    else {
                        editSocialMediaDialog.setError(getString(R.string.invalid_social_media))
                        return@EditSocialMediaDialog
                    }
                }

                SocialMedia.WHATSAPP -> {
                    if (it == "") clearWhatsappButton()
                    else if (TextValidator.isValidSocialMedia(selectedSocialMedia!!, it))
                        fillWhatsappButton(it)
                    else {
                        editSocialMediaDialog.setError(getString(R.string.invalid_social_media))
                        return@EditSocialMediaDialog
                    }
                }

            }

            helper.setSocialMedia(selectedSocialMedia!!, it)
            profile.linkedSocialMedias = helper.getSocialMedias()
            editSocialMediaDialog.dismiss()
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

    private lateinit var telegramButton: TextView
    private lateinit var instagramButton: TextView
    private lateinit var whatsappButton: TextView

    private fun clearTelegramButton() {
        telegramButton.apply {
            this.text = getString(R.string.telegram)
            setBackgroundResource(R.drawable.button_telegram_outlined)
            setTextColor(resources.getColor(R.color.telegram))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_add_24)
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.primary))
            telegramButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
    }

    private fun fillTelegramButton(text: String) {
        telegramButton.apply {
            this.text = text
            setBackgroundResource(R.drawable.button_telegram_filled)
            setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.telegram_icon)
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
            telegramButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        }
    }

    private fun clearWhatsappButton() {
        whatsappButton.apply {
            this.text = getString(R.string.whatsapp)
            setBackgroundResource(R.drawable.button_whatsapp_outlined)
            setTextColor(resources.getColor(R.color.telegram))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_add_24)
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.primary))
            whatsappButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
    }

    private fun fillWhatsappButton(text: String) {
        whatsappButton.apply {
            this.text = text
            setBackgroundResource(R.drawable.button_whatsapp_filled)
            setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.whatsapp_icon)
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
            whatsappButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
    }

    private fun clearInstagramButton() {
        instagramButton.apply {
            this.text = getString(R.string.instagram)
            setBackgroundResource(R.drawable.button_instagram_outlined)
            setTextColor(resources.getColor(R.color.instagram))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_add_24)
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.primary))
            instagramButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
    }

    private fun fillInstagramButton(text: String) {
        instagramButton.apply {
            this.text = text
            setBackgroundResource(R.drawable.button_instagram_filled)
            setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.instagram_icon)
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
            instagramButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
    }

    private fun loadSocialMedias() {
        profile.linkedSocialMedias?.apply {
            telegram?.let {
                fillTelegramButton(it)
            }
            whatsapp?.let {
                fillWhatsappButton(it)
            }
            instagram?.let {
                fillInstagramButton(it)
            }
        }
    }

    private fun editName() {
        nameDialog.show(requireActivity().supportFragmentManager, null)
    }

    private fun editBio() {
        bioDialog.show(requireActivity().supportFragmentManager, null)
    }

    private fun editTel() {
        telDialog.show(requireActivity().supportFragmentManager, null)
    }

    private val socialMediaViewClick = View.OnClickListener { clickedView ->
        when (clickedView) {
            telegramButton -> {
                selectedSocialMedia = SocialMedia.TELEGRAM
                (profile.linkedSocialMedias?.telegram).let { editSocialMediaDialog.show(it) }
            }

            instagramButton -> {
                selectedSocialMedia = SocialMedia.INSTAGRAM
                (profile.linkedSocialMedias?.instagram).let { editSocialMediaDialog.show(it) }
            }

            whatsappButton -> {
                selectedSocialMedia = SocialMedia.WHATSAPP
                (profile.linkedSocialMedias?.whatsapp).let { editSocialMediaDialog.show(it) }
            }
        }

    }

    override fun initViews(view: View) {
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

        telegramButton = view.findViewById(R.id.telegramButton)
        instagramButton = view.findViewById(R.id.instagramButton)
        whatsappButton = view.findViewById(R.id.whatsappButton)


        telegramButton.setOnClickListener(socialMediaViewClick)
        instagramButton.setOnClickListener(socialMediaViewClick)
        whatsappButton.setOnClickListener(socialMediaViewClick)

    }

    override fun main() {
        helper.getProfile()?.let { profile = it }

        picture = profile.picture

        if (picture != null)
            image.setImageBitmap(picture!!.toBitmap())
        nameText.text = profile.name.toString()
        bioText.text = profile.bio.toString()
        telText.text = profile.tel.toString()

        image.setOnClickListener { chooseDialog.showDialog(picture != null) }
        smallImageIcon.setOnClickListener { image.performClick() }
        loadSocialMedias()
    }
}