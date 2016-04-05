package ru.samsung.itschool.cells;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Results extends Activity {

	DBManager manager;
	TextView resultstext;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = DBManager.getInstance(this);
		setContentView(R.layout.activity_results);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.result_menu, menu);
	    return true;
	}
	
	public void onClickAllResults(){
		setContentView(R.layout.results_all);
		lv = (ListView)findViewById(R.id.listView);
		ArrayList<Result> resultslist = manager.getAllResults();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;
		for (Result i : resultslist){
			map = new HashMap<String, Object>();
			map.put("name", i.name);
			if (i.withFlags){
				map.put("withFlags", R.drawable.yes);
			} else{
				map.put("withFlags", R.drawable.no);
			}
			if (i.win){
				map.put("win", R.drawable.yes);
			} else{
				map.put("win", R.drawable.no);
			}
			map.put("side", i.N + "");
			data.add(map);
		}
		String[] from = {"name", "withFlags", "win", "side"};
		int[] to = {R.id.textView1, R.id.ImageView1, R.id.ImageView2, R.id.textView2};
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_all, from, to);
		lv.setAdapter(adapter);
		lv = null;
	}
	
	public void onClickSumWins()
	{
		setContentView(R.layout.results_sum);
		lv = (ListView)findViewById(R.id.listViewSum);
		ArrayList<ResultSum> resultslist = manager.getSumWins();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;
		for (ResultSum i : resultslist){
			map = new HashMap<String, Object>();
			map.put("name", i.name);
			map.put("wins", i.wins);
			data.add(map);
		}
		String[] from = {"name", "wins"};
		int[] to = {R.id.name, R.id.score};
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_sum, from, to);
		lv.setAdapter(adapter);
		lv = null;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
		{
	    case R.id.allres:
	        onClickAllResults();
	        return true;
	    case R.id.sumwins:
	    	onClickSumWins();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}


}
