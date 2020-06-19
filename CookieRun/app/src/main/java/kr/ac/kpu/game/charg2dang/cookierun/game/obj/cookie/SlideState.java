package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.graphics.Canvas;
import android.graphics.RectF;


import androidx.annotation.NonNull;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.SlideButton;


// 충돌박스 진입시 한번 수정
public class SlideState extends FSM
{
	public static final int FRMAE_PER_SECOND = 12;
	private static final int SLIDE_FRAME_COUNT = 2;

	private RectF box;
	private float heightOffset;
	private static float halfHegith, halfWidth;
	private float scale = 1.0f;
	private static final FrameAnimationBitmap fabSlide = new FrameAnimationBitmap( R.mipmap.cookie_slide_brave , FRMAE_PER_SECOND, SLIDE_FRAME_COUNT);;

	public SlideState(Cookie cookie)
	{
		super(cookie);

		heightOffset = cookie.getHeight() / 3.5f;
		fabSlide.reset();

		if( box == null )
		{
			box = new RectF();
			halfHegith = fabSlide.getHeight() / 2;
			halfWidth = fabSlide.getWidth() / 2;
		}

		cookie.setFSMState(CookieState.slide);

		// 쿠기 슬라이드 상태일때 바로 바닥으로 내리기 위해서...
		float offsetY = cookie.isGiantMode() ? 200 : 100;
		cookie.move(0,  offsetY);

		cookie.setColliderBox(box);

		updateColliderBox( cookie.getScale() );
	}
	public void updateColliderBox(float scale)
	{
		box.left  	= cookie.getX() - (halfWidth * scale);
		box.top     = cookie.getY() - (halfHegith * scale);
		box.right	= cookie.getX() + (halfWidth * scale);
		box.bottom  = cookie.getY() + (halfHegith * scale);
	}


	@Override
	public void update(long timeDiffNanos)
	{
		// 여기서 갈 수 있는 상태
		// RUN
		if(SlideButton.getInstance().isPressed() == false)
			cookie.pushState(new RunState(cookie));


		updateColliderBox( cookie.getScale() );
		cookie.setColliderBox(box);

		// Slide 유지
	}

	@Override
	public void draw(@NonNull Canvas canvas)
	{
		if( cookie.canHitted() == false )
		{

		}

		canvas.save();
		canvas.scale(cookie.getScale(), cookie.getScale() , cookie.getX(), cookie.getY());
		fabSlide.draw(canvas, cookie.getX(), cookie.getY() );
		canvas.restore();

		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
		}

	}

	@Override
	public void exit()
	{
		// 히트박스 복구.
		cookie.move(0, - (halfHegith * cookie.getScale()));
		cookie.setColliderBox(null);
	}

	public FrameAnimationBitmap getFab()
	{
		return fabSlide;
	}


}
