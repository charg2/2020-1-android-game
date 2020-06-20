package kr.ac.kpu.game.charg2dang.cookierun.game.obj.Item;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.ColliderTag;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.RecyclePool;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.Recyclable;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.SoundEffects;

public class Jelly extends GameObject implements BoxCollidable, Recyclable
{
	private static final 	String 			TAG 			= Jelly.class.getSimpleName();
	private static  		float 			halfWidth;
	private static  		float 			halfHegith;
	private 				RectF 			box;
	private static 			SharedBitmap 	bitamp;
	private 				float 			x, y;
	private 				float 			scale;
	private static final  	float  			velocity = Framework.getCommonVelocity();

	public Jelly(float x, float y)
	{
		this.x = x;
		this.y = y;

		box = new RectF();

		if( halfHegith == 0)
		{
			bitamp = SharedBitmap.load(R.mipmap.item_jelly);

			halfHegith = bitamp.getHeight() / 2;
			halfWidth = bitamp.getWidth() / 2;
		}

		updateForColliderBox();
	}

	@Override
	public void update(long timeDiffNanos)
	{
		this.x += velocity * GameTimer.getInstance().getDeltaSecondsSingle();

		updateForColliderBox();

		if(box.right <= 0)
		{
			this.state = false;
		}
	}

	private void updateForColliderBox()
	{
		box.left  	= x - ( halfWidth * scale );
		box.top     = y - ( halfHegith * scale );
		box.right	= x + ( halfWidth * scale );
		box.bottom  = y + ( halfHegith * scale);
	}

	@Override
	public void onCollision(BoxCollidable o1)
	{
		ScoreManager.getInstance().addScore(7);

		SoundEffects.get().play(R.raw.se_jelly);

		state = false;
	}


	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale , x, y);
		bitamp.draw(canvas, (x - halfWidth), (y - halfHegith));
		canvas.restore();

		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
		}
	}


	@Override
	public RectF getColliderBox()  {  return this.box; }
	@Override
	public ColliderTag getTag() {  return ColliderTag.Item;  }
	public void setScale(float scale) { this.scale = scale; }


	public static Jelly get(float x, float y)//, int width, int height, int typeIndex)
	{
		RecyclePool rpool = SceneManager.getInstance().getScene(SceneType.game).getRecyclePool();
		Jelly item = (Jelly) rpool.get(Jelly.class);
		if (item == null)
		{
			item = new Jelly(x, y);//, width, height, typeIndex);
		}
		else
			{
			item.x = x;
			item.y = y;
			item.state = true;
			item.updateForColliderBox();
		}


		return item;
	}

	@Override
	public void recycle()
	{
	}
}
