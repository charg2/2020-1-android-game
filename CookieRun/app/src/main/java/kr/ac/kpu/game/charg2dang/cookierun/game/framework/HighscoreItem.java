package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class HighscoreItem implements Comparable<HighscoreItem>
{
	public String name;
	public Date date;
	public int score;

	public HighscoreItem(String name, Date date, int score)
	{
		this.name = name;
		this.date = date;
		this.score = score;
	}


	public HighscoreItem(JSONObject s) throws JSONException
	{
		name = s.getString("name");
		long dateValue = s.getLong("date");
		date = new Date(dateValue);
		score = s.getInt("score");
	}



	public String toJsonString()
	{
		return "{\"name\":\"" + name + "\", \"date\":" + date.getTime() + ",\"score\":" + score + "}";
	}

	@Override
	public int compareTo(HighscoreItem o)
	{
		if(this.score > o.score)
		{
			return -1;
		}
		else if(this.score == o.score)
		{
			return 0;
		}
		else
		{
			return 1;
		}

	}
}
