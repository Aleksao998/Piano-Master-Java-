	package projekat;
	import java.io.*;
	import java.util.*;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;
	public class Kompozicija {
	public Razlomak r14=new Razlomak(1,4);
	public Razlomak r18=new Razlomak(1,8);
	public List<MuzickiSimbol> ListaSimbola=new ArrayList<MuzickiSimbol>();
	public Map<Integer,String> Map2=new HashMap<Integer,String>();
	
	public String komp="";
	public class MyArray{
	
	public String[] niz=new String[2];
	public MyArray(String a,String b) {
	niz[0]=a;
	niz[1]=b;
	}
	public String getNiz0() {
	return niz[0];
	}
	public String getNiz1() {
	return niz[1];
	}
	public String toString() {
	String txt=""+niz[0]+" "+niz[1];
	
	return txt;
	}
	}
	String putanja;
	String pesma;
	public Map<Character,MyArray> Map= new HashMap<Character,MyArray>();
	//Konstruktor
	public Kompozicija() throws IOException {
		putanja="C:\\Users\\aleks\\Desktop\\PoopProject\\PoopDrugiDomaci\\poop\\map.csv";
		mapa();
		
	}
	
	public void pokreniKompoziciju(String p) throws IOException {
		
		pesma="C:\\Users\\aleks\\Desktop\\PoopProject\\PoopDrugiDomaci\\poop\\"+p+".txt";

		ucitajKompoziciju();
	}
	
	
	
	//Metode Kompozicije
	public void mapa() {
	try {
	
	File file = new File(putanja); 
	
	BufferedReader br = new BufferedReader(new FileReader(file)); 
	 
	String st;
	Pattern pattern = Pattern.compile(",",Pattern.CASE_INSENSITIVE); 
	while ((st = br.readLine()) != null) {
	String[] result = pattern.split(st);
	
	Character key=result[0].charAt(0);
	MyArray obj= new MyArray(result[1],result[2]); 
	Map.put(key,obj);
	Map2.put(Integer.parseInt(result[2]),result[1]);
	
	}
	
	}
	catch (Exception e) {
	
	}
	}
	public void ucitajKompoziciju() throws IOException {
	
	ListaSimbola.clear();

	komp="";

	File file = new File(pesma); 
	String st;
	BufferedReader br = new BufferedReader(new FileReader(file)); 
	while ((st = br.readLine()) != null) {
	komp+=st;
	}
	
	Pattern pattern = Pattern.compile("([^\\[])|(\\[[^\\]]*\\])");
	Matcher m = pattern.matcher(komp);
	while(m.find()) {
	
	if(m.group(1)!=null) {
	//Pauza 1/8
	if(m.group().charAt(0)==' ') {

	MuzickiSimbol simbol=new Pauza(r18);
	ListaSimbola.add(simbol);
	}
	//Pauza 1/4 
	else if(m.group().charAt(0)=='|') {
	
	MuzickiSimbol simbol=new Pauza(r14);
	ListaSimbola.add(simbol);
	}
	//Nota 1/4
	else {
	
	MyArray celaNota=Map.get(m.group().charAt(0));
	
	
	boolean povisena=false;
	
	if(celaNota.niz[0].charAt(1)=='#') {
	
	povisena =true;
	}
	MuzickiSimbol simbol=new Nota(celaNota.niz[0],povisena,celaNota.niz[1],r14);
	ListaSimbola.add(simbol);
	
	}
	}
	else {
	//Akord
	if(m.group().charAt(2)==' ') {
	
	int i=1;
	//Pravi akord
	           	while (m.group().charAt(i)!=']' && i<m.group().length()) {
	           	if(m.group().charAt(i)==' ') {
	           	i++;
	           	continue;
	           	}
	           	MyArray celaNota=Map.get(m.group().charAt(i));
	           
	boolean povisena=false;	
	if(celaNota.niz[0].charAt(1)=='#') {
	povisena =true;
	}
	           	MuzickiSimbol simbol=new Nota(celaNota.niz[0],povisena,celaNota.niz[1],r14);
	           	ListaSimbola.add(simbol);
	           	i++;
	           	}
	        
	
	
	}
	//Note 1/8
	else {

	int i=1;
	Akord akord=new Akord(r14);
	while (m.group().charAt(i)!=']' && i<m.group().length()) {
	MyArray celaNota=Map.get(m.group().charAt(i));
	boolean povisena=false;	
	if(celaNota.niz[0].charAt(1)=='#') {
	povisena =true;
	}
	           	MuzickiSimbol simbol=new Nota(celaNota.niz[0],povisena,celaNota.niz[1],r14);
	           	akord.dodajUAkord(simbol);
	           	i++;
	           	}
	ListaSimbola.add(akord);
	
	
	
	}
	}
	}
	}
	
	public static void main(String[] arg0) throws IOException {
	Kompozicija k=new Kompozicija();
	MidiFile m=new MidiFile();
	m.napraviFajl("C:\\Users\\aleks\\Desktop\\PoopProject\\PoopDrugiDomaci\\poop\\pesma", k);
	TxtFile t=new TxtFile();
	t.napraviFajl("C:\\Users\\aleks\\Desktop\\PoopProject\\PoopDrugiDomaci\\poop\\ispis", k);
	}
	}