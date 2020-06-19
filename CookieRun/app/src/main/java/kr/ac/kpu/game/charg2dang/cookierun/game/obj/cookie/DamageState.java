package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.RectF;


import androidx.annotation.NonNull;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;

public class DamageState extends FSM
{
	public static final int FRMAE_PER_SECOND = 12;
	private static final int RUN_FRAME_COUNT = 2;

	private static final FrameAnimationBitmap fab = new FrameAnimationBitmap( R.mipmap.cookie_damage_brave, FRMAE_PER_SECOND, RUN_FRAME_COUNT);
	private static int halfHegith;
	private static int halfWidth;
	private float recoveryTime = 0;
	private float recoveryTimer = 0.3f;
	private static RectF box;

	public DamageState(Cookie cookie)
	{
		super(cookie);

		cookie.setFSMState(CookieState.damage);

		fab.reset();

		fab.setAlpha(150);

		if( box == null )
		{
			box = new RectF();
			halfHegith = fab.getHeight() / 2;
			halfWidth = fab.getWidth() / 2;
		}

		cookie.setColliderBox(box);
		updateForColliderBox();
		cookie.setFSMState(CookieState.run);
	}

	private void updateForColliderBox()
	{
		box.left  	= cookie.getX() - (halfWidth * cookie.getScale());
		box.top     = cookie.getY() - (halfHegith * cookie.getScale());
		box.right	= cookie.getX() + (halfWidth * cookie.getScale() );
		box.bottom  = cookie.getY() + (halfHegith * cookie.getScale());
	}


	@Override
	public void update(long timeDiffNanos)
	{
		recoveryTime += GameTimer.getInstance().getCurrentDeltaSecondsSngle();
		if(recoveryTime >= recoveryTimer)
		{
			cookie.pushState(new RunState(cookie));
			recoveryTime = 0.0f;
		}
		cookie.setColliderBox(box);
		updateForColliderBox();
	}

	@Override
	public void draw(@NonNull Canvas canvas)
	{
		canvas.save();
		canvas.scale(cookie.getScale(), cookie.getScale() , cookie.getX(), cookie.getY());
		fab.draw(canvas, cookie.getX(), cookie.getY());
		canvas.restore();
	}

	@Override
	public void exit()
	{

	}
}
