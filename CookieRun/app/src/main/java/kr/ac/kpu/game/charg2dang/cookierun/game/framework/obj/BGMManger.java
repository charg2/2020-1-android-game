package kr.ac.kpu.game.charg2dang.cookierun.game.framework.obj;

import android.media.MediaPlayer;

import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;

// media player
public class BGMManger
{
	private final String TAG = BGMManger.class.getSimpleName();

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
//		mediaPlayer = MediaPlayer.create();

	}

}
