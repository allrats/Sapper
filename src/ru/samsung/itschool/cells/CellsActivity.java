package ru.samsung.itschool.cells;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CellsActivity extends Activity {
	public static int N = 10;
	public static String player;
	static EditText editText;
	static EditText name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText)findViewById(R.id.editText2);
		name = (EditText)findViewById(R.id.editText1);
	}
	
	public void OnClickWithFlags(View v){
		if (editText.getText().length() != 0){
			if ('1' <= editText.getText().charAt(0) && editText.getText().charAt(0) <= '9'){
				if (editText.getText().length() > 1){
					if ('0' <= editText.getText().charAt(1) && editText.getText().charAt(1) <= '9'){
						N = Integer.parseInt(editText.getText().toString());
						if (N > 50){
							N = 10;
						}
					}
				}
				else{
					N = Integer.parseInt(editText.getText().toString());
					if (N > 50){
						N = 10;
					}
				}
			}
		}
		player = name.getText().toString();
		if (player.equals("")){
			player = "Вася";
		}
		Intent intent = new Intent(this, PlayWithFlags.class);
		intent.putExtra("N", N);
		intent.putExtra("name", player);
		startActivity(intent);
	}
	
	public void OnClickWithoutFlags(View v){
		if (editText.getText().length() != 0){
			if ('1' <= editText.getText().charAt(0) && editText.getText().charAt(0) <= '9'){
				if (editText.getText().length() > 1){
					if ('0' <= editText.getText().charAt(1) && editText.getText().charAt(1) <= '9'){
						N = Integer.parseInt(editText.getText().toString());
						if (N > 50){
							N = 10;
						}
					}
				}
				else{
					N = Integer.parseInt(editText.getText().toString());
					if (N > 50){
						N = 10;
					}
				}
			}
		}
		player = name.getText().toString();
		if (player.equals("")){
			player = "Вася";
		}
		Intent intent = new Intent(this, PlayWithoutFlags.class);
		intent.putExtra("N", N);
		intent.putExtra("name", player);
		startActivity(intent);
	}
	
	public void onClickResultsButton(View v) {
		setTitle("Results");
		Intent intent = new Intent(this, Results.class);
		startActivity(intent);
	}
}


	
