package ir.hadiagdamapps.peyvand.data

class Constants {

    class Database {
        companion object {
            // should increment the version if there was a change in schema
            const val VERSION = 1
            const val NAME = "peyvand_database"
        }

        class Contacts {
            class Columns {
                companion object {
                    const val ID = "contact_id"
                    const val NAME = "name"
                    const val PICTURE = "picture"
                    const val TEL = "tel"
                    const val BIO = "bio"
                    const val PUBLIC_KEY = "public_key"
                }

            }

            companion object {
                const val NAME = "contacts"
            }
        }
    }

}

