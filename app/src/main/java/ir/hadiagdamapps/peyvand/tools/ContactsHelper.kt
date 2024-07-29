package ir.hadiagdamapps.peyvand.tools

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.peyvand.tools.Constants.Database;
import ir.hadiagdamapps.peyvand.tools.Constants.Database.Contacts.Columns;

class ContactsHelper(private val context: Context) {

    private val database = ContactsDatabaseHelper(context)

    fun getAll() = database.getAll()

    fun delete(contact: Contact) = database.delete(contact)

    fun newContact(contact: Contact) = database.insert(contact)

}


class ContactsDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, Database.NAME, null, Database.VERSION) {

    private val createTableQuery =
        "CREATE TABLE ${Database.Contacts.NAME} (" +
                "${Columns.ID} INTEGER PRIMARY KEY autoincrement," +
                "${Columns.NAME} TEXT," +
                "${Columns.PICTURE} TEXT," +
                "${Columns.TEL} TEXT," +
                "${Columns.BIO} TEXT)"
    private val deleteTableQuery =
        "DROP TABLE IF exists ${Database.Contacts.NAME}"

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
        writableDatabase.rawQuery(
            "DELETE FROM ${Database.Contacts.NAME} WHERE (${Columns.ID}=${contact.id})",
            null
        ).close()
    }

    fun insert(contact: Contact): Int {
        val values = ContentValues().apply {
            put(Columns.NAME, contact.name.toString())
            put(Columns.TEL, contact.tel.toString())
            put(Columns.PICTURE, contact.picture.toString())
            put(Columns.BIO, contact.bio.toString())
        }

        return writableDatabase.insert(Database.Contacts.NAME, null, values).toInt()
    }

    fun getAll(): List<Contact> {
        val items = ArrayList<Contact>()
        val cursor = readableDatabase.rawQuery(
            "SELECT (" +
                    "${Columns.ID}," +
                    "${Columns.NAME}," +
                    "${Columns.PICTURE}," +
                    "${Columns.TEL}," +
                    Columns.BIO +
                    ") from ${Database.Contacts.NAME}",
            null
        )

        with(cursor) {
            while (moveToNext()) {

                val id = getInt(0)
                val name = Name.parse(getString(1)) ?: continue
                val picture = Picture.parse(getString(2)) ?: continue
                val tel = Tel.parse(getString(3)) ?: continue
                val bio = Bio.parse(getString(4)) ?: continue

                items.add(Contact(id, name, picture, tel, bio))

            }
        }


        cursor.close()


        return items
    }
}