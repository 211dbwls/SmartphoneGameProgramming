package com.example.jellyking.game.enemy;

import android.graphics.Canvas;

import com.example.jellyking.R;
import com.example.jellyking.framework.Sprite;

public class EnemyFix extends Sprite {
    public EnemyFix(float x, float y) {
        super(x, y, R.dimen.block_radius, R.mipmap.enemy_fix_1);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void update() {

    }
}
