package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Coin;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.CoinSilver;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ItemSpawner;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Obstacle;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Terrain;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.HorzScrollBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.map.TextMap;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.HPBar;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.SlideButton;

public class GameScene extends Scene
{
	private  enum PlayState
	{
		normal, paused, gameOver
	}

	private TextMap mapGenerator;

	private static final String TAG = GameScene.class.getSimpleName();
	private static final int BALL_COUNT = 10;
	public static final String PREF_KEY_HIGHSCORE = "highscore";
	public static String PREFS_NAME = "Prefs";

	private Cookie cookie;
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
		mapGenerator = new TextMap("stage_01.txt",this);
		add(LayerType.event, mapGenerator);
		cookie = Cookie.getInstance();
		cookie.move( 350, 900);
		cookie.setScale(2.5f);
		add(LayerType.player, cookie);

		Coin coin = new Coin(1500 , 850);
		add(LayerType.item, coin);

		CoinSilver coinSilver = new CoinSilver(1800 , 850);
		add(LayerType.item, coinSilver);

		add(LayerType.ui, ScoreManager.getInstance().getScoreObject()); /// 머지;;
//		add(LayerType.ui, ScoreManager.getInstance().getHighScoreObject());


		add(LayerType.ui, HPBar.getInstance());


		HorzScrollBackground hzBg = new HorzScrollBackground(R.mipmap.bg_background2, ImageScrollBackground.Orientation.horizontal, -500);
		add(LayerType.bg, hzBg);
		HorzScrollBackground hzBg2 = new HorzScrollBackground(R.mipmap.bg_foreground2, ImageScrollBackground.Orientation.horizontal, -200);
		add(LayerType.bg, hzBg2);

		jumpButton = JumpButton.getInstance();
		jumpButton.setPosition(250, 1050);
		jumpButton.setScale(4);
		add(LayerType.ui, jumpButton);

		slideButton = SlideButton.getInstance();
		slideButton.setPosition(2300, 1050);
		slideButton.setScale(4);
		add(LayerType.ui, slideButton);


		Obstacle obs = new Obstacle(2000, 1250);
		obs.setScale(.9f);
		add(LayerType.obstacle, obs);


		Terrain terrain = new Terrain(1300, 1400);
		add(LayerType.terrain, terrain);
		startGame();
	}

	private void startGame()
	{
		playState = PlayState.normal;
//		scoreObject.reset();

//		SharedPreferences prefs = view.getContext().getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE);
//		int highScore = prefs.getInt(PREF_KEY_HIGHSCORE, 0);
//		highScoreObject.setScore(highScore);
	}

	public void endGame()
	{
		this.playState = PlayState.gameOver;
		int score = 0;// scoreObject.getScore();
		Log.v(TAG, "score" + score);
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
		jumpButton.onTouchEvent(event);
		slideButton.onTouchEvent(event);
//		int action = event.getAction();
//		if(action == MotionEvent.ACTION_DOWN)
//		{
//			if(playState == PlayState.gameOver)
//			{
//				startGame();
//				return false;
//			}
//			doAction();
//
////			plane.head(event.getX(), event.getY());
//		}
//		else if(action == MotionEvent.ACTION_MOVE)
//		{
////			plane.head(event.getX(), event.getY());
//		}

		return true;
	}

}
