import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game extends JFrame{
	
	public Game(){
		setTitle("Othello");
		setSize(800, 800);
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * @param args
	  */
	public static void main(String[] args) {
		Game g = new Game();
        g.setVisible(true);
		
		Board gameBoard = new GBoard( g );
		gameBoard.printBoardState( );	
		Player playerOne = new Player(1);
		Player playerTwo = new Player(2);
		Gamemaster judge = new Gamemaster(gameBoard);
		
		while(!judge.noMovesLeftAtAll()){
			judge.askForMove(playerOne, gameBoard);
			gameBoard.printBoardState();
			judge.askForMove(playerTwo, gameBoard);
			gameBoard.printBoardState();
		}
		judge.printScores(playerOne, playerTwo);
	}
	
}
