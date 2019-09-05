package GUI;

import Logika_igre.*;
import inteligenca.*;

import javax.swing.SwingWorker;

public class Racunalnik extends Mislec {
	private Igralec jaz;
	private Okno master;
	private SwingWorker<Poteza,Object> thinker;

	public Racunalnik(Okno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	
	public void na_potezi() {
		thinker = new Minimax(master, jaz, 5);
		thinker.execute();
	}
	
	public void prekini() {
		if (thinker != null) {
			thinker.cancel(true);
		}
	}
	
	public void klikni(int i, int j) {
		// Ignoriramo.
	}
}
