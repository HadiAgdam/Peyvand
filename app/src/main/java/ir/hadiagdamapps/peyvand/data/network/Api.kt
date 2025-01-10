package ir.hadiagdamapps.peyvand.data.network

import android.util.Log
import com.android.volley.NoConnectionError
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

open class Api(private val queue: RequestQueue) {

    companion object {
        const val BASE_URL = "https://hadiagdam0.pythonanywhere.com"
    }


    private fun VolleyError.toApiError(): ApiError? {
        return if (this.cause is NoConnectionError || this.networkResponse == null)
            ApiError.NO_CONNECTION
        else if (this.networkResponse.statusCode == 401)
            ApiError.LOGIN_FAILED
        else null
    }


    fun newRequest(
        url: String = BASE_URL,
        method: Method,
        json: JSONObject,
        onSuccess: (response: JSONObject) -> Unit,
        onFailed: (ApiError) -> Unit = {},
        finally: () -> Unit = {},
        label: String = url
    ) {
        JsonObjectRequest(
            method.volleyMethod,
            url,
            json,
            {
                onSuccess(it)
                finally()
            },
            {
                finally()
                it.toApiError()?.let { error -> onFailed(error) }
                Log.e("Api failed", label)
            }).let {
            queue.add(it)
        }
    }
}