import java.util.*;

/**
 * 
 * Class responsible for looking after game rules
 *
 */
public class Gamemaster {
	private Game game;
	public int currentColour = 1;
	private int nextColour = 2;
	private List[] validMoves = new List[2];

	public Gamemaster( Game game ){
		this.game = game;
		validMoves[0] = new ArrayList();
		validMoves[1] = new ArrayList();
		generateValidMoves( currentColour );
	}
	
	/**
	 * 
	 * Places a chip on a field.
	 *
	 */
	public boolean move( int x, int y ){
		if( validate( x, y ) ) {
			game.getGameBoard().placeChip( x, y, currentColour );
			game.getPlayer( currentColour - 1 ).incrementScore();
			
			/*
			 * Walks through the board, looking for chips to flip
			 */
			for( int i = 0; i < 8; i++ ){
				boolean flip = false;

				int tmpX = x + getXModificator( i );
				int tmpY = y + getYModificator( i );
				while( !game.getGameBoard().isOutOfBounds( tmpX, tmpY ) && game.getGameBoard().getChip( tmpX, tmpY ).getState().getValue() == nextColour ){
					tmpX += getXModificator( i );
					tmpY += getYModificator( i );
					if( !game.getGameBoard().isOutOfBounds( tmpX, tmpY ) && game.getGameBoard().getChip( tmpX, tmpY ).getState().getValue() == currentColour ){
						flip = true;
					}
				}
				if(flip){
					tmpX -= getXModificator( i );
					tmpY -= getYModificator( i );
					while( !game.getGameBoard().isOutOfBounds( tmpX, tmpY ) && tmpX != x || tmpY != y ){
						game.getGameBoard().getChip( tmpX, tmpY ).flip();
						game.getPlayer( currentColour - 1 ).incrementScore();
						game.getPlayer( nextColour - 1 ).decrementScore();
						tmpX -= getXModificator( i );
						tmpY -= getYModificator( i );
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * Generates list of valid moves for a player
	 * 
	 * @param number of the player
	 * @return list of coordinates of valid fields
	 * 
	 */
	public List generateValidMoves( int player ){
		validMoves[player - 1] = new ArrayList();
		
		for( int i = 0; i < game.getGameBoard().getBoardHeight(); i++ )
			for( int j = 0; j < game.getGameBoard().getBoardWidth(); j++ )
				if( game.getGameBoard().getChip( i, j ).isEmpty() ){
					
						if( isLegal( i, j, player ) ){
							validMoves[player - 1].add(new Tuple( i, j ) );
							game.getGameBoard().placeChip( i, j, 10 + player );
						} else {
							game.getGameBoard().placeChip( i, j, 0 ); //clear fields which were highlighted for the previous player
						}
				}
		return validMoves[player - 1];
	}
	
	/**
	 * 
	 * Validates coordinates
	 * 
	 * @param x coordinate of a field
	 * @param y coordinate of the field
	 * 
	 * @return true if placing a chip on the field is legal
	 *
	 */
	public boolean validate( int x, int y ){
		for(Iterator i = validMoves[currentColour - 1].iterator(); i.hasNext(); ) {
			Tuple t = ( Tuple )i.next();
			if( t.a == x && t.b == y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Validates coordinates
	 * 
	 * @param x coordinate of a field
	 * @param y coordinate of the field
	 * @param player's colour
	 * 
	 * @return true if placing a chip on the field is legal
	 *
	 */
	public boolean isLegal( int x, int y, int colour ){
		if( game.getGameBoard().isOutOfBounds( x, y ) ) return false;
		if( !game.getGameBoard().getChip( x, y ).isEmpty() ) return false;
		
		boolean valid = false;
		
		for( int i = 0; i < 8; i++ ){
			int tmpX = x + getXModificator( i );
			int tmpY = y + getYModificator( i );
			while( !game.getGameBoard().isOutOfBounds( tmpX, tmpY ) && !game.getGameBoard().getChip( tmpX,tmpY ).isEmpty() && game.getGameBoard().getChip( tmpX, tmpY ).getState().getValue() != colour){
				tmpX += getXModificator( i );
				tmpY += getYModificator( i );
				if( !game.getGameBoard().isOutOfBounds( tmpX, tmpY ) && game.getGameBoard().getChip( tmpX, tmpY ).getState().getValue() == colour ){
					return true;
				}
			}
		}
		
		return valid;
	}

	
	/**
	 * 
	 * Returns modification of x coordinate for a given direction
	 * 
	 * @param dir direction
	 * 
	 * @return modificator
	 *
	 */
	public int getXModificator( int dir ){
		switch( dir ){
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
	
	/**
	 * 
	 * Returns modification of y coordinate for a given direction
	 * 
	 * @param dir direction
	 * 
	 * @return modificator
	 *
	 */
	public int getYModificator( int dir ){
		switch( dir ){
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
	
	/**
	 * 
	 * 
	 * 
	 */
	private class Tuple{
		public int a;
		public int b;
		public Tuple(){
			a = 0;
			b = 0;
		}
		public Tuple( int a, int b ){
			this.a = a;
			this.b = b;
		}
	}

	public void playerHasMoved( int x, int y ) {
		if( validate( x, y ) ){
			move( x, y );
			int newColour = nextColour;
			nextColour = currentColour;
			generateValidMoves( newColour );
			currentColour = newColour;
		}
		if( validMoves[0].isEmpty() ){
			int newColour = nextColour;
			nextColour = currentColour;
			generateValidMoves( newColour );
			currentColour = newColour;
		}	
		if( validMoves[1].isEmpty() ){
			game.printScores();
		} else{
			game.updateNotifications();
		}
	}
}
