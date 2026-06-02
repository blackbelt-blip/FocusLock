package com.chidi.focuslock

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val minutesPicker = findViewById<NumberPicker>(R.id.minutesPicker)
        minutesPicker.minValue = 1
        minutesPicker.maxValue = 180
        minutesPicker.value = 25

        findViewById<Button>(R.id.startBtn).setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
                return@setOnClickListener
            }
            val intent = Intent(this, LockActivity::class.java)
            intent.putExtra("minutes", minutesPicker.value)
            startActivity(intent)
        }

        findViewById<Button>(R.id.accessBtn).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }
}
