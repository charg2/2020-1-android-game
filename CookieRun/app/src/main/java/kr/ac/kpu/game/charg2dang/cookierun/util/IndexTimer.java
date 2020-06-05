package kr.ac.kpu.game.charg2dang.cookierun.util;


import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;

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
//		this.time = Scene.get().getCurrentFrameTimeNanos();
		this.time = GameTimer.getInstance().getCurrentNanoTicks();
	}

	public int getIndex()
	{
//		long elapsed = Scene.get().getCurrentFrameTimeNanos() - time;
		long elapsed = GameTimer.getInstance().getCurrentNanoTicks() - time;

		int index = (int)((elapsed * fps + 500_000_000)/ 1_000_000_000);

		return index % count;
	}

	public boolean done()
	{
//		long elapsed = Scene.get().getCurrentFrameTimeNanos() - time;
		long elapsed = GameTimer.getInstance().getCurrentNanoTicks() - time;

		int index = (int)((elapsed * fps + 500_000_000)/ 1_000_000_000);

		return index >= count;
	}


	public void reset()
	{
		this.time = Scene.get().getCurrentFrameTimeNanos();
	}
}
