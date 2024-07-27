package ir.hadiagdamapps.peyvand.connect

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
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
import ir.hadiagdamapps.peyvand.tools.Profile
import ir.hadiagdamapps.peyvand.tools.ProfileHelper
import ir.hadiagdamapps.peyvand.tools.TextValidator

class ConnectFragment : MyFragment(R.layout.fragment_connect) {

    private val writer = MultiFormatWriter()

    private lateinit var qrContainer: ImageView
    private lateinit var scanButton: View
    private val profileHelper by lazy {
        ProfileHelper(requireContext())
    }
    private val contactsHelper by lazy {
        ContactsHelper(requireContext())
    }
    private val scanOptions = ScanOptions().apply {
        setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        setPrompt(getString(R.string.scan_qr_code))
        setCameraId(0)
        setBeepEnabled(false)
        setOrientationLocked(false)
        setBarcodeImageEnabled(true)
    }


    private val qrLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            if (TextValidator.isValidQr(it.contents)) {
                val contact = Contact.parseFromURL(it.contents)
                if (contact == null)
                    Toast.makeText(requireContext(), getString(R.string.invalid_qr), Toast.LENGTH_LONG)
                        .show()
                else {
                    contactsHelper.newContact(contact)
                    startActivity(Intent(requireContext(), ContactActivity::class.java).apply {
                        putExtra("contact", contact)
                    })
                }
            } else
                Toast.makeText(requireContext(), getString(R.string.invalid_qr), Toast.LENGTH_LONG)
                    .show()
        }
    }


    private fun scanCode() {
        qrLauncher.launch(scanOptions)
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