package inteligenca;

import Logika_igre.*;
import GUI.*;

import javax.swing.SwingWorker;

public class Minimax extends SwingWorker<Poteza, Object> {
	private Okno master; // Glavno okno
	private Igralec jaz;
	private int globina; // Koliko potez vnaprej razmišljamo.
	
	public Minimax(Okno master, Igralec jaz, int globina) {
		this.master = master;
		this.jaz = jaz;
		this.globina = globina;
	}
	
	/**
	 * Vrne ocenjenaPoteza z najboljšo oceno glede na dano igro in globino n.
	 */
	
	private OcenjenaPoteza algoritem(Logika igra, int n) {
		Igralec naPotezi = null;
		switch (igra.stanjeIgre()) {
		case NA_POTEZI_O: naPotezi = Igralec.O; break;
		case NA_POTEZI_X: naPotezi = Igralec.X; break;
		case NEODLOCENO:
			return new OcenjenaPoteza(null, Ocena.NEODLOCENO);
		case ZMAGA_O:
			return new OcenjenaPoteza(
						null,
						(jaz == Igralec.O ? Ocena.ZMAGA : Ocena.PORAZ));
		case ZMAGA_X:
			return new OcenjenaPoteza(
						null,
						(jaz == Igralec.X ? Ocena.ZMAGA : Ocena.PORAZ));
		}
		if (n >= globina) {
			return new OcenjenaPoteza(null, Ocena.oceni(igra, naPotezi));
		} else {
			Poteza najboljsaPoteza = null;
			int najboljsaOcena = 0;
			for (Poteza p : igra.moznePoteze()) {
				// Naredimo kopijo igre.
				Logika kopija = new Logika(igra);
				// V kopiji odigramo potezo.
				kopija.odigrajPotezo(p);
				// Ocenimo pozicijo, po odigrani potezi p.
				int ocena_p = algoritem(kopija, n + 1).ocena;
				if ((naPotezi == jaz && ocena_p > najboljsaOcena) // Iščemo boljšo potezo, ker smo sami na potezi.
					|| (naPotezi != jaz && ocena_p < najboljsaOcena) // Iščemo slabšo potezo, ker je nasprotnik na potezi.
					|| (najboljsaPoteza == null) // Nimamo še najboljše poteze.
					) {
					najboljsaPoteza = p;
					najboljsaOcena = ocena_p;
				}
			}
			return new OcenjenaPoteza(najboljsaPoteza, najboljsaOcena);
		}
	}
	
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) {
				master.odigraj(p);
			}
		} catch (Exception e) {
		}
	}
	
	protected Poteza doInBackground() throws Exception {
		Logika igra = master.kopirajIgro();
		return algoritem(igra, 0).poteza;
		
	}	
}
