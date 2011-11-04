import java.util.Scanner;


public class Board {
	private int[][] boardArray;
	
	public Board(){
		initialiseBoard();
		populateArray();
	}
	
	public boolean isOutOfBounds(int x,int y){
		if(x<0||x>=getWidth()||y<0||y>=getHeight()){
			return true;
		}
		return false;
	}
	
	public int getChip(int x,int y){
		return boardArray[y][x];
	}
	
	public void initialiseBoard(){
		int width=0;
		int height=0;
		Scanner keyboard = new Scanner(System.in);
		
		do{
		System.out.println("How wide is the board? ");
		width = keyboard.nextInt();
		}while(width%2!=0);
		
		do{
		System.out.println("How high is the board?");
		height = keyboard.nextInt();
		}while(height%2!=0);
		
		System.out.println("\nI have set up the board. W:" + width + " H:" + height + "\n");
		boardArray = new int[height][width];
	}
	

	
	public void flipChip(int x, int y){
		switch(boardArray[y][x]){
		case 0: System.out.println("empty!"); break;
		case 1: boardArray[y][x]=2; break;
		case 2: boardArray[y][x]=1; break;
		}
	}
	
	public int getHeight(){
		return boardArray.length;
	}
	
	public int getWidth(){
		return boardArray[0].length;
	}
	
	public void placeChip(int x, int y, int colour){
		boardArray[y][x]=colour;
	}
	
	public boolean emptyPlace(int x, int y){
		return boardArray[y][x]==0;
	}
	private void populateArray(){
		for(int h=0;h<getHeight();h++) for(int w=0;w<getWidth();w++) boardArray[h][w]=0;
		boardArray[getHeight()/2][getWidth()/2]=1;
		boardArray[getHeight()/2][(getWidth()/2)-1]=2;
		boardArray[getHeight()/2-1][getWidth()/2]=2;
		boardArray[getHeight()/2-1][(getWidth()/2)-1]=1;
		
	}

	public void printBoardState(){
		int w=getWidth();
		int h=getHeight();
		
		//print upper axis
		System.out.print(" ");
		for(int i=0;i<w;i++){
			System.out.print(" " + i);
		}
		System.out.println();
		
		//print what is inside
		for(int i=0;i<h;i++){ //i is row
			System.out.print(i + " ");
			for(int j=0;j<w;j++){
				switch(boardArray[i][j]){
					case 0: System.out.print("- "); break;
					case 1: System.out.print("o "); break; //white
					case 2: System.out.print("x "); break; //black
				}
			}
			System.out.println();
		}
	}
}
