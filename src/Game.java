import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * Main game class
 *
 */
public class Game extends JPanel{
	private static final long serialVersionUID = 2649079520970549857L;
	private Board gameBoard;
	private Gamemaster gamemaster;
	private Player[] players = new Player[2];
	private JLabel gameInfo;
	private JLabel gameMsgs;
	
	public Game( Player playerOne, Player playerTwo ){
		
		players[0] = playerOne;
		players[1] = playerTwo;
		
		gameBoard = new Board();
		gamemaster = new Gamemaster( this );
		gameBoard.iniGamemaster( gamemaster );
		
        setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets( 5, 5, 5, 5 );
		
		gameInfo = new JLabel();
		updateGameInfo();
		 
		c.gridx = 0;
		c.gridy = 1;
		add( gameInfo, c );
		
		gameMsgs = new JLabel();
		updateGameMsgs();
		
		c.gridx = 0;
		c.gridy = 2;
		add( gameMsgs, c );

		c.gridx = 0;
		c.gridy = 0;
		add( gameBoard, c );
		
	}
	
	/**
	 * 
	 * Updates label with game results
	 * 
	 **/
	private void updateGameInfo(){
		gameInfo.setText( getPlayer( 0 ).getName() + " " + getPlayer(0).getScore() + ":" + getPlayer( 1 ).getScore() + " " + getPlayer( 1 ).getName() );
		gameInfo.repaint();
	}
	
	/**
	 * 
	 * Updates label with game state
	 * 
	 **/	
	private void updateGameMsgs(){
		gameMsgs.setText( "Player " + getPlayer( gamemaster.currentColour - 1 ).getColourName() + " has a move." );
		gameMsgs.repaint();
	}

	public void updateNotifications() {
		updateGameInfo();
		updateGameMsgs();
	}

	public void printScores(){
		Player playerOne = getPlayer( 0 ), playerTwo = getPlayer( 1 );
		String playerName;
		int higher, lower;
		gameInfo.setText( "Game has ended." );
		if( playerOne.getScore() > playerTwo.getScore() ) {
			playerName = playerOne.getName();
			higher = playerOne.getScore();
			lower = playerTwo.getScore();
		}
		else if ( playerOne.getScore() > playerTwo.getScore() ){
			playerName = "Draw! No one";
			higher = playerOne.getScore();
			lower = playerTwo.getScore();
		}
		else{
			playerName = playerTwo.getName();
			higher = playerTwo.getScore();
			lower = playerOne.getScore();
		}
		gameMsgs.setText( playerName + " has won the game with the score " + higher + ":" + lower );
		gameMsgs.repaint();
		gameInfo.repaint();
	}
	
	public Board getGameBoard(){
		return gameBoard;
	}
	
	public Gamemaster getGamemaster(){
		return gamemaster;
	}
	
	public Player getPlayer( int n ){
		return players[n];
	}
	
	public static void main( String[] args ) {
		SwingUtilities.invokeLater( new Runnable() {
            public void run() {
        		Player playerOne = new Player( 1 );
        		Player playerTwo = new Player( 2 );
        		Game game = new Game( playerOne, playerTwo );
        		
        		JFrame othello = new JFrame();
                
        		othello.getContentPane().setLayout( new GridBagLayout() );
        		othello.getContentPane().add( game );
        		othello.pack();
        		
        		othello.setResizable( false );
        		othello.setTitle( "Othello" );
        		othello.setSize( 800, 800 );
        		othello.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                
        		othello.setVisible( true );
            }
        });
	}
}
