package com.chirag.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import chirag.orderchaos.R;


public class MainActivity extends Activity {

	String letter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);  
	}
	
	public void mode(View view){
        Intent i = new Intent(this, multi_mode.class);
        startActivity(i);
      }
	public void toBoard(View view){
        Intent i = new Intent(this, SetBoard.class);
        i.putExtra("AIPlayer", 1);
        startActivity(i);
      }
}