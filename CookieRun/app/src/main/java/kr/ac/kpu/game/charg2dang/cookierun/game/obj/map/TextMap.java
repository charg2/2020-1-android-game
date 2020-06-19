package kr.ac.kpu.game.charg2dang.cookierun.game.obj.map;


import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Point;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Item.Coin;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Item.CoinSilver;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Item.GiantPosition;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Item.Jelly;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Obstacle;


public class TextMap extends GameObject
{
	private final int blockSize;
	private final int blockWidth;
	private final int blockHeight;

	private Scene gameScene;
	private final int createAtX;
	private int currentX;
	private int mapIndex;
	private int columns, rows;
	ArrayList<String> lines;
	private double timeElapsed;
	private float spwanTimer = 0.1f;
	private float elapsedTime ;

	public TextMap(String assetFilename, Scene gameScene)
	{
		this.gameScene = gameScene;

		AssetManager assets = UiBridge.getActivity().getAssets();

		try
		{
			InputStream is = assets.open(assetFilename);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);

			String header = reader.readLine();
			String[] comps = header.split(" ");
			columns = Integer.parseInt(comps[0]);
			rows = Integer.parseInt(comps[1]);
			lines = new ArrayList<>();
			while (true)
			{
				String line = reader.readLine();
				if (line == null)
				{
					break;
				}

				lines.add(line);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		blockHeight = UiBridge.metrics.fullSize.y / rows;
		blockWidth  = UiBridge.metrics.fullSize.x / columns;

		blockSize = UiBridge.metrics.fullSize.y / rows;
		createAtX = UiBridge.metrics.fullSize.x + 2 * blockSize;
		mapIndex = 0;

		currentX = 0;
//		while (currentX <= createAtX)
//		{
//			createColumn();
//		}
	}

	private void createColumn()
	{
		float y = blockSize / 2;

		for (int row = 0; row < rows; row++)
		{
			char ch = getAt(mapIndex, row);
			createObject(ch, currentX, y);
			y += blockSize;
		}

		currentX += blockSize;
		mapIndex++;
	}
//
	private void createObject(char ch, float x, float y)
	{
		ArrayList<GameObject> itemLayer = gameScene.getLayer(LayerType.item);
		ArrayList<GameObject> obstacleLayer = gameScene.getLayer(LayerType.obstacle);
		switch (ch)
		{
			case '1':
			{
				GameObject item = Coin.get(x, y);
				itemLayer.add(item);
				break;
			}

			case '2':
			{
				Jelly item = Jelly.get(x, y);
				item.setScale(5);
				itemLayer.add(item);
				break;
			}

			case '3':
			{
				GameObject item = CoinSilver.get(x, y);
				itemLayer.add(item);

				break;
			}

			case '4':
			{
				GiantPosition item =  GiantPosition.get(x, y);
				itemLayer.add(item);
				break;
			}

			case 'x':
			{
				Obstacle obs = Obstacle.get(x, y, blockWidth * 10, blockHeight * 2);
				obstacleLayer.add(obs);
				break;
			}

		}
	}
//
	private char getAt(int index, int row)
	{
		try
		{
			int line = index / columns * rows + row;
			return lines.get(line).charAt(index % columns);
		}
		catch (Exception e)
		{
			return 0;
		}
	}


	@Override
	public void update(long timeDiffNanos)
	{
		float delta = GameTimer.getInstance().getCurrentDeltaSecondsSngle();

		currentX += delta * Framework.getInstance().getCommonVelocity();

		if (currentX < createAtX) {
			createColumn();
		}

//		update2(1);
	}
	@Override
	public void draw(Canvas canvs)
	{
		return;
	}
	@Override
	public boolean getState()
	{
		return true;
	}
}
