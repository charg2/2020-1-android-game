package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.StaticBackground;

public class MainScene extends Scene
{
	private static final String TAG = MainScene.class.getSimpleName();
	private StaticBackground bg;

	public void initObjects()
	{
		bg = new StaticBackground(R.mipmap.bg_foreground);
		add(LayerType.bg, bg);


	}


	@Override
	public void update(long frameTimeNanos)
	{
		super.update(frameTimeNanos);

	}


	@Override
	protected int getLayerCount()
	{
		return LayerType.MAX.ordinal();
	}

	@Override
	protected void onResume()  {}

	@Override
	protected void onPause() { }

	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
}
