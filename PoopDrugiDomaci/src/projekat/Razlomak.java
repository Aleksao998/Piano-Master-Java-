package projekat;

public class Razlomak {
		private int i,b;
		public Razlomak(int br,int im) {
			i=im;
			b=br;
		}
		
		public int getI() {return i;}
		public int getB() {return b;}
		public boolean jednako(Razlomak r1,Razlomak r2) {
			if((r1.getB()==r2.getB()) && (r1.getI()==r2.getI())) return true;
			return false;
		}
		public Razlomak saberiRazloak (Razlomak r1,Razlomak r2) {
			Razlomak r=new Razlomak(r1.getB()*r2.getI()+r2.getB()*r1.getI(),r1.getI()*r2.getI());
			return r;
		}
		public boolean vece(Razlomak r1,Razlomak r2) {
			if(r1.getB()*r2.getI()>r2.getB()*r1.getI()) return true;
			return false;
		}
}
