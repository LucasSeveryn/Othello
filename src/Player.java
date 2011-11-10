import java.util.*;

public class Player {
	private int score = 2;
	private String name;
	private int colour;
	
	
	public Player(int colour){
		//changeName();
		this.colour = colour;
		name = getColourName();
	}
	
	public void incrementScore(){
		score++;
	}
	
	public void decrementScore(){
		score--;
	}
	
	public void changeName(){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\nPlease enter your name: ");
		name=keyboard.next();
	}

	public String getColourName(){
		return (colour == 2) ? "Black" : "Red";
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
