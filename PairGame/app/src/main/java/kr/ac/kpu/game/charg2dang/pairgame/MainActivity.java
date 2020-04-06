package kr.ac.kpu.game.charg2dang.pairgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = {
            R.id.b_00, R.id.b_01, R.id.b_02, R.id.b_03
        , R.id.b_10, R.id.b_11, R.id.b_12, R.id.b_13
        , R.id.b_20, R.id.b_21, R.id.b_22, R.id.b_23
        , R.id.b_30, R.id.b_31, R.id.b_32, R.id.b_33
            , R.id.b_40, R.id.b_41, R.id.b_42, R.id.b_43
            , R.id.b_50, R.id.b_51, R.id.b_52, R.id.b_53
    };

    private static final int[] IMAGE_RES_IDS = {

            R.mipmap.z30px01, R.mipmap.z30px02, R.mipmap.z30px03, R.mipmap.z30px04,
            R.mipmap.z30px05, R.mipmap.z30px06, R.mipmap.z30px07, R.mipmap.z30px08,
            R.mipmap.z30px09, R.mipmap.z30px10, R.mipmap.z30px11, R.mipmap.z30px12,
    };
    private ImageButton lastButton;
    private int flips;
    private TextView scoreTextView;
    private Object scoreFormatString;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        connectViews();

        startGame();
    }

    private void connectViews() {
        scoreTextView = findViewById((R.id.scoreTextView));
        Resources res = getResources();
        scoreFormatString = res.getString(R.string.flips_fmt);
    }

    private void startGame()
    {
        int[] buttonIds = shuffleButtonIds();

        for(int i = 0; i <BUTTON_IDS.length; ++i )
        {
            ImageButton btn = findViewById(buttonIds[i]);
            int resId = IMAGE_RES_IDS[i / 2];
            btn.setTag(resId);
            btn.setImageResource(R.mipmap.i12341608834);
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            btn.setEnabled(true);
        }

        lastButton = null;
        flips = 0;
        showScore();
    }

    private void showScore() {
        TextView textView = findViewById(R.id.scoreTextView);
        Resources res = getResources();
        String fmt = res.getString(R.string.flips_fmt);
        String text = String.format(fmt, flips);
        textView.setText(text);
    }

    private int[] shuffleButtonIds()
    {
        int[] buttonIds = Arrays.copyOf(BUTTON_IDS, BUTTON_IDS.length);
        Random rand = new Random();
        for(int  i = 0; i < BUTTON_IDS.length; ++i)
        {
            int r = rand.nextInt(buttonIds.length);
            int temp = buttonIds[i];
            buttonIds[i] = buttonIds[r];
            buttonIds[r] = temp;
        }

        return buttonIds;
    }

    public void onBtnItem(View view)
    {
        Log.d(TAG, "Button ID = " + view.getId());
        Log.d(TAG, "Button Index = " + (view.getId() - R.id.b_00));

        ImageButton btn = (ImageButton)view;
        int resId = (int)btn.getTag();
        btn.setImageResource(resId);
        btn.setEnabled(false);

        if(lastButton == null) // ㅎ나번도 눌린적 없으면
        {
            lastButton = btn;
            return;
        }

        if((int) lastButton.getTag() == (int)btn.getTag()) // 같은 이미지의 버튼이면...
        {
            lastButton = null;
            return;
        }

        lastButton.setImageResource(R.mipmap.i12341608834);
        lastButton.setEnabled(true);
        lastButton = btn;

        flips++;
        showScore();
    }

    public void onBtnRestart(View view)
    {
        Log.d(TAG, "onBtnRestart()");

        new AlertDialog.Builder(this).setTitle(R.string.restart_title)
                .setMessage(R.string.restart_msg)
                .setNegativeButton(R.string.restart_no, null)
                .setPositiveButton(R.string.restart_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame();
            }
        }).create().show();

    }
}
