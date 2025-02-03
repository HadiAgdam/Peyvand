package ir.hadiagdamapps.peyvand.profile.viewmodel

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hadiagdamapps.peyvand.data.ProfileHelper
import ir.hadiagdamapps.peyvand.data.models.social_media.SocialMedia
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val application: Application) : ViewModel() {

    private val repository = ProfileHelper(application)

    private val _triggerPermissionLauncher = MutableSharedFlow<Unit>() // .emit
    val triggerPermissionLauncher = _triggerPermissionLauncher

    private val _triggerPickImageLauncher = MutableSharedFlow<Unit>()
    val triggerPickImageLauncher = _triggerPickImageLauncher.asSharedFlow()

    private val _triggerLaunchCamera = MutableSharedFlow<Unit>()
    val triggerLunchCamera = _triggerLaunchCamera.asSharedFlow()

    private val _triggerSettingsLauncher = MutableSharedFlow<Unit>()
    val triggerSettingsLauncher = _triggerSettingsLauncher.asSharedFlow()


    var name: Name? by mutableStateOf(null)
        private set
    var tel: Tel? by mutableStateOf(null)
        private set
    var bio: Bio? by mutableStateOf(null)
        private set
    var picture: Picture? by mutableStateOf(null)
        private set

    var instagramHandle: String? by mutableStateOf(null)
        private set
    var telegramHandle: String? by mutableStateOf(null)
        private set
    var xHandle: String? by mutableStateOf(null)
        private set

    init {
        initProfile()
    }

    private fun initProfile() {
        repository.getProfile()?.let {
            name = it.name
            tel = it.tel
            bio = it.bio
            picture = it.picture

            it.linkedSocialMedias?.let { socialMedias ->
                instagramHandle = socialMedias.instagram
                telegramHandle = socialMedias.telegram
                xHandle = socialMedias.x
            }
        }
    }

    fun pickImageResult(uri: Uri?) {
        if (uri == null) return
        val picture =
            Picture.parse(application.contentResolver, uri) ?: return
        repository.setPicture(picture)
        this.picture = picture
    }

    fun requestPermissionResult(granted: Boolean) {
        viewModelScope.launch { // wierd
            if (granted)
                _triggerLaunchCamera.emit(Unit)
            else
                _triggerSettingsLauncher.emit(Unit)
        }
    }

    fun cameraResult(data: Intent?) {
        data?.let {
            picture = Picture.parse(it.extras?.get("data") as Bitmap)

            repository.setPicture(picture!!)
            this.picture = picture
        }
    }

    fun unSelectPicture() {
        picture = null
        repository.deletePicture()
    }

    fun putName(name: Name) {
        this.name = name
        repository.setName(name)
    }

    fun putTel(tel: Tel) {
        this.tel = tel
        repository.setTel(tel)
    }

    fun putBio(bio: Bio) {
        this.bio = bio
        repository.setBio(bio)
    }

    fun setSocialMedia(socialMedia: SocialMedia, value: String) {
        repository.setSocialMedia(socialMedia, value)
        when (socialMedia) {
            SocialMedia.X -> xHandle = value
            SocialMedia.INSTAGRAM -> instagramHandle = value
            SocialMedia.TELEGRAM -> telegramHandle = value
        }
    }

}