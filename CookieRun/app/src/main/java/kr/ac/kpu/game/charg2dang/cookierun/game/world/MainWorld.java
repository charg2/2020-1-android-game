package kr.ac.kpu.game.charg2dang.cookierun.game.world;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameWorld;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
//import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Ball;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ItemSpawner;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
//import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Joystick;
//import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Plane;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ScoreObject;
import kr.ac.kpu.game.charg2dang.cookierun.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.ui.SlideButton;

public class MainWorld extends GameWorld
{
	private  enum PlayState
	{
		normal, paused, gameOver
	}

	public enum LayerType
	{
		bg, enemy, player, ui, MAX
	}
	private static final String TAG = MainWorld.class.getSimpleName();
	private static final int BALL_COUNT = 10;
	public static final String PREF_KEY_HIGHSCORE = "highscore";
	public static String PREFS_NAME = "Prefs";

	private Cookie cookie;
	private JumpButton  jumpButton;
	private SlideButton slideButton;
	private ItemSpawner enemyGenerator = new ItemSpawner();
//	private Plane plane;
	private ScoreObject scoreObject;
	private ScoreObject highScoreObject;

	private MainWorld.PlayState playState = PlayState.normal;

	public static void create()
	{
		if(singleton != null)
		{
			Log.e(TAG, "Gameworld sublcass ");
		}

		GameWorld.singleton = new MainWorld();
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
	public static MainWorld get()
	{
		return (MainWorld) singleton;
	}


	public ArrayList<GameObject> objectsAt(LayerType enemy)
	{
		return super.objectsAt(enemy.ordinal());
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

//			add(LayerType.missile, new Ball(res, x, y, dx, dy));
		}

//		float playerY = rect.bottom - 100;
//		plane =  new Plane(res, 500, playerY, 0.0f, 0.0f);
//		add(LayerType.player, plane);


		cookie = Cookie.getInstande();
		cookie.setPosition( 350, 700);
		cookie.setScale(3);
		add(LayerType.player, cookie);



//		scoreObject = new ScoreObject(800, 100, R.mipmap.number_64x84);
//		add(LayerType.ui, scoreObject);
//
//		highScoreObject = new ScoreObject(800, 100, R.mipmap.number_24x32);
//		add(LayerType.ui, highScoreObject);
//
//		add(LayerType.bg, new ImageScrollBackground(R.mipmap.bg_city, ImageScrollBackground.Orientation.vertical, -25));
//		add(LayerType.bg, new ImageScrollBackground(R.mipmap.cloud1, ImageScrollBackground.Orientation.vertical, 100));


		jumpButton = new JumpButton(250, 1250);
		jumpButton.setScale(4);
		add(LayerType.ui, jumpButton);


		slideButton = new SlideButton(2300, 1200);
		slideButton.setScale(4);
		add(LayerType.ui, slideButton);

//		plane.setJoystick(joystick);

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

	public void doAction()
	{
//		cookie.fire();
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

		enemyGenerator.update();
	}



	public boolean onTouchEvent(MotionEvent event)
	{
		jumpButton.onTouchEvent(event);
		slideButton.onTouchEvent(event);

		event.getX();
		event.getY();
		String state = "Y : " +  event.getY() + " , X : "+ event.getX();
		Log.d(TAG, state );

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
