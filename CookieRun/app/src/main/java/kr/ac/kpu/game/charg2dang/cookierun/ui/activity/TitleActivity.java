package kr.ac.kpu.game.charg2dang.blocksample.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.ac.kpu.game.charg2dang.blocksample.R;

public class TitleActivity extends AppCompatActivity
{

	private static final String TAG = TitleActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		Log.d(TAG, "onCreate()");
	}

	@Override
	protected void onPause()
	{
		Log.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		Log.d(TAG, "onResume()");
		super.onResume();
	}

	public void onBtnStart(View view)
	{
		Log.d(TAG, "onBtnStart");
		// activity 전환
		Intent intent = new Intent(this, MainActivity.class); // d인텐트를 만드는 방법은 다양함.
		startActivity(intent);
	}


}
