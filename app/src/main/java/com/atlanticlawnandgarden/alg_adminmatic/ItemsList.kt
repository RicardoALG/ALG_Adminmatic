package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_items_list.*

class ItemsList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@ItemsList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        ItemBtn.setOnClickListener(({
            var clickintent = Intent(this@ItemsList, Item::class.java)
            startActivity(clickintent)
        }))
    }
}
