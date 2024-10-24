package com.sameer.silentecho.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sameer.silentecho.R
import java.lang.StringBuilder

class SplashActivity : AppCompatActivity() {
    private lateinit var fadeIn: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in2)
        findViewById<ImageView>(R.id.imageView).startAnimation(fadeIn)
        findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
        autoText()

        Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
        },2000)

    }

    fun autoText() {
        val label = "Silent Echo"
        val stringBuilder = StringBuilder()
        Thread {
            try {
                for (letter in label) {
                    stringBuilder.append(letter)
                    Thread.sleep(120)
                    runOnUiThread {
                        findViewById<TextView>(R.id.team_name).text = stringBuilder.toString()
                    }
                }
            } catch (e: InterruptedException) {
                // Handle the interruption
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle other exceptions
                e.printStackTrace()
            }
        }.start()
    }
}