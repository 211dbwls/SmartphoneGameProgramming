package com.example.jellyking.game.enemy;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.jellyking.R;
import com.example.jellyking.framework.BoxCollidable;
import com.example.jellyking.framework.Metrics;
import com.example.jellyking.framework.Sprite;

public class EnemyDrop extends Sprite implements BoxCollidable {
    protected RectF boundingBox = new RectF();  // boundingBox

    public EnemyDrop(float x, float y) {
        super(x, y, R.dimen.enemy_radius, R.mipmap.enemy_drop_1);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void update() {
        /* boundingBox */
        float widthRadius = Metrics.size(R.dimen.enemy_radius);
        boundingBox.set(x - widthRadius, y - widthRadius, x + widthRadius, y + widthRadius);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }

    @Override
    public RectF getBoundingRectHead() {
        return null;
    }

    @Override
    public RectF getBoundingRectFoot() {
        return null;
    }

    @Override
    public RectF getBoundingRectLeft() {
        return null;
    }

    @Override
    public RectF getBoundingRectRight() {
        return null;
    }
}
