import java.util.*;


public class Gamemaster {
Board board;
int currentColour;
int scoreInThisTurn;


	public Gamemaster(Board board){
		this.board=board;
	}
	
	public void printScores(Player playerOne, Player playerTwo){
		int higher,lower;
		System.out.println("Game has ended.");
		if(playerOne.getScore()>playerTwo.getScore()) {
			System.out.print(playerOne.getName());
			higher=playerOne.getScore();
			lower=playerTwo.getScore();
		}
		else if(playerOne.getScore()>playerTwo.getScore()){
			System.out.println("Draw! No one");
			higher=playerOne.getScore();
			lower=playerTwo.getScore();
		}
		else{
			System.out.print(playerTwo.getName());
			higher=playerTwo.getScore();
			lower=playerOne.getScore();
		}
		System.out.print(" has won the game with the score " + higher + ":" + lower);
	}
	
	public boolean noMovesLeft(){
		for(int i=0;i<board.getHeight();i++){
			for(int j=0;j<board.getWidth();j++)
				for(int dir=0;dir<8;dir++) if (isLegal(j,i,dir)) return false;
		}
		return true;
	}
	
	public boolean noMovesLeftAtAll(){
		boolean bufferA,bufferB;
				
		currentColour=1;
		bufferA = noMovesLeft();
		currentColour=2;
		bufferB = noMovesLeft();
		
		return bufferA&&bufferB;
		
	}
	
	public void askForMove(Player player, Board board){
		this.board=board;
		currentColour=player.getColour();
		scoreInThisTurn=0;
		
		System.out.println("\n ||||||||| Player " + player.getName() + "'s turn! |||||||||\n");
		Scanner keyboard = new Scanner(System.in);
		int x, y;
		if(noMovesLeft()){
			System.out.println("No moves left for you," + player.getName() + " automatic pass...");
		}
		do{
		System.out.println("Where would you like to place chip, " + player.getName()+"?");
		System.out.print("x: "); x = keyboard.nextInt();
		System.out.print("y: "); y = keyboard.nextInt(); System.out.println();
		}while(!move(x,y));
	}
		
	public boolean move(int x, int y){
		for(int dir=0;dir<8;dir++) if (isLegal(x,y,dir)) flip(x,y,dir);
		if(scoreInThisTurn>0){
			board.placeChip(x, y, currentColour);
			System.out.println("You have earned " + scoreInThisTurn + " points this turn.");
			return true;
		}else{
			System.out.println("Illegal move!");
			return false;
		}
	}
	
	
	
	public boolean isLegal(int x,int y, int dir){
		if(board.isOutOfBounds(x, y)) return false;
		if(!board.emptyPlace(x, y)) return false;
		int xModificator=getXModificator(dir),yModificator=getYModificator(dir);
		
		//make one move
		if(board.isOutOfBounds(x+xModificator,y+yModificator)) return false;
		x+=xModificator;
		y+=yModificator;
		if(board.getChip(x, y)==currentColour) return false;

		
		while(!board.isOutOfBounds(x, y)&&!(board.getChip(x, y)==currentColour)&&!board.emptyPlace(x, y)){
			x+=xModificator;
			y+=yModificator;
			if((!board.isOutOfBounds(x, y))&&(board.getChip(x, y)==currentColour)) return true;
		}
		
		return false;		
	}
	

	public void flip(int x,int y, int dir){
		int xModificator=getXModificator(dir),yModificator=getYModificator(dir);
	
		//make one move
		x+=xModificator;
		y+=yModificator;
		
		while(board.getChip(x, y)!=currentColour&&!board.emptyPlace(x, y)){
			board.flipChip(x, y);
			scoreInThisTurn++;
			x+=xModificator;
			y+=yModificator;
		}
	}
	
	public int getXModificator(int dir){
		switch(dir){
		case 0: return 0; 
		case 1: return 1;  
		case 2: return 1;  
		case 3: return 1;  
		case 4: return 0; 
		case 5: return -1;
		case 6: return -1; 
		case 7: return -1; 
		}
		return 0;
	}
	
	public int getYModificator(int dir){
		switch(dir){
		case 0: return -1; 
		case 1: return -1; 
		case 2: return 0;
		case 3: return 1; 
		case 4: return 1;
		case 5: return 1; 
		case 6: return 0; 
		case 7: return -1;
		}
		return 0;
	}
}
