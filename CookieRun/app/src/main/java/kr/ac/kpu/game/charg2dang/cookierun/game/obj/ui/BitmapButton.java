package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class BitmapButton  extends GameObject
{
	protected static final 	String 			TAG = BitmapButton.class.getSimpleName();
	protected final SharedBitmap defaultBitmap;
	protected final SharedBitmap presedBitmap;

	protected RectF box;
	protected float x, y;

	protected boolean down= false;
	protected float scale = 1.0f;

	BitmapButton(float x, float y, int resID, int presedResID)
	{
		defaultBitmap = SharedBitmap.load(resID);
		presedBitmap = SharedBitmap.load(resID);

		this.x = x;
		this.y = y;

		box = new RectF();
		updateForColliderBox();
	}

	public void updateForColliderBox()
	{
		box.left = x - (scale * defaultBitmap.getWidth());
		box.right = x + (scale * defaultBitmap.getWidth());
		box.top = y + (scale * defaultBitmap.getHeight());
		box.bottom = y +  (scale * defaultBitmap.getHeight());
	}

	public void setScale(float scale)
	{
		this.scale = scale;
		updateForColliderBox();
	}

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;

		updateForColliderBox();
	}

	private static BitmapButton instance;
	public static BitmapButton getInstance()
	{
		if(instance == null)
		{
			instance = new BitmapButton(0, 0, 0, 0);

			throw new UnsupportedOperationException("Not implemention yet.");
		}

		return instance;
	}


	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale, x, y);

		if (down == false) defaultBitmap.draw(canvas, x, y);
		else presedBitmap.draw(canvas, x, y);

		canvas.restore();
	}

	public boolean isPressed()
	{
		return down;
	}

	public float getHeight()
	{
		return this.defaultBitmap.getHeight();
	}
	public float getWidth()
	{
		return this.defaultBitmap.getWidth();
	}

	public void onTouchEvent(MotionEvent event)
	{

	}

}
