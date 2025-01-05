package ir.hadiagdamapps.peyvand.data

import android.content.Context
import ir.hadiagdamapps.peyvand.data.models.contact.Contact
import ir.hadiagdamapps.peyvand.data.db.ContactsDatabaseHelper

class ContactsHelper(private val context: Context) {

    private val database = ContactsDatabaseHelper(context)

    fun getAll() = database.getAll()

    fun delete(contact: Contact) = database.delete(contact)

    fun newContact(contact: Contact) = database.insert(contact)

}


