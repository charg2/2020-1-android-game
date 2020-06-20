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
	private ArrayList<HighscoreItem> scores;//  =  new ArrayList<>();// ScoreManger.getInstance().getScores();

	static final int[] rankImgs =
			{
					R.mipmap.cookie_illust_blaze_powder, R.mipmap.cookie_illust_blaze_rod, R.mipmap.cookie_illust_blocks_disc,
					R.mipmap.cookie_illust_book_normal, R.mipmap.cookie_illust_brick, R.mipmap.cookie_illust_carrot_golden,
					R.mipmap.cookie_illust_clay_ball, R.mipmap.cookie_illust_chrip_disc, R.mipmap.cookie_illust_cat_disc,
					R.mipmap.cookie_illust_dye_powder_cyan, R.mipmap.cookie_illust_dye_powder_brown, R.mipmap.cookie_illust_dye_powder_black,
					R.mipmap.cookie_illust_dye_powder_lime, R.mipmap.cookie_illust_dye_powder_green, R.mipmap.cookie_illust_dye_powder_gray,
					R.mipmap.cookie_illust_lead, R.mipmap.cookie_illust_iron_ingot, R.mipmap.cookie_illust_far_disc,
					R.mipmap.cookie_illust_dye_powder_pink, R.mipmap.cookie_illust_dye_powder_silver, R.mipmap.cookie_illust_melon_speckled
			};

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
	}

	@Override
	protected void onResume()
	{
		super.onResume();
//		SharedPreferences perfs = getSharedPreferences("", Context.MODE_PRIVATE);
//		int highscore = perfs.getInt(MainWorld.PREF_KEY_HIGHSCORE, 0);

	}



	@Override
	protected void onStart()  // 액티비티 전환시 sharedPrefer..의 JSON String을 읽어서 items로 만들고 화면에 뿌림.
	{
		super.onStart();

		// 액티비지 전환 후 score manage에서 관리하는 score를 읽어서 ArrayList<>로 만들어 옴.
		scores = Serializer.load(this);

		listView.setAdapter(adapter); // 리스트 뷰 갱신.
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
			TextView score_tv = view.findViewById(R.id.score);
			ImageView imgView = view.findViewById(R.id.scroeRankingImageView);

			imgView.setImageResource(HighscoreActivity.rankImgs[position]);

			HighscoreItem s = scores.get(position);
			DateFormat format = new SimpleDateFormat("YYYY-MM-dd h:mm a");
			format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			String datestr = format.format(s.date);
			tv.setText( "rank :" + (position + 1) + "\ndate : " + datestr );
			score_tv.setText("" + s.score);

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


//	public void addHighscore(String name, int score)
//	{
//		scores.add(new HighscoreItem(name, new Date(), score));
//		Serializer.save(this, scores);
//		listView.setAdapter(adapter);
//	}

}
