package ir.hadiagdamapps.peyvand.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import ir.hadiagdamapps.peyvand.data.Constants
import ir.hadiagdamapps.peyvand.data.models.contact.Contact
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel
import ir.hadiagdamapps.peyvand.data.Constants.Database.Contacts.Columns
import ir.hadiagdamapps.peyvand.data.models.contact.ContactUpdate
import ir.hadiagdamapps.peyvand.data.models.social_media.LinkedSocialMedias
import org.json.JSONObject

class ContactsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, Constants.Database.NAME, null, Constants.Database.VERSION) {

    private val createTableQuery =
        "CREATE TABLE ${Constants.Database.Contacts.NAME} (" +
                "${Columns.ID} INTEGER PRIMARY KEY autoincrement," +
                "${Columns.NAME} TEXT," +
                "${Columns.PICTURE} TEXT," +
                "${Columns.TEL} TEXT," +
                "${Columns.BIO} TEXT," +
                "${Columns.PUBLIC_KEY} TEXT," +
                "${Columns.SOCIAL_MEDIAS} TEXT)"
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
            delete(
                Constants.Database.Contacts.NAME,
                "${Columns.ID} = ?",
                arrayOf(contact.id.toString())
            )
            close()
        }
    }

    fun insert(contact: Contact): Int {
        return writableDatabase.insert(
            Constants.Database.Contacts.NAME,
            null,
            ContentValues().apply {
                put(Columns.NAME, contact.name.toString())
                put(Columns.TEL, contact.tel.toString())
                put(Columns.PICTURE, contact.picture.toString())
                put(Columns.BIO, contact.bio.toString())
                contact.publicKey?.let { put(Columns.PUBLIC_KEY, it) }
                put(Columns.SOCIAL_MEDIAS, contact.socialMedias.toString())
            }).toInt()
    }

    fun getAll(): List<Contact> {
        val cursor = readableDatabase.rawQuery(
            "select " +
                    "${Columns.ID}," +
                    "${Columns.NAME}," +
                    "${Columns.PICTURE}," +
                    "${Columns.TEL}," +
                    "${Columns.BIO}," +
                    "${Columns.PUBLIC_KEY}," +
                    Columns.SOCIAL_MEDIAS +
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
                            picture = getStringOrNull(2)?.let { Picture.parse(it) },
                            tel = Tel.parse(getString(3)) ?: continue,
                            bio = Bio.parse(getString(4)) ?: continue,
                            publicKey = getString(5),
                            socialMedias = LinkedSocialMedias.fromJson(getStringOrNull(6)?.let {
                                JSONObject(
                                    it
                                )
                            })
                        )
                    )
                while (moveToNext())

                close()
            }
        }


    }

    fun update(contact: Contact) {
        writableDatabase.update(Constants.Database.Contacts.NAME, ContentValues().apply {
            put(Columns.NAME, contact.name.toString())
            put(Columns.TEL, contact.tel.toString())
            put(Columns.PICTURE, contact.picture.toString())
            put(Columns.BIO, contact.bio.toString())
            put(Columns.SOCIAL_MEDIAS, contact.socialMedias.toString())
        }, "{=${Columns.ID} = ?", arrayOf(contact.id.toString()))
    }

    fun update(contact: ContactUpdate) {
        writableDatabase.update(Constants.Database.Contacts.NAME, ContentValues().apply {
            put(Columns.NAME, contact.name.toString())
            put(Columns.PICTURE, contact.pictureUrl)
            put(Columns.BIO, contact.bio.toString())
            put(Columns.SOCIAL_MEDIAS, contact.socialMedias.toString())
        }, "${Columns.PUBLIC_KEY} = ?", arrayOf(contact.publicKey.toString()))
    }
}