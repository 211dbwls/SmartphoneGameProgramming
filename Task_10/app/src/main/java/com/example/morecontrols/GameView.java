package com.example.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {

    private static final String TAG = GameView.class.getSimpleName();
    
    private Paint paint;
    private Paint textPaint;
    private Paint circlePaint;
    private Bitmap soccerBitmap;

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
        paint.setColor(Color.parseColor("#FFE4E1"));

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("Blue"));
        textPaint.setTextSize(50);

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#EB8C8C"));
        circlePaint.setTextSize(50);

        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        /* RoundRect 그리기 */
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();

        float rx = width / 10, ry = height / 10;
        canvas.drawRoundRect(left, top, width - right, height - bottom, rx, ry, paint);

        Log.d(TAG, "size: " + width +  ", " + height + " padding: " + left + ", " + top + ", " + right + ", " + bottom);

        /* 축구공 그리기 */
        int cx = left + (width - left - right) / 2;
        int cy = top + (height - top - bottom) / 2;
        int ballRadius = width / 10;

        Rect src = new Rect(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        RectF dst = new RectF();
        dst.left = cx - ballRadius;
        dst.top = cy - ballRadius;
        dst.right = cx + ballRadius;
        dst.bottom = cy + ballRadius;

        canvas.drawBitmap(soccerBitmap, src, dst, null);

        /* 텍스트 그리기 */
        String text = "Soccer";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float tx = cx - bounds.width() / 2;
        float ty = cy + ballRadius;

        canvas.drawText(text, tx, ty, textPaint);

        /* 원 그리기 */
        canvas.drawCircle(cx - width / 4, cy - height / 4, height / 10, circlePaint);
        canvas.drawCircle(cx + width / 4, cy + height / 4, height / 10, circlePaint);
    }
}
