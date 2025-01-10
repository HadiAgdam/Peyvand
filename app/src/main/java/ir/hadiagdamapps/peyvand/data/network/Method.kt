package ir.hadiagdamapps.peyvand.data.network

import com.android.volley.Request

enum class Method(val volleyMethod: Int) {

    PUT(Request.Method.PUT),
    GET(Request.Method.GET),
    POST(Request.Method.POST)

}