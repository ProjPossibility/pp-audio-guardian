package projectpossibility;

public class DFTpair implements Comparable{
	private double amp,freq;
	
	public DFTpair(double f, double a)
	{
		amp = a;
		freq = f;
	}
	
	public double getAmp() {
		return amp;
	}
	
	public void setAmp(double amp) {
		this.amp = amp;
	}
	
	public double getFreq() {
		return freq;
	}
	
	public void setFreq(double freq) {
		this.freq = freq;
	}
	
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		DFTpair temp = (DFTpair)arg0;
		if(this.amp > temp.getAmp())
			return 1;
		else if (this.amp == temp.getAmp())
			return 0;
		else 
			return -1;
	}
}