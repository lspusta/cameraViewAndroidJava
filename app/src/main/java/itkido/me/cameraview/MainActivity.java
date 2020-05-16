package itkido.me.cameraview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    CameraView camera;
    Button btnTakePicture;
    ImageView thumbnail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = findViewById(R.id.camera);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        thumbnail = findViewById(R.id.thumbnail);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture();
            }
        });

        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(PictureResult result) {
                // A Picture was taken!
                Log.d(TAG, "onPictureTaken: a picture was taken");

                result.toBitmap(1920, 1080, new BitmapCallback() {
                    @Override
                    public void onBitmapReady(@Nullable Bitmap bitmap) {

                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(bitmap)
                                .into(thumbnail);
                    }
                });


            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }
}
