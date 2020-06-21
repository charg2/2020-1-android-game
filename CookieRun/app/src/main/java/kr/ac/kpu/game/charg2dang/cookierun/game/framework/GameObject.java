package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;

public class GameObject
{
	protected boolean state = true;
	protected float x, y;

	public void update(long timeDiffNanos)
	{}

    public void draw(Canvas canvas)
	{}

	public boolean getState()
	{
		return state;
	}

	public void setState(boolean newState)
	{
		this.state = newState;
	}

	public void move(float dx, float dy)
	{
		x += dx;
		y += dy;
	}

	protected void drawDebug(Canvas canvas, RectF box)
	{
		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
		}
	}

	public void updateAfterCollide()
	{ }

}
