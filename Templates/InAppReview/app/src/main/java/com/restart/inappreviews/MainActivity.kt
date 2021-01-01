package com.restart.inappreviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch_review.setOnClickListener {
            val manager = ReviewManagerFactory.create(this)

            val reviewFlow = manager.requestReviewFlow()
            reviewFlow.addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo = request.result

                    val flow = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener {
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                    }
                } else {
                    Toast.makeText(this, "Failed in requesting review flow", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}