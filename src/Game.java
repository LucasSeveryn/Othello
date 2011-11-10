import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game extends JPanel implements ActionListener{
	public Board gameBoard;
	public Gamemaster gamemaster;
	private JLabel gameInfo;
	private JLabel gameMsgs;
	
	public Game(Player playerOne, Player playerTwo){
		gameBoard = new Board();
		gamemaster = new Gamemaster(this, playerOne, playerTwo);
		gameBoard.iniGamemaster(gamemaster);
		
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		
		gameInfo = new JLabel();
		updateGameInfo();
		 
		c.gridx = 0;
		c.gridy = 1;
		add(gameInfo, c);
		
		gameMsgs = new JLabel();
		updateGameMsgs();
		
		c.gridx = 0;
		c.gridy = 2;
		add(gameMsgs, c);

		c.gridx = 0;
		c.gridy = 0;
		add(gameBoard, c);
		
	}
	
	private void updateGameInfo(){
		gameInfo.setText(gamemaster.players[0].getName() + " " + gamemaster.players[0].getScore() + ":" + gamemaster.players[1].getScore() + " " + gamemaster.players[1].getName());
		gameInfo.repaint();
	}
	
	private void updateGameMsgs(){
		gameMsgs.setText("Player " + gamemaster.players[gamemaster.currentColour - 1].getColourName() + " has a move.");
		gameMsgs.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void updateNotifications() {
		updateGameInfo();
		updateGameMsgs();
	}

	public void printScores(){
		Player playerOne = gamemaster.players[0], playerTwo = gamemaster.players[1];
		String playerName;
		int higher, lower;
		gameInfo.setText( "Game has ended." );
		if( playerOne.getScore() > playerTwo.getScore() ) {
			playerName = playerOne.getName();
			higher = playerOne.getScore();
			lower = playerTwo.getScore();
		}
		else if (playerOne.getScore() > playerTwo.getScore() ){
			playerName = "Draw! No one";
			higher = playerOne.getScore();
			lower = playerTwo.getScore();
		}
		else{
			playerName = playerTwo.getName();
			higher = playerTwo.getScore();
			lower = playerOne.getScore();
		}
		gameMsgs.setText(playerName + " has won the game with the score " + higher + ":" + lower);
		gameMsgs.repaint();
		gameInfo.repaint();
	}
	
	public static void main(String[] args) {
		Player playerOne = new Player(1);
		Player playerTwo = new Player(2);
		Game game = new Game(playerOne, playerTwo);
		
		JFrame othello = new JFrame();
        
		othello.getContentPane().setLayout(new GridBagLayout());
		othello.getContentPane().add(game);
		othello.pack();
		
		othello.setResizable(false);
		othello.setTitle("Othello");
		othello.setSize(800, 800);
		othello.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		othello.setVisible(true);
	}
}
