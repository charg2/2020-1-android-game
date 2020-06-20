package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Item.ItemSpawner;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Terrain;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.HorzScrollBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.StaticBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.map.TextMap;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.HPBar;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.PauseButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.PauseText;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.ResumeButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.SlideButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.StopButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.util.CollisionHelper;

public class GameScene extends Scene
{
	private CollisionHelper collisionHelper = new CollisionHelper();
	private PauseButton 	pauseButton;
	private ResumeButton 	resumeButton;
	private StopButton 		stopButton;
	private static final StaticBackground pauseBg = new StaticBackground(R.mipmap.ui_pause_bg);
	private PauseText 		pauseText;

	private  enum PlayState
	{
		normal, paused, gameOver
	}

	private TextMap mapGenerator;

	private static final String TAG = GameScene.class.getSimpleName();
	private static final int BALL_COUNT = 10;
	public static final String PREF_KEY_HIGHSCORE = "highscore";
	public static String PREFS_NAME = "Prefs";

	private Cookie 		cookie;
	private JumpButton  jumpButton;
	private SlideButton slideButton;
	private ItemSpawner itemGenerator = new ItemSpawner();

	private GameScene.PlayState playState = PlayState.normal;

	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}

	@Override
	protected int getLayerCount()
	{
		return LayerType.MAX.ordinal();
	}


	public void initObjects()
	{
		cookie = Cookie.getInstance();
		cookie.setPosition( 350, 900);
		cookie.setScale(2.5f);
		add(LayerType.player, cookie);

		add(LayerType.ui, ScoreManager.getInstance().getScoreObject()); /// 머지;;
//		add(LayerType.ui, ScoreManager.getInstance().getHighScoreObject());

		add(LayerType.ui, HPBar.getInstance());

		HorzScrollBackground hzBg = new HorzScrollBackground(R.mipmap.bg_background2, ImageScrollBackground.Orientation.horizontal, -500);
		add(LayerType.bg, hzBg);
		HorzScrollBackground hzBg2 = new HorzScrollBackground(R.mipmap.bg_foreground2, ImageScrollBackground.Orientation.horizontal, -200);
		add(LayerType.bg, hzBg2);

		pauseButton = PauseButton.getInstance();
		pauseButton.setPosition(UiBridge.metrics.fullSize.x / 1.2f, 100);
		pauseButton.setScale(4);
		add(LayerType.ui, pauseButton);

		resumeButton = ResumeButton.getInstance();
		stopButton = StopButton.getInstance();
		pauseText = pauseText.getInstance();

		jumpButton = JumpButton.getInstance();
		jumpButton.setPosition(250, 1050);
		jumpButton.setScale(4);
		add(LayerType.ui, jumpButton);

		slideButton = SlideButton.getInstance();
		slideButton.setPosition(2300, 1050);
		slideButton.setScale(4);
		add(LayerType.ui, slideButton);

		mapGenerator = new TextMap("stage_01.txt",this);
		add(LayerType.event, mapGenerator);

		Terrain terrain = new Terrain(1300, 1400);
		add(LayerType.terrain, terrain);

		startGame();

	}

	private void startGame()
	{
		playState = PlayState.normal;

		cookie.move(0, -50.f);
//		scoreObject.reset();

//		SharedPreferences prefs = view.getContext().getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE);
//		int highScore = prefs.getInt(PREF_KEY_HIGHSCORE, 0);
//		highScoreObject.setScore(highScore);
	}

	public void endGame()
	{
		this.playState = PlayState.gameOver;
		int score = 0;// scoreObject.getScore();

		SharedPreferences prefs = view.getContext().getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE);
		int highScore = prefs.getInt(PREF_KEY_HIGHSCORE, 0);
		if (score > highScore)	// score가 high_screo 보다 높으면 바꿔서 저장.
		{
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(PREF_KEY_HIGHSCORE, score);
			editor.commit();
		}
	}



	@Override
	public void update(long frameTimeNanos)
	{
		super.update(frameTimeNanos);

		if( playState != PlayState.normal )
		{
			return;
		}

		itemGenerator.update();
	}


	public boolean onTouchEvent(MotionEvent event)
	{
		if(paused == false)
		{
			pauseButton.onTouchEvent(event);
			jumpButton.onTouchEvent(event);
			slideButton.onTouchEvent(event);
		}
		else
		{
			resumeButton.onTouchEvent(event);
			stopButton.onTouchEvent(event);
		}
		return true;
	}


	public void reset()
	{
		HPBar.getInstance().reset();
		cookie.reset();
		resume();
		ScoreManager.getInstance().reset();
		mapGenerator = new TextMap("stage_01.txt",this);
	}

	@Override
	protected void onResume()
	{
		clearUI();
		setGameUI();
		cookie.setPosition(350, 900);
//		pauseBg.setState(false);
//		switch (currentPauseReason)
//		{
//			case Stop:
//				pauseText.setState(false);
//				resumeButton.setState(false);
//				stopButton.setState(false);
//
//				break;
//
//			case CookieDeath:
//				setGameUI();
//
//				break;
//
//
//			default:
//				break;
//
//		}

	}

	@Override
	protected void onPause(PauseReason reason)
	{


		switch (reason)
		{
			case Stop:
			{
				clearUI();

				setPauseUI();

				break;
			}


			case CookieDeath:
			{
				clearUI();
				break;
			}

			default:
				Log.d(TAG, "Undefined behaviour..");
				break;
		}
	}

	private void clearUI()
	{
		layers.get(LayerType.ui.ordinal()).clear();
	}

	private void setGameUI()
	{
		layers.get(LayerType.ui.ordinal()).clear();

		add(LayerType.ui, ScoreManager.getInstance().getScoreObject()); /// 머지;;
//		add(LayerType.ui, ScoreManager.getInstance().getHighScoreObject());

		add(LayerType.ui, HPBar.getInstance());


		pauseButton = PauseButton.getInstance();
		pauseButton.setPosition(UiBridge.metrics.fullSize.x / 1.2f, 100);
		pauseButton.setScale(4);
		pauseButton.setState(true);
		add(LayerType.ui, pauseButton);

		jumpButton = JumpButton.getInstance();
		jumpButton.setPosition(250, 1050);
		jumpButton.setScale(4);
		jumpButton.setState(true);
		add(LayerType.ui, jumpButton);

		slideButton = SlideButton.getInstance();
		slideButton.setPosition(2300, 1050);
		slideButton.setScale(4);
		stopButton.setState(true);
		add(LayerType.ui, slideButton);
	}


	private void setPauseUI()
	{

		pauseBg.setState(true);
		add(LayerType.ui, pauseBg);

		pauseText.setPosition(UiBridge.metrics.center.x - pauseText.getWidth(),  UiBridge.metrics.fullSize.y / 6.0f  );
		pauseText.setState(true);
		add(LayerType.ui, pauseText);

		resumeButton.setPosition(UiBridge.metrics.center.x - resumeButton.getWidth(),  UiBridge.metrics.fullSize.y / 3.5f  );
		resumeButton.setState(true);
		add(LayerType.ui, resumeButton);

		stopButton.setPosition(UiBridge.metrics.center.x - stopButton.getWidth(),  UiBridge.metrics.fullSize.y / 1.8f  );
		stopButton.setState(true);
		add(LayerType.ui, stopButton);

	}


}
