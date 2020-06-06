package kr.ac.kpu.game.charg2dang.cookierun.util;

import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;

import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;

public class CollisionHelper
{
	private static RectF r2 = new RectF();
	private static RectF r1 = new RectF();
//
//	public static boolean collides(BoxCollidable o1, BoxCollidable o2)
//	{
//		o1.getBox(r1);
//		o2.getBox(r2);
//
//		if(r1.left > r2.right)
//			return false;
//
//		if(r1.right < r2.left)
//			return false;
//
//		if(r1.top > r2.bottom)
//			return false;
//
//		if(r1.bottom < r2.top)
//			return false;
//
//		return true;
//	}

	public static void collides(BoxCollidable o1, BoxCollidable o2)
	{
		RectF r1 = o1.getBox();
		RectF r2 = o2.getBox();

		if(r1 == null || r2 == null)
		{
			int n = 0;
		}

		if(r1.left > r2.right)
			return;

		if(r1.right < r2.left)
			return;

		if(r1.top > r2.bottom)
			return;

		if(r1.bottom < r2.top)
			return;


		o1.onCollision(o2);
		o2.onCollision(o1);
	}
}
