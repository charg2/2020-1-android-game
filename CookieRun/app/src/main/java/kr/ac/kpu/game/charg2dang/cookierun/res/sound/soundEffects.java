package kr.ac.kpu.game.charg2dang.blocksample.res.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import kr.ac.kpu.game.charg2dang.blocksample.R;

public class soundEffects
{
	private static soundEffects singleton;
	private Context context;
	private SoundPool soundPool;

	public static soundEffects get()
	{
		if(soundEffects.singleton == null)
		{
			soundEffects.singleton = new soundEffects();
		}

		return soundEffects.singleton;
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

	private  static final int[] SOUND_IDS = {R.raw.hadouken };


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
		int streamId =soundPool.play(soundId, 1f, 1f, 1, 0, 1f);

		return streamId;
	}


}
