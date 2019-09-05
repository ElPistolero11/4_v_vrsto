package Logika_igre;

public enum Igralec {
	O,
	X;
	
	public Igralec nasprotnik() {
		if (this == O) {
			return X;
		} else {
			return O;
		}
	}
	
	public Polje vrniPolje() {
		if (this == O) { return Polje.O; }
		else { return Polje.X; }
	}
}
