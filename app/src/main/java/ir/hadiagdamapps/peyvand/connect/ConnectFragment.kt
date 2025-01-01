package ir.hadiagdamapps.peyvand.connect

import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.MyFragment
import ir.hadiagdamapps.peyvand.data.ProfileHelper

class ConnectFragment : MyFragment(R.layout.fragment_connect) {

    private val writer = MultiFormatWriter()
    private val profileHelper by lazy { ProfileHelper(requireContext()) }

    private lateinit var qrContainer: ImageView
    private val dialog by lazy { BottomSheetDialog(requireContext()) }
    private val dialogView = layoutInflater.inflate(R.layout.share_bottom_menu_dialog, null, false)

    private fun loadQrCode() {
        val bitMatrix = profileHelper.getProfile()?.let {
            writer.encode(
                profileHelper.toQrString(it), BarcodeFormat.QR_CODE, 720, 720
            )
        } ?: return
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