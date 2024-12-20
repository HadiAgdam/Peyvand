package ir.hadiagdamapps.peyvand.connect

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.contacts.ContactActivity
import ir.hadiagdamapps.peyvand.tools.Contact
import ir.hadiagdamapps.peyvand.tools.ContactsHelper
import ir.hadiagdamapps.peyvand.tools.MyFragment
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.ProfileHelper
import ir.hadiagdamapps.peyvand.tools.TextValidator

class ConnectFragment : MyFragment(R.layout.fragment_connect) {

    private val writer = MultiFormatWriter()

    private lateinit var qrContainer: ImageView
    private val profileHelper by lazy {
        ProfileHelper(requireContext())
    }

    private fun loadQrCode() {
        val profile = profileHelper.getProfile() ?: return
        val bitMatrix =
            writer.encode(profile.toQrString(profileHelper), BarcodeFormat.QR_CODE, 720, 720)
        val encoder = BarcodeEncoder()
        qrContainer.setImageBitmap(encoder.createBitmap(bitMatrix))
    }

    override fun initViews(view: View) {
        qrContainer = view.findViewById(R.id.qrContainer)
    }

    override fun main() {
    }

    override fun onResume() {
        super.onResume()
        loadQrCode()
    }
}