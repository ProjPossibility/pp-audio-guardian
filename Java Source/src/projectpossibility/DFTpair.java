package projectpossibility;
/**
 * DFTpair stores an amplitude and frequency like coordinates of a wave in
 * the frequency domain
 * @author Team AG
 *
 */
public class DFTpair implements Comparable{
	private double amp,freq;
/**
 * The constructor for the DFTpair takes in parameters frequency and amplitude, 
 * denoted by f and a.
 * @param f frequency calculated
 * @param a amplitude from original wave form
 */
	public DFTpair(double f, double a)
	{
		amp = a;
		freq = f;
	}
/**  
 * @return the stored value of amplitude of DFT pair.
 */
	public double getAmp() {
		return amp;
	}
/**
 * Sets the pair's amplitude to the value of amp.
 * @param amp desired amplitude value
 */
	public void setAmp(double amp) {
		this.amp = amp;
	}
/**
 * @return stored value of frequency for the pair.
 */
	public double getFreq() {
		return freq;
	}
/**
 * Sets the pair's frequency to the desired value	
 * @param freq desired value of frequency
 */
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