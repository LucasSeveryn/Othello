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
	private Dimension size = new Dimension(36,36);
	public int value = 0;
	public Dimension dot = new Dimension((int)(size.width/3),(int)(size.height/3));
    public Dimension arc = new Dimension((int)Math.sqrt(size.width),(int)Math.sqrt(size.height));
    private boolean mouseEntered = false;
    private boolean mousePressed = false;
	
	public Field( int v ){
		super();
		this.setPreferredSize(new Dimension(70, 70));
		setValue(v);
	}
	
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // turn on anti-alias mode
        Graphics2D antiAlias = (Graphics2D)g;
        antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // draw white rectangle
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth()-1,getHeight()-1);
        
        // draw black border
        g.setColor(Color.decode("#eeeeee"));
        g.drawRect(0,0,getWidth()-1,getHeight()-1);

        // draw inside light border
        g.setColor(Color.decode("#c0c0c0"));
        g.drawRect(1,1,getWidth()-3,getHeight()-3);

        // draw chip
        int dC = getHeight() - 20;
		int xC = 10;
        int yC = 10;
        if(getValue() == 1){
        	g.setColor(Color.BLACK);
        	g.fillOval(xC, yC, dC, dC);
        } else if(getValue() == 2){
        	g.setColor(Color.RED);
        	g.fillOval(xC, yC, dC, dC);
        	System.out.println("Piesek");
        }
 
     }
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public int getValue(){
		return value;
	}
	public void setValue(int v){
		value = v;
		repaint();
	}
	
	public int flip(){
		switch(getValue()){
			case 0: break;
			case 1: setValue(2); break;
			case 2: setValue(1); break;
		}
		return getValue();
	}
}
