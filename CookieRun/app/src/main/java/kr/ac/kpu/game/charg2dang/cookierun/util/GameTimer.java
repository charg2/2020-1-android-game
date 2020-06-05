package kr.ac.kpu.game.charg2dang.cookierun.util;

import android.util.Log;

public class GameTimer
{
	private static final String TAG = IndexTimer.class.getSimpleName();
	private static final long NANOS_IN_ONE_SECOND = 1_000_000_000;
	private static final float NANOS_IN_ONE_SECOND_FLOAT = 1_000_000_000f;
	private long currentNanoTick;
	private long deltaNanoTick;
	private long prevNanoTick;
	private static GameTimer inst;

	public static GameTimer getInstance()
	{
		if(inst == null)
		{
			inst = new GameTimer();
		}

		return inst;
	}


	public GameTimer()
	{
		currentNanoTick = System.nanoTime(); //System.currentTimeMillis();
		prevNanoTick = currentNanoTick;//currentTick;
		deltaNanoTick = 0;
	}

	public void upadte(long curTickNanos)
	{
		prevNanoTick = this.currentNanoTick;
		this.currentNanoTick = curTickNanos;
		deltaNanoTick = (this.currentNanoTick - prevNanoTick);
	}


	public long getCurrentNanoTicks()
	{
		return currentNanoTick;
	}

	public long getCurrentNanoSeconds()
	{
		return currentNanoTick / NANOS_IN_ONE_SECOND;
	}


	public long getDeltaNanoTick()
	{
		return deltaNanoTick;
	}

	public float getSingleDeltaTime()
	{
		return deltaNanoTick / 1_000_000.0f;
	}
}
