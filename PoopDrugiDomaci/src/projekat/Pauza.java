package projekat;

public class Pauza extends MuzickiSimbol {
	
	public Pauza(Razlomak r) {
		super(r);
	}
	@Override
	public int vrsta() {
		return 2;
	}
	public String toString() {
		boolean provera=trajanje.jednako(trajanje, new Razlomak(1,8));
		if (provera == true)
			return "_";
		else 
			return "|";
	}
	
	public int getMidi() {
		return -1;
	}
	@Override
	public String ispis() {
		boolean provera=trajanje.jednako(trajanje, new Razlomak(1,8));
		if (provera == true)
			return "_";
		else 
			return "|";
	}
}