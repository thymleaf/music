package com.thymleaf.music.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerControllerView extends View {
    // setup initial color
    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint drawPaint;



    private List<Point> circlePoints;

    public PlayerControllerView(Context context) {
        super(context, null);
    }


    public PlayerControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        circlePoints = new ArrayList<Point>();
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint.setStyle(Paint.Style.FILL); // change to fill
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 5, drawPaint);
        }
    }

    // Append new circle each time user presses on screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
        // indicate view should be redrawn
        postInvalidate();
        return true;
    }
}
