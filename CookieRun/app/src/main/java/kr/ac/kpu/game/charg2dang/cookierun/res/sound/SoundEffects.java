package kr.ac.kpu.game.charg2dang.cookierun.res.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import kr.ac.kpu.game.charg2dang.cookierun.R;

public class SoundEffects
{
	private static SoundEffects singleton;
	private Context context;
	private SoundPool soundPool;

	public static SoundEffects get()
	{
		if(SoundEffects.singleton == null)
		{
			SoundEffects.singleton = new SoundEffects();
		}

		return SoundEffects.singleton;
	}


	public void init(Context context)
	{
		this.context = context;
		AudioAttributes audioAttributes = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
			this.soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(3).build();
		}
		else
		{
			this.soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		}
	}

	private  static final int[] SOUND_IDS =
			{R.raw.bgm_game, R.raw.bgm_lobby, R.raw.bgm_main
					, R.raw.se_bt_off, R.raw.se_bt_on		// btn
					, R.raw.se_coin, R.raw.se_hpup, R.raw.se_jelly, R.raw.se_powerup //
					, R.raw.se_jump, R.raw.se_slide, R.raw.se_score, R.raw.se_title };



	private HashMap<Integer, Integer> soundIdMap = new HashMap<>();
	public void loadAll()
	{
		for ( int resId : SOUND_IDS )
		{
			int soundId = soundPool.load(context, resId, 1);
			soundIdMap.put(resId, soundId);
		}

	}

	public int play(int resId)
	{
		int soundId = soundIdMap.get(resId);
		int streamId = soundPool.play(soundId, 1f, 1f, 1, 0, 1f);

		return streamId;
	}


}
