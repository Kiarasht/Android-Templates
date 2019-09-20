package com.restart.send

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deep_a.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("example://test/a")))
        }

        deep_b.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("example://test/b")))
        }

        deep_c.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("example://test/c")))
        }
    }
}
