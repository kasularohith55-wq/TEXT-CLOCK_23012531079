package com.example.textclock_23012531079
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var timeText: TextView
    private lateinit var secondsText: TextView
    private lateinit var ampmText: TextView
    private lateinit var dateText: TextView

    private val handler = Handler(Looper.getMainLooper())

    // formats
    private val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    private val secFormat = SimpleDateFormat("ss", Locale.getDefault())
    private val ampmFormat = SimpleDateFormat("a", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("EEE, MMM d yyyy", Locale.getDefault())

    private val tickRunnable = object : Runnable {
        override fun run() {
            val now = Date()
            timeText.text = timeFormat.format(now)
            secondsText.text = secFormat.format(now)
            ampmText.text = ampmFormat.format(now)
            dateText.text = dateFormat.format(now)

            // schedule precisely on next second boundary
            val delay = 1000L - (now.time % 1000L)
            handler.postDelayed(this, delay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeText = findViewById(R.id.timeText)
        secondsText = findViewById(R.id.secondsText)
        ampmText = findViewById(R.id.ampm)
        dateText = findViewById(R.id.dateText)

        // subtle continuous translation animation on the pulse view
        val pulse = findViewById<android.view.View>(R.id.pulse)
        val animator = ObjectAnimator.ofFloat(pulse, "alpha", 0.5f, 1f)
        animator.duration = 1200
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.start()
    }

    override fun onResume() {
        super.onResume()
        handler.post(tickRunnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(tickRunnable)
    }
}