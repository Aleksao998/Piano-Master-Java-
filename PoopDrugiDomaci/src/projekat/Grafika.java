package projekat;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.border.Border;
public class Grafika extends JFrame implements ActionListener, KeyListener{
	private MidiPlayer player=null;
	private Kompozicija kompozicija;
	private JPanel containerPane;
	private JPanel topPane;
	private JLayeredPane bottomPane;
	private JPanel centerPane;
	private JPanel centerTopPane;
	private JPanel centerBottomPane;
	private JPanel centerCenterPane;
	private JPanel topTopPane;
	private JPanel topCenterPane;
	public Dirka[] sveDirke=new Dirka[100];
	private AutomatskiSviraj auto;
	private Color boja;
	private IspisPesme ispisPesme;
	private int midiBr=36;
	private Record record;
	public Grafika() throws IOException, MidiUnavailableException {
		super("Virtual Piano");
		kompozicija=new Kompozicija();
		record=new Record();
		player=new MidiPlayer();
		 containerPane = new JPanel();
	     topPane = new JPanel(new GridLayout(3, 1));
	     topTopPane = new JPanel();
	     topCenterPane = new JPanel();
	     bottomPane = new JLayeredPane();
	     centerTopPane=new JPanel();
	     centerBottomPane=new JPanel();
	     centerCenterPane=new JPanel();
	     
	     centerPane = new JPanel(new GridLayout(3, 1));
	     topPane.setBackground(Color.white);
	     bottomPane.setBackground(Color.green);
	     containerPane.setLayout(new GridLayout(3, 1));
	     addKeyListener(this); //Dodaj key listner
	     setExtendedState(MAXIMIZED_BOTH);
	     bottomPane.setLayout(null);
		
	     int j=0;
	     int br=1;
	     for(int i=0; i<35; i++) {
	    	 Dirka jb = new Dirka();
	    	 jb.setBounds(j,0,53,500);
	    	 String txt=kompozicija.Map2.get(midiBr);
	    	 jb.setText(""+midiBr);
	    	 jb.setName(""+midiBr);
	    	 sveDirke[midiBr]=jb;
	    	 j=j+55;
	    	 if(br==3) {
	    		 midiBr+=1;
	    		 br++;
	    	 }
	    	 else if(br==7) {
	    		 midiBr+=1;
	    		 br=1;
	    	 }
	    	 else {
	    		 midiBr+=2;
	    		 br++;
	    	 }
	    	 
		     jb.setBackground(Color.WHITE);
		     jb.addActionListener(this); 
		     bottomPane.add(jb,new Integer(1));
	     }
	     j=40;
	     br=1;
	     midiBr=37;
	     for(int i=0; i<34; i++) {
	    	 if(i==2 || i==6 || i==9 || i==13 || i==16 || i==20 || i==23 || i==27 || i==30) {
	    		 j=j+55;
	    		 continue;
	    	 }
	    	 Dirka jbcrna = new Dirka();
		     jbcrna.setMargin(new Insets(0,0,0,0));
		     String txt=kompozicija.Map2.get(midiBr);
		     jbcrna.setText(""+midiBr);
		     jbcrna.setForeground(Color.white);
		     jbcrna.setName(""+midiBr);
		     jbcrna.setBounds(j,0,27,250);
		     sveDirke[midiBr]=jbcrna;
		     if(br==2) {
		    	 midiBr+=3;
		    	 br++;
		     }
		     else if(br==5) {
		    	 midiBr+=3;
		    	 br=1;
		     }
		     else {
		    	 midiBr+=2;
		    	 br++;
		     }
		     j=j+55;
	    	 jbcrna.setBackground(Color.black);
	    	 jbcrna.addActionListener(this);
	    	 bottomPane.add(jbcrna,new Integer(2)); 
	     }
	     dodajTextPanel();
	     dodajPesmaPanel();
	     dodajTopPanel();
	 	 auto=new AutomatskiSviraj(player, kompozicija, this, ispisPesme);
	     //
	     centerPane.add(centerTopPane);
	     centerPane.add(ispisPesme);
	     centerPane.add(centerBottomPane);
	     
	    
	     containerPane.add(topPane);
	     containerPane.add(centerPane);
	     containerPane.add(bottomPane);
	     add(containerPane);
	     
	     setFocusable(true);
	     setVisible(true);//Mora na kraju
	}
	public void dodajTopPanel() {
		
		topTopPane.setLayout(new GridLayout(1,2));
		topCenterPane.setBackground(Color.green);
		topCenterPane.setLayout(new GridLayout(1,3));
		
		Button snimi=new Button("Snimaj");
		snimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				record.pokreniNit();
				//Vrati fokus 
				requestFocusInWindow();
			}
		});
		topTopPane.add(snimi);
		Button stop=new Button("Prekini");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					record.zavrsi();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Vrati fokus 
				requestFocusInWindow();
			}
		});
		
		topTopPane.add(stop);
		
		
		Button autoplay=new Button("Pusti pesmu");
		autoplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auto.pokreniSviranje();
				//Vrati fokus 
				requestFocusInWindow();
			}
		});
		
		Button pauseautoplay=new Button("Pauziraj pesmu");
		pauseautoplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auto.stani();
				requestFocusInWindow();
			}
		});
		Button resetautoplay=new Button("Ugasi pesmu");
		resetautoplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auto.zavrsi();
				requestFocusInWindow();
			}
		});
		topCenterPane.add(autoplay);
		topCenterPane.add(pauseautoplay);
		topCenterPane.add(resetautoplay);
		
		
		
		topPane.add(topTopPane);
		topPane.add(topCenterPane);
	}
	public static void main(String[] args) throws IOException, MidiUnavailableException {  
		new Grafika();  
		}

	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		JButton jb = (JButton)obj;
		
		int midi=Integer.parseInt(jb.getName());
		try {
			
			ispisPesme.kreni(midi);
			
			player.play(midi,new Long(70));
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Vrati fokus 
		requestFocusInWindow();
	}
	
	
	
	
	
	void dodajPesmaPanel() {
		ispisPesme=new IspisPesme(kompozicija);
		ispisPesme.setBackground(Color.white);
	}
	void dodajTextPanel() {
		centerBottomPane.setLayout(new GridLayout(1, 12));
		 JTextField t= new JTextField();
		 t.setBackground(Color.WHITE);
		 centerBottomPane.add(t);
		 t.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		for(int i=0;i<8;i++) {
			 JTextField t1= new JTextField();
			 t1.setBackground(Color.white);
			 t1.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, Color.BLACK));
			 centerBottomPane.add(t1);
		}
		JTextField end= new JTextField();
		 end.setBackground(Color.WHITE);
		 end.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, Color.BLACK));
		 centerBottomPane.add(end);
		 centerBottomPane.add(end);
		 
		 
		 centerTopPane.setLayout(new GridLayout(1, 3));
		 
		 JPanel dugmici1= new JPanel();
		 dugmici1.setLayout(new GridLayout(1,1));
		 dugmici1.setBackground(Color.white);
		 centerTopPane.add(dugmici1);
		 JPanel dugmici2= new JPanel();
		 dugmici2.setLayout(new GridLayout(3, 1));
		 dugmici2.setBackground(Color.white);
		 centerTopPane.add(dugmici2);
		 JPanel dugmici3= new JPanel();
		 dugmici3.setLayout(new GridLayout(1,1));
		 dugmici3.setBackground(Color.white);
		 centerTopPane.add(dugmici3);
		 JPanel dugmici21= new JPanel();
		 dugmici21.setBackground(Color.white);
		 dugmici2.add(dugmici21);
		 //Text pesme
		 TextField nazivPesme=new TextField();
		 dugmici2.add(nazivPesme,BorderLayout.CENTER);
		 //Button reset
		 Button reset=new Button("Reset");
		 reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ispisPesme.reset();
			
				//Vrati fokus 
				requestFocusInWindow();
			}
		});
		 dugmici1.add(reset);
		//Button start
		 Button start=new Button("Start");
		 start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						kompozicija.pokreniKompoziciju(nazivPesme.getText());
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ispisPesme.pokreniIspis();
				
					//Vrati fokus 
					requestFocusInWindow();
				}
			});
		 dugmici3.add(start);
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		char ch = e.getKeyChar();
		
		int midi = Integer.parseInt(kompozicija.Map.get(ch).niz[1]);
		
		boja=sveDirke[midi].getBackground();
		sveDirke[midi].setBackground(Color.GRAY);
		ispisPesme.kreni(midi);
		player.play(midi);
		long a=System.currentTimeMillis();
		record.kreni(ch, a);
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar();
		int midi = Integer.parseInt(kompozicija.Map.get(ch).niz[1]);
		sveDirke[midi].setBackground(boja);
		player.release(midi);
		long a=System.currentTimeMillis();
		record.kreni(ch, a);
	}

	@Override
	public void keyTyped(KeyEvent e) {

		
	}

	
	
	
}
