package com.example.vitdmit.lab3colorlines;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vitdmit.lab3colorlines.Views.Field;

public class MainActivity extends AppCompatActivity {

    Field mImageView;
    Button mResetButton;
    Canvas mCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.fieldView);
        mImageView.setCurrentActivity(this);
        mResetButton = findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageView.reset();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//        Bitmap bitmap = Bitmap.createBitmap(width, height,  Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setColor(getResources().getColor(R.color.colorAccent));
//        paint.setStrokeWidth(5);
//        paint.setStyle(Paint.Style.STROKE);
//        final int sz  =100;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++)
//                mCanvas.drawRect(new Rect(i * sz, j * sz, sz, sz), paint);
//        }
//        mImageView.setImageBitmap(bitmap    );
    }
}
