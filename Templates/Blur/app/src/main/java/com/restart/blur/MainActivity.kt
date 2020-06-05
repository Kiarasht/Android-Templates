package com.restart.blur

import android.os.Bundle
import android.util.Log
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
            val start = System.currentTimeMillis()

            blur_image_view.setImageBitmap(Blur.blur(R.id.activity_parent_layout, this, 4, 25f))

            Log.i("Duration", ((System.currentTimeMillis() - start)).toString() + " ms")

            imageView.visibility = View.GONE
            imageView2.visibility = View.GONE
            textView.visibility = View.GONE
        }

        imageView2.setOnClickListener {
            val start = System.currentTimeMillis()

            imageView2.setImageBitmap(Blur.blur(R.id.imageView2, this, 2))

            Log.i("Duration", ((System.currentTimeMillis() - start)).toString() + " ms")

            imageView.visibility = View.GONE
            textView.visibility = View.GONE
        }
    }
}
