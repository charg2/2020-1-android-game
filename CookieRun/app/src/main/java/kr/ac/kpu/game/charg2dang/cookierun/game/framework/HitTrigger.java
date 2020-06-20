package kr.ac.kpu.game.charg2dang.cookierun.game.framework;


public class HitTrigger
{
	private float hitTimer;
	private final float hitRecoveryTime;
	private  boolean isHitted;

	public HitTrigger(float recoveryTime)
	{
		hitRecoveryTime = recoveryTime;
		hitTimer = 0.f;
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
			hitTimer += GameTimer.getInstance().getDeltaSecondsSingle();
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
