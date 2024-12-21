package ir.hadiagdamapps.peyvand.register

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import ir.hadiagdamapps.peyvand.MainActivity
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Activity

class RegisterActivity : Activity(R.layout.activity_register) {


    private val privacyDialog by lazy {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.privacy_title)
            setMessage(R.string.privacy_content)

            setPositiveButton(R.string.yes) { _, _ -> }
            setNegativeButton(R.string.no) { _, _ -> }

            setCancelable(false)
        }.create()
    }

    private val askPrivacyDialog by lazy {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.privacy_title)
            setMessage(R.string.ask_privacy)

            setPositiveButton(R.string.ok) { _, _ -> privacyDialog.show() }

            setCancelable(false)
        }.create()
    }


    private val pager: ViewPager2 by lazy { findViewById(R.id.pager) }
    private val nextButton: Button by lazy { findViewById(R.id.nextButton) }
    private val backButton: Button by lazy { findViewById(R.id.backButton) }
    private val adapter by lazy {
        RegisterPagerAdapter(this, pager, supportFragmentManager, lifecycle) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    override fun initViews() {
        pager.adapter = adapter
        nextButton.setOnClickListener { adapter.page() }
        backButton.setOnClickListener { adapter.back() }
        pager.isUserInputEnabled = false
    }

    override fun main() {
        askPrivacyDialog.show()
    }
}