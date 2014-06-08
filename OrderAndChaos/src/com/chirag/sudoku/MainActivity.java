package com.chirag.sudoku;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import chirag.orderchaos.R;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class MainActivity extends Activity {

	String letter;
	public static BluetoothSocket mainSocket;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
	    {
	    	this.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	        ActionBar actionbar = getActionBar();
//	        actionbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Order And Chaos" + "</font>")));
	        Drawable drawable = getResources().getDrawable(getResources()
	                  .getIdentifier("back", "drawable", getPackageName()));
	        actionbar.setBackgroundDrawable(drawable);
	        actionbar.show();
//	        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFFFF")));
	        actionbar.setDisplayShowHomeEnabled(false);
	        actionbar.setDisplayShowTitleEnabled(false);
	        
	    }
	    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    }	
	    
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu); 
        SharedPreferences.Editor editor;
        SharedPreferences stat_initialize = getSharedPreferences("initialize",0);
        SharedPreferences stat_order = getSharedPreferences("order",0);
        SharedPreferences stat_chaos = getSharedPreferences("chaos",0);
        boolean initial_value = stat_initialize.getBoolean("value_exist", false); 
        
        if(!initial_value)
        {
        	editor = stat_order.edit();
        	editor.putInt("orderwins", 0);
        	editor.commit();
        	editor = stat_chaos.edit();
        	editor.putInt("chaoswins", 0);
        	editor.commit();
        	start_tutorial();
        	stat_initialize.edit().putBoolean("value_exist", true).commit();
        	
        }
       
	}
	

	
	public boolean onCreateOptionsMenu(Menu menu) {
	   
		 getMenuInflater().inflate(R.menu.tutorial, menu);

	    return true;
	    
	}
	

	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		start_tutorial();
		return super.onOptionsItemSelected(item);
	}
	
	public void mode(View view){
        Intent i = new Intent(this, MultiMode.class);
        startActivity(i);
      }
	
	public void level(View view){
        Intent i = new Intent(this, Level.class);
        startActivity(i);
      }
	
	public void toStats(View view)
	{
		Intent i = new Intent(this,Statistics.class);
		startActivity(i);
	}
	
	public void start_tutorial()
	{
		Intent i = new Intent(this,Tutorial.class);
		startActivity(i);		
	
	}
}