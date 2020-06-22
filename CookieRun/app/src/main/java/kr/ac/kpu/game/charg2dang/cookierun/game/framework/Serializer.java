package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Serializer
{
	public static final String PREFS_NAME = "Highscore940925";
	public static final String PREFS_KEY = "scores940925";
	private static final String TAG = Serializer.class.getSimpleName() ;

	public static void save(Context context, ArrayList<HighscoreItem> items)
	{
		SharedPreferences perfs = context.getSharedPreferences( PREFS_NAME , Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = perfs.edit();

		Collections.sort(items);

		String jsonString = convertJson(items);
		editor.putString(PREFS_KEY, jsonString);
		editor.commit();
	}

	private static String convertJson(ArrayList<HighscoreItem> items)
	{
		int limit = 30;

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		String comma = "";

		for( HighscoreItem item : items)
		{
			sb.append(comma);
			sb.append(item.toJsonString());
			comma = ",";

			limit -= 1;
			if(limit == 0)
			{
				break;
			}
		}


		sb.append("]");
		return sb.toString();
	}

	public static ArrayList<HighscoreItem> load(Context context)
	{
		ArrayList<HighscoreItem> items = new ArrayList<>();
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String jsonString = prefs.getString(PREFS_KEY, "[]");

		try
		{
			JSONArray jsonArray = new JSONArray(jsonString);
			int count = jsonArray.length();

			for (int i = 0; i < count; i++)
			{
				JSONObject s = jsonArray.getJSONObject(i);

				HighscoreItem item = new HighscoreItem(s);
				items.add(item);
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		};



		return items;
	}

}

