package kr.ac.kpu.game.charg2dang.cookierun.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.HighscoreItem;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Serializer;

public class HighscoreActivity extends AppCompatActivity
{
	private static final String TAG = HighscoreActivity.class.getSimpleName();
	private ListView listView;
	private ArrayList<HighscoreItem> scores  = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activeity_highscore);

		Framework.setHightscoreActiviey(this);

		scores = Serializer.load(this);

		listView = findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		doFullScreen();

		Log.d(TAG, "high high");
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
//		scores.add(new HighscoreItem("Babo", new Date(), 340));
	}

	@Override
	protected void onResume()
	{
		super.onResume();
//		SharedPreferences perfs = getSharedPreferences("", Context.MODE_PRIVATE);
//		int highscore = perfs.getInt(MainWorld.PREF_KEY_HIGHSCORE, 0);

	}


	private void doFullScreen()
	{
		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_IMMERSIVE|
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
						View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
						View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
						View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
						View.SYSTEM_UI_FLAG_FULLSCREEN);
	}

	public void onBtnBack(View view)
	{
		Intent intent = new Intent(this, MainActivity.class);

		startActivity(intent);
	}

	BaseAdapter adapter = new BaseAdapter()
	{
		@Override
		public int getCount()
		{
			return scores.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view = convertView;
			if( view == null )
			{
				LayoutInflater inflater = getLayoutInflater();
				view = inflater.inflate(R.layout.score_item, null);
			}



			TextView tv = view.findViewById(R.id.scoreItemTextView);
			ImageView imgView = view.findViewById(R.id.scroeRankingImageView);

			switch (position)
			{
//				case 0:
//					imgView.setImageResource(R.mipmap.gold);
//					break;
//				case 1:
//					imgView.setImageResource(R.mipmap.silver);
//					break;
//				case 2:
//					imgView.setImageResource(R.mipmap.copper);
//					break;
				default:
					imgView.setImageResource(R.mipmap.ic_launcher_round);
					break;
			}


			HighscoreItem s = scores.get(position);
			DateFormat format = new SimpleDateFormat("YYYY-MM-dd h:mm a");
			format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			String datestr = format.format(s.date);
			tv.setText( "name :" +s.name + "\ndate : " + datestr + "\nscore :" + s.score );
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

		@Override
		public boolean hasStableIds()
		{
			return false;
		}



		@Override
		public boolean areAllItemsEnabled()
		{
			return false;
		}

		@Override
		public boolean isEnabled(int position)
		{
			return false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer)
		{

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer)
		{

		}


		@Override
		public int getItemViewType(int position)
		{
			return 0;
		}

		@Override
		public int getViewTypeCount()
		{
			return 100;
		}

		@Override
		public boolean isEmpty()
		{
			return false;
		}
	};


	public void addHighscore(String name, int score)
	{
		scores.add(new HighscoreItem(name, new Date(), score));
		Serializer.save(this, scores);
		listView.setAdapter(adapter);
	}
}
