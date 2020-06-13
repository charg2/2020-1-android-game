package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;

public class Framework
{
	private final String TAG = Framework.class.getSimpleName();
	private  boolean isDebuMode = true;
	private static Framework  inst;
	private Paint collisionBoxpaint = new Paint();
	private View view;
	public Resources getResources()
	{
		return this.view.getResources();
	}


	private Paint cookieFontPaint;
	private Typeface typeface;


	public static Framework getInstance()
	{
		if(inst == null)
		{
			inst = new Framework();
		}
		return inst;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	public Framework()
	{
		collisionBoxpaint.setColor(Color.RED);
		collisionBoxpaint.setAlpha(70);


	}

	public void setFont(Paint fontPaint)
	{
		cookieFontPaint = fontPaint;
	}



	public boolean isDebugMode()
	{
		return this.isDebuMode;
	}

	public Paint getDebugPaint()
	{
		return collisionBoxpaint;
	}

	public Paint getFontPaint()
	{
		return cookieFontPaint;
	}
}
