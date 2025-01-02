package ir.hadiagdamapps.peyvand.connect

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.Clipboard
import ir.hadiagdamapps.peyvand.data.ProfileHelper
import ir.hadiagdamapps.peyvand.data.storage.FileManager

class ConnectFragment : Fragment() {

    private val encoder = BarcodeEncoder()
    private val writer = MultiFormatWriter()
    private val profileHelper by lazy { ProfileHelper(requireContext()) }
    private var qrCodeBitmap: Bitmap? = null
    private val fileManager by lazy { FileManager(requireContext().contentResolver) }
    private val saveLauncher = registerForActivityResult(CreateDocument()) { uri: Uri? ->
        try {
            fileManager.saveBitmapToUri(qrCodeBitmap!!, uri!!)
            Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "failed", Toast.LENGTH_LONG).show()
        }
    }
    private var qrString: String? = null

    private lateinit var qrContainer: ImageView
    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogView: View

    private lateinit var copyLinkDialogButton: TextView
    private lateinit var saveQrCodeButton: View

    private fun loadQrCode() {
        val bitMatrix = profileHelper.getProfile()
            ?.let {
                writer.encode(
                    profileHelper.toQrString(it),
                    BarcodeFormat.QR_CODE,
                    720,
                    720
                )
            } ?: return

        encoder.createBitmap(bitMatrix)?.apply {
            qrCodeBitmap = this
            qrContainer.setImageBitmap(qrCodeBitmap)
        } ?: Toast.makeText(requireContext(), "null", Toast.LENGTH_LONG).show()

    }


    private fun saveQrCode() {
        saveLauncher.launch("peyvand.png")
    }

    private fun initDialog(inflater: LayoutInflater, container: ViewGroup?) {
        dialog = BottomSheetDialog(requireContext())
        dialogView = inflater.inflate(R.layout.share_bottom_menu_dialog, container, false)

        dialog.setContentView(dialogView)

        copyLinkDialogButton = dialogView.findViewById(R.id.option_copy)
        saveQrCodeButton = dialogView.findViewById(R.id.option_save)

        qrContainer.setOnClickListener { dialog.show() }

        val profileUrl = profileHelper.getProfileUrl()
        if (profileUrl == null) {
            copyLinkDialogButton.visibility = View.GONE
        }

        copyLinkDialogButton.setOnClickListener {
            dialog.hide()
            Clipboard.copy(requireContext(), profileUrl!!)
            Toast.makeText(
                requireContext(),
                getString(R.string.copied_to_clipboard),
                Toast.LENGTH_LONG
            ).show()
        }

        saveQrCodeButton.setOnClickListener {
            dialog.hide()
            qrCodeBitmap?.apply {
                saveQrCode()
            } ?: Toast.makeText(
                requireContext(),
                getString(R.string.save_qr_code_failed),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connect, container, false).apply {
            qrContainer = findViewById(R.id.qrContainer)
            initDialog(inflater, container)
            loadQrCode()
        }

    }
}