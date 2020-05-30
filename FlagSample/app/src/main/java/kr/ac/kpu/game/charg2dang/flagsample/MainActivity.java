package kr.ac.kpu.game.charg2dang.flagsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
	private ListView listView;
//	private JSONArray continents;
	private JSONObject json;
	private ImageView imageView;
	private HashMap<String, Bitmap> imageCache = new HashMap<>();


	//http://scgyong.net/thumbs/
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startJsonDownloadThread();
//		loadContries();
		
		imageView = findViewById(R.id.imageView);

//		startImageDownloadThread();


		listView = findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}

	private void startJsonDownloadThread()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				final JSONObject json = loadJosnFrowNetwork();
				Log.d("zz","network1");

				listView.post(new Runnable()
				{
					@Override
					public void run()
					{
						MainActivity.this.json = json;
						Log.d("zz","network2");

						adapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}

	private  JSONObject loadJosnFrowNetwork()
	{
		String strRrl = "http://scgyong.net/thumbs/index.php?fast=1";
//		String strRrl = "http://scgyong.net/thumbs/";
		try
		{
			URL url = new URL(strRrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");

			BufferedReader streamReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();

			String inputStr;
			while((inputStr = streamReader.readLine()) != null)
			{
				sb.append(inputStr);
			}

			JSONObject jobj = new JSONObject(sb.toString());
			return jobj;
		}
		catch (MalformedURLException e) // URL 형태가 아닌경우.
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}



	private void startImageDownloadThread(final String strRul, final ImageView imageView, final int position)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				final Bitmap bitmap = loadBitmapFromNetwork(strRul);

				imageView.post(new Runnable()
				{
					@Override
					public void run()
					{
						imageCache.put(strRul, bitmap);

						int first = listView.getFirstVisiblePosition();
						int last = listView.getLastVisiblePosition();

						if(position < first || position > last)
						{
							return;
						}

						imageView.setImageBitmap(bitmap);
					}
				});
			}
		}).start();
	}

	private Bitmap loadBitmapFromNetwork(String strRrl)
	{
//		String strRrl = "http://scgyong.net/thumbs/slow.php/204_192131.jpg";
		try
		{
			URL url = new URL(strRrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			return bitmap;
		}
		catch (MalformedURLException e) // URL 형태가 아닌경우.
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
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
//			this.continents = jarr;

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
			if(json == null) return 0;

			try
			{
				JSONArray albums = json.getJSONArray("albums");
				return albums.length();

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

			String artistName = "", albumTitle = "";
			// part2 : data at position;
			try
			{
				JSONArray albums = json.getJSONArray("albums");
				JSONObject album = albums.getJSONObject(position);
				artistName = album.getString("artistName");
				albumTitle = album.getString("albumTitle");

				String imageUrl = album.getString("image");
				ImageView iv = view.findViewById(R.id.imageView);
				Bitmap bitmap = imageCache.get(imageUrl);
				if(bitmap != null)
				{
					iv.setImageBitmap(bitmap);
				}
				else
				{
					iv.setImageResource(R.mipmap.note);
					startImageDownloadThread(imageUrl, iv, position);
				}

			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			// part3 connection
			TextView abtv = view.findViewById(R.id.albumTitleTextView);
			TextView antv = view.findViewById(R.id.artistNameTextView);

			abtv.setText(albumTitle);
			antv.setText(artistName);

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
