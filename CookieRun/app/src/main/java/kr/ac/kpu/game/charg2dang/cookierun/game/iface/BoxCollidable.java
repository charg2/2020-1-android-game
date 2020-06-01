package kr.ac.kpu.game.charg2dang.cookierun.game.iface;

import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.ColliderTag;

public interface BoxCollidable
{
	public RectF getBox();

	void onCollision(BoxCollidable o1);
	public ColliderTag getTag();
}
