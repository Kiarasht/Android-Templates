package com.restart.lottieanimations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation_code.setAnimation(R.raw.second)
        animation_code.repeatCount = LottieDrawable.INFINITE
        animation_code.playAnimation()

        val lottieDrawable = LottieDrawable()
        LottieCompositionFactory.fromRawRes(this, R.raw.third).addListener {
            lottieDrawable.speed = 1.25f
            lottieDrawable.scale = 1.25f
            lottieDrawable.repeatCount = LottieDrawable.INFINITE
            lottieDrawable.enableMergePathsForKitKatAndAbove(true)
            lottieDrawable.composition = it
        }

        animation_composition.setImageDrawable(lottieDrawable)
        lottieDrawable.playAnimation()
    }
}