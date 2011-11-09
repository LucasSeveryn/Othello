import javax.swing.JFrame;
import javax.swing.JPanel;

public class GBoard extends Board{
	JFrame frame;
	
	public GBoard( JFrame f ){
		frame = f;
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);

	    panel.setLayout(null);
	}
	
	public void initialiseBoard(){
		int width=8;
		int height=8;
		boardArray = new int[height][width];
	}
	
	public void printBoardState(){
		int w = getWidth();
		int h = getHeight();
		
		//print upper axis
		
		//print what is inside
		for(int i=0;i<h;i++){ //i is row
			for(int j=0;j<w;j++){
				switch(boardArray[i][j]){
					case 0: System.out.print("- "); break;
					case 1: System.out.print("o "); break; //white
					case 2: System.out.print("x "); break; //black
				}
			}
		}
	}
}
