package com.chirag.sudoku;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {
	
	enum player{
		computer,human;
	}
	
	int[][] board = new int[6][6];
	static final int comp_gain = 500;
	static final int inf_pos = 2000000;
	static final int inf_neg = -2000000;
	public int[] Board(int[][] playBoard)
	{
		for(int i=0;i<6;i++)
			for(int j=0;j<6;j++)
			{
//				System.out.println(playBoard[i][j]);
				board[i][j] = playBoard[i][j];
			}

		return myMove();
	}
	
	int[] myMove()
	{
	//	System.out.println("Called from myMove");
		return minmax(1,player.computer,inf_neg,inf_pos);	
	}
	
	
	int [] minmax(int depth,player seed,int alpha,int beta)
	{
		int moveX=-1,moveY=-1,value=-1,score=0;
		List<int[]> possibleMoves = generateMoves();
		if(depth==0 || possibleMoves.isEmpty())
		{
			return new int[] {evaluate(),moveX,moveY,value};
		}
		else
		{
			for(int[] move: possibleMoves)
			{
				System.out.println("move[0]="+move[0]+" move[1]=" + move[1] + " move[2]="+move[2]);
				board[move[0]][move[1]] = move[2];
				if(seed == player.human)
				{
					score = minmax(depth-1,player.computer,alpha,beta)[0];
					if(score<=beta)
					{
						beta = score;
						moveX = move[0];
						moveY = move[1];
						value = move[2];
					}
					
				}
				else
				{
					score = minmax(depth-1,player.human,alpha,beta)[0];
					if(score>=alpha)
					{
						alpha = score;
						moveX = move[0];
						moveY = move[1];
						value = move[2];
					}
				}
				
				board[move[0]][move[1]] = -1;
				if(alpha>beta)
					break;
			}
			return new int[]{(seed==player.computer)?alpha:beta,moveX,moveY,value};
		}	
	}
	
	List<int[]> generateMoves()
	{
		List<int[]> possibleMoves = new ArrayList<int[]>();
	      for (int row = 0; row < 6; ++row) {
	          for (int col = 0; col < 6; ++col) {
	             if (board[row][col]== -1) {
	                possibleMoves.add(new int[] {row, col,1});
	                possibleMoves.add(new int[] {row, col,2});
	             }
	          }
	       }
		return possibleMoves;
		
	}
	
	int evaluate()
	{
//		System.out.println("hi from evaluate");
		int score=0;
		int local_score ;
		int flag=0;
		
		//row
	for(int p=0;p<2;p++)
	{	
		for(int i=0;i<6;i++)
		{
			local_score=1;
			int j=p;
			int pos;
			flag =0;
			while((j<5+p) && (board[i][j]==-1))
				j++;
			pos = j;
			for(;j<5+p;j++)
			{
				if(board[i][pos]==board[i][j])
					local_score = local_score*10;
				else if(board[i][j]==-1);
				else
				{
	//				if(!(Math.abs(pos-j)==5))
						{
							local_score = comp_gain;
							flag=1;
							break;
						}
				}
			}
			
			if(flag==1)
				score = score + local_score;
			else
				score = score - local_score;
		}
	}	
		
		//column
	for(int p=0;p<2;p++)
	{
		for(int j=0;j<6;j++)
		{
			local_score=1;
			int i=p;
			int pos;
			flag=0;
			while( (i<p+5) && (board[i][j]==-1))
				i++;
			pos = i;
			for(;i<5+p;i++)
			{
				if(board[pos][j]==board[i][j])
					local_score = local_score*10;
				else if(board[i][j]==-1);
				else
				{
	//				if(!(Math.abs(pos-i)==5))
					{
						local_score = comp_gain;
		//				System.out.println("Awarding 500 from column " + j);
						flag=1;
						break;
					}
				}
			}
			if(flag==1)
				score = score + local_score;
			else
				score = score - local_score;
	//		System.out.println("Score = "+score + " pos,i="+pos);
		}
	}	
		
		//diagonals
			local_score=1;
			int j=0;
			int i=4;
			int posj,posi;
			flag=0;
			while((j<5) && (i>=0) && (board[i][j]==-1) )
			{
				j++;i--;
			}
			posj = j;
			posi = i;
			for(;j<5 && i>=0;j++,i--)
			{
				if(board[posi][posj]==board[i][j])
					local_score = local_score*10;
				else if(board[i][j]==-1);
				else
				{
					local_score = comp_gain;
			//		System.out.println("Awarding 500 from upper short diagonal ");
					flag=1;
					break;
				}
				
			}
			if(flag==1)
				score = score + local_score;
			else
				score = score - local_score;
		
			i=5;
			local_score=1;
			j=1;
			flag=0;
			
			while((j<6) && (i>0) && (board[i][j]==-1))
			{
				//System.out.println("board[" +i + "][" + j +"]= "+ board[i][j] );
				j++;
				i--;
			}
			posj = j;
			posi = i;
			for(;j<6 && i>0;j++,i--)
			{
				if(board[posi][posj]==board[i][j])
					local_score = local_score*10;
				else if(board[i][j]==-1);
				else
				{
					local_score = comp_gain;
					flag=1;
					break;
				//	System.out.println("Awarding 500 from lower short diagonal ");
				}
				
			}
			if(flag==1)
				score = score + local_score;
			else
				score = score - local_score;
			
		for(int p=0;p<2;p++)
		{	
				local_score=1;
				j=p;
				i=5-p;
				flag=0;
				
				while((j<p+5) &&(i>=((p==0)?1:0)) && (board[i][j]==-1))
				{
					j++;i--;
				}
				posi=i;posj=j;
				for(;j<p+5 && i>=((p==0)?1:0);j++,i--)
				{
					if(board[posi][posj]==board[i][j])
						local_score = local_score*10;
					else if(board[i][j]==-1);
					else
					{
//						if(!((Math.abs(posj-j)==5) && (Math.abs(posi-i)==5)))
						{
							local_score = comp_gain;
							flag=1;
							break;
						}
					}
				}
				if(flag==1)
					score = score + local_score;
				else
					score = score - local_score;
		}	
		// alternate diagonals		
		
				local_score=1;
				 j=0;
				 i=1;
				 flag=0;
				while((j<5) && (i<6) && (board[i][j]==-1))
				{
					j++;i++;
				}
				posj = j;
				posi = i;
				for(;j<5 && i<6;j++,i++)
				{
					if(board[posi][posj]==board[i][j])
						local_score = local_score*10;
					else if(board[i][j]==-1);
					else
					{
						local_score = comp_gain;
						flag=1;
						break;
	//					System.out.println("Awarding 500 from lower short alt diagonal ");
					}
				}
				if(flag==1)
					score = score + local_score;
				else
					score = score - local_score;
				
				local_score=1;
				 j=1;
				 i=0;flag=0;
				while((j<6) && (i<5) && (board[i][j]==-1))
				{
					j++;i++;
				}
				posj = j;
				posi = i;
				for(;j<6 && i<5;j++,i++)
				{
					if(board[posi][posj]==board[i][j])
						local_score = local_score*10;
					else if(board[i][j]==-1);
					else
					{
						local_score = comp_gain;
						flag=1;
						break;
	//					System.out.println("Awarding 500 from upper short alt diagonal ");
					}
				}
				if(flag==1)
					score = score + local_score;
				else
					score = score - local_score;		
				
		for(int p=0;p<2;p++)
		{
				local_score=1;
				j=0+p;
				i=0+p;
				flag=0;
				while((j<5+p) &&(i<5+p) && (board[i][j]==-1))
				{
					j++;i++;
				}
				posi=i;posj=j;
				for(;j<5+p && i<5+p;j++,i++)
				{
					if(board[posi][posj]==board[i][j])
						local_score = local_score*10;
					else if(board[i][j]==-1);
					else
					{
	//					if(!((Math.abs(posj-j)==5) && (Math.abs(posi-i)==5)))
						{
							local_score = comp_gain;
							flag=1;
							break;
						}
					}
				}
				if(flag==1)
					score = score + local_score;
				else
					score = score - local_score;
		}		
//		System.out.print("Score = " + score + " ");			
		return score;
	}
}
