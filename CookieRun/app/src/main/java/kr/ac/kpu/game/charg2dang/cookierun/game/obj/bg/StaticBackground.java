package kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class StaticBackground extends GameObject
{
	private SharedBitmap bitamp;
	private boolean state = true;
	private Paint paint;
	private RectF destRect;
	private Rect srcRect;

	public StaticBackground(int resID)
	{
		bitamp = SharedBitmap.load(resID);
		paint = new Paint();
		srcRect = new Rect();
		destRect = new RectF();


		srcRect.top = 0;
		srcRect.bottom = bitamp.getHeight();
		srcRect.left = 0;
		srcRect.right = bitamp.getWidth();

		destRect.top = 0;
		destRect.bottom = UiBridge.metrics.fullSize.y;
		destRect.left = 0;
		destRect.right = UiBridge.metrics.fullSize.x;

	}

	@Override
	public void update(long timeDiffNanos)
	{

	}

	@Override
	public void draw(Canvas canvs)
	{
//		bitamp.draw(canvs, 0, 0);


		canvs.drawBitmap(bitamp.getBitmap(), srcRect, destRect, null);
//		bitamp.draw(canvs, rect, paint);
	}

	@Override
	public boolean getState()
	{
		return state;
	}

	public void setState(boolean b)
	{
		this.state = b;
	}
}
