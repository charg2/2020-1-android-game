package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;

import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.RunState;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;

public abstract class FSM
{
	protected Cookie cookie;

	public FSM(Cookie cookie)
	{
		this.cookie = cookie;
	}


	public void update()
	{
	}

	public void draw(Canvas canvs)
	{
	}

	public void exit()
	{
	}


	public FrameAnimationBitmap getFab()
	{
		return null;
	}

}
