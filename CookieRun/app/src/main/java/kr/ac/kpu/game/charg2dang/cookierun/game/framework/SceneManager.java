package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Stack;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;

public class SceneManager
{
	static final String TAG = SceneManager.class.getSimpleName();
	private SceneType 			currentSceneType = SceneType.main;
	private Scene 				currentScene;
	private Scene[]				scenes = new Scene[SceneType.max.ordinal()];
	// private Stack<Scene> 		sceneStack = new Stack<>();
	private boolean				resetFlag = false;
	private SceneManager()
	{
	}


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
			currentScene = scenes[currentSceneType.ordinal()];
			currentScene.setCurrentSceneType(currentSceneType);
			if( resetFlag == true )
			{
				currentScene.reset();
			}
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

	public void changeScene(SceneType sceneType, boolean neededReset)
	{
		this.currentSceneType = sceneType;
		resetFlag = neededReset;
	}

	public void draw(Canvas canvas)
	{
		currentScene.draw(canvas);
	}

	public void collide(long frameTimeNanos)
	{
		currentScene.collide(frameTimeNanos);
	}

	public void save()
	{

	}
}
