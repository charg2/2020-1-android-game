package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.view.MotionEvent;

import java.sql.ResultSet;

import javax.xml.transform.Result;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.StaticBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.ScoreObject;

public class ResultScene extends Scene
{
	private static final String TAG = ResultScene.class.getSimpleName();
	private StaticBackground 	bg;
	private ScoreObject			scoreObject;

	public void initObjects()
	{
		bg = new StaticBackground(R.mipmap.bg_loading);
		add(LayerType.bg, bg);

		scoreObject = ScoreManager.getInstance().getScoreObject();
		add(LayerType.ui, scoreObject);

	}


	@Override
	public void update(long frameTimeNanos)
	{
		super.update(frameTimeNanos);

		if(false) // 조건 맞으면
			SceneManager.getInstance().changeScene(SceneType.game);

	}


	@Override
	protected int getLayerCount()
	{
		return LayerType.MAX.ordinal();
	}

	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
}
