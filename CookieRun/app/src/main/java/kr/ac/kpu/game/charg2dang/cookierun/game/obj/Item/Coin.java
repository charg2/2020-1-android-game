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
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;


public class Coin extends  GameObject implements  BoxCollidable, Recyclable
{
	private static final int RUN_FRAME_COUNT  = 4;
	private static final int FRMAE_PER_SECOND = 10;

	private static final FrameAnimationBitmap fabs = new FrameAnimationBitmap( R.mipmap.item_big_coin, FRMAE_PER_SECOND, RUN_FRAME_COUNT);

	private static final  float  velocity = Framework.getCommonVelocity();
	private static float halfHegith;
	private static float halfWidth;
	private float x, y;
	RectF box;
	private static final String TAG = Coin.class.getSimpleName();

	public Coin(float x, float y)
	{
		this.x = x;
		this.y = y;

		box = new RectF();

		if( halfHegith == 0)
		{
			halfHegith = fabs.getHeight() / 2;
			halfWidth = fabs.getWidth() / 2;
		}

		updateForColliderBox();
	}


	public void updateForColliderBox()
	{
		box.left  	= x - halfWidth;
		box.top     = y - halfHegith;
		box.right	= x + halfWidth;
		box.bottom  = y + halfHegith;
	}

	public static Coin get(float x, float y)//, int width, int height, int typeIndex)
	{
		RecyclePool rpool = SceneManager.getInstance().getScene(SceneType.game).getRecyclePool();
		Coin item = (Coin) rpool.get(Coin.class);
		if (item == null)
		{
			item = new Coin(x, y);//, width, height, typeIndex);
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
	public RectF getColliderBox()
	{
		return this.box;
	}

	@Override
	public void onCollision(BoxCollidable o1)
	{
		ScoreManager.getInstance().addScore(20);
		state = false;
	}

	@Override
	public ColliderTag getTag()
	{
		return ColliderTag.Coin;
	}

	@Override
	public void update(long timeDiffNanos)
	{
		if(box.right == 0)
		{
			this.state = false;
		}

		this.x += velocity * GameTimer.getInstance().getCurrentDeltaSecondsSngle();
//		String str = "x : " + x + ", : "  +  velocity * ( timeDiffNanos / 10_000_000.0d ) ;
//		Log.d("Coin", str);
		updateForColliderBox();


	}


	@Override
	public void draw(Canvas canvs)
	{
		fabs.draw(canvs, x, y);

		if( Framework.getInstance().isDebugMode() == true)
		{
			canvs.drawRect(box, Framework.getInstance().getDebugPaint());
		}
	}

	@Override
	public boolean getState()
	{
		return state;
	}

	@Override
	public void recycle()
	{
//
//		MainWorld mw = MainWorld.get();
//		mw.getRecyclePool().get(
//
//		)
	}
}
