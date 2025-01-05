package ir.hadiagdamapps.peyvand.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.peyvand.data.Constants
import ir.hadiagdamapps.peyvand.data.models.contact.Contact
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel

class ContactsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, Constants.Database.NAME, null, Constants.Database.VERSION) {

    private val createTableQuery =
        "CREATE TABLE ${Constants.Database.Contacts.NAME} (" +
                "${Constants.Database.Contacts.Columns.ID} INTEGER PRIMARY KEY autoincrement," +
                "${Constants.Database.Contacts.Columns.NAME} TEXT," +
                "${Constants.Database.Contacts.Columns.PICTURE} TEXT," +
                "${Constants.Database.Contacts.Columns.TEL} TEXT," +
                "${Constants.Database.Contacts.Columns.BIO} TEXT," +
                "${Constants.Database.Contacts.Columns.PUBLIC_KEY} TEXT)"
    private val deleteTableQuery =
        "DROP TABLE IF exists ${Constants.Database.Contacts.NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(deleteTableQuery)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun delete(contact: Contact) {
        writableDatabase.apply {
            delete(Constants.Database.Contacts.NAME, "${Constants.Database.Contacts.Columns.ID} = ?", arrayOf(contact.id.toString()))
            close()
        }
    }

    fun insert(contact: Contact): Int {
        return writableDatabase.insert(Constants.Database.Contacts.NAME, null, ContentValues().apply {
            put(Constants.Database.Contacts.Columns.NAME, contact.name.toString())
            put(Constants.Database.Contacts.Columns.TEL, contact.tel.toString())
            put(Constants.Database.Contacts.Columns.PICTURE, contact.picture.toString())
            put(Constants.Database.Contacts.Columns.BIO, contact.bio.toString())
            contact.publicKey?.apply { put(Constants.Database.Contacts.Columns.PUBLIC_KEY, this) }
        }).toInt()
    }

    fun getAll(): List<Contact> {
        val cursor = readableDatabase.rawQuery(
            "select " +
                    "${Constants.Database.Contacts.Columns.ID}," +
                    "${Constants.Database.Contacts.Columns.NAME}," +
                    "${Constants.Database.Contacts.Columns.PICTURE}," +
                    "${Constants.Database.Contacts.Columns.TEL}," +
                    "${Constants.Database.Contacts.Columns.BIO}," +
                    " ${Constants.Database.Contacts.Columns.PUBLIC_KEY}" +
                    " from ${Constants.Database.Contacts.NAME}",
            null
        )

        return ArrayList<Contact>().apply {

            with(cursor) {
                if (moveToFirst()) do
                    add(
                        Contact(
                            id = getInt(0),
                            name = Name.parse(getString(1)) ?: continue,
                            picture = Picture.parse(getString(2)),
                            tel = Tel.parse(getString(3)) ?: continue,
                            bio = Bio.parse(getString(4)) ?: continue,
                            publicKey = getString(5),
                        )
                    )
                while (moveToNext())

                close()
            }
        }



    }
}