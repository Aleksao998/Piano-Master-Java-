package projekat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Record implements Runnable {
	private static final long cetvrtina=300;
	private static final long osmina=150;
	
	private Thread nit;
	boolean radi=false;
	private String mojaPesma;
	private boolean popunjenObj;

	public class struct{
		char karakter;
		long vrPoc,vrKraj;
		public struct(char c,long vp, long vk) {
			karakter=c;
			vrPoc=vp;
			vrKraj=vk;
		}
		public String toString() {
			return ""+karakter+" "+vrPoc+" "+vrKraj;
		}
	}
	char k;
	long vP,vK;
	private List<struct> sviSimboli= new ArrayList<struct>();
	public void pokreniNit() {
		mojaPesma="";
		sviSimboli.clear();
		
		nit=new Thread(this);
		nit.start();
	}
	@Override
	public void run() {
		try {
			while(!nit.interrupted()) {
				synchronized (this) {while(radi==false) wait();}
				struct obj1=new struct(k,vP,vK);
				
				sviSimboli.add(obj1);
				radi=false;
			}
		}
		catch(InterruptedException ee){
			
		}
	}
	public synchronized void stani() {
		radi=false;
	}
	public void zavrsi() throws IOException {
		nit.interrupt();
		
		obrada();
	}
	public void obrada() throws IOException {
		int cur=0;
		for(;cur<sviSimboli.size();cur++) {
			long trajanje=sviSimboli.get(cur).vrKraj-sviSimboli.get(cur).vrPoc;
			//nota 1/4
			if(cur+1<sviSimboli.size())
			if(sviSimboli.get(cur+1).vrKraj-sviSimboli.get(cur).vrKraj<50){
				mojaPesma+="[";
				while(sviSimboli.get(cur+1).vrKraj-100<sviSimboli.get(cur).vrKraj) {
					mojaPesma+=sviSimboli.get(cur).karakter;
					if(cur+1>=sviSimboli.size())break;
					cur++;
				}
				mojaPesma+="]";
				if(cur>=sviSimboli.size())break;
				
			}
			else if(trajanje>osmina) {
				mojaPesma+=sviSimboli.get(cur).karakter; 
			}
			//note 1/8
			else if(cur+1<sviSimboli.size()) {
				mojaPesma+="[";
				long tr=sviSimboli.get(cur).vrKraj-sviSimboli.get(cur).vrPoc;
				while(tr<=osmina) {
					mojaPesma+=sviSimboli.get(cur).karakter+" ";
					if(cur+1>=sviSimboli.size())break;
					if(sviSimboli.get(cur+1).vrKraj-sviSimboli.get(cur+1).vrPoc>osmina) {
						break;
					}
					cur++;
					
					tr=sviSimboli.get(cur).vrKraj-sviSimboli.get(cur).vrPoc;
				}
				mojaPesma+="]";
				if(cur>=sviSimboli.size())break;
			}
			
				
		
			//Pauze
			if(cur+1<sviSimboli.size() && cur!=0)
			if(sviSimboli.get(cur+1).vrPoc-sviSimboli.get(cur).vrKraj< 350) {
				
			}
			else if(sviSimboli.get(cur+1).vrPoc-sviSimboli.get(cur).vrKraj>350 && sviSimboli.get(cur+1).vrPoc-sviSimboli.get(cur).vrKraj<500) {
				mojaPesma+="|";
			}
			else if(sviSimboli.get(cur+1).vrPoc-sviSimboli.get(cur).vrKraj>550 && sviSimboli.get(cur+1).vrPoc-sviSimboli.get(cur).vrKraj<cetvrtina+650) {
				mojaPesma+=" ";
			}
			else {
				 long t = (sviSimboli.get(cur+1).vrPoc-sviSimboli.get(cur).vrKraj)/cetvrtina;
				 for(int j=0;j<t;j++) mojaPesma+=" ";
			}
			
		}
		System.out.println(mojaPesma);
		TxtFile t=new TxtFile();
		t.napraviFajl("C:\\Users\\aleks\\Desktop\\PoopProject\\PoopDrugiDomaci\\poop\\mojaPesma", mojaPesma);
		Kompozicija k=new Kompozicija();
		k.pokreniKompoziciju("mojaPesma");
		MidiFile m=new MidiFile();
		m.napraviFajl("C:\\Users\\aleks\\Desktop\\PoopProject\\PoopDrugiDomaci\\poop\\mojaPesmaPeva", k);
		
	}
	public synchronized void kreni(char z, long a) {
		if(popunjenObj==false) {
			k=z; vP=a;	
			popunjenObj=true;
			
		}
		else {
			vK=a;
			radi=true;
			notifyAll();
	
			popunjenObj=false;
		}
	
	}
}
