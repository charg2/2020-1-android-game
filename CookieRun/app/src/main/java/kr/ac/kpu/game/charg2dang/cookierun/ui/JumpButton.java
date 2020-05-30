package kr.ac.kpu.game.charg2dang.cookierun.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.sql.Ref;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class JumpButton implements GameObject
{
	private static final String TAG = JumpButton.class.getSimpleName();
	private final SharedBitmap jump;
	private final SharedBitmap jumpPressed;
	private RectF box;
	private final float x, y;
	private double angle;
	private boolean down;
	private float scale;
	private Cookie cookie;

	public JumpButton(float x, float y)
	{
		this.jump = SharedBitmap.load(R.mipmap.jump);
		this.jumpPressed = SharedBitmap.load(R.mipmap.jump_pressed);

		this.x = x;// - ( jump.getWidth() / 2 );
		this.y = y;// - ( jump.getHeight() / 2 );

		down = false;

		box = new RectF();

		cookie = Cookie.getInstande();

		resizeBox();
	}

	public void setScale(float scale)
	{
		this.scale = scale;

		resizeBox();
	}

	public void resizeBox()
	{
		box.left = x;
		box.right = x + (scale * jump.getWidth());
		box.top = y + (scale * jump.getHeight());
		box.bottom = y;
	}


	@Override
	public void update()
	{ }

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale , x, y);

		if(down == false)  jump.draw(canvas, x, y);
		else jumpPressed.draw(canvas, x, y);

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
						cookie.jump();
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
	}
}
