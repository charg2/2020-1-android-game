//package kr.ac.kpu.game.charg2dang.cookierun.ui.activity;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import kr.ac.kpu.game.charg2dang.cookierun.R;
//import kr.ac.kpu.game.charg2dang.cookierun.game.world.MainWorld;
//
//public class HighscoreActivity extends AppCompatActivity
//{
//	private TextView higisocreTextView;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_highscore);
//
//		higisocreTextView = findViewById(R.id.highscoreTextView);
//	}
//
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		SharedPreferences perfs = getSharedPreferences(MainWorld.PREFS_NAME, Context.MODE_PRIVATE);
//		int highscore = perfs.getInt(MainWorld.PREF_KEY_HIGHSCORE, 0);
//		higisocreTextView.setText((String.valueOf(highscore)));
//	}
//}
