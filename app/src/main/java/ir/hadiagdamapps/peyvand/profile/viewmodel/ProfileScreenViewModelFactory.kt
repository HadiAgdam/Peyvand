package ir.hadiagdamapps.peyvand.profile.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileScreenViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileScreenViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}