package kr.ac.kpu.game.charg2dang.cookierun.game.framework.obj;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.ColliderTag;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class BitmapObject extends GameObject implements BoxCollidable
{
	private static final String TAG = BitmapObject.class.getSimpleName();
	protected SharedBitmap sbmp;
	protected final RectF dstRect;
	protected int width;
	protected int height;
	private RectF box;

	public BitmapObject(float x, float y, int width, int height, int resId)
	{
		this.x = x;
		this.y = y;
		this.dstRect = new RectF();
		box = new RectF();

		if (resId == 0)
		{
			return;
		}
		sbmp = SharedBitmap.load(resId);


		if (width == 0)
		{
			width = UiBridge.x(sbmp.getWidth());
		}
		else if (width < 0)
		{
			width = UiBridge.x(sbmp.getWidth()) * -width / 100;
		}
		this.width = width;


		if (height == 0)
		{
			height = UiBridge.x(sbmp.getHeight());
		}
		else if (height < 0)
		{
			height = UiBridge.x(sbmp.getHeight()) * -height / 100;
		}
		this.height = height;

		updateForColliderBox();
	}

	public void draw(Canvas canvas)
	{
		int halfWidth = width / 2;
		int halfHeight = height / 2;

		dstRect.left = x - halfWidth;
		dstRect.top = y - halfHeight;
		dstRect.right = x + halfWidth;
		dstRect.bottom = y + halfHeight;

		canvas.drawBitmap(sbmp.getBitmap(), null, dstRect, null);

		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
		}
	}

	@Override
	public void update(long deltaTime)
	{
		updateForColliderBox();
		if(box.right <= 0)
		{
			this.state = false;
		}
	}



	public void updateForColliderBox()
	{
		int hw = width / 2;
		int hh = height / 2;

		box.left = x - hw;
		box.top = y - hh;
		box.right = x + hw;
		box.bottom = y + hh;
	}

	@Override
	public RectF getColliderBox()
	{
		return box;
	}


	@Override
	public void onCollision(BoxCollidable o1)  { }
	@Override
	public ColliderTag getTag()
	{
		return null;
	}
}