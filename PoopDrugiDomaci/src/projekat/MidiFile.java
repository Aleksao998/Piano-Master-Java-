package projekat;

import javax.sound.midi.*;
import java.io.*;

public class MidiFile {

		private static final int time=25;
		
		public boolean napraviFajl(String putanja, Kompozicija kompozicija) {
				long actionTime = 1;
				try {
					//Sekvenca 24
					Sequence sekvenca = new Sequence(javax.sound.midi.Sequence.PPQ, 24);


					Track t = sekvenca.createTrack();

					byte[] b = { (byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7 };
					SysexMessage sm = new SysexMessage();
					sm.setMessage(b, 6);
					MidiEvent me = new MidiEvent(sm, (long) 0);
					t.add(me);

					//Tempo
					MetaMessage mt = new MetaMessage();
					byte[] bt = { 0x02, (byte) 0x00, 0x00 };
					mt.setMessage(0x51, bt, 3);
					me = new MidiEvent(mt, (long) 0);
					t.add(me);

					//Ime
					mt = new MetaMessage();
					String TrackName = new String("midifile track");
					mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
					me = new MidiEvent(mt, (long) 0);
					t.add(me);

					//set omni
					ShortMessage mm = new ShortMessage();
					mm.setMessage(0xB0, 0x7D, 0x00);
					me = new MidiEvent(mm, (long) 0);
					t.add(me);

					//set poly
					mm = new ShortMessage();
					mm.setMessage(0xB0, 0x7F, 0x00);
					me = new MidiEvent(mm, (long) 0);
					t.add(me);

					//set piano
					mm = new ShortMessage();
					mm.setMessage(0xC0, 0x00, 0x00);
					me = new MidiEvent(mm, (long) 0);
					t.add(me);
					//Upisivanje u fajl
					for (int i = 0; i < kompozicija.ListaSimbola.size(); i++) {
						//Ako je pauza
						if (kompozicija.ListaSimbola.get(i).vrsta()==2) {
							if (kompozicija.ListaSimbola.get(i).getTrajanje()==new Razlomak(1,4))actionTime+=time;
							else actionTime +=2*time;
						}
						else if (kompozicija.ListaSimbola.get(i).vrsta()==1) {
							
							int midi=kompozicija.ListaSimbola.get(i).getMidi();
							mm = new ShortMessage();
							mm.setMessage(0x90, midi, 0x60); 
							me = new MidiEvent(mm, actionTime);
							t.add(me);

							if (kompozicija.ListaSimbola.get(i).getTrajanje()==new Razlomak(1,4)) actionTime+=time;
							else actionTime+=2*time;
							
							mm = new ShortMessage();
							mm.setMessage(0x80, midi, 0x40);
							me = new MidiEvent(mm, actionTime);
							t.add(me);
						}
						else {//Akord
							Akord a=(Akord)kompozicija.ListaSimbola.get(i);
							for (int j = 0; j < a.akordi.size(); j++) {
								MuzickiSimbol pomocni=a.akordi.get(j);
								
								int midi=pomocni.getMidi();
								
								mm = new ShortMessage();
								mm.setMessage(0x90, midi, 0x60); 
								me = new MidiEvent(mm, actionTime);
								t.add(me);
								mm = new ShortMessage();
								mm.setMessage(0x80, midi, 0x40);
								me = new MidiEvent(mm, actionTime+2*time);
								t.add(me);
							}
							
							actionTime += 2*time;
						} 
					}
					
					mt = new MetaMessage();
					byte[] bet = {}; // empty array
					mt.setMessage(0x2F, bet, 0);
					me = new MidiEvent(mt, (long) 140);
					t.add(me);

					File file = new File(putanja+".midi");
					MidiSystem.write(sekvenca, 1, file);
					return true;
				} 
				catch (Exception e) {
					return false;
				} 
			}
		
	}




