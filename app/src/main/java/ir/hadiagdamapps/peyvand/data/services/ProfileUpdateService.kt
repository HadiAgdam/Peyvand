package ir.hadiagdamapps.peyvand.data.services

import android.content.Context
import android.os.Handler
import android.os.Looper
import ir.hadiagdamapps.peyvand.data.storage.ProfileUpdateManager

class ProfileUpdateService(context: Context) {

    companion object {
        private const val DELAY = 3000L
    }

    private val updateManager = ProfileUpdateManager(context)
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    private fun tick() {
        updateManager.sync()
    }

    fun start() {
        runnable = object : Runnable {
            override fun run() {
                tick()
                handler.postDelayed(this, DELAY)
            }
        }

        handler.post(runnable!!)
    }

    fun stop() {
        runnable?.let { handler.removeCallbacks(it) }
    }

}