package com.restart.blur

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            blur_image_view.setImageBitmap(Blur.blur(R.id.activity_parent_layout, this))
            imageView.visibility = View.GONE
            imageView2.visibility = View.GONE
            textView.visibility = View.GONE
        }
    }
}
