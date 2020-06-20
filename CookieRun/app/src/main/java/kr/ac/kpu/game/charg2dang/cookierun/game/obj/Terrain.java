package kr.ac.kpu.game.charg2dang.cookierun.game.obj;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.ColliderTag;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;

public class Terrain extends GameObject implements  BoxCollidable
{
    private static final String TAG = Terrain.class.getSimpleName();
	private static int halfWidthSize;
	private static int halfHeightSize;
	private static SharedBitmap bitamp ;

	private int speed = -1500;
	private float scrollX;
	private Rect srcRect = new Rect();
	private RectF dstRect = new RectF();

    private float halfWidth, halfHeight;
    private float x, y, scale;

    private RectF box;
    private boolean state = true;

    public Terrain(float x, float y)
    {
    	if(bitamp == null)
		{
        	bitamp = SharedBitmap.load(R.mipmap.terrain_ground);
	        halfWidthSize = bitamp.getWidth() / 2;
			halfHeightSize = bitamp.getHeight() / 2;
		}

		scale = 3.0f;
    	box = new RectF();

        this.x = x;
        this.y = y;

        this.halfWidth = bitamp.getWidth() / 2;
        this.halfHeight = bitamp.getHeight() / 2;

		srcRect.set(0, 0, bitamp.getWidth(), bitamp.getHeight());

		updateForColliderBox();
    }


	public void updateForColliderBox()
	{
		box.left  	= x - ( halfWidth * scale );
		box.top     = y - ( halfHeight * scale );
		box.right	= x + ( halfWidth * scale );
		box.bottom  = y + ( halfHeight * scale);
	}

	@Override
	public void update(long timeDiffNanos)
	{
		if (speed == 0)
			return;

		float amount = speed * GameTimer.getInstance().getDeltaSecondsSingle();

		scrollX += amount;
	}

	// 충돌 박스 크기만큼만 보여줌.
	@Override
    public void draw(Canvas canvas)
    {
		int left = 0;
//		int top = (int) (UiBridge.metrics.size.y / 1.1 );
		int top = (int)box.top;
		int right = UiBridge.metrics.fullSize.x;// + UiBridge.getNavigationBarHeight();;
//		int bottom = UiBridge.metrics.fullSize.y;
		int bottom = (int)box.bottom;

		int pageSize = bitamp.getWidth() * (bottom - top) / bitamp.getHeight();

		canvas.save();
		canvas.clipRect(left, top, right, bottom);

		float curr = scrollX % pageSize;
		if (curr > 0) curr -= pageSize;
		curr += left;
		while (curr < right)
		{
			dstRect.set(curr, top, curr + pageSize, bottom);
			curr += pageSize;
			canvas.drawBitmap(bitamp.getBitmap(), srcRect, dstRect, null);
		}
		canvas.restore();


		drawDebug(canvas, box);
	}

	public float getTop()
	{
		return box.top;
	}


	@Override
	public boolean getState()
	{
		return state;
	}

	@Override
	public RectF getColliderBox()
	{
		return box;
	}

	@Override
	public void onCollision(BoxCollidable o1)
	{
		// 아무것도 ㅋ
	}

	@Override
	public ColliderTag getTag()
	{
		return ColliderTag.Terrain;
	}
}
