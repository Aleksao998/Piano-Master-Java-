package projekat;

import java.awt.Color;

public class AutomatskiSviraj implements Runnable {
	private boolean radi=true;
	private Thread nit;
	private int tek=0;
	private Kompozicija kompozicija;
	private MidiPlayer midiPlayer;
	private Grafika gg;
	private IspisPesme is;
	public void pokreniSviranje() {
		
		nit=new Thread(this);
		nit.start();
		radi=true;
		tek=0;
		is.pokreniIspis(); 
	}
    public AutomatskiSviraj(MidiPlayer m, Kompozicija k, Grafika g,IspisPesme i) {
    	
		midiPlayer=m;
		kompozicija=k;
		gg=g;
		is=i;
	}
	@Override
	public void run() {
		try {
			while(!nit.interrupted()) {
				synchronized (this) {while(radi==false) wait();}
				sviraj(tek);
				pomeri();
				
			
				
			}
		}
		catch(InterruptedException ee){
			
		}
		
	}
	public void sviraj(int tek) throws InterruptedException {
		if(kompozicija.ListaSimbola.get(tek).vrsta()==3){
			Akord a=(Akord)kompozicija.ListaSimbola.get(tek);
			for(int i=0; i<a.akordi.size();i++) {
				midiPlayer.play(a.akordi.get(i).getMidi());
				gg.sveDirke[a.akordi.get(i).getMidi()].setBackground(Color.red);
			}
			Thread.sleep(250);
			for(int i=0; i<a.akordi.size();i++) {
				midiPlayer.release(a.akordi.get(i).getMidi());
				gg.sveDirke[a.akordi.get(i).getMidi()].setBackground(Color.white);
			}
		}
		
		else if(tek<=kompozicija.ListaSimbola.size())
		if(kompozicija.ListaSimbola.get(tek).getMidi()!=-1) {
			midiPlayer.play(kompozicija.ListaSimbola.get(tek).getMidi());
			if(kompozicija.ListaSimbola.get(tek).getTrajanje().jednako(kompozicija.ListaSimbola.get(tek).getTrajanje(), new Razlomak(1,4))){
				oboji(tek);
				Thread.sleep(500);
				vratiBoju(tek);
			}
			else {
				oboji(tek);
				Thread.sleep(250);
				vratiBoju(tek);
			}
			midiPlayer.release(kompozicija.ListaSimbola.get(tek).getMidi());
		}
		
		
	}
	public void oboji(int tek) {
		System.out.println();
		gg.sveDirke[kompozicija.ListaSimbola.get(tek).getMidi()].setBackground(Color.red);
	}
	public void vratiBoju(int tek) {
		gg.sveDirke[kompozicija.ListaSimbola.get(tek).getMidi()].setBackground(Color.white);
	}
	public void pomeri() throws InterruptedException {
		if(tek<=kompozicija.ListaSimbola.size()) {
			is.kreni(kompozicija.ListaSimbola.get(tek).getMidi());
			
			if(kompozicija.ListaSimbola.get(tek+1).getMidi()==-1) {
				System.out.print("Pauza");
				 Thread.sleep(500);
				 tek++;
			 }
			tek++;
		}
	
		
	}
	public void zavrsi() {
		System.out.println("usao");
		nit.interrupt();
		gg.sveDirke[kompozicija.ListaSimbola.get(tek).getMidi()].setBackground(Color.white);
		tek=0;
		is.zavrsi();
		is.reset(); 
	}
	public synchronized void kreni(int m) {
		
		radi=true;
		notifyAll();
	}
	public synchronized void stani() {
		if(radi==false) {
			radi=true;
			notifyAll();
		}
		else radi=false;
		
	}
}
