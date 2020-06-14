//package kr.ac.kpu.game.charg2dang.cookierun.ui.activity;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import kr.ac.kpu.game.charg2dang.cookierun.R;
//
//public class HighscoreActivity extends AppCompatActivity
//{
//	private TextView higisocreTextView;
//	private RecyclerView recyclerView;
//	private RecyclerView.LayoutManager layoutManager;
//	private RecyclerView.Adapter mAdapter;
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//
//
//		recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//		recyclerView.setHasFixedSize(true);
//
//		// use a linear layout manager
//		layoutManager = new LinearLayoutManager(this);
//		recyclerView.setLayoutManager(layoutManager);
//
//		// specify an adapter (see also next example)
//		mAdapter = new MyAdapter(myDataset);
//		recyclerView.setAdapter(mAdapter);
//	}
//
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
////		SharedPreferences perfs = getSharedPreferences("", Context.MODE_PRIVATE);
////		int highscore = perfs.getInt(MainWorld.PREF_KEY_HIGHSCORE, 0);
//		higisocreTextView.setText((String.valueOf(10)));
//	}
//
//	public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//		private String[] mDataset;
//
//		// Provide a reference to the views for each data item
//		// Complex data items may need more than one view per item, and
//		// you provide access to all the views for a data item in a view holder
//		public static class MyViewHolder extends RecyclerView.ViewHolder {
//			// each data item is just a string in this case
//			public TextView textView;
//			public MyViewHolder(TextView v) {
//				super(v);
//				textView = v;
//			}
//		}
//
//		// Provide a suitable constructor (depends on the kind of dataset)
//		public MyAdapter(String[] myDataset) {
//			mDataset = myDataset;
//		}
//
//		// Create new views (invoked by the layout manager)
//		@Override
//		public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//														 int viewType) {
//			// create a new view
//			TextView v = (TextView) LayoutInflater.from(parent.getContext())
//					.inflate(R.layout.my_text_view, parent, false);
//
//
//			MyViewHolder vh = new MyViewHolder(v);
//			return vh;
//		}
//
//		// Replace the contents of a view (invoked by the layout manager)
//		@Override
//		public void onBindViewHolder(MyViewHolder holder, int position) {
//			// - get element from your dataset at this position
//			// - replace the contents of the view with that element
//			holder.textView.setText(mDataset[position]);
//
//		}
//
//		// Return the size of your dataset (invoked by the layout manager)
//		@Override
//		public int getItemCount() {
//			return mDataset.length;
//		}
//	}
//}
