package kr.ac.kpu.game.charg2dang.cookierun.game.iface;

import android.graphics.Canvas;

public interface GameObject
{
	public void update(long timeDiffNanos);
    public void draw(Canvas canvs);

	public boolean getState();
}
