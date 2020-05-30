package kr.ac.kpu.game.charg2dang.cookierun.util;


public class HitTrigger
{
	private long hitTimer;
	private final long hitRecoveryTime;
	private  boolean isHitted;

	public HitTrigger(long recoveryTime)
	{
		hitRecoveryTime = recoveryTime;
		hitTimer = 0;
		isHitted = false;
	}

	public void update(long diffNanoTime)
	{
		if(isHitted == false)
		{
			return;
		}
		else
		{
			hitTimer += diffNanoTime;
			if(hitTimer >= hitRecoveryTime)
			{
				isHitted = false;
				hitTimer = 0;
			}
		}
	}

	public boolean canHitted()
	{
		return isHitted == false;
	}

	public void hit()
	{
		isHitted = true;
	}

}
