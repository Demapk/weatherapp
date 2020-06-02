package ru.demapk.weatherapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ru.demapk.weatherapp.R
import ru.demapk.weatherapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()

    private val navigateTask = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(navigateTask, 1000)

    }


}
