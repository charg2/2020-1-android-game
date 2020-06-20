package kr.ac.kpu.game.charg2dang.cookierun.game.obj;

import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.ColliderTag;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.obj.BitmapObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.RecyclePool;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.Recyclable;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class Obstacle extends BitmapObject implements  Recyclable
{
    private static final 	String 			TAG 			= Obstacle.class.getSimpleName();
	private static  		float 			halfWidth;
	private static  		float 			halfHegith;
	private 				RectF 			box;
	private static 			SharedBitmap 	bitamp;
	private 				float 			scale			= 2;
	private static final  	float  			velocity 		= Framework.getCommonVelocity();

	public Obstacle(float x, float y, int width, int height)
    {
		super(x, y, width, height, R.mipmap.obstacle_fork);
		this.x = x;
        this.y = y;

		box = new RectF();

		if( bitamp == null)
		{
			bitamp = SharedBitmap.load(R.mipmap.obstacle_fork);

			halfHegith = bitamp.getHeight() / 2;
			halfWidth = bitamp.getWidth() / 2;
		}

		updateForColliderBox();
	}

	@Override
	public void update(long timeDiffNanos)
	{
		this.x += velocity * GameTimer.getInstance().getDeltaSecondsSingle();

		super.update(timeDiffNanos);
		updateForNewColliderBox();
//		if(box.right <= 0)
//////		{
//////			this.state = false;
//////		}
	}

	public void updateForNewColliderBox()
	{
		box.left  	= x - ( halfWidth * scale );
		box.top     = y - ( halfHegith * scale );
		box.right	= x + ( halfWidth * scale );
		box.bottom  = y + ( halfHegith * scale);
	}


	@Override
	public void onCollision(BoxCollidable o1)
	{
		Cookie cookie = (Cookie)o1;

		if ( cookie.isGiantMode() == true )
		{
			this.state = false;
		}
		else
		{
			cookie.decreaseHP(10.f, true);
		}

	}


//	@Override
//    public void draw(Canvas canvas)
//	{
//		canvas.save();
//		canvas.scale(scale, scale, x, y);
//		bitamp.draw(canvas, (x - halfWidth), (y - halfHegith));
//		canvas.restore();
//
//		if( Framework.getInstance().isDebugMode() == true)
//		{
//			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
//		}
//	}


	public static Obstacle get(float x, float y , int width, int height)//, int typeIndex)
	{
		RecyclePool rpool = SceneManager.getInstance().getScene(SceneType.game).getRecyclePool();
		Obstacle item = (Obstacle) rpool.get(Obstacle.class);
		if (item == null)
		{
			item = new Obstacle(x, y , width, height);//, typeIndex);
		}
		else
		{
			item.x = x;
			item.y = y;
			item.width = width;
			item.height = height;
			item.state = true;
			item.updateForColliderBox();
		}

		return item;
	}

	@Override
	public RectF getColliderBox()
	{
		return this.box;
	}

	@Override
	public ColliderTag getTag() {  return ColliderTag.Obstacle;  }
	public void setScale(float scale) { this.scale = scale; }
	@Override
	public void recycle()  { }
}
