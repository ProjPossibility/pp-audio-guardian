// Discrete Fourier Transform
// analyzes frequencies and corresponding amplitude in sound sample
// concept source: http://jvalentino2.tripod.com/dft/index.html
public class DFT {
	
	// output pairs of freq/amp doubles
	public double[] transform(byte[] data){
		//this array is the value of the signal at time i*h

		int n = (data.length)/2;
		int x[] = new int[n];
		//convert each pair of byte values from the byte array to an Endian value
		for (int i = 0; i < n*2; i+=2) {
			int b1 = data[i];
			int b2 = data[i + 1];
			if (b1 < 0) b1 += 0x100;
			if (b2 < 0) b2 += 0x100;
			int value;
			//Store the data based on the original Endian encoding format
			value = b1 + (b2 << 8);
			x[i/2] = value;
		}
		double f[] = new double[n/2];
		int index = 0;
		double[] output = new double[n+1];
		for (int j = 0; j < n/2; j++) {

			double firstSummation = 0;
			double secondSummation = 0;

			for (int k = 0; k < n; k++) {
		     		double twoPInjk = ((2 * Math.PI) / n) * (j * k);
		     		firstSummation +=  x[k] * Math.cos(twoPInjk);
		     		secondSummation += x[k] * Math.sin(twoPInjk);
			}

		        f[j] = Math.abs( Math.sqrt(Float11.pow(firstSummation,2) + 
		        Float11.pow(secondSummation,2)) );
		    
		    //Calculate amplitude at specific frequency
			double amplitude = 2 * f[j]/n;
			
			//Calculate the length in seconds of the sample
			float T = (float) (((data.length)/2)*8000);
			
			//Calculate the time interval at each equidistant point
			float h = (T / n);
			
			//Calculate frequency tested
			double frequency = j * h / T * 8000;
			
			//Store pairs into output array
			output[index]=frequency;
			output[index+1]=amplitude;
			
			//increment array
			index+=2;
		}
		// return
		return output;
	}
}