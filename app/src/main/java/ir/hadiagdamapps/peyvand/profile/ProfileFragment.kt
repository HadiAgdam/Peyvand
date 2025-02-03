package ir.hadiagdamapps.peyvand.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.register.ChoosePictureDialogFragment
import ir.hadiagdamapps.peyvand.data.TextValidator
import ir.hadiagdamapps.peyvand.data.models.social_media.SocialMedia
import ir.hadiagdamapps.peyvand.profile.dialog.EditBioDialog
import ir.hadiagdamapps.peyvand.profile.dialog.EditNameDialog
import ir.hadiagdamapps.peyvand.profile.dialog.EditSocialMediaDialog
import ir.hadiagdamapps.peyvand.profile.dialog.EditTelDialog
import ir.hadiagdamapps.peyvand.profile.view.components.EditProfileView
import ir.hadiagdamapps.peyvand.profile.view.components.ProfilePictureContainer
import ir.hadiagdamapps.peyvand.profile.view.components.SocialMediaButton
import ir.hadiagdamapps.peyvand.profile.viewmodel.ProfileScreenViewModel
import ir.hadiagdamapps.peyvand.profile.viewmodel.ProfileScreenViewModelFactory
import ir.hadiagdamapps.peyvand.view.components.Title

class ProfileFragment : Fragment() {

    private var selectedSocialMedia: SocialMedia? = null
    private val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    private val viewmodel: ProfileScreenViewModel by viewModels {
        ProfileScreenViewModelFactory(requireActivity().application) // What ?
    }

    private val nameDialog by lazy {
        EditNameDialog(viewmodel.name!!) { name ->
            viewmodel.putName(name)
        }
    }

    private val telDialog by lazy {
        EditTelDialog(viewmodel.tel!!) { tel ->
            viewmodel.putTel(tel)
        }
    }

    private val bioDialog by lazy {
        EditBioDialog(viewmodel.bio!!) { bio ->
            viewmodel.putBio(bio)
        }
    }

    private val permissionDeniedDialog by lazy {
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

        }
    }

    private fun socialMediaViewClick(socialMedia: SocialMedia) {
        selectedSocialMedia = socialMedia

        editSocialMediaDialog.show(
            when (socialMedia) {

                SocialMedia.TELEGRAM -> viewmodel.telegramHandle

                SocialMedia.INSTAGRAM -> viewmodel.instagramHandle

                SocialMedia.X -> viewmodel.xHandle

            }
        )

    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        viewmodel.pickImageResult(it)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            viewmodel.requestPermissionResult(granted)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) viewmodel.cameraResult(data)
    }

    private val chooseDialog: ChoosePictureDialogFragment by lazy {
        ChoosePictureDialogFragment(requireActivity().supportFragmentManager,
            { // camera
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            },
            { // gallery
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            { // unSelect
                viewmodel.unSelectPicture()
            })
    }

    private val editSocialMediaDialog: EditSocialMediaDialog by lazy {
        EditSocialMediaDialog(requireActivity().supportFragmentManager) {
            when (selectedSocialMedia!!) {

                SocialMedia.INSTAGRAM -> {
                    if (it != "" && !TextValidator.isValidSocialMedia(selectedSocialMedia!!, it)) {
                        editSocialMediaDialog.setError(getString(R.string.invalid_social_media))
                        return@EditSocialMediaDialog
                    }
                }

                SocialMedia.TELEGRAM -> {

                    if (it != "" && !TextValidator.isValidSocialMedia(selectedSocialMedia!!, it)) {
                        editSocialMediaDialog.setError(getString(R.string.invalid_social_media))
                        return@EditSocialMediaDialog
                    }
                }

                SocialMedia.X -> {
                    if (it != "" && !TextValidator.isValidSocialMedia(selectedSocialMedia!!, it)) {
                        editSocialMediaDialog.setError(getString(R.string.invalid_social_media))
                        return@EditSocialMediaDialog
                    }
                }

            }
            viewmodel.setSocialMedia(selectedSocialMedia!!, it)
            editSocialMediaDialog.dismiss()
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                LaunchedEffect(Unit) { // permission dialog
                    viewmodel.triggerPermissionLauncher.collect {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }

                LaunchedEffect(Unit) { // pick image
                    viewmodel.triggerPickImageLauncher.collect {
                        pickImage.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )

                    }
                }

                LaunchedEffect(Unit) { // camera
                    viewmodel.triggerLunchCamera.collect {
                        startActivityForResult(cameraIntent, 0)
                    }
                }

                LaunchedEffect(Unit) { // settings
                    viewmodel.triggerSettingsLauncher.collect {
                        permissionDeniedDialog.show()
                    }
                }

                Surface {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Title("Profile")

                        Spacer(Modifier.height(48.dp))

                        ProfilePictureContainer({
                            chooseDialog.showDialog(viewmodel.picture != null) // TODO recheck
                        }, viewmodel.picture)

                        Spacer(Modifier.height(48.dp))

                        Row(
                            Modifier
                                .height(64.dp)
                                .fillMaxWidth()
                                .padding(4.dp)
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            SocialMediaButton(
                                modifier = Modifier.weight(1f),
                                text = viewmodel.telegramHandle,
                                defaultText = stringResource(R.string.telegram),
                                icon = painterResource(R.drawable.telegram_icon),
                                color = colorResource(R.color.telegram),
                                click = { socialMediaViewClick(SocialMedia.TELEGRAM) }
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            SocialMediaButton(
                                modifier = Modifier.weight(1f),
                                text = viewmodel.instagramHandle,
                                defaultText = stringResource(R.string.instagram),
                                icon = painterResource(R.drawable.instagram_icon),
                                color = colorResource(R.color.instagram),
                                click = { socialMediaViewClick(SocialMedia.INSTAGRAM) }
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            SocialMediaButton(
                                modifier = Modifier.weight(1f),
                                text = viewmodel.xHandle,
                                defaultText = "X(Twitter)",
                                icon = painterResource(R.drawable.x_icon),
                                color = colorResource(R.color.x),
                                click = { socialMediaViewClick(SocialMedia.X) }
                            )
                        }


                        Spacer(Modifier.height(48.dp))

                        EditProfileView(
                            title = stringResource(R.string.edit_name_title),
                            icon = painterResource(R.drawable.account_icon),
                            onClick = { editName() }
                        ) {
                            Text(
                                viewmodel.name.toString(),
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                stringResource(R.string.edit_name_description),
                                style = MaterialTheme.typography.caption
                            )
                        }

                        Divider(Modifier.fillMaxWidth())

                        EditProfileView(
                            title = stringResource(R.string.bio_title),
                            icon = painterResource(R.drawable.baseline_info_outline_24),
                            onClick = { editBio() }
                        ) {
                            Text(
                                viewmodel.bio.toString(),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Divider(Modifier.fillMaxWidth())

                        EditProfileView(
                            title = stringResource(R.string.tel_title),
                            icon = painterResource(R.drawable.call_icon),
                            onClick = { editTel() }
                        ) {
                            Text(
                                viewmodel.tel.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body1
                            )
                        }

                    }
                }
            }
        }
    }

}