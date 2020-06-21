package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.SoundEffects;

public class DoubleJumpState extends FSM
{
	public static final int FRMAE_PER_SECOND = 12;
	private static final int RUN_FRAME_COUNT = 7;
	private static RectF box;
	private static final float JUMP_POWER = -60.f;
	private static float speed = 0.0f;

	private static final FrameAnimationBitmap fab = new FrameAnimationBitmap( R.mipmap.cookie_arijump_brave, FRMAE_PER_SECOND, RUN_FRAME_COUNT);
	private long doubleJumpTimer = 5000_000_00L;
	private long doubleJumpTime;
	private static float halfHegith, halfWidth;

	public DoubleJumpState(Cookie cookie)
	{
		super(cookie);

		cookie.setFSMState(CookieState.jump2);
		if( box == null )
		{
			box = new RectF();
			halfHegith = fab.getHeight() / 2;
			halfWidth = fab.getWidth() / 2;
		}

		speed = JUMP_POWER;

		calcBox();
		cookie.setColliderBox(box);

		SoundEffects.get().play(R.raw.se_jump2);

	}

	public void calcBox()
	{
		box.left  	= cookie.getX() - (halfWidth * cookie.getScale());
		box.top     = cookie.getY() - (halfHegith * cookie.getScale());
		box.right	= cookie.getX() + (halfWidth * cookie.getScale() );
		box.bottom  = cookie.getY() + (halfHegith * cookie.getScale());
	}



	@Override
	public void update(long timeDiffNanos)
	{
		doubleJumpTime += timeDiffNanos;
		if(doubleJumpTime >= doubleJumpTimer)
		{
			// 시간 끝....
//			jumpTime = 0;
			cookie.pushState(new RunState(cookie));
		}

		float timeDiffSeconds = GameTimer.getInstance().getDeltaSecondsSingle();
		speed += 9.8f * timeDiffSeconds * cookie.getMass();
		if(speed > 0)
		{
			speed = 0;
		}
//		delta -= 2;
		cookie.move(0, speed );

		calcBox();
	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(cookie.getScale(), cookie.getScale() , cookie.getX(), cookie.getY());
		fab.draw(canvas, cookie.getX(), cookie.getY());
		canvas.restore();



		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
		}
	}

	@Override
	public void exit()
	{

	}
}
