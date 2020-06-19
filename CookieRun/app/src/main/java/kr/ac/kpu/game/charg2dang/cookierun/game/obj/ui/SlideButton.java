package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class SlideButton extends GameObject
{
	private static final String TAG = SlideButton.class.getSimpleName();
	private final SharedBitmap slide;
	private final SharedBitmap slidePressed;

	private float x, y;
	private final Cookie cookie;
	private boolean down;
	private float scale;
	private RectF box;


	private static SlideButton instance;
	private boolean state = true;

	public static SlideButton getInstance()
	{
		if(instance == null)
		{
			instance = new SlideButton(0, 0);
		}

		return instance;
	}


	public SlideButton(float x, float y)
	{
		this.slide = SharedBitmap.load(R.mipmap.slide_button);
		this.slidePressed = SharedBitmap.load(R.mipmap.slide_pressed);
		this.x = x;// - ( jump.getWidth() / 2 );
		this.y = y;// - ( jump.getHeight() / 2 );

		down = false;

		box = new RectF();

		cookie = Cookie.getInstance();
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

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		resizeBox();
	}


	@Override
	public void update(long timeDiffNanos)
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

	@Override
	public boolean getState()
	{
		return state;
	}

	public boolean isPressed()
	{
		return down;
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
