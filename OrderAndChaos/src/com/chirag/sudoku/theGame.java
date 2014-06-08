package com.chirag.sudoku;

import chirag.orderchaos.R;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
public class TheGame extends Activity {
	
	public int arr[][] = new int[6][6];
	private int row_count[]= new int[6];
	private int col_count[] = new int[6];
	private int diag_count[] = new int[12];
	private int history[][] = new int[2][2];
	private int history_pointer = 0;
	private int dead_chain_count=0;
	private int dead_chains[] = new int[18];
	TheGame()
	{
		for(int i=0;i<6;i++)
			for(int j=0;j<6;j++)
					arr[i][j]=-1;
		for(int i=0;i<6;i++)
		{
			row_count[i] = 0;
			col_count[i] = 0;
			diag_count[i] = 0;
		}
		diag_count[6]=0;
		history_pointer = 0;
		dead_chain_count=0;
		
		for(int i=0;i<2;i++)
			for(int j=0;j<2;j++)
				history[i][j] = -1;
		
		for(int i=0;i<18;i++)
			dead_chains[i] = 0;
	}
	
	private int check_row(int val,int i,int j)
	{
		//row
		int col;
		for(col=0;col<5;col++)
			if(arr[i][col]!=val)
				break;
		if(col==5)
		{
			return 1;
		}
		
		for(col=1;col<6;col++)
			if(arr[i][col]!=val)
				break;
		if(col==6)
		{
			return 1;
		}
		
		return 0;
	}
	private int check_col(int val,int i,int j)
	{	
		//columns
		int row;
		for(row=0;row<5;row++)
			if(arr[row][j]!=val)
				break;
		
		if(row==5)
		{
//			System.out.println("\nrow==5\n");
			return 1;
		}
		
		for(row=1;row<6;row++)
			if(arr[row][j]!=val)
				break;
		
		if(row==6)
		{
	//		System.out.println("\nrow==6\n");
			return 1;
		}
		
//		System.out.println("Hi from check_col");
		return 0;
	}
	
	private int check_diag(int val,int i,int j)
	{	
		//diagonal
		int col;int row;
		row=i;col=j;
		int sum = i+j;
		if(sum==4 )
		{
			for(row=0,col=4;row<5 && col>=0;row++,col--)
				if(arr[row][col]!=val)
					break;
			if(row==5 && col==-1)
				return 1;
		}
		if(sum==5)
		{
			for(row=0,col=5;row<5 && col>=1;row++,col--)
				if(arr[row][col]!=val)
					break;
			if(row==5 && col==0)
				return 1;
			
			for(row=1,col=4;row<6 && col>=0;row++,col--)
				if(arr[row][col]!=val)
					break;
			if(row==6 && col==-1)
				return 1;
		}
		
		if(sum==6)
		{
			for(row=1,col=5;row<6 && col>=1;row++,col--)
				if(arr[row][col]!=val)
					break;
			if(row==6 && col==0)
				return 1;
		}	
		
		return 0;
	}
	
	private int diagonals(int val,int i,int j)
	{
		int row = i;
		int col = j;
		while(row>0 && col>0)
		{
			row--;
			col--;
		}
		if(!(row==0 && col==0) && !(row==1 && col==0) && !(row==0 && col==1))
			return 0;
		else if((row==1 && col==0))
		{
			for(;row<6 && col<5;row++,col++)
				if(arr[row][col]!=val)
					break;	
			
			if(row==6 && col==5)
				return 1;
		}
		
		else if((row==0 && col==1))
		{
			for(;row<5 && col<6;row++,col++)
				if(arr[row][col]!=val)
					break;		
			
			if(row==5 && col==6)
				return 1;
		}
		else
		{
			for(row=0,col=0;row<5 && col<5;row++,col++)
				if(arr[row][col]!=val)
					break;
			
			if(row==5 && col==5)
				return 1;
			
			for(row=1,col=1;row<6 && col<6;row++,col++)
				if(arr[row][col]!=val)
					break;
			
			if(row==6 && col==6)
				return 1;
		}
		
		return 0;
	}
	
	
	public int setValue(int val,int i,int j)
	{
		moves_history(i,j);
		arr[i][j] = val;
		row_count[i]++;
		col_count[j]++;
		diag_count[i+j]++;

		if(row_count[i]>=5)
		{

			if(check_row(val,i,j)==1)
				{
					return 1;
				}
		}
		if(col_count[j]>=5)
		{
			if(check_col(val,i,j)==1)
				{
					return 1;
				}
		}

		if(diag_count[i+j]>=5)
		{
			if(check_diag(val,i,j)==1)
			{
				return 1;
			}
		}
		if(diagonals(val,i,j)==1)
			return 1;
	    if(check_dead_chains()==1)
	    	return 2;
	    
		return 0;
	}
	
	//dead_chains[] array 0-5 rows, 6-11 columns, 12-14 right diagonals and 15-17 left diagonals
	
	int check_dead_chains()
	{
		int val;
		int dead;
/*		for(int i=0;i<6;i++)
		{
			for(int j=0;j<6;j++)
		
				System.out.print(arr[i][j]+"  ");
			System.out.println();
		}*/
		//row
		for(int i=0;(i<6);i++)
		{
//			System.out.println("dead_chains["+i+"]="+dead_chains[i]);
			dead=0;
			if(row_count[i]>0 && (dead_chains[i]==0))
			{
				val=-1;
				for(int j=0;j<5;j++)
					if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}

				val=-1;
				for(int j=1;j<6;j++)
					if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}
			}
			if(dead==2)
			{
				dead_chains[i] = 1;
				dead_chain_count++;
			}
					
		}
		
		
		//column
		for(int j=0;j<6;j++)
		{
			dead=0;
			if(col_count[j]>0 && dead_chains[j+6]==0)
			{
				val=-1;
				for(int i=0;i<5;i++)
					if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}

				val=-1;
				for(int i=1;i<6;i++)
					if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}
			}
			if(dead==2)
			{
				dead_chains[j+6] = 1;
				dead_chain_count++;
			}
					
		}
		
		//right-diagonals short
		int row=0,col=4;
		do
		{
			int i=row; int j=col;dead=0;
			if(diag_count[i+j]>0 && dead_chains[12+row]==0 )
			{
				val=-1;				
				for(;i<row+5 && j>=0;i++,j--)
				{
					    if(arr[i][j]==-1 || val==arr[i][j]);
						else if(val==-1)
						{val=arr[i][j];}
						else{dead++; break;}		
				}
			}	
			if(dead==1)
			{
				dead_chains[12+row] = 1;
				dead_chain_count++;
			}
			
			row++;
			col++;
		}while((row==1 && col==5));
		
		//right-diagonals middle
		row=0;col=5;
		int i=row; int j=col;dead=0;
		if(diag_count[i+j]>0 && dead_chains[14]==0 )
		{
			val=-1;				
			for(;i<5 && j>=1;i++,j--)
			{
				    if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}		
			}
			
			val=-1;	
			i=row+1; j=col-1;
			for(;i<6 && j>=0 && dead_chains[14]==0 ;i++,j--)
			{
				    if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}		
			}			
		}
		if(dead==2)
		{
			dead_chains[14] = 1;
			dead_chain_count++;
		}
		
		
		//left-diagonals
		 row=0;col=1;
		do
		{
		    i=row;j=col;dead=0;
			if(dead_chains[15+row]==0 )
			{
				val=-1;				
				for(;i<row+5 && j<5+col;i++,j++)
				{
					    if(arr[i][j]==-1 || val==arr[i][j]);
						else if(val==-1)
						{val=arr[i][j];}
						else{dead++; break;}		
				}
			}	
			if(dead==1)
			{
				dead_chains[15+row] = 1;
				dead_chain_count++;
			}
			
			row++;
			col--;
		}while((row==1 && col==0));
		
		//left-diagonal middle
		row=0;col=0;
		 i=row; j=col;dead=0;
		if(dead_chains[17]==0 )
		{
			val=-1;				
			for(;i<5 && j<5;i++,j++)
			{
				    if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}		
			}
			
			val=-1;	
			i=row+1; j=col+1;
			for(;i<6 && j<6 && dead_chains[17]==0 ;i++,j++)
			{
				    if(arr[i][j]==-1 || val==arr[i][j]);
					else if(val==-1)
					{val=arr[i][j];}
					else{dead++; break;}		
			}			
		}
		if(dead==2)
		{
			dead_chains[17] = 1;
			dead_chain_count++;
		}
/*		System.out.println("dead_chain_count: "+dead_chain_count );		
		
		for(i=0;i<18;i++)
			System.out.print(dead_chains[i]+ "   ");
		System.out.println();*/
		if(dead_chain_count==18)
			return 1;
		else
			
		   return 0;
	}
	
	
	void moves_history(int i,int j)
	{
		history[history_pointer][0] = i;
		history[history_pointer][1] = j;
		history_pointer= (history_pointer==0)?1:0;
	}
	
	int[] undo_move(Context context)
	{
		int local_pointer= (history_pointer==0)?1:0;
		int i = history[local_pointer][0];
		int j = history[local_pointer][1];
//		System.out.println(" history[local_pointer][0]="+ history[local_pointer][0]+"   history[local_pointer][1]"+ history[local_pointer][1]+"local_pointer=" + local_pointer);
		if((history_pointer==0) &&(history[history_pointer][0] == -1) && (history[history_pointer][1] == -1))
		{
			CharSequence text = "Cannot Undo";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.TOP|Gravity.LEFT, 50, 50);
			toast.show();
			return new int []{-1,-1};
		}
		arr[i][j] = -1;
		history[local_pointer][0] = -1;
		history[local_pointer][1] = -1;		
		history_pointer = (history_pointer==0)?1:0;;
		
		dead_chain_count=0;
		for(int index=0;index<18;index++)
			dead_chains[index] = 0;
		check_dead_chains();
		return new int []{i,j};
	}
	
	void cleanArray()
	{
		for(int i=0;i<6;i++)
			for(int j=0;j<6;j++)
				arr[i][j] = -1;
		
		for(int i=0;i<6;i++)
		{
			row_count[i] = 0;
			col_count[i] = 0;
			diag_count[i] = 0;
		}
		
		diag_count[6]=0;
		history_pointer = 0;
		dead_chain_count=0;
		
		for(int i=0;i<2;i++)
			for(int j=0;j<2;j++)
				history[i][j] = -1;
		
		for(int i=0;i<18;i++)
			dead_chains[i] = 0;
	}
}
