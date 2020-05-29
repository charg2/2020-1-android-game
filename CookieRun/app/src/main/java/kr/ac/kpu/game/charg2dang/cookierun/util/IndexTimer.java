package kr.ac.kpu.game.charg2dang.cookierun.util;


import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameWorld;

public class IndexTimer
{
	private static final String TAG = IndexTimer.class.getSimpleName();
	private final int count;
	private final int fps;
	private long time;

	public IndexTimer(int count, int framePerSecond)
	{
		this.count = count;
		this.fps = framePerSecond;
		this.time = GameWorld.get().getCurrentFrameTimeNanos();
	}

	public int getIndex()
	{
		long elapsed = GameWorld.get().getCurrentFrameTimeNanos() - time;
		int index = (int)((elapsed * fps + 500_000_000)/ 1_000_000_000);

		return index % count;
	}

	public boolean done()
	{
		long elapsed = GameWorld.get().getCurrentFrameTimeNanos() - time;
		int index = (int)((elapsed * fps + 500_000_000)/ 1_000_000_000);

		return index >= count;
	}


	public void reset()
	{
		this.time = GameWorld.get().getCurrentFrameTimeNanos();
	}
}
