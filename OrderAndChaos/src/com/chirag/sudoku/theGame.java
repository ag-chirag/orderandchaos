package com.chirag.sudoku;

public class theGame {
	
	public int arr[][] = new int[6][6];
	private int row_count[]= new int[6];
	private int col_count[] = new int[6];
	private int diag_count[] = new int[12];
	theGame()
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
//		System.out.println("row = "+row + " col = "+col);
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
		arr[i][j] = val;
		row_count[i]++;
		col_count[j]++;
		diag_count[i+j]++;
//		System.out.println("i="+i+" j="+j + " val= "+ val);
		if(row_count[i]>=5)
		{
//			System.out.println("row_count["+ i +"]" + "=" + row_count[i] + "\n");
//			System.out.println("hi from rows\n");
			if(check_row(val,i,j)==1)
				{
//					System.out.println("return from row\n");
					return 1;
				}
		}
		if(col_count[j]>=5)
		{
//			System.out.println("col_count["+j+"]"+"="+col_count[j]);
//			System.out.println("hi from cols\n");
			if(check_col(val,i,j)==1)
				{
//					System.out.println("return from col\n");
					return 1;
				}
		}

		if(diag_count[i+j]>=5)
		{
//			System.out.println("hi from diag\n");
			if(check_diag(val,i,j)==1)
			{
//				System.out.println("return from diag\n");
				return 1;
			}
		}
//		System.out.println("hi from diagonals\n");
		if(diagonals(val,i,j)==1)
			return 1;
		
		return 0;
	}
}
