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
	private Integer currentBGM = 0;

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

		bgms.get(R.raw.bgm_lobby2).start();
		bgms.get(R.raw.bgm_main).start();;
	}

	public void play(int bgmID)
	{
		MediaPlayer bgm = bgms.get(bgmID);
		if(bgm != null)
		{
			bgm.start();
		}
		else
		{
			Log.d("BGMManger", "INVALID BGM ID");
			throw new RuntimeException();
		}

	}

}
