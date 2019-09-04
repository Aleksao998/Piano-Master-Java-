package projekat;
import java.util.*;

import java.io.*;
public class TxtFile {

	public void napraviFajl(String putanja,Kompozicija kompozicija) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(putanja+".txt"));
	    String k=kompozicija.komp;
	    writer.append(k);
	    writer.close();
	}
	public void napraviFajl(String putanja,String s) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(putanja+".txt"));
	    writer.append(s);
	    writer.close();
	}
}
