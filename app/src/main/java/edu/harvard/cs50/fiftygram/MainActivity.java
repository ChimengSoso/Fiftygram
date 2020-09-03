package edu.harvard.cs50.fiftygram;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;

import java.io.FileDescriptor;
import java.io.IOException;

import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private ImageView imageView;
    private Bitmap original;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);

//        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void apply(Transformation<Bitmap> filter) {
        if (original != null) {
            Glide
                    .with(this)
                    .load(original)
                    .apply(RequestOptions.bitmapTransform(filter))
                    .into(imageView);
        }
    }

    public void saveImage(View view) {
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "New_Image", "Cat");
    }

    public void applySepia(View view) {
        apply(new SepiaFilterTransformation());
    }

    public void applyToon(View view) {
        apply(new ToonFilterTransformation());
    }

    public void applySketch(View view) {
        apply(new SketchFilterTransformation());
    }

    public void applyContrast(View view) {
        apply(new ContrastFilterTransformation());
    }

    public void applyInvert(View view) {
        apply(new InvertFilterTransformation());
    }

    public void applyPixel(View view) {
        apply(new PixelationFilterTransformation());
    }

    public void applyBright(View view) {
        apply(new BrightnessFilterTransformation());
    }

    public void choosePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                ParcelFileDescriptor parcelFileDescriptor =
                        getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                original = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                imageView.setImageBitmap(original);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
