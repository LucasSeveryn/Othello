public class Player{
	private int score = 2;
	private String name;
	private int colour;
	
	public Player( int colour ){
		this.colour = colour;
		name = getColourName();
	}
	
	public void incrementScore(){
		score++;
	}
	
	public void decrementScore(){
		score--;
	}

	public String getColourName(){
		return ( colour == 1 ) ? "Black" : "Red";
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
