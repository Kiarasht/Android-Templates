package com.restart.receivedata

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                when {
                    intent.type == PLAIN_TEXT_MIME -> handleSendText(intent)
                    intent.type?.startsWith(MEDIA_IMAGE_MIME) == true -> handleSendImage(intent)
                    intent.type?.startsWith(MEDIA_Audio_MIME) == true -> handleSendAudio(intent)
                }
            }

            intent?.action == Intent.ACTION_SEND_MULTIPLE -> {
                when {
                    intent.type?.startsWith(MEDIA_IMAGE_MIME) == true -> handleSendMultipleImages(intent)
                    intent.type?.startsWith(MEDIA_Audio_MIME) == true -> handleSendMultipleAudios(intent)
                }

            }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            textView.text = it
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            first_imageView.setImageURI(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSendAudio(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            textView.text = "Playing Audio"

            MediaPlayer().apply {
                setDataSource(applicationContext, it)
                prepare()
                start()
            }
        }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            first_imageView.setImageURI(it[0] as? Uri)
            second_imageView.setImageURI(it[1] as? Uri)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSendMultipleAudios(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            textView.text = "Playing Multiple Audios"

            it.forEachIndexed { index, _ ->
                MediaPlayer().apply {
                    setDataSource(applicationContext, it[index] as Uri)
                    prepare()
                    start()
                }
            }
        }
    }

    companion object {
        private const val PLAIN_TEXT_MIME = "text/plain"
        private const val MEDIA_IMAGE_MIME = "image/"
        private const val MEDIA_Audio_MIME = "audio/"
    }
}
