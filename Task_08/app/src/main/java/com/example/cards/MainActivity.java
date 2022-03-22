package com.example.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int[] BUTTON_IDS = new int[] {  // 버튼 id
            R.id.card_00, R.id.card_01, R.id.card_02,R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12,R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22,R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32,R.id.card_33
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnRestart(View view) {
        Log.d(TAG, "Restart");
    }

    public void onBtnCard(View view) {
        if(!(view instanceof ImageButton)) {  // view가 ImageButton이 아닌 경우 예외 처리
            Log.d(TAG, "Not an ImageButton: " + view);
            return;
        }
        ImageButton imageButton = (ImageButton) view;

        int cardIndex = findButtonIndex(imageButton.getId());  // 카드 인덱스 얻어옴.
        Log.d(TAG, "Card: " + cardIndex);

    }

    private int findButtonIndex(int id) {  // 카드 인텍스 리턴하는 함수.
        for(int i = 0; i < BUTTON_IDS.length; ++i) {
            if(id == BUTTON_IDS[i]) {
                    return i;
            }
        }
        return -1;
    }
}