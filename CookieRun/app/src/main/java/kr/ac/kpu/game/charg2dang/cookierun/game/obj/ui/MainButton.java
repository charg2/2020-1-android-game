
package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.SoundEffects;

public class MainButton extends GameObject
{
	private static final 	String 			TAG = MainButton.class.getSimpleName();
	private final 			SharedBitmap 	resumePressed;
	private final 			SharedBitmap 	resume;
	private 				float 			x, y;
	private 				boolean 		down = false;
	private 				float 			scale = 2;
	private 				RectF 			box;

	private static MainButton instance;
	public static MainButton getInstance()
	{
		if(instance == null)
		{
			instance = new MainButton(0, 0);
		}

		return instance;
	}


	public MainButton(float x, float y)
	{
		this.resume = SharedBitmap.load(R.mipmap.ui_result_mainbutton);
		this.resumePressed = SharedBitmap.load(R.mipmap.ui_result_mainbutton);
		this.x = x;//
		this.y = y;//

		down = false;

		box = new RectF();

		updateForColliderBox();
	}


	public void updateForColliderBox()
	{
		box.left = x;
		box.right = x + (scale * resume.getWidth());
		box.top = y + (scale * resume.getHeight());
		box.bottom = y;
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


	@Override
	public void update(long timeDiffNanos)
	{ }

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale, x, y);

		if (down == false) resume.draw(canvas, x, y);
		else resumePressed.draw(canvas, x, y);

		canvas.restore();
	}

	public boolean isPressed()
	{
		return down;
	}

	public float getHeight()
	{
		return this.resume.getHeight();
	}


	public float getWidth()
	{
		return this.resume.getWidth();
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
						if(down == false)
						{
							SoundEffects.get().play(R.raw.se_bt_on);
							SceneManager.getInstance().changeScene(SceneType.main, false);
						}
						down = true;
					}
				}

				break;

			case MotionEvent.ACTION_UP:
				if(down != true)
					return;
				else
				{
					down = false;
//					SceneManager.getInstance().getCurrentScene().resume();
				}
				break;

			default:
				break;
		}
	}

}
