package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.service.notification.NotificationListenerService;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.StaticBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.GameStartButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.RankingButton;
import kr.ac.kpu.game.charg2dang.cookierun.ui.activity.HighscoreActivity;

public class MainScene extends Scene
{
	private static final String TAG = MainScene.class.getSimpleName();
	private StaticBackground bg;
	private GameStartButton gameStartButton;
	private RankingButton rankingButton;

	public void initObjects()
	{
		bg = new StaticBackground(R.mipmap.bg_main_lobby);
		add(LayerType.bg, bg);

		gameStartButton = GameStartButton.getInstance();
		gameStartButton.setPosition(UiBridge.metrics.center.x, UiBridge.metrics.center.y);
		add(LayerType.ui, gameStartButton);

		rankingButton = RankingButton.getInstance();
		add(LayerType.ui, rankingButton);
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
	protected void onPause(PauseReason reason) { }

	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		gameStartButton.onTouchEvent(event);
		rankingButton.onTouchEvent(event);
		return true;
	}
}
