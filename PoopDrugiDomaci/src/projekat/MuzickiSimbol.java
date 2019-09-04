package projekat;

public abstract class MuzickiSimbol {
	// nota = 1
	// pauza= 2
	// akord= 3
	protected Razlomak trajanje;
	public MuzickiSimbol(Razlomak r) {
		trajanje=r;
	}
	public Razlomak getTrajanje() {return trajanje;} 
	abstract public int vrsta();
	abstract public int getMidi();
	abstract public String ispis();
}
