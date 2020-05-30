package kr.ac.kpu.game.charg2dang.cookierun.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class JumpButton implements GameObject
{
	private static final String TAG = JumpButton.class.getSimpleName();
	private final SharedBitmap jump;
	private final SharedBitmap jumpPressed;

	private final float x, y;
	private float yDown, xDown;
	private double angle;
	private boolean down;
	private float scale;

	public JumpButton(float x, float y)
	{
		this.jump = SharedBitmap.load(R.mipmap.jump);
		this.jumpPressed = SharedBitmap.load(R.mipmap.jump_pressed);
		this.x = x;
		this.y = y;

		down = false;
	}


	public void setScale(float scale)
	{
		this.scale = scale;
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
				// 영역일때만 true
				down = true;
				break;

			case MotionEvent.ACTION_UP:

				if(!down) return;

				float dx = event.getX() - xDown;
				float dy = event.getY() - yDown;

				this.angle = Math.atan2(dy, dx);

				break;

			default:

				break;
		}
	}
}
