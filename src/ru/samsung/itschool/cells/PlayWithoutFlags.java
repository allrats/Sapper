package ru.samsung.itschool.cells;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class PlayWithoutFlags extends ParentActivity implements OnClickListener, OnLongClickListener{

	@Override
	public boolean onLongClick(View v) {
		Button tappedCell = (Button) v;
		int tappedX = getX(tappedCell);
		int tappedY = getY(tappedCell);
		if (cell2[tappedY][tappedX] == 0 && !lose){
			if (firstClick){
				if (cellsvalues[tappedY][tappedX].equals("Mine")){
					cell[tappedY][tappedX].setBackgroundColor(Color.BLUE);
					cellscolors[tappedY][tappedX] = Color.BLUE;
					cell2[tappedY][tappedX] = 1;
					taps++;
					if (wasWin()){
						IfWin();
					}
				}
				else{
					cell[tappedY][tappedX].setBackgroundColor(Color.RED);
					cellscolors[tappedY][tappedX] = Color.RED;
					cell2[tappedY][tappedX] = 1;
					IfLose();
				}
			}
			else{
				if (cellsvalues[tappedY][tappedX].equals("Mine")){
					int m = -1;
					for (int i = max(0, tappedY - 1); i <= min(cell.length - 1, tappedY + 1); i++){
						for (int j = max(0, tappedX - 1); j <= min(cell.length - 1, tappedX + 1); j++){
							if (!cellsvalues[i][j].equals("Mine")){
								int a = Integer.parseInt((String) cellsvalues[i][j]);
								cell[i][j].setText((a - 1) + "");
								cellsvalues[i][j] = (a - 1) + "";
							}
							else{
								m++;
							}
						}
					}
					cell[tappedY][tappedX].setText(m + "");
					cellsvalues[tappedY][tappedX] = m + "";
					mines--;
				}
				cell[tappedY][tappedX].setBackgroundColor(Color.RED);
				cellscolors[tappedY][tappedX] = Color.RED;
				taps++;
				cell2[tappedY][tappedX] = 1;
				lose = true;
				IfLose();
		    }
		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		Button tappedCell = (Button) v;
		int tappedX = getX(tappedCell);
		int tappedY = getY(tappedCell);
		if (lose || win){
			TryAgain();
		}
		else{
			if (!firstClick){
				if (cellsvalues[tappedY][tappedX].equals("Mine")){
					int m = -1;
					for (int i = max(0, tappedY - 1); i <= min(cell.length - 1, tappedY + 1); i++){
						for (int j = max(0, tappedX - 1); j <= min(cell.length - 1, tappedX + 1); j++){
							if (!cellsvalues[i][j].equals("Mine")){
								int a = Integer.parseInt((String) cellsvalues[i][j]);
								cell[i][j].setText((a - 1) + "");
								cellsvalues[i][j] = (a - 1) + "";
							}
							else{
								m++;
							}
						}
					}
					cell[tappedY][tappedX].setText(m + "");
					cellsvalues[tappedY][tappedX] = m + "";
					mines--;
				}
				cell[tappedY][tappedX].setBackgroundColor(Color.GREEN);
				cellscolors[tappedY][tappedX] = Color.GREEN;
				taps++;
				cell2[tappedY][tappedX] = 1;
				if (wasWin()){
					IfWin();
				}
				if (cellsvalues[tappedY][tappedX].equals(0 + "")){
					OnTapCellWithZero(tappedX, tappedY);
				}
				firstClick = true;
				if (wasWin()){
					IfWin();
				}
		    }
			else{
				if (cell2[tappedY][tappedX] == 0 && !lose){
					if (cellsvalues[tappedY][tappedX].equals("Mine")){
						cell[tappedY][tappedX].setBackgroundColor(Color.RED);
						cellscolors[tappedY][tappedX] = Color.RED;
						cell2[tappedY][tappedX] = 1;
						IfLose();
					}
					else{
						cell[tappedY][tappedX].setBackgroundColor(Color.GREEN);
						cellscolors[tappedY][tappedX] = Color.GREEN;
						taps += 1;
						cell2[tappedY][tappedX] = 1;
						if (wasWin()){
							IfWin();
						}
						if (cellsvalues[tappedY][tappedX].equals(0 + "")){
							OnTapCellWithZero(tappedX, tappedY);
						}
					}
			    }
			}	
		}
	}
	
	@Override
	protected boolean wasWin()
	{
		if (taps == HEIGHT * WIDTH)
		{
			return true;
		}
		return false;
	}
	
	@Override
	protected void TryAgain(){
		manager.addResult(new Result(name, win, WIDTH, false));
		finish();
	}
}
