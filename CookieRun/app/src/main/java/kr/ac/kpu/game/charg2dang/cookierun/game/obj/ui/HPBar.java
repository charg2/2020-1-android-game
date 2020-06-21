package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class HPBar extends GameObject
{
	private final 	SharedBitmap 	bg 				= SharedBitmap.load(R.mipmap.ui_hp_bar_bg);
	private final 	SharedBitmap 	main 			= SharedBitmap.load(R.mipmap.ui_hp_bar);
	private 		Cookie 			cookie 			= Cookie.getInstance();
	private 		Rect 			bgSrcRect 		= new Rect();
	private 		Rect 			mainSrcRect 	= new Rect();

	private 		float			mainWitdh		= main.getBitmap().getWidth();
	private 		RectF 			bgDstRect 		= new RectF();
	private 		RectF 			mainDstRect 	= new RectF();
	private	 		float			scale;
	private 		float 			currentHP ;
	private 		float 			maxHP;
	private 		ObjectAnimator 	hpGageAnimator;
	private 		float			goalHP;

	private static HPBar instance;
	public static HPBar getInstance()
	{
		if( instance == null)
		{
			instance = new HPBar();
		}
		return instance;
	}

	private HPBar()

	{
		reset();
	}

	public void setCookie(Cookie cookie)
	{
		this.cookie = cookie;
		this.currentHP = cookie.getCurrentHP();
		this.maxHP = cookie.getMaxHP();


	}

	@Override
	public void update(long timeDiffNanos)
	{
		float tempCurrentHP = cookie.getCurrentHP();

		if( currentHP != tempCurrentHP)
		{
			currentHP = tempCurrentHP;
		}

		mainDstRect.right =  mainDstRect.left  + ( mainWitdh * ( currentHP / maxHP) );
		if(mainDstRect.right < mainDstRect.left)
		{
			mainDstRect.right = mainDstRect.left;
		}

//		mainSrcRect.right = (int)mainDstRect.right - mainSrcRect.left;
//		if(mainSrcRect.right < 0)
//		{
//			mainSrcRect.right = 0;
//		}


	}

	@Override
	public void draw(Canvas canvs)
	{
		canvs.save();
		canvs.scale(scale, scale);

		Bitmap bgBitmap =  bg.getBitmap();
		Bitmap mainBitmap = main.getBitmap();

		canvs.drawBitmap(bgBitmap, bgSrcRect, bgDstRect,null);
		canvs.drawBitmap(mainBitmap, mainSrcRect, mainDstRect,null);

		canvs.restore();
	}


	public void reset()
	{
		scale			= 1.5f;
		currentHP 		= cookie.getCurrentHP();
		maxHP 			= cookie.getMaxHP();

		bgSrcRect.left = 0;
		bgSrcRect.right = bg.getWidth();
		bgSrcRect.top = 0;
		bgSrcRect.bottom = bg.getHeight();

		mainSrcRect.left = 0;
		mainSrcRect.right = bg.getWidth();
		mainSrcRect.top = 0;
		mainSrcRect.bottom = bg.getHeight();

		bgDstRect.left 		= ( UiBridge.metrics.fullSize.x / 4 )  -  ( bgSrcRect.right / 2 );
		bgDstRect.right 	= ( UiBridge.metrics.fullSize.x / 4 )  +  ( bgSrcRect.right / 2 );
		bgDstRect.top 		= ( UiBridge.metrics.fullSize.y / 20 )  -  ( bgSrcRect.bottom / 2 );
		bgDstRect.bottom 	= ( UiBridge.metrics.fullSize.y / 20 )  +  ( bgSrcRect.bottom / 2 );

		mainDstRect.left 	= ( UiBridge.metrics.fullSize.x / 4 )  -  ( bgSrcRect.right / 2 );
		mainDstRect.right 	= ( UiBridge.metrics.fullSize.x / 4 )  +  ( bgSrcRect.right / 2 );
		mainDstRect.top 	= ( UiBridge.metrics.fullSize.y / 20 )  -  ( bgSrcRect.bottom / 2 );
		mainDstRect.bottom 	= ( UiBridge.metrics.fullSize.y / 20 )  +  ( bgSrcRect.bottom / 2 );

//		hpGageAnimator = ObjectAnimator.ofFloat(this, "goalHP", maxHP);
	}

	public void setGoalHP(float goalHP)
	{
//		this.goalHP = goalHP;
	}
}
