package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;

public class Framework
{
	private final String TAG = Framework.class.getSimpleName();
	private  boolean isDebuMode = true;
	private static Framework  inst;
	private Paint collisionBoxpaint = new Paint();
	public static Framework getInstance()
	{
		if(inst == null)
		{
			inst = new Framework();
		}
		return inst;
	}

	public Framework()
	{
		collisionBoxpaint.setColor(Color.RED);
		collisionBoxpaint.setAlpha(70);
	}


	public boolean isDebugMode()
	{
		return this.isDebuMode;
	}

	public Paint getDebugPaint()
	{
		return collisionBoxpaint;
	}
}
