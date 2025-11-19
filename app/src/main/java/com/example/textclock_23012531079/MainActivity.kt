package com.example.textclock_23012531079

import android.os.Bundle
import android.view.View
import android.widget.TextClock // Import TextClock
import android.widget.Toast     // Import Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Recommended for modern UI
        setContentView(R.layout.activity_main)

        // Set up insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Correctly find the TextClock by its ID
        val textClock = findViewById<TextClock>(R.id.textClock)

        // 2. Use the correct method name "setOnClickListener" and the correct variable name "textClock"
        textClock.setOnClickListener {
            // 3. Use the correct syntax for Toast.makeText and .show()
            Toast.makeText(this@MainActivity, "TextClock Clicked!", Toast.LENGTH_LONG).show()
        }
    }
}
