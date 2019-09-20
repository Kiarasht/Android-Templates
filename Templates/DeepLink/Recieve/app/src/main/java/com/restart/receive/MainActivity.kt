package com.restart.receive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_a.setOnClickListener {
            startActivity(Intent(this, A::class.java))
        }

        activity_b.setOnClickListener {
            startActivity(Intent(this, B::class.java))
        }

        activity_c.setOnClickListener {
            startActivity(Intent(this, C::class.java))
        }
    }
}
