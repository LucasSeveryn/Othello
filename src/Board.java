import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.Scanner;
import javax.swing.JPanel;
import java.awt.Component;

public class Board extends JPanel{
	public Gamemaster gamemaster;
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
		
		Graphics2D antiAlias = (Graphics2D)g;
        antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.decode("#696969"));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
		
		g.setColor(Color.decode("#ffffff"));
		g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 5, 5);
		
		super.paintChildren(g);
        
	}
	
	public boolean isOutOfBounds(int x,int y){
		return (x<0||x>=getBoardWidth()||y<0||y>=getBoardHeight());		
	}
	
	public Field getChip(int x, int y){
		return boardArray[y][x];
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
	
	public void placeChip(int x, int y, int colour){
		boardArray[y][x].setValue(colour);
		repaint();
	}
	
	public boolean emptyPlace(int x, int y){
		return boardArray[y][x].getValue() == 0;
	}
	private void populateArray(){
		int midX = getBoardWidth()/2;
		int midY =  getBoardHeight()/2;
		
		for(int h=0;h<getBoardHeight();h++) 
			for(int w=0;w<getBoardWidth();w++) {
				
				boardArray[h][w] = new Field(0, h, w, this);
			}

		boardArray[midY][midX].setValue(1);
		boardArray[midY][midX-1].setValue(2);
		boardArray[midY - 1][midX].setValue(2);
		boardArray[midY - 1][midX - 1].setValue(1);
		
		
		for(int h = 0; h < getBoardHeight(); h++) 
			for(int w = 0;w < getBoardWidth(); w++) {
				add( boardArray[h][w] );
			}
	}

	public void printBoardState(){
		int w = getBoardWidth();
		int h = getBoardHeight();
		
		//print upper axis
		System.out.print(" ");
		for(int i=0;i<w;i++){
			System.out.print(" " + i);
		}
		System.out.println();
		
		//print what is inside
		for(int i=0;i<h;i++){ //i is row
			System.out.print(i + " ");
			for(int j=0;j<w;j++){
				switch(boardArray[i][j].getValue()){
					case 0: System.out.print("- "); break;
					case 1: System.out.print("o "); break; //white
					case 2: System.out.print("x "); break; //black
				}
			}
			System.out.println();
		}
	}
}
