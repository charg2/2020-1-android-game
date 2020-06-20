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
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.res.sound.SoundEffects;

public class CoinSilver extends GameObject implements  BoxCollidable//, Recyclable
{
	private static final int RUN_FRAME_COUNT  = 4;
	private static final int FRMAE_PER_SECOND = 10;
	private static final FrameAnimationBitmap fab = new FrameAnimationBitmap( R.mipmap.item_coin_silver, FRMAE_PER_SECOND, RUN_FRAME_COUNT);

	private static final  float  velocity = Framework.getCommonVelocity();
	private static float halfHegith;
	private static float halfWidth;
	private float 		x, y;
	private 				float 			scale = 3.0f;
	RectF box;
	private static final String TAG = CoinSilver.class.getSimpleName();

	public CoinSilver(float x, float y)
	{
		this.x = x;
		this.y = y;

		box = new RectF();

		if( halfHegith == 0)
		{
			halfHegith = fab.getHeight() / 2;
			halfWidth = fab.getWidth() / 2;

		}

		updateForColliderBox();
	}


	private void updateForColliderBox()
	{
		box.left  	= x - ( halfWidth * scale );
		box.top     = y - ( halfHegith * scale );
		box.right	= x + ( halfWidth * scale );
		box.bottom  = y + ( halfHegith * scale);
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
		SoundEffects.get().play(R.raw.se_coin);
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

		this.x += velocity * GameTimer.getInstance().getDeltaSecondsSingle();
//		String str = "x : " + x + ", : "  +  velocity * ( timeDiffNanos / 10_000_000.0d ) ;
//		Log.d("Coin", str);
		updateForColliderBox();
	}


	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale , x, y);
		fab.draw(canvas, x, y);
		canvas.restore();

		if( Framework.getInstance().isDebugMode() == true)
		{
			canvas.drawRect(box, Framework.getInstance().getDebugPaint());
		}
	}

	public static CoinSilver get(float x, float y)//, int width, int height, int typeIndex)
	{
		RecyclePool rpool = SceneManager.getInstance().getScene(SceneType.game).getRecyclePool();
		CoinSilver item = (CoinSilver) rpool.get(CoinSilver.class);
		if (item == null)
		{
			item = new CoinSilver(x, y);//, width, height, typeIndex);
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


//
//	@Override
//	public void recycle()
//	{
////		MainWorld mw = MainWorld.get();
////		mw.getRecyclePool().get();
//	}
}
