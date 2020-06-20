package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.media.MediaPlayer;

import kr.ac.kpu.game.charg2dang.cookierun.R;

// media player
public class BGMManger
{
	private final String TAG = BGMManger.class.getSimpleName();
	private int	currentBGM = 0;


	MediaPlayer mediaPlayer;


	private static BGMManger insance;
	public static BGMManger getInstance()
	{
		if(insance == null)
		{
			insance = new BGMManger();
		}

		return insance;
	}


	private BGMManger()
	{
		mediaPlayer = MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_game);
		mediaPlayer.start();
	}

	public void play(int bgmID)
	{

	}

}
