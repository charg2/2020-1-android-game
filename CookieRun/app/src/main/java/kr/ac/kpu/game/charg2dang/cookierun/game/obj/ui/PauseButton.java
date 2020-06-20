package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.scenes.GameScene;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.SoundEffects;

public class PauseButton extends GameObject
{
	private static final 	String TAG = PauseButton.class.getSimpleName();
	private final 			SharedBitmap 	slidePressed;
	private final 			SharedBitmap 	slide;
	private 				Runnable 		onClickRunnable;
	private 				float x, y;
	private 				boolean down;
	private 				float scale;
	private 				RectF box;


	private static PauseButton instance;

	public static PauseButton getInstance()
	{
		if(instance == null)
		{
			instance = new PauseButton(0, 0);
		}

		return instance;
	}


	public PauseButton(float x, float y)
	{
		this.slide = SharedBitmap.load(R.mipmap.ui_pause_button);
		this.slidePressed = SharedBitmap.load(R.mipmap.ui_pause_button_pressed);
		this.x = x;//
		this.y = y;//

		down = false;

		box = new RectF();

		updateForColliderBox();
	}


	public void updateForColliderBox()
	{
		box.left = x;
		box.right = x + (scale * slide.getWidth());
		box.top = y + (scale * slide.getHeight());
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
		canvas.scale(scale, scale , x, y);

		if(down == false)  slide.draw(canvas, x, y);
		else slidePressed.draw(canvas, x, y);

		canvas.restore();
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
			{
				float dx = event.getX();
				float dy = event.getY();

				if (box.left <= dx && dx <= box.right)
				{
					if (box.bottom <= dy && dy <= box.top)
					{
						if (down == false)
						{
							SoundEffects.get().play(R.raw.se_bt_on);
							GameScene currentScene = (GameScene) SceneManager.getInstance().getCurrentScene();
							currentScene.pause(PauseReason.Stop);
						}
						down = true;
					}
				}

				break;
			}
			case MotionEvent.ACTION_UP:
			{
				if (down != true) return;

				down = false;

				break;
			}
			default:

				break;
		}
	}

	private void onPause()
	{
		GameScene currentScene = (GameScene)SceneManager.getInstance().getCurrentScene();
		currentScene.pause(PauseReason.Stop);
	}
}