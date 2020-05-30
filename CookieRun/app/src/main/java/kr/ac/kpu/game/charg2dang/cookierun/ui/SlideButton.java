package kr.ac.kpu.game.charg2dang.cookierun.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class SlideButton implements GameObject
{
	private static final String TAG = SlideButton.class.getSimpleName();
	private final SharedBitmap slide;
	private final SharedBitmap slidePressed;

	private final float x, y;
	private final Cookie cookie;
	private float yDown, xDown;
	private double angle;
	private boolean down;
	private float scale;
	private RectF box;

	public SlideButton(float x, float y)
	{
		this.slide = SharedBitmap.load(R.mipmap.slide_button);
		this.slidePressed = SharedBitmap.load(R.mipmap.slide_pressed);
		this.x = x;// - ( jump.getWidth() / 2 );
		this.y = y;// - ( jump.getHeight() / 2 );

		down = false;

		box = new RectF();

		cookie = Cookie.getInstande();
		resizeBox();
	}


	public void resizeBox()
	{
		box.left = x;
		box.right = x + (scale * slide.getWidth());
		box.top = y + (scale * slide.getHeight());
		box.bottom = y;
	}

	public void setScale(float scale)
	{
		this.scale = scale;

		resizeBox();
	}
	@Override
	public void update()
	{ }

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale , x, y);

		if(down == false)  slide.draw(canvas, x, y);
		else slidePressed.draw(canvas, x, y);

		canvas.restore();
	}


	public void onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				float dx = event.getX();
				float dy = event.getY();

				if(box.left <= dx && dx <= box.right)
				{
					if (box.bottom <= dy && dy <= box.top)
					{
						cookie.slide();
						down = true;
					}
				}

				break;

			case MotionEvent.ACTION_UP:

				if(down != true) return;

				down = false;

				break;

			default:

				break;
		}

//		public int getHorzDirection ()
//		{
////		if(false == down) return 0;
////		return angle < Math.PI / 2 && angle > -Math.PI / 2 ?  1 : -1;
////
//
//		}
	}
}
