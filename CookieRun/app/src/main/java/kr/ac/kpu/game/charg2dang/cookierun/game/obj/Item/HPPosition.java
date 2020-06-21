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
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.Recyclable;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class HPPosition  extends GameObject implements  BoxCollidable, Recyclable
{
	private static final 	String 			TAG 			= HPPosition.class.getSimpleName();
	private static  		float 			halfWidth;
	private static  		float 			halfHegith;
	private 				RectF 			box;
	private static 			SharedBitmap 	bitamp;
	private 				float 			x, y;
	private 				float 			scale			= 1.0f;
	private static final  	float  			velocity = Framework.getCommonVelocity();

	public HPPosition(float x, float y)
	{
		this.x = x;
		this.y = y;

		box = new RectF();

		if( halfHegith == 0)
		{
			bitamp = SharedBitmap.load(R.mipmap.item_hp_potion);

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
		((Cookie)o1).decreaseHP( -10.f , false);  // hp 추가.

		state = false;
	}


	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale , x, y);
		bitamp.draw(canvas, (x - halfWidth), (y - halfHegith));
		canvas.restore();

		drawDebug(canvas, box);
	}


	public static HPPosition get(float x, float y)//, int width, int height, int typeIndex)
	{
		RecyclePool rpool = SceneManager.getInstance().getScene(SceneType.game).getRecyclePool();
		HPPosition item = (HPPosition) rpool.get(HPPosition.class);
		if (item == null)
		{
			item = new HPPosition(x, y);//, width, height, typeIndex);
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
	public RectF getColliderBox()  {  return this.box; }
	@Override
	public ColliderTag getTag() {  return ColliderTag.Item;  }
	public void setScale(float scale) { this.scale = scale; }

	@Override
	public void recycle() { }
}
