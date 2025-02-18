package ir.hadiagdamapps.peyvand.contacts

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.Activity
import ir.hadiagdamapps.peyvand.data.Clipboard
import ir.hadiagdamapps.peyvand.data.models.contact.Contact

class ContactActivity : Activity(R.layout.activity_contact) {

    private lateinit var imageView: ImageView
    private lateinit var nameText: TextView
    private lateinit var bioText: TextView
    private lateinit var telText: TextView
    private lateinit var saveContactButton: View
    private lateinit var backIcon: View
    private lateinit var copyTelIcon: View
    private lateinit var telegramContainer: View
    private lateinit var telegramText: TextView
    private lateinit var instagramContainer: View
    private lateinit var instagramText: TextView
    private lateinit var xContainer: View
    private lateinit var xText: TextView

    private lateinit var contact: Contact

    private fun saveContact() {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(ContactsContract.Intents.Insert.NAME, contact.name.toString())
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.tel.toString())

        startActivity(intent)
    }

    private fun initSocialMedias() {
        contact.socialMedias.x?.let {
            xContainer.visibility = View.VISIBLE
            xText.text = it

            xContainer.setOnClickListener { _ ->
                val uri = Uri.parse("https://x.com/$it")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(intent)
                } catch (ex: Exception) {
                    Toast.makeText(this, R.string.x, Toast.LENGTH_SHORT).show()
                }
            }
        }
        contact.socialMedias.telegram?.let {
            telegramContainer.visibility = View.VISIBLE
            telegramText.text = it

            telegramContainer.setOnClickListener { _ ->
                val uri = Uri.parse("tg://resolve?domain=$it")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(intent)
                } catch (ex: Exception) {
                    Toast.makeText(this, R.string.telegra_not_installed, Toast.LENGTH_SHORT).show()
                }
            }
        }
        contact.socialMedias.instagram?.let {
            instagramContainer.visibility = View.VISIBLE
            instagramText.text = it


            instagramContainer.setOnClickListener { _ ->
                val uri = Uri.parse("http://instagram.com/_u/$it")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.instagram.android")
                try {
                    startActivity(intent)
                } catch (ex: Exception) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://instagram.com/$it")
                        )
                    )
                }
            }
        }

    }

    override fun initViews() {
        imageView = findViewById(R.id.image)
        nameText = findViewById(R.id.nameText)
        bioText = findViewById(R.id.bioText)
        telText = findViewById(R.id.telText)
        saveContactButton = findViewById(R.id.saveContactButton)
        saveContactButton.setOnClickListener { saveContact() }
        backIcon = findViewById(R.id.backIcon)
        backIcon.setOnClickListener { finish() }
        copyTelIcon = findViewById(R.id.copyTelIcon)
        instagramContainer = findViewById(R.id.instagramContainer)
        instagramText = findViewById(R.id.instagramText)
        telegramContainer = findViewById(R.id.telegramContainer)
        telegramText = findViewById(R.id.telegramText)
        xContainer = findViewById(R.id.xContainer)
        xText = findViewById(R.id.xText)
    }

    override fun main() {
        contact = intent.getParcelableExtra("contact")!!

        saveContactButton.setOnClickListener { saveContact() }
        nameText.text = contact.name.toString()
        bioText.text = contact.bio.toString()
        telText.text = contact.tel.toString()
        copyTelIcon.setOnClickListener {
            Clipboard.copy(
                this@ContactActivity,
                contact.tel.toString()
            )
            Toast.makeText(this@ContactActivity, R.string.copied_to_clipboard, Toast.LENGTH_SHORT)
                .show()
        }

        if (contact.picture != null)
            imageView.setImageBitmap(contact.picture!!.toBitmap())

        initSocialMedias()
    }
}