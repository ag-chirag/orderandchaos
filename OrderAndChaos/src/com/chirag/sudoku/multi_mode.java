package com.chirag.sudoku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import chirag.orderchaos.R;

public class multi_mode extends Activity{

	@SuppressLint("NewApi")
	BluetoothAdapter myBluetoothAdapter;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.multimode);  
	}
	
	public void bluetooth(View view)
	{
		
	      if (!myBluetoothAdapter.isEnabled()) {
	         Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	         startActivityForResult(turnOn,1);
	         Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,Toast.LENGTH_LONG).show();
	
	      }
	      else{  Toast.makeText(getApplicationContext(),"Bluetooth is already on",
	             Toast.LENGTH_LONG).show();
	
	      }

	}
	public void toBoard(View view){
        Intent i = new Intent(this, SetBoard.class);
        startActivity(i);
      }
}
