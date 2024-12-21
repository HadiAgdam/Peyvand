package ir.hadiagdamapps.peyvand.contacts

import android.content.Intent
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Activity
import ir.hadiagdamapps.peyvand.tools.Clipboard
import ir.hadiagdamapps.peyvand.data.models.contact.Contact

class ContactActivity : Activity(R.layout.activity_contact) {
    private lateinit var imageView: ImageView
    private lateinit var nameText: TextView
    private lateinit var bioText: TextView
    private lateinit var telText: TextView
    private lateinit var saveContactButton: View
    private lateinit var backIcon: View
    private lateinit var copyTelIcon: View

    private lateinit var contact: Contact

    private fun saveContact() {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(ContactsContract.Intents.Insert.NAME, contact.name.toString())
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.tel.toString())

        startActivity(intent)
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
            Toast.makeText(this@ContactActivity, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
        }


        if(contact.picture != null)
            imageView.setImageBitmap(contact.picture!!.toBitmap())

    }
}