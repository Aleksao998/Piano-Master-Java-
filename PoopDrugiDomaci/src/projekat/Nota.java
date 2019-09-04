package projekat;

public class Nota extends MuzickiSimbol {
	private String nota;
	private boolean povisena=false;
	private String midiBr;
	
	
	public Nota(String _nota,boolean _povisena, String _midiBr, Razlomak _r) {
		super(_r);
		nota=_nota;
		povisena=_povisena;
		midiBr=_midiBr;
	}
	

	public String getNota() {
		return nota;
	}
	public boolean getPovisena() {
		return povisena;
	}
	public String getMidiBr() {
		return midiBr;
	}
	public int getMidi() {
		return Integer.parseInt(midiBr);
	}
	
	
	@Override
	public int vrsta() {
		return 1;
	}
	public String toString() {
		String txt="";
		
		if(povisena==true) txt=""+nota.charAt(0)+"#"+nota.charAt(1);
		else txt=nota;
		
		return txt;
	}
	@Override
	public String ispis() {
		String txt="";
		
		if(povisena==true) txt=""+nota.charAt(0)+"#"+nota.charAt(1);
		else txt=nota;
		
		return txt;
	}
}
