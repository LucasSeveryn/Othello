import java.util.*;



public class Player {
	private int score;
	private String name;
	private int colour;
	
	
	public Player(int colour){
		changeName();
		this.colour=colour;
		score=0;
	}
	
	public void incrementScore(){
		score++;
	}
	
	
	public void changeName(){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\nPlease enter name: ");
		name=keyboard.next();
	}

	public String getName() {
		return name;
	}
	

	public int getScore() {
		return score;
	}


	public int getColour() {
		return colour;
	}
	
}
