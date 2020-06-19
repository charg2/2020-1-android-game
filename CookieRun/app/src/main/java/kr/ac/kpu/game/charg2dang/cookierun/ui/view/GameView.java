package kr.ac.kpu.game.charg2dang.cookierun.ui.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.scenes.GameScene;
import kr.ac.kpu.game.charg2dang.cookierun.game.scenes.LoadingScene;
import kr.ac.kpu.game.charg2dang.cookierun.game.scenes.MainScene;
import kr.ac.kpu.game.charg2dang.cookierun.game.scenes.ResultScene;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.IndexTimer;

public class GameView extends View
{
	private static final String TAG = GameView.class.getSimpleName();
	private static final int FRAME_RATE_SECONDS = 10;
	private Rect mainRect;
	private Paint mainPaint;
	private SceneManager sceneManager;
	private Framework framework;
	private IndexTimer timer;
	private GameTimer gameTimer;
	private boolean paused;

	public GameView(Context context)
	{
		super(context);
		UiBridge.setView(this);

		initResource();;
	}

	public GameView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);

		initResource();;
	}

	private void initResource()
	{
		// init font
		AssetManager am = this.getContext().getAssets();
		Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG );
		paint.setTypeface( Typeface.createFromAsset( am, "font_rix_toy_story.ttf" ) );
		paint.setTextSize( 200.0f );


		sceneManager 	= SceneManager.getInstance();
		gameTimer 		= GameTimer.getInstance();

		Point size = new Point();
		Framework.getInstance().setView(this);
		Framework.getInstance().setFont(paint);
		paint.setColor(Color.WHITE);

		mainRect = new Rect(0 ,0, size.x, size.y);

		SharedBitmap.setResources(getResources());
		mainPaint = new Paint();
		mainPaint.setColor(0xFFFFEEEE);

		prepareScene();

		timer = new IndexTimer(FRAME_RATE_SECONDS, 1);

		postFrameCallback();
	}

	private void prepareScene()
	{
		Scene loadingScene 	= new LoadingScene();
		Scene mainScene 	= new MainScene();
		Scene gameScene 	= new GameScene();
		Scene resultScene 	= new ResultScene();

		loadingScene.setRect(mainRect);
		mainScene.setRect(mainRect);
		gameScene.setRect(mainRect);
		resultScene.setRect(mainRect);

		loadingScene.initResources(this);
		mainScene.initResources(this);
		gameScene.initResources(this);
		resultScene.initResources(this);

		sceneManager.addScene(SceneType.loading, loadingScene);
		sceneManager.addScene(SceneType.main, mainScene);
		sceneManager.addScene(SceneType.game, gameScene);
		sceneManager.addScene(SceneType.result, resultScene);


		sceneManager.changeScene(SceneType.loading, false);
	}



	private void postFrameCallback()
	{
		if (paused == true)
		{
			return;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback()
			{
				@Override
				public void doFrame(long frameTimeNanos)
				{

					gameTimer.upadte(frameTimeNanos);

					update(frameTimeNanos);
					collide(frameTimeNanos);

					invalidate();
					postFrameCallback();

					//
					Framework.getInstance().increaseFrameCount();
				}
			});
		}
	}


	public void collide(long frameTimeNanos)
	{
		sceneManager.collide(frameTimeNanos);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawRect(mainRect, mainPaint);
		sceneManager.draw(canvas);
	}

	private int frameCheckCounter = 0;
	public void update(long frameTimeNanos)
	{
		sceneManager.update(frameTimeNanos);

		frameCheckCounter++;
		if(timer.done())
		{
			Log.d(TAG, "Frames Per Second = " + ((float) frameCheckCounter / FRAME_RATE_SECONDS));
			frameCheckCounter = 0;
			timer.reset();
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return SceneManager.getInstance().getCurrentScene().onTouchEvent(event);
	};

	public void pause()
	{
		this.paused = true;
	}
	public void resume()
	{
		this.paused = false;
	}
}
