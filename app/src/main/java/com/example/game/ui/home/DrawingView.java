package com.example.game.ui.home;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {

    private ArrayList<ColoredPath> paths = new ArrayList<>();
    private Paint currentPaint;
    private Path currentPath;
    private int currentColor = Color.BLACK; // Variable para almacenar el color actual
    private boolean isErase = false; // Variable para controlar si se está borrando

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        currentPaint = new Paint();
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(5);
        currentPaint.setColor(currentColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ColoredPath coloredPath : paths) {
            canvas.drawPath(coloredPath.path, coloredPath.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new Path();
                currentPath.moveTo(x, y);

                if (isErase || currentPaint.getColor() != currentColor) {
                    currentPaint = new Paint(currentPaint);
                    currentPaint.setColor(isErase ? Color.WHITE : currentColor); // Use white for eraser
                }

                paths.add(new ColoredPath(currentPath, currentPaint));
                break;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void clearDrawing() {
        paths.clear();
        invalidate();
    }

    public ArrayList<ColoredPath> getPaths() {
        return paths;
    }

    public void setColor(int color) {
        currentColor = color;
        isErase = false; // Cuando se cambia el color, asegurarse de que no estamos en modo borrador
    }

    public void setEraseMode(boolean val) {
        isErase = val;
        if (val) {
            currentPaint.setXfermode(null); // Cambiar al modo borrador
            currentPaint.setColor(Color.WHITE); // Suponiendo que el fondo es blanco
        } else {
            currentPaint.setXfermode(null); // Volver al modo de dibujo normal
            currentPaint.setColor(currentColor);
        }
    }

    public void clearScreen() {
        clearDrawing();
    }

    static class ColoredPath {
        Path path;
        Paint paint;

        ColoredPath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }
}
