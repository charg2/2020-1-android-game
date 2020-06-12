package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.ScoreObject;

public class ScoreManager
{
	static final String TAG = ScoreManager.class.getSimpleName();
	private SceneType 			currentSceneType;
	private ScoreObject 		scoreObject;
	private ScoreObject 		highScoreObject;

	private ScoreManager()
	{
		scoreObject = new ScoreObject(800, 100, R.mipmap.number_64x84);
		highScoreObject = new ScoreObject(800, 100, R.mipmap.number_24x32);
	}

	public ScoreObject getScoreObject()
	{
		return scoreObject;
	}

	public ScoreObject getHighScoreObject()
	{
		return highScoreObject;
	}

	static ScoreManager inctance;

	public static ScoreManager getInstance()
	{
		if(inctance == null)
		{
			inctance = new ScoreManager();
		}
		return inctance;
	}

	public void update(long timeDiffNano)
	{
	}

	public void addScore(int score)
	{
		scoreObject.addScore(score);
	}


	public void collide(long frameTimeNanos)  { }
}
