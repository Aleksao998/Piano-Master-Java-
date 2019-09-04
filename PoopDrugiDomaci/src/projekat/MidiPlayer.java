package projekat;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Scanner;

public class MidiPlayer {
	
	private static final int DEFAULT_INSTRUMENT = 1;
	private MidiChannel channel;
	
	public MidiPlayer() throws MidiUnavailableException {
		this(DEFAULT_INSTRUMENT);
	}
	
	public MidiPlayer(int instrument) throws MidiUnavailableException {
		channel = getChannel(instrument);
	}
	
	public void play(final int note) {
		channel.noteOn(note, 150);
	}
	
	public void release(final int note) {
		channel.noteOff(note, 150);
	}
	public void play(final int note, final long length)throws InterruptedException {
		synchronized (this) {
			play(note);	
		}
		Thread.sleep(length);
		synchronized (this) {
			release(note);
		}
	}
	
	private static MidiChannel getChannel(int instrument) throws MidiUnavailableException {
		Synthesizer synthesizer = MidiSystem.getSynthesizer();
		synthesizer.open();
		return synthesizer.getChannels()[instrument];
	}
	
}