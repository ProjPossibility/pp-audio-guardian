
public class SoundProcessor {
	// TestCxfft.java
	class TestCxfft
	{
	  public static void main(String[] args)
	  {
	    System.out.println("TestCxfft.java running");
	    int n = 32;
	    double A[] = new double[n];
	    double B[] = new double[n];
	    double C[] = new double[n];
	    double a[] = new double[n/2]; // for convolution
	    double b[] = new double[n/2]; // for convolution
	    double c[] = new double[n/2]; // for convolution
	    double th, v;

	    for(int i=0; i<n/2; i++) /* sin 2 th  other coefficients zero */
	    {
	      th = (2.0*2.0*Math.PI)*(double)i/(double)n;
	      A[2*i] = Math.sin(th);
	      A[2*i+1] = 0.0;
	    }
	    System.out.println("sin 2 th data");
	    for(int i=0; i<n/2; i++)
	      System.out.println("A["+i+"] real="+A[2*i]+", imag="+A[2*i+1]);

	    B = Cxfft.fft(A, 1.0);
	    System.out.println("sin 2 th, FFT transform");
	    for(int i=0; i<n/2; i++)
	      System.out.println("B["+i+"] cos="+B[2*i]+", sin="+B[2*i+1]);
	    System.out.println(" ");


	    for(int i=0; i<n/2; i++) /* square wave 4/Pi(cos th - 1/3 cos 3 th + */
	                             /*  1/5 cos 5 th - 1/7 cos 7 th */
	    {
	      v = 1.0;
	      if(i>=n/8 && i<3*n/8) v = -1.0;
	      if(i==n/8 || i==3*n/8) v = 0.0;
	      A[2*i] = v;
	      A[2*i+1] = 0.0;
	    }
	    System.out.println("square wave data");
	    for(int i=0; i<n/2; i++)
	      System.out.println("A["+i+"] real="+A[2*i]+", imag="+A[2*i+1]);

	    System.out.println("square wave generate");
	    for(int i=0; i<n/2; i++)
	    {
	      th = 2.0*Math.PI*(double)i/(double)(n/2);
	      v = (4.0/Math.PI)*(Math.cos(th) - Math.cos(3.0*th)/3.0
	           + Math.cos(5.0*th)/5.0 - Math.cos(7.0*th)/7.0
	           + Math.cos(9.0*th)/9.0 - Math.cos(11.0*th)/11.0
	           + Math.cos(13.0*th)/13.0 - Math.cos(15.0*th)/15.0
	           + Math.cos(17.0*th)/17.0);
	      System.out.println("V["+i+"] real ="+v) ;
	    }

	    B = Cxfft.fft(A, 1.0);
	    System.out.println("square wave, FFT transform");
	    for(int i=0; i<n/2; i++)
	      System.out.println("B["+i+"] cos="+B[2*i]+", sin="+B[2*i+1]);
	    System.out.println(" ");

	    C = Cxfft.fft(B, -1.0);
	    System.out.println("square wave, FFT inverse transform");
	    for(int i=0; i<n/2; i++)
	      System.out.println("C["+i+"] real="+C[2*i]+", imag="+C[2*i+1]);
	    System.out.println(" ");

	    for(int i=0; i<n/2; i++) /* triangle wave 8/Pi^2(cos th + 1/9 cos 3 th + */
	                             /*    1/25 cos 5 th */
	    {
	      v = 1.0-4.0*(double)i/(double)(n/2); /* not exactly balanced */
	      if(i>=n/4) v = -3.0+4.0*(double)i/(double)(n/2);
	      A[2*i] = v;
	      A[2*i+1] = 0.0;
	    }
	    System.out.println("triangle wave data");
	    for(int i=0; i<n/2; i++)
	      System.out.println("A["+i+"] real="+A[2*i]+", imag="+A[2*i+1]);

	    System.out.println("triangle wave generate");
	    for(int i=0; i<n/2; i++)
	    {
	      th = 2.0*Math.PI*(double)i/(double)(n/2);
	      v = (8.0/(Math.PI*Math.PI))*(
	           Math.cos(th) + Math.cos(3.0*th)/9.0 + Math.cos(5.0*th)/25.0 +
	           Math.cos(7.0*th)/49.0 + Math.cos(9.0*th)/81.0 +
	           Math.cos(11.0*th)/121.0 + Math.cos(13.0*th)/169.0 +
	           Math.cos(15.0*th)/225.0 + Math.cos(17.0*th)/289.0 );
	      System.out.println("V["+i+"] real ="+v) ;
	    }

	    B = Cxfft.fft(A, 1.0);
	    System.out.println("triangle wave, FFT transform");
	    for(int i=0; i<n/2; i++)
	      System.out.println("B["+i+"] cos="+B[2*i]+", sin="+B[2*i+1]);
	    System.out.println(" ");


	    for(int i=0; i<n/2; i++) /* saw tooth wave 2/Pi(sin th - 1/2 sin 2 th + */
	                             /*    1/3 sin 3 th - 1/4 sin 4 th */
	    {
	      v = 2.0*(double)i/(double)(n/2); /* not exactly balanced */
	      if(i>=n/4) v = -2.0+2.0*(double)i/(double)(n/2);
	      if(i==n/4) v = 0.0;
	      A[2*i] = v;
	      A[2*i+1] = 0.0;
	    }
	    System.out.println("saw tooth wave data");
	    for(int i=0; i<n/2; i++)
	      System.out.println("A["+i+"] real="+A[2*i]+", imag="+A[2*i+1]);
	    System.out.println("sawtooth wave generate");
	    for(int i=0; i<n/2; i++)
	    {
	      th = 2.0*Math.PI*(double)i/(double)(n/2);
	      v =(3.0/Math.PI)*(Math.sin(th) - Math.sin(2.0*th)/2.0
	          + Math.sin(3.0*th)/3.0 - Math.sin(4.0*th)/4.0
	          + Math.sin(5.0*th)/5.0 - Math.sin(6.0*th)/6.0
	          + Math.sin(7.0*th)/7.0 - Math.sin(8.0*th)/8.0
	          + Math.sin(9.0*th)/9.0 - Math.sin(10.0*th)/10.0
	          + Math.sin(11.0*th)/11.0 - Math.sin(12.0*th)/12.0
	          + Math.sin(13.0*th)/13.0 - Math.sin(14.0*th)/14.0
	          + Math.sin(15.0*th)/15.0 - Math.sin(16.0*th)/16.0
	          + Math.sin(17.0*th)/17.0 - Math.sin(18.0*th)/18.0
	          + Math.sin(19.0*th)/19.0 - Math.sin(20.0*th)/20.0);
	      System.out.println("V["+i+"] real ="+v) ;
	    }

	    B = Cxfft.fft(A, 1.0);
	    System.out.println("sawtooth wave, FFT transform");
	    for(int i=0; i<n/2; i++)
	      System.out.println("B["+i+"] cos="+B[2*i]+", sin="+B[2*i+1]);
	    System.out.println(" ");

	    System.out.println("set up convolution test");
	    for(int i=0; i<n/2; i++) /* square wave 4/Pi(cos th - 1/3 cos 3 th + */
	                             /*  1/5 cos 5 th - 1/7 cos 7 th */
	    {
	      v = 1.0;
	      if(i>=n/8 && i<3*n/8) v = -1.0;
	      if(i==n/8 || i==3*n/8) v = 0.0;
	      a[i] = v;
	    }
	    System.out.println("square wave data");
	    for(int i=0; i<n/2; i++)
	      System.out.println("a["+i+"] real="+a[i]);

	    for(int i=0; i<n/2; i++) /* triangle wave 8/Pi^2(cos th + 1/9 cos 3 th + */
	                             /*    1/25 cos 5 th */
	    {
	      v = 1.0-4.0*(double)i/(double)(n/2); /* not exactly balanced */
	      if(i>=n/4) v = -3.0+4.0*(double)i/(double)(n/2);
	      b[i] = v;
	    }
	    System.out.println("triangle wave data");
	    for(int i=0; i<n/2; i++)
	      System.out.println("b["+i+"] real="+b[i]);


	    System.out.println("classic convolution of a with b");
	    c = conv(a, b);
	    System.out.println("classic convolution of a with b output");
	    for(int i=0; i<n/2; i++)
	      System.out.println("c["+i+"] real="+c[i]);

	    System.out.println("FFT convolution of a with b");
	    c = Cxfft.fftconv(a, b);
	    System.out.println("FFT convolution of a with b output");
	    for(int i=0; i<n/2; i++)
	      System.out.println("c["+i+"] real="+c[i]);

	    System.out.println("end TestCxfft");
	  } // end main

	  public static double[] conv(double[] a, double[] b)
	  {
	    // standard convolution of a with b returned
	    int n = a.length;
	    int m = b.length;
	    int k = n+m-1;
	    double c[] = new double[k];
	    for(int i=0; i<k; i++) c[i] = 0.0;
	    for(int i=0; i<n; i++)
	    {
	      for(int j=0; j<m; j++)
	      {
		c[i+j] = c[i+j] + a[i]*b[j];
	      }
	    }
	    return c;
	  } // end conv

	  public static double[] fftconv(double[] a, double[] b)
	  {
	    // fft convolution
	    int n = a.length;
	    int m = b.length;
	    if(n != m) { System.out.println("fftconv n!=m"); System.exit(1);}
	    double c[] = new double[n];
	    double AA[] = new double[4*n];
	    double AAT[] = new double[4*n];
	    double BB[] = new double[4*n];
	    double BBT[] = new double[4*n];
	    double CC[] = new double[4*n];
	    double CCT[] = new double[4*n];
	    for(int i=0; i<n; i++)
	    {
	      AA[2*i] = a[i];
	      AA[2*i+1] = 0.0;
	      AA[2*(i+n)] = 0.0;
	      AA[2*(i+n)+1] = 0.0;
	      BB[2*i] = b[i];
	      BB[2*i+1] = 0.0;
	      BB[2*(i+n)] = 0.0;
	      BB[2*(i+n)+1] = 0.0;
	    }
	    AAT = Cxfft.fft(AA, 1.0);
	    BBT = Cxfft.fft(BB, 1.0);
	    for(int i=0; i<2*n; i++)
	    { 
	      CCT[2*i] = AAT[2*i]*BBT[2*i] - AAT[2*i+1]*BBT[2*i+1];
	      CCT[2*i+1] = AAT[2*i]*BBT[2*i+1] + AAT[2*i+1]*BBT[2*i];
	    }
	    CC = Cxfft.fft(CCT, -1.0);
	    for(int i=0; i<n; i++)
	    {
	      c[i] = CC[2*i];
	    }
	    return c;
	  } // end fftconv

	} // end class TestCxfft


}
