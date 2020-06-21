package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
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

		cookie.setFSMState(CookieState.death);

		xOffset = -100.f;
		menuTime = 0.f;
	}

	@Override
	public void update(long timeDiffNanos)
	{

		if(fab.done() == true)
		{
			// show end game ui
//
//			if(SceneManager.getInstance().getCurrentScene().getPaused() == false)
//				SceneManager.getInstance().getCurrentScene().pause(PauseReason.CookieDeath);

			menuTime += GameTimer.getInstance().getDeltaSecondsSingle();
			if(this.menuTimer <= menuTime)
			{
				Log.d(TAG, "resultScene1");
//				SceneManager.getInstance().getCurrentScene().resume();
				SceneManager.getInstance().changeScene(SceneType.result, true);

				menuTime = 0.0f;
			}

		}
		else
		{
		//	xOffset += Framework.getInstance().getCommonVelocity() * GameTimer.getInstance().getCurrentDeltaSecondsSngle();

//			if(SceneManager.getInstance().getCurrentScene().getPaused() == false)
//				SceneManager.getInstance().getCurrentScene().pause(PauseReason.CookieDeath);

			menuTime += GameTimer.getInstance().getDeltaSecondsSingle();
			if(this.menuTimer <= menuTime)
			{
//				SceneManager.getInstance().getCurrentScene().resume();
				SceneManager.getInstance().changeScene(SceneType.result, true);

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
//		SceneManager.getInstance().getScene(SceneType.game).resume();
//		SceneManager.getInstance().getScene(SceneType.game).reset();

//		SceneManager.getInstance().getCurrentScene().reset();
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
