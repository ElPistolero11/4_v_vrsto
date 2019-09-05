package Logika_igre;

import java.util.LinkedList;
import java.util.List;

public class Logika {
	public static final int M = 6; /* Višina igralne plošče. */
	public static final int N = 7; /* Širina igralne plošče. */
	public static final int A = 4; /* Dolžina vrste za zmago. */
	
	public Igralec naPotezi; /* Tisti igralec, ki je na potezi. */
	public Polje[][] plosca;
	
	 /**
	  * Vsi možni četvorci na plošči.
	  */
	public static final List<Cetvorec> cetvorci = new LinkedList<Cetvorec>();
	
	/**
	 * Se izvede, ko se požene program.
	 **/
	static {
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				for (int [] smer : new int[][] {{1, 0}, {0, 1}, {1, 1}, {1, -1}}) {
					int smer_i = smer[0];
					int smer_j = smer[1];
					if ((i + (A - 1) * smer_i < M)
						&& (j + (A - 1) * smer_j < N)
						&& (j + (A - 1) * smer_j >= 0)
						) {
						int [] x = new int [A];
						int [] y = new int [A];
						for (int k = 0; k < A; k++) {
							x[k] = i + smer_i * k;
							y[k] = j + smer_j * k;
						}
						cetvorci.add(new Cetvorec(x, y));
					}
				}
			}
		}
	}
	
	/**
	 * Na začetku so vsa polja prazna, na potezi je O.
	 */	
	public Logika() {
		plosca = new Polje[M][N];
		naPotezi = Igralec.O;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
	}

	/**
	 * Vrne kopijo igre.
	 */
	public Logika(Logika igra) {
		plosca = new Polje[M][N];
		this.naPotezi = igra.naPotezi;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = igra.plosca[i][j];
			}
		}
	}
	
	/**
	 * Vrne ploščo.
	 */
	public Polje[][] vrniPlosco() {
		return plosca;
	}
	
	/**
	 * Vrne seznam možnih potez.
	 */
	public List<Poteza> moznePoteze() {
		LinkedList<Poteza> p = new LinkedList<Poteza>();
		for (int j = 0; j < N; j++) {
			for (int i = M - 1; i > -1; i--) {
				if (plosca[i][j] == Polje.PRAZNO) {
					p.add(new Poteza(i, j));
					break;
				}
			}
		}
		return p;
	}
	
	/**
	 * Vrne true, če je v stolpcu še prosto mesto, in odigra potezo p;
	 * vrne false sicer.
	 */
	public boolean odigrajPotezo(Poteza p) {		
		int x = p.vrniX();
		int y = p.vrniY();
		for (Poteza q : moznePoteze()) {
			if (q.vrniX() == x && q.vrniY() == y) {
				plosca[x][y] = naPotezi.vrniPolje();
				naPotezi = naPotezi.nasprotnik();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vrne zmagovalni četvorec ali pa null, če le-tega ni.
	 */
	
	public Cetvorec zmagovalniCetvorec() {
		for (Cetvorec z : cetvorci) {
			Igralec mozni_zmagovalec = kdoZmaga(z);
			if (mozni_zmagovalec != null) {
				return z;
			} else {
				continue;
			}
		}
		return null;
	}
	
	/**
	 * Vrne igralca, ki je zmagal, ali null, če četvorec ni zmagovalni.
	 */
	
	public Igralec kdoZmaga(Cetvorec z) {
		int stevec_O = 0;
		int stevec_X = 0;
		for (int i = 0; i < A && (stevec_O == 0 || stevec_X == 0); i++) {
			switch (plosca[z.x[i]][z.y[i]]) {
			case O: stevec_O += 1; break;
			case X: stevec_X += 1; break;
			case PRAZNO: break;
			}
		}
		if (stevec_O == A) {
			return Igralec.O;
		} else if (stevec_X == A) {
			return Igralec.X;
		} else {
			return null;
		}
	}
	
	/**
	 * Ali je igre konec? Konec je, ko na plošči obstaja četvorec,
	 * katerega vsa polja so O ali X.
	 */
	
	public Stanje stanjeIgre() {
		Cetvorec z = zmagovalniCetvorec();
		if (z != null) {
			switch (plosca[z.x[0]][z.y[0]]) {
			case O: return Stanje.ZMAGA_O;
			case X: return Stanje.ZMAGA_X;
			case PRAZNO: assert false;
			}
		}
		/* Ker je pri tej igri mogoč tudi remi, je treba preveriti, če je kakšno polje še nezasedeno. */
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca [i][j] == Polje.PRAZNO) {
					if (naPotezi == Igralec.O) {
						return Stanje.NA_POTEZI_O;
					} else {
						return Stanje.NA_POTEZI_X;
					}
				}
			}
		}
		/* Zmagovalca ni, vsa polja pa so neprazna. */
		return Stanje.NEODLOCENO;
	}
}
