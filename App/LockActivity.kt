package com.chidi.focuslock

import android.app.KeyguardManager
import android.content.Context
import android.os.*
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LockActivity : AppCompatActivity() {
    private var secondsLeft = 0
    private lateinit var timer: CountDownTimer
    private lateinit var timerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        val minutes = intent.getIntExtra("minutes", 25)
        secondsLeft = minutes * 60
        timerText = findViewById(R.id.timerText)
        findViewById<TextView>(R.id.watermark).text = "built by CHIDI"

        startService(Intent(this, LockService::class.java))

        timer = object : CountDownTimer((secondsLeft * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsLeft = (millisUntilFinished / 1000).toInt()
                val m = secondsLeft / 60
                val s = secondsLeft % 60
                timerText.text = String.format("%02d:%02d", m, s)
            }
            override fun onFinish() { finish() }
        }.start()
    }

    override fun onBackPressed() { /* blocked */ }

    override fun onPause() {
        super.onPause()
        // Bring back to front - accessibility service helps enforce
        val am = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        am.moveTaskToFront(taskId, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        stopService(Intent(this, LockService::class.java))
    }
}
