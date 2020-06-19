package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;


import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class PauseText extends GameObject
{
	private static final 	String TAG = PauseButton.class.getSimpleName();
	private final 			SharedBitmap pauseText;
	private 				Runnable 		onClickRunnable;
	private 				float x, y;
	private 				float 			scale = 2;
	private 				RectF box;


	private static PauseText instance;
	private boolean state = true;

	public static PauseText getInstance()
	{
		if(instance == null)
		{
			instance = new PauseText(0, 0);
		}

		return instance;
	}


	public PauseText(float x, float y)
	{
		this.pauseText = SharedBitmap.load(R.mipmap.ui_pause_text);

		this.x = x;//
		this.y = y;//

		box = new RectF();

		updateForColliderBox();
	}


	public void updateForColliderBox()
	{
		box.left = x;
		box.right = x + (scale * pauseText.getWidth());
		box.top = y + (scale * pauseText.getHeight());
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

		pauseText.draw(canvas, x, y);

		canvas.restore();
	}


	public float getHeight()
	{
		return this.pauseText.getHeight();
	}
	public float getWidth()
	{
		return this.pauseText.getWidth();
	}
	public void onTouchEvent(MotionEvent event) { }
}