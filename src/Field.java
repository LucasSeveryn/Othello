import javax.swing.JComponent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

public class Field extends JComponent implements MouseListener {
	private Board board;
	private Dimension size = new Dimension( 69, 69 );
	private int value = 0;
	private int x;
	private int y;
    
	
	private boolean hover = false;
    private boolean click = false;
	
	public Field( int v, int x, int y, Board b ){
		super();
		this.setPreferredSize( size );
		setValue( v );
		addMouseListener( this );
		board = b;
		this.x = x;
		this.y = y;
	}
	
	public boolean isEmpty(){
		return value == 0 || value > 10;
	}
	
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D antiAlias = (Graphics2D)g;
        antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
    	if( click && value > 10){
    		g.setColor( Color.decode("#666666") );
    	} else {
        	g.setColor( Color.WHITE );
        }
        g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
        
        if( hover ){
        	if( value > 10 ){
        		g.setColor( Color.decode("#555555") );
        	} else {
        		g.setColor( Color.decode("#aaaaaa") );
        	}
        } else {
        	g.setColor( Color.decode("#eeeeee") );
        }
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g.setColor(Color.decode("#c0c0c0"));
        g.drawRect(1, 1, getWidth()-3, getHeight()-3);

        int dC = getHeight() - 20;
		int xC = 10;
        int yC = 10;
        if(getValue() == 1){
        	g.setColor(Color.BLACK);
        	g.fillOval(xC, yC, dC, dC);
        } else if(getValue() == 2){
        	g.setColor(Color.decode("#dc322f"));
        	g.fillOval(xC, yC, dC, dC);
        } else if(getValue() == 11){
        	g.setColor(Color.LIGHT_GRAY);
        	g.fillOval(xC, yC, dC, dC);
        } else if(getValue() == 12){
        	g.setColor(Color.PINK);
        	g.fillOval(xC, yC, dC, dC);
        }
 
     }

	public void mouseClicked( MouseEvent arg0 ) {
		board.getGamemaster().playerHasMoved(x, y);
	}

	public void mouseEntered(MouseEvent arg0) {
		hover = true;
		if( value > 10 ){
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		repaint();
	}

	public void mouseExited(MouseEvent arg0) {
		hover = false;
		repaint();
		
	}

	public void mousePressed(MouseEvent arg0) {
		click = true;
		repaint();
	}

	public void mouseReleased(MouseEvent arg0) {
		click = false;
		repaint();
	}

	public int getValue(){
		return value;
	}
	public void setValue(int v){
		this.value = v;
		repaint();
	}
	
	public int flip(){
		setValue(board.getGamemaster().currentColour);
		return getValue();
	}
}
