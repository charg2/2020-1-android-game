package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.StaticBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.JumpButton;

public class LoadingScene extends Scene
{
	private static final String TAG = LoadingScene.class.getSimpleName();
	private StaticBackground bg;
	private float loadingTimer = 2.0f;
	private float loadingTime = .0f;

	public void initObjects()
	{
		bg = new StaticBackground(R.mipmap.bg_loading);
		add(LayerType.bg, bg);
	}

	private JumpButton jumpButton;


	@Override
	public void update(long frameTimeNanos)
	{
		super.update(frameTimeNanos);

		loadingTime += GameTimer.getInstance().getCurrentDeltaSecondsSngle();
		if( loadingTime >= loadingTimer )
		{
			SceneManager.getInstance().changeScene(SceneType.main, false);
//			Scene.currentSceneType = SceneType.game;
			loadingTime = 0;
		}

	}


	@Override
	protected int getLayerCount()
	{
		return LayerType.MAX.ordinal();
	}

	@Override
	protected void onResume()  { }

	@Override
	protected void onPause(PauseReason reason)  {}

	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
}
