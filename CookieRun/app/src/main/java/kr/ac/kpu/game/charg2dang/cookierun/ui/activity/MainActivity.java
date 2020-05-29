package kr.ac.kpu.game.charg2dang.cookierun.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.game.charg2dang.cookierun.game.world.MainWorld;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.soundEffects;
import kr.ac.kpu.game.charg2dang.cookierun.ui.view.GameView;

public class MainActivity extends AppCompatActivity
{
    private static final long GAMEVIEW_UPDATE_INTERVAL_MSEC = 30;
    private static final String TAG = MainActivity.class.getSimpleName();
    private GameView gameView;
    private soundEffects se;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        MainWorld.create();
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        se = soundEffects.get();
        se.init(this);
        se.loadAll();

//        gameView = findViewById(R.id.gameView);

//        postUpdate();
    }

//    private void postUpdate()
//    {
//        gameView.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                gameView.update();
//                gameView.invalidate();
//                postUpdate();
//            }
//        }, GAMEVIEW_UPDATE_INTERVAL_MSEC);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        if (event.getAction() == MotionEvent.ACTION_DOWN)
//        {
//            gameView.doAction();
//        }
//        return true;
//    }

//    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig)
    {
        Log.d(TAG, "New Configration : " + newConfig);
        super.onConfigurationChanged(newConfig);
    }


    // 액티비티가 내려갈때
    // 다른 앧티비티가 그 위에 올라갈때;
    // onPause가 불리는 상황 : 현재 동작 중인 겡미 월드를 멈추게 해야함.
    @Override
    protected void onPause()
    {
        gameView.resume();

//        GameWorld.get().pause();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        gameView.resume();
//        GameWorld.get().resume();
        super.onResume();
    }
}
