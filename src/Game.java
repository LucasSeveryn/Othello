import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game extends JPanel implements ActionListener{
	public Board gameBoard;
	public Gamemaster gamemaster;
	
	public Game(){
		gameBoard = new Board();
		gamemaster = new Gamemaster(gameBoard);
		gameBoard.iniGamemaster(gamemaster);
		
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		
		c.gridx = 0;
		c.gridy = 0;
		add(gameBoard, c);
	}
	
	public static void main(String[] args) {
		JFrame othello = new JFrame();
		Game game = new Game();
        
		othello.getContentPane().setLayout(new GridBagLayout());
		othello.getContentPane().add(game);
		othello.pack();
		
		othello.setResizable(false);
		othello.setTitle("Othello");
		othello.setSize(800, 800);
		othello.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		othello.setVisible(true);

		game.gameBoard.printBoardState( );	
		Player playerOne = new Player(1);
		Player playerTwo = new Player(2);
		
		while(!game.gamemaster.noMovesLeftAtAll()){
			game.gamemaster.askForMove(playerOne, game.gameBoard);
			game.gameBoard.printBoardState();
			game.gamemaster.askForMove(playerTwo, game.gameBoard);
			game.gameBoard.printBoardState();
		}
		game.gamemaster.printScores(playerOne, playerTwo);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
