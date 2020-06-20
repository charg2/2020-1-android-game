package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.util.Log;

import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;

public class NerfComponent
{
	Cookie cookie;
	private float nerfTime = 0.0f;
	private float nerfTimer = 1.0f;

	public NerfComponent(Cookie cookie)
	{
		this.cookie = cookie;
	}


	public void update(long deltaTime)
	{
		nerfTime += GameTimer.getInstance().getDeltaSecondsSingle();
		if(nerfTime > nerfTimer)
		{
			nerfTime = 0.f;
			cookie.decreaseHP(1.0f, false);
		}
	}
}
