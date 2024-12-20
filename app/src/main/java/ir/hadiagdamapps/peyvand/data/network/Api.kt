package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.NoConnectionError
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest

open class Api {

    companion object {
        const val BASE_URL = ""
    }


    fun VolleyError.toApiError(): ApiError? {
        return if (this.cause is NoConnectionError || this.networkResponse == null)
            ApiError.NO_CONNECTION
        else null
    }


}