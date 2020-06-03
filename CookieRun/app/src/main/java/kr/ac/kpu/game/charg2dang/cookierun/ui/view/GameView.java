package kr.ac.kpu.game.charg2dang.cookierun.ui.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.util.IndexTimer;

public class GameView extends View
{
	private static final String TAG = GameView.class.getSimpleName();
	private static final int FRAME_RATE_SECONDS = 10;
	private Rect mainRect;
	private Paint mainPaint;

	private ArrayList<GameObject> objects;
	private Scene world;
	private IndexTimer timer;
	private boolean paused;


	public GameView(Context context)
	{
		super(context);

		initResource();;
	}

	public GameView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);

		initResource();;

	}

	private void initResource()
	{
		WindowManager wm = (WindowManager) getContext().getSystemService((Service.WINDOW_SERVICE));
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		mainRect = new Rect(0 ,0, size.x, size.y);

		SharedBitmap.setResources(getResources());
		mainPaint = new Paint();
		mainPaint.setColor(0xFFFFEEEE);

		world = Scene.get();
		world.setRect(mainRect);
		world.initResources(this);

		timer = new IndexTimer(FRAME_RATE_SECONDS, 1);
		postFrameCallback();

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
					update(frameTimeNanos);
					collide(frameTimeNanos);
					invalidate();
					postFrameCallback();
				}
			});
		}
	}



	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawRect(mainRect, mainPaint);
		world.draw(canvas);
//        canvas.drawBitmap(ballImage, xBall, yBall, null);
	}


	private int count;
	public void update(long frameTimeNanos)
	{
		world.update(frameTimeNanos);
//        Log.d(TAG, "update() : " + xBall + ", " + yBall);

		count++;
		if(timer.done())
		{
			Log.d(TAG, "Frames Per Second = " + ((float)count / FRAME_RATE_SECONDS));
			count = 0;
			timer.reset();
		}
	}


	public void collide(long frameTimeNanos)
	{
		world.collide(frameTimeNanos);
	}



	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return world.onTouchEvent(event);
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
