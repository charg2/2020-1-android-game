package kr.ac.kpu.game.charg2dang.cookierun.util;

public class Timer
{
	private static final String TAG = IndexTimer.class.getSimpleName();
	private long current_tick;
	private long delta_tick;
	private long prev_tick;


	public Timer()
	{
		current_tick = System.currentTimeMillis();
		prev_tick = current_tick;
		delta_tick = 0;
	}

	public void upadte()
	{
		prev_tick = current_tick;
		current_tick = System.currentTimeMillis();
		delta_tick = current_tick - prev_tick;
	}

	public float getDeltaTime()
	{
		return delta_tick / 1000.0f;
	}
}
