package ir.hadiagdamapps.peyvand.contacts

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.models.contact.Contact
import ir.hadiagdamapps.peyvand.data.ContactsHelper
import ir.hadiagdamapps.peyvand.data.MyFragment
import ir.hadiagdamapps.peyvand.data.TextValidator

class ContactsFragment : MyFragment(R.layout.fragment_contacts) {


    private lateinit var scanButton: View
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ContactsRecyclerAdapter

    private val contactsHelper by lazy {
        ContactsHelper(requireContext())
    }

    private val scanOptions by lazy {
        ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt(getString(R.string.scan_qr_code))
            setCameraId(0)
            setBeepEnabled(false)
            setOrientationLocked(false)
            setBarcodeImageEnabled(true)
        }
    }

    private val qrLauncher = registerForActivityResult(ScanContract()) { scanResult ->
        if (scanResult.contents != null) {
            if (TextValidator.isValidQrString(scanResult.contents))
                Contact.parseFromURL(scanResult.contents)?.let {
                    contactsHelper.newContact(it)
                    adapter.addItem(it)
                    startActivity(Intent(requireContext(), ContactActivity::class.java).apply {
                        putExtra("contact", it)
                    })
                } ?: Toast.makeText(
                    requireContext(), getString(R.string.invalid_qr), Toast.LENGTH_LONG
                ).show()
            else Toast.makeText(
                requireContext(),
                getString(R.string.invalid_qr),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            if (granted) {
                qrLauncher.launch(scanOptions)
            } else {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(R.string.permission_dialog_title)
                    setMessage(R.string.permission_dialog_text_camera)

                    setNeutralButton(R.string.dismiss, null)

                    setPositiveButton(R.string.ok) { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 0)
                    }

                }.show()
            }
        }


    private fun initContent() {
        adapter = ContactsRecyclerAdapter(requireContext())
        recycler.adapter = adapter
    }


    private fun scanCode() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    override fun initViews(view: View) {
        recycler = view.findViewById(R.id.recycler)
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        scanButton = view.findViewById(R.id.scanButton)
        scanButton.setOnClickListener { scanCode() }
    }

    override fun main() {
        initContent()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            qrLauncher.launch(scanOptions)
        }
    }
}