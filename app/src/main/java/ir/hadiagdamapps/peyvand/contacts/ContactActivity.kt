package ir.hadiagdamapps.peyvand.contacts

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Activity
import ir.hadiagdamapps.peyvand.tools.Contact

class ContactActivity :Activity(R.layout.activity_contact) {
    private lateinit var imageView: ImageView
    private lateinit var nameText: TextView
    private lateinit var bioText: TextView
    private lateinit var telText: TextView
    private lateinit var saveContactButton: View

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
    }

    override fun main() {
        contact = intent.getParcelableExtra("contact")!!

        saveContactButton.setOnClickListener { saveContact() }
        nameText.text = contact.name.toString()
        bioText.text = contact.bio.toString()
        telText.text = contact.tel.toString()

        // TODO ("add image preview")
    }
}