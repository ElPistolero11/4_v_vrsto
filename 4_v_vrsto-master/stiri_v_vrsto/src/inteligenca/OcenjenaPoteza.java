package inteligenca;

import Logika_igre.*;

/**
 * Objekt sestavljen iz Poteze in ocene le-te. Če je poteza null, je igre konec,
 * izid pa je spravljen v oceni.
 */

public class OcenjenaPoteza {
	public Poteza poteza;
	public int ocena;
	
	public OcenjenaPoteza(Poteza poteza, int ocena) {
		super();
		this.poteza = poteza;
		this.ocena = ocena;
	}
}
