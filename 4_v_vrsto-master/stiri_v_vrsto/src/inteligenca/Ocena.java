package inteligenca;

import Logika_igre.*;

public class Ocena {
	public static final int ZMAGA = 1000000; // Nedosegljivo veliko število.
	public static final int NEODLOCENO = 0;
	public static final int PORAZ = -ZMAGA; // Če nekdo zmaga, njegov nasprotnik izgubi; sešteti se mora v 0.
	public static final int A = Logika.A;
	
	public static int vrednostCetvorca(int i) {
		assert (i < A);
		if (i == 0) { return 0; }
		return (ZMAGA >> ((A - i) * 10) );
	}
	
	public static int oceni(Logika igra, Igralec jaz) {
		Polje [][] plosca = igra.vrniPlosco();
		Igralec naPotezi = null;
		switch (igra.stanjeIgre()) {
		case ZMAGA_O: return (Igralec.O == jaz ? ZMAGA : PORAZ);
		case ZMAGA_X: return (Igralec.X == jaz ? ZMAGA : PORAZ);
		case NEODLOCENO: return NEODLOCENO;
		case NA_POTEZI_O: naPotezi = Igralec.O; break;
		case NA_POTEZI_X: naPotezi = Igralec.X; break;
		}
		int tocke_O = 0;
		int tocke_X = 0;
		for (Cetvorec z : Logika.cetvorci) {
			int stevilo_O = 0;
			int stevilo_X = 0;
			for (int i = 0; i < A; i++) {
				if (stevilo_O != 0 && stevilo_X != 0) {
					break;
				} else {
					switch (plosca[z.x[i]][z.y[i]]) {
					case O: stevilo_O += 1;
					case X: stevilo_X += 1;
					case PRAZNO: continue;
					}
				}
			}
			if (stevilo_X == 0) { tocke_O += vrednostCetvorca(stevilo_O); }
			if (stevilo_O == 0) { tocke_X += vrednostCetvorca(stevilo_X); }
		}
		if (naPotezi == Igralec.X) { tocke_O /= 2; }
		if (naPotezi == Igralec.O) { tocke_X /= 2; }
		return (jaz == Igralec.O ? tocke_O - tocke_X : tocke_X - tocke_O);
	}
}
