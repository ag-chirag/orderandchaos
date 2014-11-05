package com.chirag.sudoku;
import chirag.orderchaos.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
public class ResultActivity extends Activity{

	private int winner;
	private int order;
	private String tagline_order = "Order Rules!";
	private String tagline_chaos = "Chaos Rules!";
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.result);   
		
		Button yes = (Button)findViewById(R.id.yes);
		Button no = (Button)findViewById(R.id.no);
		TextView declare = (TextView)findViewById(R.id.declare);
		String tag;
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
        	winner = extras.getInt("winner");
        	order = extras.getInt("order");
        }
        
        if(winner==1)
        {
        	yes.setText("Play Again!");
        	tag= "You Win! ";
        }
        else
        {
        	yes.setText("Try Again!");
        	tag= "You Loose! ";
        }
        
        if(order==1)
        	tag+=tagline_order;
        else
        	tag+=tagline_chaos;
        
        declare.setText(tag);
	}

	public void positive(View v)
	{
		setResult(1);
		Finish();
	}
	
	public void exit(View v)
	{
		setResult(0);
		Finish();
	}
	protected void onStop() {
	    super.onStop();
	}
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	}
	
	protected void Finish()
	{
		super.finish();
	}
}
