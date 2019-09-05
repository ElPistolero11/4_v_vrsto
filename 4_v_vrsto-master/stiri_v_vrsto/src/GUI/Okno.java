package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Logika_igre.*;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	/**
	 *  Igralno polje. 
	 **/
	private Igralno_Polje polje;
	
	/**
	 * Statusna vrstica.
	 */
	private JLabel vrstica;
	
	/**
	 * Logika igre.
	 */
	private Logika igra;
	
	private Mislec MislecO; /* Vleče poteze O */
	private Mislec MislecX; /* Vleče poteze X */
	
	private JMenuItem ClovekRacunalnik;
	private JMenuItem RacunalnikClovek;
	private JMenuItem RacunalnikRacunalnik;
	private JMenuItem ClovekClovek;
	
	/**
	 * Glavno okno, ki se ustvari na začetku. Tudi požene igro.
	 */
	public Okno() {
		this.setTitle("Štiri v vrsto");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		/* Menu. */
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu menu_igra = new JMenu("Igra");
		menu_bar.add(menu_igra);
		
		/* Vsi štirje možni tipi igre, ki so na voljo v meniju */
		ClovekRacunalnik = new JMenuItem("Človek <> Računalnik");
		menu_igra.add(ClovekRacunalnik);
		ClovekRacunalnik.addActionListener(this);
		
		RacunalnikClovek = new JMenuItem("Računalnik <> Človek");
		menu_igra.add(RacunalnikClovek);
		RacunalnikClovek.addActionListener(this);
		
		ClovekClovek = new JMenuItem("Človek <> Človek");
		menu_igra.add(ClovekClovek);
		ClovekClovek.addActionListener(this);
		
		RacunalnikRacunalnik = new JMenuItem("Računalnik <> Računalnik");
		menu_igra.add(RacunalnikRacunalnik);
		RacunalnikRacunalnik.addActionListener(this);
		
		/* Polje. */
		polje = new Igralno_Polje(this);
		GridBagConstraints postavitev_polja = new GridBagConstraints();
		postavitev_polja.gridx = 0;
		postavitev_polja.gridy = 0;
		postavitev_polja.fill = GridBagConstraints.BOTH;
		postavitev_polja.weightx = 1;
		postavitev_polja.weighty = 1;
		postavitev_polja.fill = GridBagConstraints.CENTER;
		
		getContentPane().add(polje, postavitev_polja);
		
		/* Statusna vrstica. */
		vrstica = new JLabel();
		vrstica.setFont(new Font(vrstica.getFont().getName(),
							    vrstica.getFont().getStyle(),
							    25));
		GridBagConstraints postavitev_vrstice = new GridBagConstraints();
		postavitev_vrstice.gridx = 0;
		postavitev_vrstice.gridy = 1;
		postavitev_vrstice.anchor = GridBagConstraints.CENTER;
		getContentPane().add(vrstica, postavitev_vrstice);
		
		/* Poženemo igro. */
		novaIgra(new Clovek(this, Igralec.O), new Racunalnik(this, Igralec.X));
	}
	
	/**
	 * Požene novo igro.
	 */
	public void novaIgra(Mislec IgralecO, Mislec IgralecX) {
		/* Prekinemo igralce. */
		if (MislecO != null) {MislecO.prekini();}
		if (MislecX != null) {MislecX.prekini();}
		this.igra = new Logika();
		MislecO = IgralecO;
		MislecX = IgralecX;
		switch (igra.stanjeIgre()) {
		case NA_POTEZI_O: MislecO.na_potezi(); break;
		case NA_POTEZI_X: MislecX.na_potezi(); break;
		default: break;
		}
		osveziGUI();
	}
	
	/**
	 * Naredi kopijo igre.
	 */
	public Logika kopirajIgro() {
		return new Logika(igra);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == ClovekRacunalnik) {
			novaIgra(new Clovek(this, Igralec.O),
					  new Racunalnik(this, Igralec.X));
		}
		else if (event.getSource() == RacunalnikClovek) {
			novaIgra(new Racunalnik(this, Igralec.O),
					  new Clovek(this, Igralec.X));
		}
		else if (event.getSource() == RacunalnikRacunalnik) {
			novaIgra(new Racunalnik(this, Igralec.O),
					  new Racunalnik(this, Igralec.X));
		}
		else if (event.getSource() == ClovekClovek) {
			novaIgra(new Clovek(this, Igralec.O),
			          new Clovek(this, Igralec.X));
		}
	}
	
	/**
	 * Se sproži, ko uporabnik klikne na polje.
	 * 
	 * @param i
	 * @param j
	 */	
	public void klikNaPolje(int i, int j) {
		if (igra != null) {
			switch (igra.stanjeIgre()) {
			case NA_POTEZI_O: MislecO.klikni(i, j); break;
			case NA_POTEZI_X: MislecX.klikni(i, j);break;
			default: break;
			}
		}		
	}
	
	/**
	 * Odigra potezo.
	 */
	
	public void odigraj(Poteza p) {
		igra.odigrajPotezo(p);
		osveziGUI();
		switch (igra.stanjeIgre()) {
		case NA_POTEZI_O: MislecO.na_potezi(); break;
		case NA_POTEZI_X: MislecX.na_potezi(); break;
		case ZMAGA_O: break;
		case ZMAGA_X: break;
		case NEODLOCENO: break;
		}
	}
	
	public Cetvorec zmagovalniCetvorec() {
		return igra.zmagovalniCetvorec();
	}
	
	/**
	 * Osveži GUI, popravi statusno vrstico.
	 */
	public void osveziGUI() {
		if (igra != null) {
			switch (igra.stanjeIgre()) {
			case NA_POTEZI_O: vrstica.setText("Na potezi je O"); break;
			case NA_POTEZI_X: vrstica.setText("Na potezi je X"); break;
			case NEODLOCENO: vrstica.setText("Neodločeno"); break;
			case ZMAGA_O: vrstica.setText("Zmagal je O"); break;
			case ZMAGA_X: vrstica.setText("Zmagal je X"); break;
			}
		} else {
			vrstica.setText("Igra ne poteka");
		}
		polje.repaint();
	}
	
	/**
	 * Vrne trenutno ploščo, če igra poteka, sicer null.
	 */
	public Polje[][] vrniPlosco() {
		if (igra != null) {
			return igra.vrniPlosco();
		} else {
			return null;
		}
	}
}