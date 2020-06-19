package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.JumpButton;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.SlideButton;

// any -> damage
// run 에서 이동 가능한 스테이트 jump, slide
public class RunState extends FSM
{
	private static final String TAG = RunState.class.getSimpleName();
	private static final int FRMAE_PER_SECOND = 12;
	private static final int RUN_FRAME_COUNT = 4;

	private static RectF colliderBox;
	private static final FrameAnimationBitmap fabRun = new FrameAnimationBitmap( R.mipmap.cookie_run_brave, FRMAE_PER_SECOND, RUN_FRAME_COUNT);
	private static Point size;
	private static Point halfSize;


	public RunState(Cookie cookie)
	{
		super(cookie);

		fabRun.reset();
		if( colliderBox == null )
		{
			colliderBox = new RectF();
			halfSize = new Point();
			size = new Point();

			halfSize.y = fabRun.getHeight() / 2;
			halfSize.x = fabRun.getWidth() / 2;
		}

		cookie.setColliderBox(colliderBox);
		updateForColliderBox();
		cookie.setFSMState(CookieState.run);
	}


	@Override
	public void update(long timeDiffNanos)
	{
		cookie.setColliderBox(colliderBox);
		updateForColliderBox();
		if (JumpButton.getInstance().isPressed() == true && cookie.isGround() == true)
		{
			cookie.pushState(new JumpState(cookie));
		}
		// slide event
		else if (SlideButton.getInstance().isPressed() == true && cookie.isGround() == true)
		{
			cookie.pushState(new SlideState(cookie));
		}
	}

	public void updateForColliderBox()
	{
		colliderBox.left  		= cookie.getX() - (halfSize.x * cookie.getScale());
		colliderBox.top   		= cookie.getY() - (halfSize.y * cookie.getScale());
		colliderBox.right		= cookie.getX() + (halfSize.x * cookie.getScale());
		colliderBox.bottom  	= cookie.getY() + (halfSize.y * cookie.getScale());

	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(cookie.getScale(), cookie.getScale() , cookie.getX(), cookie.getY());
		fabRun.draw(canvas, cookie.getX(), cookie.getY());
		canvas.restore();

		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(colliderBox, Framework.getInstance().getDebugPaint());
		}

	}

	@Override
	public void exit()
	{
		cookie.setColliderBox(null);
	}
	public FrameAnimationBitmap getFab()
	{
		return fabRun;
	}
	public Point getSize()
	{
		return size;
	}
}
