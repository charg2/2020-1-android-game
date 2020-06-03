package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;

public class SceneManger
{
	static final String TAG = SceneManger.class.getSimpleName();
	private SceneType 			currentSceneType;
	private Scene 				currentScene;
	private ArrayList<Scene> 	scenes;

	private SceneManger(){}
	static SceneManger inctance;
	public SceneManger getInctance()
	{
		if(inctance == null)
		{
			inctance = new SceneManger();
		}

		return inctance;
	}

	public void update(long timeDiffNano)
	{
		if(currentSceneType != currentScene.getCurrentSceneType())
		{
			currentScene = scenes.get(currentSceneType.ordinal());
			currentScene.setCurrentSceneType(currentSceneType);
		}

		currentScene.update(timeDiffNano);
	}

	public void changeScene(SceneType sceneType)
	{

	}


	public void render(Canvas canvas)
	{
		currentScene.draw(canvas);
	}


}
