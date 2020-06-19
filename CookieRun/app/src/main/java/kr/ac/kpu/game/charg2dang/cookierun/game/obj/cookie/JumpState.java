package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.SoundEffects;


// any -> damage
// jump 에서 바로 이동 가능한 스테이트 doubleJump

public class JumpState extends FSM
{
	public static final int FRMAE_PER_SECOND = 12;
	private static final int JUMP_FRAME_COUNT = 2;

	private static final float JUMP_POWER = -30.f;
	private static final float GRAVITY_SPEED = 4500;
	private static float speed = 0.0f;

	private static final FrameAnimationBitmap fabJump= new FrameAnimationBitmap( R.mipmap.cookie_jump_brave , FRMAE_PER_SECOND, JUMP_FRAME_COUNT);;
	// 사운드도 관리 해도 될듯.
	private static RectF box;
	private boolean onceButtonUped = false;
	private final long jumpTimer = 500_000_000L;
	private long jumpTime = 0;
	private static float halfHegith, halfWidth;
	private float delta;
	private static Point size;
	private static Point halfSize;

	public JumpState(Cookie cookie)
	{
		super(cookie);

		if( box == null )
		{
			box = new RectF();
			halfHegith = fabJump.getHeight() / 2;
			halfWidth = fabJump.getWidth() / 2;
		}

		cookie.setFSMState(CookieState.jump);
		fabJump.reset();
		calcBox();
		cookie.setColliderBox(box);

		speed = JUMP_POWER;
//		delta = 60;

		SoundEffects.get().play(R.raw.se_jump);
	}

	public void calcBox()
	{
		box.left  	= cookie.getX() - (halfWidth * cookie.getScale());
		box.top     = cookie.getY() - (halfHegith * cookie.getScale());
		box.right	= cookie.getX() + (halfWidth * cookie.getScale() );
		box.bottom  = cookie.getY() + (halfHegith * cookie.getScale());
	}


	// 매 업데이트 마다 충돌박스를 수정해야함.
	@Override
	public void update(long timeDiffNanos)
	{
		jumpTime += timeDiffNanos;
		if(jumpTime >= jumpTimer)
		{
			jumpTime = 0; // 이거 왜 껏더라?
			cookie.pushState(new RunState(cookie));
		}
		if ( onceButtonUped == true && JumpButton.getInstance().isPressed() == true)
		{
			cookie.pushState(new DoubleJumpState(cookie));
		}
		else
		{
			if( JumpButton.getInstance().isPressed() == false )
			{
				onceButtonUped = true;
			}
		}


		float timeDiffSeconds = GameTimer.getInstance().getCurrentDeltaSecondsSngle();
		speed += 9.8f * timeDiffSeconds * cookie.getMass();
		if(speed > 0)
		{
			speed = 0;
		}
		cookie.move( 0 , speed);
		calcBox();
	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(cookie.getScale(), cookie.getScale() , cookie.getX(), cookie.getY());
		fabJump.draw(canvas, cookie.getX(), cookie.getY());
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

	public Point getSize()
	{
		return size;
	}

	public Point getHalfSize()
	{
		return halfSize;
	}

	public FrameAnimationBitmap getFab()
	{
		return fabJump;
	}
}
