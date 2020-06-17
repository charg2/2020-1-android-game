package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.ScoreObject;

public class ScoreManager extends GameObject
{
	static final String TAG = ScoreManager.class.getSimpleName();
	private SceneType 			currentSceneType;
	private ScoreObject 		scoreObject;
	private ScoreObject 		highScoreObject;
	private ScoreObject  		currentScoreObject;
	private ScoreObject  		resultScore;
	private ScoreManager()
	{
		scoreObject = new ScoreObject(UiBridge.metrics.fullSize.x / 1.2f, UiBridge.metrics.fullSize.y / 7.0f, R.mipmap.number_cookierun2);
		highScoreObject = new ScoreObject(800, 100, R.mipmap.number_cookierun2);
		resultScore = new ScoreObject(UiBridge.metrics.fullSize.x / 2, UiBridge.metrics.fullSize.y / 2.0f, R.mipmap.number_cookierun1);;
		currentScoreObject = scoreObject;
	}

	public ScoreObject getScoreObject()
	{
		return scoreObject;
	}

	public ScoreObject getResultScore()
	{
		return resultScore;
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


	@Override
	public void update(long timeDiffNano)
	{
		scoreObject.update(timeDiffNano);
		highScoreObject.update(timeDiffNano);
		// 판별식 멀로 그릴지;
	}

	@Override
	public void draw(Canvas canvs)
	{
		scoreObject.draw(canvs);
	}
	public void addScore(int score)
	{
		scoreObject.addScore(score);
		resultScore.addScore(score);
	}

	public void reset()
	{
		scoreObject.reset();
		resultScore.reset();
	}

}
