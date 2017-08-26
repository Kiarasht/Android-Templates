package com.restart.sharingdata;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String ic_android_white;
    private String ic_android_green;
    private String ic_android_red;

    /**
     * Create basic views and listeners.
     * <p>
     * Permission is not part of this template and it does not follow the recommended steps.
     * Permission was added to create images from the res folder of this project on the phone to
     * create a context of when a photo on the phone needs to be shared.
     * https://developer.android.com/training/permissions/requesting.html
     *
     * @param savedInstanceState N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        Button textButton = (Button) findViewById(R.id.text_button);
        Button binaryContentButton = (Button) findViewById(R.id.binary_content_button);
        Button multipleContentButton = (Button) findViewById(R.id.multiple_content_button);

        textButton.setOnClickListener(this);
        binaryContentButton.setOnClickListener(this);
        multipleContentButton.setOnClickListener(this);

        ic_android_white = createImageOnSDCard(R.drawable.ic_android_white);
        ic_android_green = createImageOnSDCard(R.drawable.ic_android_green);
        ic_android_red = createImageOnSDCard(R.drawable.ic_android_red);
    }

    /**
     * Create an image on the phone's SD card to later be able to share it.
     *
     * @param resID resource ID of an image coming from the res folder.
     * @return Return the path of the image that was created on the phone SD card.
     */
    private String createImageOnSDCard(int resID) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resID);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + resID + ".jpg";
        File file = new File(path);
        try {
            OutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getPath();
    }

    /**
     * OnClickListeners for the three share functionality.
     *
     * @param view Incoming view that was clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_button:
                onShareText();
                break;
            case R.id.binary_content_button:
                onShareOnePhoto();
                break;
            case R.id.multiple_content_button:
                onShareMultiplePhotos();
                break;
        }
    }

    /**
     * Create and start intent to share a standard text value.
     */
    private void onShareText() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is a text I'm sharing.");
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share..."));
    }

    /**
     * Create and start intent to share a photo with apps that can accept a single image
     * of any format.
     */
    private void onShareOnePhoto() {
        Uri path = FileProvider.getUriForFile(this, "com.restart.sharingdata", new File(ic_android_green));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is one image I'm sharing.");
        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share..."));
    }

    /**
     * Create and start intent to share multiple photos with apps that can accept images
     * of any format.
     */
    private void onShareMultiplePhotos() {
        ArrayList<Uri> photos = new ArrayList<>();
        photos.add(FileProvider.getUriForFile(this, "com.restart.sharingdata", new File(ic_android_white)));
        photos.add(FileProvider.getUriForFile(this, "com.restart.sharingdata", new File(ic_android_green)));
        photos.add(FileProvider.getUriForFile(this, "com.restart.sharingdata", new File(ic_android_red)));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This multiple images I'm sharing.");
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, photos);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share..."));
    }
}
