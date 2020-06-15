package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;

import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;

public abstract class FSM
{
	protected Cookie cookie;

	public FSM(Cookie cookie)
	{
		this.cookie = cookie;

	}

	abstract public void update(long timeDiffNanos);

	abstract public void draw(Canvas canvs);

	abstract public void exit();

	public FrameAnimationBitmap getFab()
	{
		return null;
	}

}
