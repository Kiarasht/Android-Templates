package com.restart.shareaudiofiles

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.FileUtils
import java.io.File


class MainActivity : AppCompatActivity(), View.OnClickListener {
    /** See manifest where authorities is defined as well */
    private val authorities = "com.restart.shareaudiofiles.fileprovider"
    private var firstAudio: File? = null
    private var secondAudio: File? = null
    private var thirdAudio: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        saveAudiosToPhone()

        share_one_audio.setOnClickListener(this)
        share_three_audio.setOnClickListener(this)
    }

    private fun shareOneAudio() {
        val path = FileProvider.getUriForFile(this, authorities, firstAudio!!)

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, path)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "audio/mp3"
        startActivity(Intent.createChooser(shareIntent, "Share..."))
    }

    private fun shareThreeAudios() {
        val paths = ArrayList<Uri>()
        paths.add(FileProvider.getUriForFile(this, authorities, firstAudio!!))
        paths.add(FileProvider.getUriForFile(this, authorities, secondAudio!!))
        paths.add(FileProvider.getUriForFile(this, authorities, thirdAudio!!))

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, paths)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "audio/mp3"
        startActivity(Intent.createChooser(shareIntent, "Share..."))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.share_one_audio -> shareOneAudio()
            R.id.share_three_audio -> shareThreeAudios()
        }
    }

    /**
     * Save three audio files located in res -> raw folder into phone download directory to share later.
     * @see R.raw.example
     * @see R.raw.example_2
     * @see R.raw.example_3
     */
    private fun saveAudiosToPhone() {
        val downloadPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).absolutePath + "/"

        var inputStream = resources.openRawResource(R.raw.example)
        firstAudio = File(downloadPath + "audio.mp3")
        FileUtils.copyInputStreamToFile(inputStream, firstAudio)

        inputStream = resources.openRawResource(R.raw.example_2)
        secondAudio = File(downloadPath + "audio_2.mp3")
        FileUtils.copyInputStreamToFile(inputStream, secondAudio)

        inputStream = resources.openRawResource(R.raw.example_3)
        thirdAudio = File(downloadPath + "audio_3.mp3")
        FileUtils.copyInputStreamToFile(inputStream, thirdAudio)
    }
}
