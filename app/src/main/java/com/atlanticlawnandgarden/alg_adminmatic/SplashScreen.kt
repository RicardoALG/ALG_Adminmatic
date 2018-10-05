package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@SplashScreen, MainMenu::class.java)
            startActivity(clickintent)
        }))
    }
}
