package ir.hadiagdamapps.peyvand.data.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class ApiSingleton private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: ApiSingleton? = null

        fun getInstance(context: Context): ApiSingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiSingleton(context).also { INSTANCE = it }
            }
        }
    }

    private val requestQueue: RequestQueue =
        Volley.newRequestQueue(context.applicationContext)


    fun getRequestQueue(): RequestQueue = requestQueue
}