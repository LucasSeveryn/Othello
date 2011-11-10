import java.util.*;


public class Gamemaster {
	public Board board;
	int currentColour = 1;
	int scoreInThisTurn;
	public List[] validMoves = new List[2];
	

	public Gamemaster( Board board ){
		this.board = board;
		validMoves[0] = new ArrayList();
		validMoves[1] = new ArrayList();
		generateValidMoves(currentColour);
		//generateValidMoves(2);
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
			System.out.print( playerTwo.getName() );
			higher = playerTwo.getScore();
			lower = playerOne.getScore();
		}
		System.out.print(" has won the game with the score " + higher + ":" + lower);
	}
	
	public boolean noMovesLeft(){
		for(int i = 0;i < board.getBoardHeight(); i++){
			for(int j = 0; j < board.getBoardWidth(); j++ )
				for(int dir = 0; dir < 8; dir++ ) if ( isLegal(j, i, dir, currentColour) ) return false;
		}
		return true;
	}
	
	public boolean noMovesLeftAtAll(){
		boolean bufferA, bufferB;
				
		currentColour = 1;
		bufferA = noMovesLeft();
		currentColour = 2;
		bufferB = noMovesLeft();
		
		return bufferA && bufferB;
		
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
		if( validate( x, y ) ) {
			board.getChip(x, y).setValue( board.gamemaster.currentColour );
			for( int i = 0; i < 8; i++ ){
				int tmpX = x + getXModificator(i);
				int tmpY = y + getYModificator(i);
				while( isLegal(tmpX, tmpY, currentColour) ){
					board.getChip(tmpX, tmpY).flip();
					tmpX += getXModificator(i);
					tmpY += getYModificator(i);
				}
			}
		}
		return true;
	}
	
	public List<Tuple> generateValidMoves( int player ){
		System.out.println("Generate");
		this.validMoves[player - 1] = new ArrayList();
		
		for( int i = 0; i < board.getBoardHeight(); i++ )
			for( int j = 0; j < board.getBoardWidth(); j++ )
				if( board.getChip(i, j).isEmpty() ){
						if( isLegal( i, j, player ) ){
							this.validMoves[player - 1].add(new Tuple(i, j));
							board.getChip(i, j).setValue(10 + player);
							System.out.println( i + " " + j + " " + player);
						}
				}
		for(Iterator i = validMoves[0].iterator(); i.hasNext(); ) {
			Tuple t = (Tuple)i.next();
			System.out.println( t.a + " " + t.b );
		}
		return this.validMoves[player - 1];
	}
	
	public boolean validate( int x, int y ){
		System.out.println(x + " " + y + " " + currentColour);
		for(Iterator i = validMoves[0].iterator(); i.hasNext(); ) {
			Tuple t = (Tuple)i.next();
			if( t.a == x && t.b == y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLegal(int x, int y, int colour){
		if( board.isOutOfBounds(x, y) ) return false;
		if( !board.getChip(x, y).isEmpty() ) return false;
		
		boolean valid = false;
		
		for( int i = 0; i < 8; i++ ){
			int tmpX = x + getXModificator(i);
			int tmpY = y + getYModificator(i);
			while(!board.isOutOfBounds(tmpX, tmpY) && !board.getChip(tmpX,tmpY).isEmpty() && board.getChip(tmpX, tmpY).getValue() != colour){
				tmpX += getXModificator(i);
				tmpY += getYModificator(i);
				if( board.getChip(tmpX, tmpY).getValue() == colour ){
					return true;
				}
			}
		}
		
		return valid;
	}
	
	public boolean isLegal(int x, int y, int dir, int colour){
		if( board.isOutOfBounds(x, y) ) return false;
		if( !board.emptyPlace(x, y) ) return false;
		int xModificator = getXModificator(dir), yModificator = getYModificator(dir);
		
		//make one move
		if( board.isOutOfBounds( x + xModificator, y + yModificator ) ) return false;
		x += xModificator;
		y += yModificator;
		if( board.getChip( x, y ).getValue() == colour ) return false;

		
		while( !board.isOutOfBounds( x, y ) && !( board.getChip( x, y ).getValue() == colour ) && !board.emptyPlace( x, y ) ){
			x += xModificator;
			y += yModificator;
			if((!board.isOutOfBounds(x, y)) && (board.getChip(x, y).getValue() == colour)) return true;
		}
		
		return false;		
	}
	

	public void flip(int x,int y, int dir){
		int xModificator=getXModificator(dir),yModificator=getYModificator(dir);
	
		//make one move
		x+=xModificator;
		y+=yModificator;
		
		while(board.getChip(x, y).getValue()!=currentColour&&!board.emptyPlace(x, y)){
			board.getChip(x, y).flip();
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
		if(validate(x, y)){
			move(x, y);
			int newColour = ( currentColour == 1 ) ? 1 : 2;
			generateValidMoves( newColour );
			currentColour = newColour;
		}
		
	}
}
