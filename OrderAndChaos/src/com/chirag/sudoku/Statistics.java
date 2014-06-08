package com.chirag.sudoku;

import chirag.orderchaos.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Statistics extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.stat);  
        
        int orderwins = getSharedPreferences("order",0).getInt("orderwins",0);
        int chaoswins = getSharedPreferences("chaos",0).getInt("chaoswins",0);
        ((TextView)findViewById(R.id.orderwins)).setText("Order "+orderwins);
        ((TextView)findViewById(R.id.chaoswins)).setText("Chaos "+chaoswins);
	}

}
