package ru.samsung.itschool.cells;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class PlayWithFlags extends ParentActivity implements OnClickListener, OnLongClickListener{
	
	@Override
	public boolean onLongClick(View v) {
		Button tappedCell = (Button) v;
		int tappedX = getX(tappedCell);
		int tappedY = getY(tappedCell);
		if (!lose){
			if (cell2[tappedY][tappedX] == 0){
				cell[tappedY][tappedX].setBackgroundColor(Color.BLUE);
				cell[tappedY][tappedX].setTextColor(Color.BLUE);
				cellscolors[tappedY][tappedX] = Color.BLUE;
				textcolors[tappedY][tappedX] = Color.BLUE;
				cell2[tappedY][tappedX] = 2;
				flags++;
			}
			else if (cell2[tappedY][tappedX] == 2){
				cell[tappedY][tappedX].setBackgroundColor(Color.BLACK);
				cell[tappedY][tappedX].setTextColor(Color.BLACK);
				cellscolors[tappedY][tappedX] = Color.BLACK;
				textcolors[tappedY][tappedX] = Color.BLACK;
				cell2[tappedY][tappedX] = 0;
				flags--;
			}
			if (wasWin()){
				IfWin();
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
			if (!firstClick && cell2[tappedY][tappedX] == 0){
				if (cellsvalues[tappedY][tappedX].equals("Mine")){
					int m = -1;
					for (int i = max(0, tappedY - 1); i <= min(cell.length - 1, tappedY + 1); i++){
						for (int j = max(0, tappedX - 1); j <= min(cell.length - 1, tappedX + 1); j++){
							if (!cellsvalues[i][j].equals("Mine")){
								int a = Integer.parseInt((String) cell[i][j].getText());
								cell[i][j].setText((a - 1) + "");
								cellsvalues[i][j] = ((a - 1) + "");
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
				cell2[tappedY][tappedX] = 1;
				if (cellsvalues[tappedY][tappedX].equals(0 + "")){
					OnTapCellWithZero(tappedX, tappedY);
				}
				firstClick = true;
				taps++;
			    if (wasWin()){
					IfWin();
				}
		    }
			else{
				if (cell2[tappedY][tappedX] == 0){
					if (cellsvalues[tappedY][tappedX].equals("Mine")){
						cell[tappedY][tappedX].setBackgroundColor(Color.RED);
						cellscolors[tappedY][tappedX] = Color.RED;
						cell2[tappedY][tappedX] = 1;
						IfLose();
					}
					else{
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
					}
			    }
			}	
		}
	}
	
	@Override
	protected boolean wasWin()
	{
		if (taps + flags == HEIGHT * WIDTH && flags == mines)
		{
			return true;
		}
		return false;
	}
	
	@Override
	protected void TryAgain(){
		manager.addResult(new Result(name, win, WIDTH, true));
		finish();
	}
}
