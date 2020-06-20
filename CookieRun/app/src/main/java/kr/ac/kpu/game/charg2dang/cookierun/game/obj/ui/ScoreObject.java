package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;

public class ScoreObject extends GameObject
{
	private final Bitmap bitmap;
	private final int width, height;
	private final float top, right;
	private int scoreValue;
	private int scoreDisplay;
    private ObjectAnimator scoreAndmator;//= ObjectAnimator.ofInt(this, "scoreDisplay");

	private RectF dstRect = new RectF();
	private Rect srcRect = new Rect();

	public void setScoreDisplay(int scoreDisplay)
    {
        this.scoreDisplay = scoreDisplay;
    }

	public ScoreObject( float right, float top, int resId)
	{
		Resources res = Framework.getInstance().getResources();
		bitmap = BitmapFactory.decodeResource(res, resId);
		width = bitmap.getWidth() /10; // 총 10개
		height = bitmap.getHeight();

		this.right = right;
		this.top = top;

		srcRect.top = 0;
		srcRect.bottom = height;

		scoreAndmator = ObjectAnimator.ofInt(this, "scoreDisplay", 0);
	}

	@Override
	public void update(long timeDiffNanos)
	{
	}

	@Override
	public void draw(Canvas canvs)
	{
		dstRect.right = right;
		dstRect.top = top;
		dstRect.left = right - width;
		dstRect.bottom = top + height;

		int value = scoreDisplay;


//		fontPaint.setColor(Color.BLACK);
//		fontPaint.setTextSize(200);
//		canvs.drawText("" + value , dstRect.left,  dstRect.top,  fontPaint);
//		fontPaint.setColor(Color.WHITE);
//		fontPaint.setTextSize(190);
//		canvs.drawText("" + value , dstRect.left,  dstRect.top,  fontPaint);

		if(value == 0)
		{
			srcRect.left = 0;
			srcRect.right = width;
			canvs.drawBitmap(bitmap, srcRect, dstRect, null);
			return;
		}

		while( value > 0)
		{
			int digit = value % 10;
			srcRect.left = digit * width;
			srcRect.right = srcRect.left + width;

			canvs.drawBitmap(bitmap, srcRect, dstRect, null);
			value /= 10;

			dstRect.left -= width;
			dstRect.right -= width;
		}
	}

	public int addScore(int score)
	{
		this.scoreValue += score;
        scoreAndmator.setIntValues(scoreDisplay, scoreValue);
        scoreAndmator.setDuration(500);

        scoreAndmator.start();

        return scoreValue;
	}

	public void reset()
	{
		addScore(-scoreValue);
	}

	public int getScore()
	{
		return scoreValue;
	}

	public void setScore(int score)
	{
		addScore(score - scoreValue);
	}
}
