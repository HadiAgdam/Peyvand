package ir.hadiagdamapps.peyvand.data.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import ir.hadiagdamapps.peyvand.data.storage.ProfileUpdateManager

class ProfileUpdateService : Service() {

    private var looping = false
    private val updateManager = ProfileUpdateManager(baseContext)

    private fun tick() {
        updateManager.sync()
    }

    private fun loop() {
        looping = true
        Thread {
            while (looping) {
                try {
                    tick()
                } catch (ex: Exception) {
                    Log.e("error", ex.toString())
                }
                Thread.sleep(3000)
            }
        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        loop()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null
}