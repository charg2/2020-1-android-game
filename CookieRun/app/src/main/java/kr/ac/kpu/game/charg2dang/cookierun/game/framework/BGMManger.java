package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import kr.ac.kpu.game.charg2dang.cookierun.R;

// media player
public class BGMManger
{
	private final String TAG = BGMManger.class.getSimpleName();
	private HashMap<Integer, MediaPlayer> bgms = new HashMap<>();
	private MediaPlayer currentMediaPlayer;

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
		bgms.put(R.raw.bgm_game, MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_game));
		bgms.put(R.raw.bgm_game2, MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_game2));
		bgms.put(R.raw.bgm_game3, MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_game3));
		bgms.put(R.raw.bgm_lobby, MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_lobby));
		bgms.put(R.raw.bgm_lobby2, MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_lobby2));
		bgms.put(R.raw.bgm_main, MediaPlayer.create(Framework.getInstance().getContext(), R.raw.bgm_main));

		for (HashMap.Entry<Integer, MediaPlayer> entry : bgms.entrySet())
		{
			if(entry.getValue() != null)
			{
				entry.getValue().setLooping(true);
			}
		}
	}


	public void play(int bgmID)
	{
		if(currentMediaPlayer != null)
		{
			currentMediaPlayer.pause();
		}

			currentMediaPlayer = bgms.get(bgmID);
			if (currentMediaPlayer != null)
			{
				currentMediaPlayer.start();
			}
			else
			{
				Log.d("BGMManger", "INVALID BGM ID");
				throw new RuntimeException();
			}

	}

}
