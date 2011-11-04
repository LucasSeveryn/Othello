public class Game {

	/**
	 * @param args
	  */
	public static void main(String[] args) {
		Board gameBoard = new Board();
		gameBoard.printBoardState();	
		Player playerOne = new Player(1);
		Player playerTwo = new Player(2);
		Gamemaster judge = new Gamemaster(gameBoard);
		
		while(!judge.noMovesLeft()){
			judge.askForMove(playerOne, gameBoard);
			gameBoard.printBoardState();
			judge.askForMove(playerTwo, gameBoard);
			gameBoard.printBoardState();
		}
		judge.printScores(playerOne, playerTwo);
	}
	
}
