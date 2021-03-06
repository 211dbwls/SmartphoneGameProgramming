package com.example.cards;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int[] BUTTON_IDS = new int[] {  // 버튼 id
            R.id.card_00, R.id.card_01, R.id.card_02,R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12,R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22,R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32,R.id.card_33
    };

    private int[] resIds = new int[]{  // 카드 이미지 리소스
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd
    };

    private ImageButton previousImageButton;  // 이전에 눌린 이미지 버튼

    private int flips;  // 카드 연 횟수
    private TextView scoreTextView;

    private int openCardCount;  // 열린 카드 확인용 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        scoreTextView = findViewById(R.id.scoreTextView);

        startGame();
    }

    private void startGame() {
        Random r = new Random();
        for(int i = 0; i < resIds.length; i++) {  // 카드 랜덤 배치
            int t = r.nextInt(resIds.length);

            int id = resIds[t];
            resIds[t] = resIds[i];
            resIds[i] = id;
        }

        openCardCount = BUTTON_IDS.length;  // 총 카드 개수 설정

        for(int i = 0; i < BUTTON_IDS.length; i++) {  // 각 카드에 이미지 부여
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            int resId = resIds[i];
            btn.setVisibility(View.VISIBLE);  // 카드 보이도록
            // btn.setSelected(false);
            btn.setImageResource(R.mipmap.card_blue_back);  // 뒷면 이미지 보이도록
            btn.setTag(resId);  // 태그로 설정
        }

        setScore(0);  // score 초기화
        previousImageButton = null;  // 이전에 누른 버튼 null로 초기화
    }

    public void onBtnRestart(View view) {
        Log.d(TAG, "Restart");
        askRetry();
    }

    private void askRetry() {  // Restart 알림창 띄우는 함수
        new AlertDialog.Builder(this)
                .setTitle(R.string.Restart)
                .setMessage(R.string.restart_alert_msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create()
                .show();
    }

    public void onBtnCard(View view) {
        if(!(view instanceof ImageButton)) {  // view가 ImageButton이 아닌 경우 예외 처리
            Log.d(TAG, "Not an ImageButton: " + view);
            return;
        }
        ImageButton imageButton = (ImageButton) view;

        int cardIndex = findButtonIndex(imageButton.getId());  // 카드 인덱스 얻어옴.
        Log.d(TAG, "Card: " + cardIndex);

        if(previousImageButton == imageButton) {  // 이전에 눌린 버튼을 또 클릭한 경우
            Log.d(TAG, "Same Image Button");
            Toast.makeText(this,
                    R.string.same_card,
                    Toast.LENGTH_SHORT).show();  // Toast message
            return;
        }

        int resId = (Integer) imageButton.getTag();  // 태그로 이미지 리소스 가져옴

        if(previousImageButton != null) {  // 이전에 이미지 버튼을 클릭한 경우
            int previousResourceId = (Integer) previousImageButton.getTag();  // 이전에 누른 카드 태그 가져옴

            if(previousResourceId == resId) {  // 같은 이미지 카드를 선택한 경우
                imageButton.setVisibility(View.INVISIBLE);  // 현재 누른 카드 사라지도록
                previousImageButton.setVisibility(View.INVISIBLE);  // 이전에 누른 카드 사라지도록
                openCardCount -=  2;  // 열린 카드 수 마이너스
                if (openCardCount == 0) {  // 모든 카드를 제거한 경우
                    askRetry();  // 재시작
                }
                previousImageButton = null;  // 이전에 누른 버튼 null로 설정
            }
            else {  // 다른 이미지 카드를 선택한 경우
                // imageButton.setSelected(true);
                imageButton.setImageResource(resId);  // 현재 누른 카드 이미지 변경
                // previousImageButton.setSelected(false);
                previousImageButton.setImageResource(R.mipmap.card_blue_back);  // 이전에 누른 카드 이미지 변경
                setScore(flips + 1);  // 횟수 증가
                previousImageButton = imageButton;  // 이전에 누른 버튼 저장
            }
        }
        else {  // 이번에 클릭한 이미지 버튼이 없는 경우
            // imageButton.setSelected(true);
            imageButton.setImageResource(resId);  // 현재 누른 카드 이미지 변경
            setScore(flips + 1);  // 횟수 증가
            previousImageButton = imageButton;  // 이전에 누른 버튼 저장
        }
    }

    private void setScore(int score) {  // 카드 연 횟수 증가시키는 함수
        flips = score;
        Resources res = getResources();
        String format = res.getString(R.string.flips_fmt);
        String text = String.format(format, score);
        scoreTextView.setText(text);
    }

    private int findButtonIndex(int id) {  // 카드 인텍스 리턴하는 함수
        for(int i = 0; i < BUTTON_IDS.length; ++i) {
            if(id == BUTTON_IDS[i]) {
                    return i;
            }
        }
        return -1;
    }
}