import java.util.*;


public class Gamemaster {
	public Game game;
	int currentColour = 1;
	int nextColour = 2;
	int scoreInThisTurn;
	public List[] validMoves = new List[2];
	public Player[] players = new Player[2];
	

	public Gamemaster(Game game, Player playerOne, Player playerTwo ){
		this.game = game;
		validMoves[0] = new ArrayList();
		validMoves[1] = new ArrayList();
		players[0] = playerOne;
		players[1] = playerTwo;
		generateValidMoves( currentColour );
		//generateValidMoves(2);
	}
		
	public boolean move(int x, int y){
		System.out.println(x + " " + y);
		if( validate( x, y ) ) {
			game.gameBoard.getChip(x, y).setValue( currentColour );
			players[currentColour - 1].incrementScore();
			for( int i = 0; i < 8; i++ ){
				boolean flip = false;
				System.out.println("Dir:" + i);
				int tmpX = x + getXModificator(i);
				int tmpY = y + getYModificator(i);
				while(!game.gameBoard.isOutOfBounds(tmpX, tmpY) && game.gameBoard.getChip(tmpX, tmpY).getValue() == nextColour){
					tmpX += getXModificator(i);
					tmpY += getYModificator(i);
					if( !game.gameBoard.isOutOfBounds(tmpX, tmpY) && game.gameBoard.getChip(tmpX, tmpY).getValue() == currentColour ){
						flip = true;
					}
				}
				if(flip){
					tmpX -= getXModificator(i);
					tmpY -= getYModificator(i);
					while(!game.gameBoard.isOutOfBounds(tmpX, tmpY) && tmpX != x || tmpY != y){
						System.out.println("get and flip: " + tmpX + " " + tmpY);
						game.gameBoard.getChip(tmpX, tmpY).flip();
						players[currentColour - 1].incrementScore();
						players[nextColour - 1].decrementScore();
						tmpX -= getXModificator(i);
						tmpY -= getYModificator(i);
					}
				}
			}
		}
		return true;
	}
	
	public List<Tuple> generateValidMoves( int player ){
		System.out.println("Generate");
		this.validMoves[player - 1] = new ArrayList();
		
		for( int i = 0; i < game.gameBoard.getBoardHeight(); i++ )
			for( int j = 0; j < game.gameBoard.getBoardWidth(); j++ )
				if( game.gameBoard.getChip(i, j).isEmpty() ){
					
						if( isLegal( i, j, player ) ){
							this.validMoves[player - 1].add(new Tuple(i, j));
							game.gameBoard.getChip(i, j).setValue(10 + player);
						} else {
							game.gameBoard.getChip(i, j).setValue(0); //clear fields which were highlighted for the previous player
						}
				}
		return this.validMoves[player - 1];
	}
	
	public boolean validate( int x, int y ){
		for(Iterator i = validMoves[currentColour - 1].iterator(); i.hasNext(); ) {
			Tuple t = (Tuple)i.next();
			if( t.a == x && t.b == y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLegal(int x, int y, int colour){
		if( game.gameBoard.isOutOfBounds(x, y) ) return false;
		if( !game.gameBoard.getChip(x, y).isEmpty() ) return false;
		
		boolean valid = false;
		
		for( int i = 0; i < 8; i++ ){
			int tmpX = x + getXModificator(i);
			int tmpY = y + getYModificator(i);
			while(!game.gameBoard.isOutOfBounds(tmpX, tmpY) && !game.gameBoard.getChip(tmpX,tmpY).isEmpty() && game.gameBoard.getChip(tmpX, tmpY).getValue() != colour){
				tmpX += getXModificator(i);
				tmpY += getYModificator(i);
				if( !game.gameBoard.isOutOfBounds(tmpX, tmpY) && game.gameBoard.getChip(tmpX, tmpY).getValue() == colour ){
					return true;
				}
			}
		}
		
		return valid;
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
	
	private class Tuple{
		public int a;
		public int b;
		public Tuple(){
			a = 0;
			b = 0;
		}
		public Tuple(int a, int b){
			this.a = a;
			this.b = b;
		}
	}

	public void playerHasMoved(int x, int y) {
		if( validate(x, y) ){
			move(x, y);
			int newColour = nextColour;
			nextColour = currentColour;
			generateValidMoves( currentColour );
			generateValidMoves( newColour );
			currentColour = newColour;
		}
		if( validMoves[0].isEmpty() && validMoves[1].isEmpty() ){
			System.out.println("finished");
			game.printScores();
		} else{
			game.updateNotifications();
		}
	}
}
