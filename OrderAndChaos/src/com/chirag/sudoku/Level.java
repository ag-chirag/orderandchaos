package com.chirag.sudoku;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import chirag.orderchaos.R;


public class Level extends Activity {

	public enum level
	{
		easy(1),medium(2),extreme(3);
		int value;
		level(int p)
		{
			value = p;
		}
		public int getValue()
		{
			return value;
		}
	}
	
	level level;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.level); 
	}
	
	public void toEasy(View view)
	{
        Intent i = new Intent(this, SetBoard.class);
        i.putExtra("AIPlayer", 1);
        i.putExtra("level", 1);
        i.putExtra("Bluetooth", -1);
        i.putExtra("yourTurn",-1);
        startActivity(i);
    }
	
	public void toMedium(View view)
	{
        Intent i = new Intent(this, SetBoard.class);
        i.putExtra("AIPlayer", 1);
        i.putExtra("Bluetooth", -1);
        i.putExtra("level",2);
        i.putExtra("yourTurn",-1);
        startActivity(i);
    }
	
	public void toExtreme(View view)
	{
        Intent i = new Intent(this, SetBoard.class);
        i.putExtra("AIPlayer", 1);
        i.putExtra("Bluetooth", -1);
        i.putExtra("level", 3);
        i.putExtra("yourTurn",-1);
        startActivity(i);
    }
}	
	