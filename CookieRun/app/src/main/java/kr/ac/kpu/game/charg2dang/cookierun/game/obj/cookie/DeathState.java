package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class DeathState extends FSM
{
	public static final int FRMAE_PER_SECOND = 12;
	private static final int RUN_FRAME_COUNT = 5;
	private static final String TAG = DeathState.class.getSimpleName();

	private static FrameAnimationBitmap fab;
	private static  SharedBitmap bitmap;

	private float menuTime;
	private final float menuTimer = 0.2f;
	private float xOffset;


	public DeathState(Cookie cookie)
	{
		super(cookie);

		if(fab == null)
		{
			fab = new FrameAnimationBitmap( R.mipmap.cookie_death_brave, FRMAE_PER_SECOND, RUN_FRAME_COUNT);
			bitmap = SharedBitmap.load(R.mipmap.cookie_death__);
		}
		else
		{
			fab.reset();
		}

		xOffset = -100.f;
		menuTime = 0.f;
	}

	@Override
	public void update(long timeDiffNanos)
	{
		if(fab.done() == true)
		{
			// show end game ui
			SceneManager.getInstance().getCurrentScene().pause(PauseReason.CookieDeath);

			menuTime += GameTimer.getInstance().getCurrentDeltaSecondsSngle();
			if(this.menuTimer <= menuTime)
			{
				Log.d(TAG, "resultScene1");
				SceneManager.getInstance().getCurrentScene().resume();
				SceneManager.getInstance().changeScene(SceneType.result, true);

				menuTime = 0.0f;
			}

		}
		else
		{
		//	xOffset += Framework.getInstance().getCommonVelocity() * GameTimer.getInstance().getCurrentDeltaSecondsSngle();
			menuTime += GameTimer.getInstance().getCurrentDeltaSecondsSngle();
			if(this.menuTimer <= menuTime)
			{
				Log.d(TAG, "resultScene2");
				SceneManager.getInstance().changeScene(SceneType.result, true);
				SceneManager.getInstance().getCurrentScene().resume();
				menuTime = 0.0f;
			}

		}
	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(cookie.getScale(), cookie.getScale() , cookie.getX(), cookie.getY());
		if(fab.done() == false)
		{
			fab.draw(canvas, cookie.getX(), cookie.getY());
		}
		else
		{
			bitmap.draw(canvas, cookie.getX() , cookie.getY());
		}
		canvas.restore();
	}

	@Override
	public void exit()
	{
		SceneManager.getInstance().getCurrentScene().resume();
	}

	private static Point size;
	private static Point halfSize;
	public Point getSize()
	{
		return size;
	}
	public Point getHalfSize()
	{
		return halfSize;
	}
}
