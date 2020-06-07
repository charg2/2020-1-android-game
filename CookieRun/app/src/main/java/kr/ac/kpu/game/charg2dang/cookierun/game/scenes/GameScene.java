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
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Coin;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.CoinSilver;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ItemSpawner;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Obstacle;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Terrain;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.HorzScrollBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ScoreObject;
import kr.ac.kpu.game.charg2dang.cookierun.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.ui.SlideButton;

public class GameScene extends Scene
{
	private  enum PlayState
	{
		normal, paused, gameOver
	}

	private static final String TAG = GameScene.class.getSimpleName();
	private static final int BALL_COUNT = 10;
	public static final String PREF_KEY_HIGHSCORE = "highscore";
	public static String PREFS_NAME = "Prefs";

	private Cookie cookie;
	private JumpButton  jumpButton;
	private SlideButton slideButton;
	private ItemSpawner itemGenerator = new ItemSpawner();

	private ScoreObject scoreObject;
	private ScoreObject highScoreObject;

	private GameScene.PlayState playState = PlayState.normal;

	public static void create()
	{
		if(singleton != null)
		{
			Log.e(TAG, "Gameworld sublcass ");
		}

		Scene.singleton = new GameScene();
	}

	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}


	@Override
	protected int getLayerCount()
	{
		return LayerType.MAX.ordinal();
	}
	public static GameScene get()
	{
		return (GameScene) singleton;
	}



	public void initObjects()
	{
		Resources res = view.getResources();

		Random rand = new Random();
		for (int i = 0; i < BALL_COUNT; ++i)
		{
			float x = rand.nextFloat() * 1000;
			float y = rand.nextFloat() * 1000;

			float dy = (float) (rand.nextFloat() * 50.0f - 25.0f);
			float dx = (float) (rand.nextFloat() * 50.0f - 25.0f);

		}


		cookie = Cookie.getInstance();
		cookie.setPosition( 350, 900);
		cookie.setScale(3);
		add(LayerType.player, cookie);

		Coin coin = new Coin(1500 , 850);
		add(LayerType.item, coin);

		CoinSilver coinSilver = new CoinSilver(1800 , 850);
		add(LayerType.item, coinSilver);

//		scoreObject = new ScoreObject(800, 100, R.mipmap.number_64x84);
//		add(LayerType.ui, scoreObject);
//
//		highScoreObject = new ScoreObject(800, 100, R.mipmap.number_24x32);
//		add(LayerType.ui, highScoreObject);
//
//		add(LayerType.bg, new ImageScrollBackground(R.mipmap.bg_city, ImageScrollBackground.Orientation.vertical, -25));
//		add(LayerType.bg, new ImageScrollBackground(R.mipmap.cloud1, ImageScrollBackground.Orientation.vertical, 100));

		HorzScrollBackground hzBg = new HorzScrollBackground(R.mipmap.terrain_ground2, ImageScrollBackground.Orientation.horizontal, -10);
		add(LayerType.bg, hzBg);

		jumpButton = JumpButton.getInstance();
		jumpButton.setPosition(250, 1050);
		jumpButton.setScale(4);
		add(LayerType.ui, jumpButton);


		slideButton = SlideButton.getInstance();
		slideButton.setPosition(2300, 1050);
		slideButton.setScale(4);
		add(LayerType.ui, slideButton);


		Obstacle obs = new Obstacle(2000, 850);
		obs.setScale(1);
		add(LayerType.obstacle, obs);


		Terrain terrain = new Terrain(1300, 1200);
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
		int score = scoreObject.getScore();
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

	public void addScore(int score)
	{
		scoreObject.addScore(score);
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
