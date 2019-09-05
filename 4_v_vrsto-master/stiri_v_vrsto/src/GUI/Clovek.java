package GUI;

import Logika_igre.*;

public class Clovek extends Mislec {
	private Igralec jaz;
	private Okno master;
	
	public Clovek(Okno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
		
	public void na_potezi() {
		// Ignoriramo.
	}

	public void prekini() {
		// Ignoriramo.
	}

	public void klikni(int i, int j) {
		master.odigraj(new Poteza(i, j));
	}
}
