package ir.hadiagdamapps.peyvand.data

import org.json.JSONObject


fun JSONObject.getNullString(key: String): String? = optString(key, null)

