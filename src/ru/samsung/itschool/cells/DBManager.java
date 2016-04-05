package ru.samsung.itschool.cells;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
	private String DB_NAME = "results.db";

	private SQLiteDatabase db;

	private static DBManager dbManager;

	public static DBManager getInstance(Context context) {
		if (dbManager == null) {
			dbManager = new DBManager(context);
		}
		return dbManager;
	}

	private DBManager(Context context) {
		db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		createTablesIfNeedBe(); 
	}

	void addResult(Result result) {
		db.execSQL("INSERT INTO RESULTS VALUES ('" + result.name +
				"', " + (result.win ? 1 : 0) + ", " + result.N + ", " + 
				(result.withFlags ? 1 : 0) + ");");
	}
	
	ArrayList<ResultSum> getSumWins(){
		ArrayList<ResultSum> data = new ArrayList<ResultSum>();
		Cursor cursor = db.rawQuery("SELECT RESULTS.USERNAME, Count(RESULTS.WIN) AS [wins]"
				+ " FROM RESULTS WHERE ((WIN = 1)) GROUP BY RESULTS.USERNAME;", null);
        /*Cursor cursor = db.rawQuery("SELECT [TABLE].[USERNAME], [TABLE].[wins] FROM" +
		"((SELECT [RESULTS].[USERNAME], Count([RESULTS].[WIN]) AS [wins] FROM " +
		"RESULTS WHERE ((WIN = 1)) GROUP BY [RESULTS].[USERNAME]) AS [RES] RIGHT JOIN " +
		"(SELECT [RESULTS].[USERNAME] FROM RESULTS GROUP BY [RESULTS].[USERNAME]) " +
		"ON ([RES].[USERNAME] = [RESULTS].[USERNAME])) AS [TABLE];"
		, null);*/
		boolean hasMoreData = cursor.moveToFirst();

		while (hasMoreData) {
			String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
			int wins;
			try{
				wins = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("wins")));
			} catch (Exception e){
				wins = 0;
			}
			data.add(new ResultSum(name, wins));
			hasMoreData = cursor.moveToNext();
		}

		return data;
	}

	ArrayList<Result> getAllResults() {

		ArrayList<Result> data = new ArrayList<Result>();
		Cursor cursor = db.rawQuery("SELECT * FROM RESULTS;", null);
		boolean hasMoreData = cursor.moveToFirst();

		while (hasMoreData) {
			String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
			int N = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("N")));
			int win = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("WIN")));
			int withFlags = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("WITH_FLAGS")));
			data.add(new Result(name, win == 1, N, withFlags == 1));
			hasMoreData = cursor.moveToNext();
		}

		return data;
	}

	private void createTablesIfNeedBe() {
		db.execSQL("CREATE TABLE IF NOT EXISTS RESULTS (USERNAME TEXT, WIN INT, N INT,"
				+ " WITH_FLAGS INT);");
	}

}
