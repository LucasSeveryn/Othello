import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Board extends JPanel{
	private Gamemaster gamemaster;
	private static final long serialVersionUID = 1L;
	protected Field[][] boardArray;
	
	public Board(){
		initialiseBoard();
		populateArray();
        setPreferredSize(new Dimension(600, 600));
	}

	public void iniGamemaster( Gamemaster g ){
		gamemaster = g;
	}
	
	public void paint( Graphics g ){
		super.paint( g );
		
		Graphics2D antiAlias = ( Graphics2D )g;
        antiAlias.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        
        g.setColor( Color.decode( "#eeeeee" ) );
		g.fillRoundRect( 1, 1, getWidth() - 2, getHeight() - 2, 5, 5 );
		
		super.paintChildren( g );
        
	}
	
	public boolean isOutOfBounds( int x, int y ){
		return ( x < 0 || x >= getBoardWidth() || y < 0 || y >= getBoardHeight() );		
	}
	
	public Field getChip( int x, int y ){
		return boardArray[x][y];
	}
	
	public void initialiseBoard(){
		int width = 8;
		int height = 8;

		boardArray = new Field[height][width];
	}
	
	public int getBoardWidth(){
		return boardArray[0].length;
	}
	
	public int getBoardHeight(){
		return boardArray.length;
	}
	
	public void placeChip( int x, int y, int colour ){
		boardArray[x][y].setState( colour );
		repaint();
	}

	
	/**
	 * 
	 * Initializes array with empty fields
	 * 
	 **/
	private void populateArray(){
		int midX = getBoardWidth() / 2;
		int midY =  getBoardHeight() / 2;
		
		for( int h = 0; h < getBoardHeight(); h++ ) 
			for( int w = 0; w < getBoardWidth(); w++ ) {
				boardArray[h][w] = new Field( 0, h, w, this );
			}

		boardArray[midY][midX].setState( 1 );
		boardArray[midY][midX-1].setState( 2 );
		boardArray[midY - 1][midX].setState( 2 );
		boardArray[midY - 1][midX - 1].setState( 1 );
		
		for( int h = 0; h < getBoardHeight(); h++ ) 
			for( int w = 0; w < getBoardWidth(); w++ ) {
				add( boardArray[h][w] );
			}
	}

	public Gamemaster getGamemaster() {
		return gamemaster;
	}
}
