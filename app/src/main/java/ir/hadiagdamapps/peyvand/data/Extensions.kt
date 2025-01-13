package ir.hadiagdamapps.peyvand.data

import org.json.JSONObject


fun JSONObject.getStringOrNull(key: String): String? = optString(key, null)

