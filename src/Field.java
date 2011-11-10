import javax.swing.JComponent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Field extends JComponent implements MouseListener {
	private static final long serialVersionUID = 6743957685441882191L;
	private Board board;
	private Dimension size = new Dimension( 69, 69 );
	private FieldState state = FieldState.EMPTY;
	private int x;
	private int y;
    
	
	private boolean hover = false;
    private boolean click = false;
	
	public Field( int v, int x, int y, Board b ){
		super();
		this.setPreferredSize( size );
		setState( v );
		addMouseListener( this );
		board = b;
		this.x = x;
		this.y = y;
	}
	
	public boolean isEmpty(){
		return ( getState() == FieldState.EMPTY || getState() == FieldState.VALID_BLACK || getState() == FieldState.VALID_RED );
	}
	
	public void paintComponent( Graphics g )
    {
        super.paintComponent( g );

        Graphics2D antiAlias = ( Graphics2D )g;
        antiAlias.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        
    	if( click && ( getState() == FieldState.VALID_BLACK || getState() == FieldState.VALID_RED ) ){
    		g.setColor( Color.decode( "#666666" ) );
    	} else {
        	g.setColor( Color.WHITE );
        }
        g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
        
        if( hover ){
        	if( getState() == FieldState.VALID_BLACK || getState() == FieldState.VALID_RED ){
        		g.setColor( Color.decode( "#555555" ) );
        	} else {
        		g.setColor( Color.decode( "#aaaaaa" ) );
        	}
        } else {
        	g.setColor( Color.decode( "#eeeeee" ) );
        }
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1 );

        g.setColor( Color.decode( "#c0c0c0" ) );
        g.drawRect( 1, 1, getWidth() - 3, getHeight() - 3 );

        int dC = getHeight() - 20;
		int xC = 10;
        int yC = 10;
        if(getState() == FieldState.BLACK){
        	g.setColor( Color.BLACK );
        	g.fillOval( xC, yC, dC, dC );
        	g.setColor( Color.BLACK );
        	g.fillOval( xC, yC, dC, dC );
        } else if( getState() == FieldState.RED ){
        	g.setColor( Color.decode( "#dc322f" ) );
        	g.fillOval( xC, yC, dC, dC);
        } else if( getState() == FieldState.VALID_BLACK ){
        	g.setColor( Color.LIGHT_GRAY );
        	g.fillOval( xC, yC, dC, dC );
        } else if( getState() == FieldState.VALID_RED ){
        	g.setColor( Color.PINK );
        	g.fillOval( xC, yC, dC, dC );
        }
 
     }

	public void mouseClicked( MouseEvent arg0 ) {
		board.getGamemaster().playerHasMoved( x, y );
	}

	public void mouseEntered( MouseEvent arg0 ) {
		hover = true;
		if( state == FieldState.VALID_BLACK || state == FieldState.VALID_RED ){
			this.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		} else {
			this.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
		}
		repaint();
	}

	public void mouseExited( MouseEvent arg0 ) {
		hover = false;
		repaint();
		
	}

	public void mousePressed( MouseEvent arg0 ) {
		click = true;
		repaint();
	}

	public void mouseReleased( MouseEvent arg0 ) {
		click = false;
		repaint();
	}

	public FieldState getState(){
		return state;
	}
	
	public void setState( int s ){
		switch( s ){
			case 0: state = FieldState.EMPTY; break; 
			case 1: state = FieldState.BLACK; break;
			case 2: state = FieldState.RED; break;
			case 11: state = FieldState.VALID_BLACK; break;
			case 12: state = FieldState.VALID_RED; break;
		}
		repaint();
	}
	public void setState( FieldState s ){
		this.state = s;
		repaint();
	}
	
	public FieldState flip(){
		setState( board.getGamemaster().currentColour );
		return getState();
	}
}
