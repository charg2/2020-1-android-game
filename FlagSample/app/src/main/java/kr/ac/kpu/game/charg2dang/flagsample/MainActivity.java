package kr.ac.kpu.game.charg2dang.flagsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity
{
	private ListView listView;
	private JSONArray continents;
	//http://scgyong.net/thumbs/
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadContries();
		
		listView = findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}
	

	private void loadContries()
	{
		AssetManager assets = getAssets();
		try
		{
			InputStream is = assets.open("nations.js");
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader streamReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();

			String inputStr;
			while( (inputStr = streamReader.readLine()) != null )
			{
				sb.append(inputStr);
			}

			JSONArray jarr = new JSONArray(sb.toString());
			this.continents = jarr;

			adapter.notifyDataSetChanged();
		} catch (IOException | JSONException e)
		{
			e.printStackTrace();
		}
	}

	private BaseAdapter adapter = new BaseAdapter()
	{
		@Override
		public int getCount()
		{
			try
			{
				JSONObject africa = continents.getJSONObject(0);
				JSONArray jarr = africa.getJSONArray("countries");
				return jarr.length();
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			/// 재활용
			View view = convertView;
			if(view == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				view = inflater.inflate(R.layout.country_item, null);
			}

			String name = "", file = "";
			// part2 : data at position;
			try
			{
				JSONObject africa = continents.getJSONObject(0);
				JSONArray jarr = africa.getJSONArray("countries");
				JSONObject country = jarr.getJSONObject(position);
				name = country.getString("name");
				file = country.getString("file");

			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			// part3 connection
			TextView tv = view.findViewById(R.id.scoreItemTextView);
			ImageView iv = view.findViewById(R.id.imageView);
			tv.setText(name);

			AssetManager assets = getAssets();
			try
			{
				InputStream is = assets.open("flags/"+ file);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				iv.setImageBitmap(bitmap);
//				Drawable drawable = Drawable.createFromStream(is, null);
//				iv.setImageDrawable(drawble);

			} catch (IOException e)
			{
				e.printStackTrace();
			}




			return view;
		}
		@Override
		public Object getItem(int position)
		{
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return 0;
		}


	};

}
