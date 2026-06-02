package com.chidi.focuslock

import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class LockService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channel = NotificationChannel("lock", "Focus Lock", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notif = NotificationCompat.Builder(this, "lock")
            .setContentTitle("Focus Lock active")
            .setContentText("Calls and SMS will still come through")
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .build()
        startForeground(1, notif)
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
