package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;

public class SceneManager
{
	static final String TAG = SceneManager.class.getSimpleName();
	private SceneType 			currentSceneType;
	private Scene 				currentScene;
	private Scene[]				scenes = new Scene[SceneType.max.ordinal()];

	private SceneManager()
	{ }


	static SceneManager inctance;

	public void addScene(SceneType sceneType, Scene scene )
	{
		scenes[sceneType.ordinal()] = scene;
	}


	public static SceneManager getInstance()
	{
		if(inctance == null)
		{
			inctance = new SceneManager();
		}
		return inctance;
	}

	public void update(long timeDiffNano)
	{
		if(currentSceneType != currentScene.getCurrentSceneType())
		{
//			currentScene = scenes.get(currentSceneType.ordinal());
			currentScene = scenes[currentSceneType.ordinal()];
			currentScene.setCurrentSceneType(currentSceneType);
		}

		currentScene.update(timeDiffNano);
	}

	public Scene getCurrentScene()
	{
		return 	currentScene;
	}

	public Scene getScene(SceneType sceneType)
	{
		return 	scenes[sceneType.ordinal()];
	}

	public void changeScene(SceneType sceneType)
	{
		this.currentSceneType = sceneType;
	}

	public void draw(Canvas canvas)
	{
		currentScene.draw(canvas);
	}

	public void collide(long frameTimeNanos)
	{
		currentScene.collide(frameTimeNanos);
	}
}
