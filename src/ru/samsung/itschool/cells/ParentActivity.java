 package ru.samsung.itschool.cells;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.GridLayout;

public class ParentActivity extends Activity implements OnClickListener,
		OnLongClickListener {

	protected static int WIDTH = 10;
	protected static int HEIGHT = 10;

	protected Button[][] cell;
	protected static int[][] cell2;
	protected static String name;
	protected static String[][] cellsvalues;
	protected static int cellscolors[][];
	protected static int textcolors[][];
	protected static Boolean lose = false;
	protected static Boolean win = false;
	protected static boolean firstClick = false;
	protected static int taps = 0;
	protected static int flags = 0;
	protected static int mines = 0;
	protected static DBManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cells);
		manager = DBManager.getInstance(this);
		if (savedInstanceState == null) {
			WIDTH = getIntent().getIntExtra("N", 10);
			HEIGHT = WIDTH;
			name = getIntent().getStringExtra("name");
			makeCells();
			setTitle("");
			lose = false;
			win = false;
			firstClick = false;
			taps = 0;
			mines = 0;
			flags = 0;
			cell2 = new int[WIDTH][HEIGHT];
			cellsvalues = new String[WIDTH][HEIGHT];
			cellscolors = new int[WIDTH][HEIGHT];
			textcolors = new int[WIDTH][HEIGHT];
			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < HEIGHT; j++) {
					cellscolors[i][j] = Color.BLACK;
					textcolors[i][j] = Color.BLACK;
				}
			}
			generate();
		}
		else{
			WIDTH = savedInstanceState.getInt("WIDTH", 10);
			HEIGHT = savedInstanceState.getInt("HEIGHT", 10);
			lose = savedInstanceState.getBoolean("lose", false);
			win = savedInstanceState.getBoolean("win", false);
			firstClick = savedInstanceState.getBoolean("firstClick", false);
			taps = savedInstanceState.getInt("taps", 0);
			flags = savedInstanceState.getInt("flags", 0);
			mines = savedInstanceState.getInt("mines", 0);
			name = savedInstanceState.getString("name");
			cell2 = new int[WIDTH][HEIGHT];
			cellsvalues = new String[WIDTH][HEIGHT];
			cellscolors = new int[WIDTH][HEIGHT];
			for (int i = 0; i < WIDTH; i++)
			{
				cell2[i] = savedInstanceState.getIntArray("cell2" + i);
				cellsvalues[i] = savedInstanceState.getStringArray("cellsvalues" + i);
				cellscolors[i] = savedInstanceState.getIntArray("cellscolors" + i);
				textcolors[i] = savedInstanceState.getIntArray("textcolors" + i);
			}
			makeCells();
		}
		setTexts();

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("WIDTH", WIDTH);
		outState.putInt("HEIGHT", HEIGHT);
		outState.putBoolean("lose", lose);
		outState.putBoolean("win", win);
		outState.putBoolean("firstClick", firstClick);
		outState.putInt("taps", taps);
		outState.putInt("flags", flags);
		outState.putInt("mines", mines);
		outState.putString("name", name);
		for (int i = 0; i < WIDTH; i++)
		{
			outState.putIntArray("cell2" + i, cell2[i]);
			outState.putStringArray("cellsvalues" + i, cellsvalues[i]);
			outState.putIntArray("cellscolors" + i, cellscolors[i]);
			outState.putIntArray("textcolors" + i, textcolors[i]);
		}
	}
	
	protected void generate() {

		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++) {
				cell[i][j].setBackgroundColor(Color.BLACK);
				int q = (int) (Math.random() * 10);
				if (q >= 8){
					cellsvalues[i][j] = "Mine";
					mines++;
				}
			}
		}
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++) {
				int n = 0;
				if (cellsvalues[i][j] == null){
					for (int k = max(0, i - 1); k < min(cell.length, i + 2); k++){
						for (int L = max(0, j - 1); L < min(cell.length, j + 2); L++){
							if (cellsvalues[k][L] != null && cellsvalues[k][L].equals("Mine")){
								n++;
							}
						}
					}
					cellsvalues[i][j] = n + "";
				}
			}
		}
	}
	
	protected void setTexts()
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				cell[i][j].setText(cellsvalues[i][j]);
				cell[i][j].setBackgroundColor(cellscolors[i][j]);
				cell[i][j].setTextColor(textcolors[i][j]);
			}
		}
	}

	protected int min(int length, int i) {
		if (i < length){
			return i;
		}
		else{
			return length;
		}
	}

	protected int max(int i, int j) {
		if (i > j){
			return i;
		}
		else{
			return j;
		}
	}
	
	protected int getX(View v)
	{
		return Integer.parseInt(((String)v.getTag()).split(",")[1]); 
	}
	
	protected int getY(View v)
	{
		return Integer.parseInt(((String)v.getTag()).split(",")[0]);
	}

	protected void makeCells() {
		cell = new Button[HEIGHT][WIDTH];
		GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
		cellsLayout.removeAllViews();
		cellsLayout.setColumnCount(HEIGHT);
		for (int i = 0; i < HEIGHT; i++)
			for (int j = 0; j < WIDTH; j++) {
				LayoutInflater inflater = (LayoutInflater) getApplicationContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				cell[i][j] = (Button) inflater.inflate(R.layout.cell, cellsLayout, false);
				cell[i][j].setOnClickListener(this);
				cell[i][j].setOnLongClickListener(this);
				cell[i][j].setTag(i+","+j);
				cellsLayout.addView(cell[i][j]);
			}
	}
	
	protected void IfLose(){
		lose = true;
		setTitle("You lose. Tap to try again.");
		for (int i= 0; i < cell.length; i++){
			for (int j = 0; j < cell.length; j++){
				if (cell2[i][j] == 0){
					cell[i][j].setBackgroundColor(Color.WHITE);
					cell[i][j].setTextColor(Color.BLACK);
					cellscolors[i][j] = Color.WHITE;
				}
				if (cell2[i][j] == 2 && cell[i][j].getText().equals("Mine")){
					cell[i][j].setBackgroundColor(Color.BLUE);
					cell[i][j].setTextColor(Color.BLACK);
					cellscolors[i][j] = Color.BLUE;
				}
				if (cell2[i][j] == 2 && !cell[i][j].getText().equals("Mine")){
					cell[i][j].setBackgroundColor(Color.MAGENTA);
					cell[i][j].setTextColor(Color.BLACK);
					cellscolors[i][j] = Color.MAGENTA;
				}
				cell2[i][j] = 0;
				textcolors[i][j] = Color.BLACK;
			}
		}
	}
	protected void OnTapCellWithZero(int tappedX, int tappedY){
		for (int i = max(0, tappedY - 1); i < min(cell.length, tappedY + 2); i++){
			for (int j = max(0, tappedX - 1); j < min(cell.length, tappedX + 2); j++){
				if (cell2[i][j] != 1){
					cell[i][j].setBackgroundColor(Color.GREEN);
					cellscolors[i][j] = Color.GREEN;
					cell[i][j].setTextColor(Color.BLACK);
					textcolors[i][j] = Color.BLACK;
					taps++;
					cell2[i][j] = 1;
					if (cellsvalues[i][j].equals(0 + "")){
						OnTapCellWithZero(j, i);
					}
				}
			}
		}
		if (wasWin()){
			IfWin();
		}
	}
	
	protected boolean wasWin()
	{
		return false;
	}
	
	protected void IfWin(){
		win = true;
		setTitle("Congratulations! You win.");
		for (int i = 0; i < cell.length; i++){
			for (int j = 0; j < cell.length; j++){
				cell[i][j].setText("");
				cellsvalues[i][j] = "";
				cell2[i][j] = 0;
				int a = (int) (Math.random() * 6);
				switch(a){
					case 0:
						cell[i][j].setBackgroundColor(Color.BLUE);
						cellscolors[i][j] = Color.BLUE;
						break;
					case 1:
						cell[i][j].setBackgroundColor(Color.GREEN);
						cellscolors[i][j] = Color.GREEN;
						break;
					case 2:
						cell[i][j].setBackgroundColor(Color.CYAN);
						cellscolors[i][j] = Color.CYAN;
						break;
					case 3:
						cell[i][j].setBackgroundColor(Color.RED);
						cellscolors[i][j] = Color.RED;
						break;
					case 4:
						cell[i][j].setBackgroundColor(Color.YELLOW);
						cellscolors[i][j] = Color.YELLOW;
						break;
					case 5:
						cell[i][j].setBackgroundColor(Color.MAGENTA);
						cellscolors[i][j] = Color.MAGENTA;
						break;
				}
			}
		}
	}
	
	protected void TryAgain(){
		manager.addResult(new Result(name, win, WIDTH, true));
		finish();
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}