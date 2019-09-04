package projekat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IspisPesme extends JPanel implements Runnable {
	public Thread nit;
	Kompozicija kompozicija;
	
	boolean radi=false;
	Grafika g;
	public int BR;
	public boolean pokrenuta=false;
	private int midi=-1;
	private int brojac;
	public IspisPesme(Kompozicija kk)
	{
		
		BR=0;
		kompozicija=kk;
		
		
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {while(radi==false && kompozicija.ListaSimbola.get(BR).getMidi()!=-1) wait();}
				
				
				
				repaint();
				
				radi=false;
				if(BR+1<kompozicija.ListaSimbola.size())
				 if(kompozicija.ListaSimbola.get(BR+1).getMidi()==-1) {
					 
					 Thread.sleep(500);
					 radi=true;
				 }
			
				
			}
		}
		catch(InterruptedException ee){
			
		}
		
	}
	@Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(pokrenuta == true) {
        	
       
		 int j=0;
		 
		if(kompozicija.ListaSimbola.get(BR).getMidi()==-1) {
				BR=BR+1;
		}
		
			if(kompozicija.ListaSimbola.get(BR).getMidi()==midi) {
				 BR=BR+1;
				 
				
			 }
			if(BR==kompozicija.ListaSimbola.size()) {
				BR=0;
			}
			 brojac=BR;
			 
				setLayout(null);
				JTextField t= new JTextField();
				 t.setBackground(Color.WHITE);
				 t.setBounds(0,0,192,112);
				 t.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				 j=j+192;
				 add(t);
				
				
				 int uk=16; int tek=0;
				 int br=brojac-1;
				 while(tek<uk) {
					 
					 br++;
					 if(br>=kompozicija.ListaSimbola.size()) {
						 JTextField t1= new JTextField("");
						 t1.setBackground(Color.blue);
						 t1.setBounds(j,0,192,112);
						 tek=tek+2;
						 j=j+192; 
						 add(t1);
						 continue;
					 }
					 
					 boolean duzina=false;
					 tek++;
					 if(kompozicija.ListaSimbola.get(brojac).getTrajanje().jednako(kompozicija.ListaSimbola.get(brojac).getTrajanje(), new Razlomak(1,4))) {
						 duzina=true;
						 tek++;
					 }
					 JTextField t1= new JTextField(""+kompozicija.ListaSimbola.get(brojac).getMidi()+kompozicija.ListaSimbola.get(brojac).ispis());
					 
					 
					 if(tek>uk) continue;
					 t1.setFont(new Font("Verdana", Font.PLAIN, 20));
					 t1.setHorizontalAlignment(JTextField.CENTER);
					 
					 if(duzina==true) {
						 t1.setBackground(Color.RED);
						 t1.setBounds(j,0,192,112);
						 j=j+192; 
					 }
					 else {
						 t1.setBackground(Color.GREEN);
						 t1.setBounds(j,0,96,112);
						 j=j+96;  
					 }
					 t1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
					 add(t1);
					 brojac++;
				 }
				 JTextField te= new JTextField();
				 te.setBackground(Color.WHITE);
				 te.setBounds(j,0,192,112);
				 te.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				 j=j+192;
				 add(te);
				
		 
			 
		 
        } 
	 }
	public void zavrsi() {
		nit.interrupt();
	}
	public synchronized void kreni(int m) {
		midi=m;
		radi=true;
		notifyAll();
	}
	public synchronized void pokreniIspis() {
		nit=new Thread(this);
		nit.start();
		pokrenuta=true;
		BR=0;
		radi=true;
		notifyAll();
		
	}
	public synchronized void reset() {
		
		BR=0;
		radi=true;
		notifyAll();
	}
	public synchronized void stani() {
		
		radi=false;
	}
}
