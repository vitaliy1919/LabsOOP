package com.example.vitdmit.lab3colorlines.Views;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.vitdmit.lab3colorlines.Logic.Cell;
import com.example.vitdmit.lab3colorlines.Logic.ColorLineLogic;
import com.example.vitdmit.lab3colorlines.R;

import java.util.ArrayList;

public class Field extends View {
    private float offsetX, offsetY;
    private int dimentions;
    private ArrayList<Point> points = new ArrayList<>();
    private boolean firstClick = true;
    private Cell firstClickCell;
    private Point activated;
    private Cell[][]field;
    private ColorLineLogic logic;
    private int height, width;
    private float cellSize;
    private int score;
    private Paint paint = new Paint();
    private Activity currentActivity;

    public Field(Context context) {
        super(context);
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Field,
                0, 0);

        try {
            dimentions = a.getInteger(R.styleable.Field_dimentions, 9);
            init();

        } finally {
            a.recycle();
        }
    }

    private void init() {
        score = 0;
        activated = null;
        firstClick = true;
        field = new Cell[dimentions][dimentions];
        for (int row = 0; row < dimentions; row++)
            for (int column = 0; column < dimentions; column++)
                field[row][column] = new Cell();
        logic = new ColorLineLogic(getContext(), field);
        logic.generateRandomBalls();
        invalidate();
    }
    private void drawCell(int row, int column, Canvas canvas, Paint paint) {
        float left = column*cellSize + offsetX;
        float top = row*cellSize + offsetY;
        canvas.drawRect(left, top, left + cellSize, top + cellSize, paint );
    }
    public Field(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int getColor(int resource) {
        return ContextCompat.getColor(getContext(),resource);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final float FILL_COEF = 0.8f;
        if (width < 0 || height < 0)
            return;



        offsetX = 0;
        offsetY = 0;

        if (height > width) {
            cellSize = (float)width / dimentions;
            offsetY = (float)(height - width) / 2.0f;
        } else {
            cellSize = (float)height / dimentions;
            offsetX = (float)(width - height) / 2.0f;
        }

        paint.setColor(getColor(R.color.colorAccent));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < dimentions; i++) {
            for (int j = 0; j < dimentions; j++) {
                float left = offsetX + j*cellSize;
                float top = offsetY + i*cellSize;
                if (activated != null && activated.x == i && activated.y == j) {
                    paint.setStyle(Paint.Style.FILL);

                    paint.setColor(getColor(R.color.gray_pressed));
                    paint.setAlpha(200);

                }

                canvas.drawRect(left, top, left + cellSize, top + cellSize, paint);
                if (activated != null && activated.x == i && activated.y == j) {
                    paint.setAlpha(255);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(getResources().getColor(R.color.colorAccent));

                }
            }
        }

        paint.setStyle(Paint.Style.FILL);
        for (int row = 0; row < dimentions; row++)
            for (int column = 0; column < dimentions; column++) {
                if (field[row][column].isUsed()) {

                    if (logic.getRecentlyAdded().contains(new Point(row, column))) {
                        paint.setColor(getColor(R.color.light_blue));
                        paint.setAlpha(128);
                        canvas.drawRect(offsetX + cellSize*column, offsetY + cellSize*row, offsetX + cellSize*column + cellSize, offsetY + cellSize*row+cellSize, paint);

                    }
                    paint.setAlpha(255);
                    paint.setColor(field[row][column].getColor());

                    canvas.drawCircle(offsetX + cellSize*column + cellSize/2, offsetY + cellSize*row + cellSize/2, FILL_COEF*cellSize/ 2, paint);

                }
            }
        int[] nextColors = logic.getNextColors();
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < nextColors.length; i++) {
            paint.setColor(nextColors[i]);
            canvas.drawCircle(offsetX + (dimentions - i - 1) * cellSize + cellSize/2 , cellSize/2, FILL_COEF * cellSize/2, paint)  ;
        }

        String s = "Score: " + Integer.toString(score);
        paint.setColor(getResources().getColor(R.color.design_default_color_primary_dark));
        DisplayMetrics dm = new DisplayMetrics();
        float density = getContext().getResources().getDisplayMetrics().density;
        paint.setTextSize(density*25);
        Rect bound = new Rect();
        paint.getTextBounds(s, 0, s.length(), bound);
        canvas.drawText(s, offsetX, offsetY - (bound.top - bound.bottom) - cellSize , paint);
        //canvas.drawRect(rectF, paint);

    }



    public void reset() {
        init();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.d("", event.toString());
        invalidate();
        if (event.getAction() == MotionEvent.ACTION_UP){
            int column = (int)((event.getX() - offsetX) / cellSize);
            int row = (int)((event.getY() - offsetY) / cellSize);
            if (!(row >= 0 && column >= 0 && row < dimentions && column < dimentions))
                return true;
            if (firstClick) {
                if (!field[row][column].isUsed())
                    return true;
                firstClickCell = field[row][column];
                firstClick = false;
                activated = new Point(row, column);
                invalidate();
            } else {
                if (field[row][column] == firstClickCell) {
                    activated = null;
                    firstClick = true;
                    return true;
                }

                if (field[row][column].isUsed()) {
                    activated = new Point(row, column);
                    firstClickCell = field[row][column];
                    return true;
                }
                if (!logic.existsPath(activated, new Point(row, column)))
                    return true;
                activated = null;
                firstClick = true;

                if (field[row][column] == firstClickCell)
                    return true;

                firstClickCell.setUsed(false);
                field[row][column].setUsed(true);
                field[row][column].setColor(firstClickCell.getColor());
                int scoreChange = logic.findConsecutiveBalls();
                if (scoreChange != 0) {
                    score += scoreChange;
                    return  true;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                boolean gameNotFinished = true;
                do {
                    gameNotFinished = logic.generateRandomBalls();
                    if (gameNotFinished) {
                        scoreChange = logic.findConsecutiveBalls();
                    if (scoreChange != 0)
                        score += scoreChange;
                    }
                } while (gameNotFinished && scoreChange != 0);
                if (!gameNotFinished) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Game finished");
                    builder.setMessage("Your score is " + Integer.toString(score) + "\nRestart the game?");
// Add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            init();
                        }
                    });
                    builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            currentActivity.finishAffinity();
                        }
                    });
// Set other dialog properties

// Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                invalidate();


            }

//            boolean success = logic.generateRandomBalls();
//            if (!success)
//                Toast.makeText(getContext(), "Game finished!", Toast.LENGTH_LONG).show();
//            points.add(new Point(row, column));
//
//            Log.d("", "Pressed");
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
}
