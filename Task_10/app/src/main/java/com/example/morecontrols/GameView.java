package com.example.morecontrols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {

    private static final String TAG = GameView.class.getSimpleName();
    
    private Paint paint;

    public GameView(Context context) {
        super(context);
        Log.d(TAG, "GameView cons");
        
        initView();
    }

    public GameView(Context context, AttributeSet as) {
        super(context, as);
        Log.d(TAG, "GameView cons with as");
        
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();

        float rx = width / 10, ry = height / 10;
        canvas.drawRoundRect(left, top, width - right, height - bottom, rx, ry, paint);

        Log.d(TAG, "size: " + width +  ", " + height + " padding: " + left + ", " + top + ", " + right + ", " + bottom);
    }
}
