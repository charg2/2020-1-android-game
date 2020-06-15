package kr.ac.kpu.game.charg2dang.cookierun.game.framework;


import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;

public class GiantComponent
{
	public Cookie cookie;
	public final float defaultScale;
	public float currentScale;
	public final float maxScale;

	public boolean canGrow = false;

	boolean reversed = true;

	public final float giantTimer = 5.0f;
	public float giantTime = 0.0f;


	public GiantComponent(Cookie cookie)
	{
		this.cookie = cookie;

		defaultScale = cookie.getScale();
		currentScale = defaultScale;
		maxScale = defaultScale * 2.0f;
	}


	public void doAutoGrew()
	{
		canGrow = true;
		giantTime = 0.0f;
		reversed = true;
	}


	public void update(long nanotimes)
	{
		if( canGrow == true ) // 커질수 있을때만.
		{
			float deltaTime = GameTimer.getInstance().getCurrentDeltaSecondsSngle();
			giantTime += deltaTime;

			if (reversed) // size up time
			{
				currentScale += (defaultScale * deltaTime);
				if (currentScale >= maxScale)
				{
					currentScale = maxScale;
				}

				if (giantTime > 3.9f) // 3.9초가
				{
					reversed = false;
				}

			} else // size down time
			{
				currentScale -= (defaultScale * deltaTime);
				if (currentScale <= defaultScale)
				{
					currentScale = defaultScale;
				}
			}

			cookie.setScale(currentScale); // 변경된 scale을 반영.
			if (giantTime > giantTimer)		// 시간이 지나면 끔.
			{
				canGrow = false;
			}
		}
	}
}
