
public enum FieldState {
	EMPTY { public int getValue() { return 0; }},
	BLACK { public int getValue() { return 1; }},
	RED { public int getValue() { return 2; }},
	VALID_BLACK { public int getValue() { return 11; }},
	VALID_RED { public int getValue() { return 12; }};
	
	public abstract int getValue();
}
