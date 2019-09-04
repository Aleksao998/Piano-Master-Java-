package projekat;
import java.util.*;
public class Akord extends MuzickiSimbol{
	public List<MuzickiSimbol> akordi=new ArrayList<MuzickiSimbol>();
	public Akord(Razlomak r) {
		super(r);
	}
	public void dodajUAkord(MuzickiSimbol s) {
		akordi.add(s);
	}
	public int duzinaAkorda() {
		return akordi.size();
	}
	@Override
	public int vrsta() {
		return 3;
	} 
	public int getMidi() {
		return -2;
	}
	public List getAkord() {
		return akordi;
	}
	public String toString() {
		String txt="";
		txt=txt+"[";	
		for(int i=0;i<akordi.size();i++) {
			txt=txt+akordi.get(i).toString();
		}
		txt=txt+"]";	
		return txt;
	}
	@Override
	public String ispis() {
		String txt="";
		txt=txt+"[";	
		for(int i=0;i<akordi.size();i++) {
			txt=txt+akordi.get(i).toString();
		}
		txt=txt+"]";	
		return txt;
	}
}
