package ir.hadiagdamapps.peyvand.connect

import android.view.View
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.MyFragment
import ir.hadiagdamapps.peyvand.tools.Profile
import ir.hadiagdamapps.peyvand.tools.ProfileHelper

class ConnectFragment : MyFragment(R.layout.fragment_connect) {

    private val writer = MultiFormatWriter()

    private lateinit var qrContainer: ImageView
    private lateinit var scanButton: View
    private val profileHelper by lazy {
        ProfileHelper(requireContext())
    }


    private fun scanCode() {
    }


    private fun loadQrCode() {
        val profile = profileHelper.getProfile() ?: return
        val bitMatrix = writer.encode(profile.toJson().toString(), BarcodeFormat.QR_CODE, 720, 720)
        val encoder = BarcodeEncoder()
        qrContainer.setImageBitmap(encoder.createBitmap(bitMatrix))
    }

    override fun initViews(view: View) {
        qrContainer = view.findViewById(R.id.qrContainer)
        scanButton = view.findViewById(R.id.scanButton)
        scanButton.setOnClickListener { scanCode() }
    }

    override fun main() {
    }

    override fun onResume() {
        super.onResume()
        loadQrCode()
    }
}