package kr.ac.kpu.game.charg2dang.blocksample.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.ac.kpu.game.charg2dang.blocksample.R;
import kr.ac.kpu.game.charg2dang.blocksample.game.world.MainWorld;

public class HighscoreActivity extends AppCompatActivity
{
	private TextView higisocreTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);
	
		higisocreTextView = findViewById(R.id.highscoreTextView);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SharedPreferences perfs = getSharedPreferences(MainWorld.PREFS_NAME, Context.MODE_PRIVATE);
		int highscore = perfs.getInt(MainWorld.PREF_KEY_HIGHSCORE, 0);
		higisocreTextView.setText((String.valueOf(highscore)));
	}
}
