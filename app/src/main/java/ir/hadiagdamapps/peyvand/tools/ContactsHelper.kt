package ir.hadiagdamapps.peyvand.tools

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactsHelper(private val context: Context) {

    fun getContacts(): List<Contact> {
        TODO("implement a database system")
    }

}



class ContactsDatabaseHelper(private val context: Context): SQLiteOpenHelper(context, "", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}