package projectpossibility;

public class QuickSort{

    public static void qsort(Comparable[] c,int start,int end){
        if(end <= start) return;
        Comparable comp = c[start];
        int i = start,j = end + 1;
        for(;;){
            do i++; while(i<end && c[i].compareTo(comp)<0);
            do j--; while(j>start && c[j].compareTo(comp)>0);
            if(j <= i)   break;
            Comparable tmp = c[i];
            c[i] = c[j];
            c[j] = tmp;
        }
        c[start] = c[j];
        c[j] = comp;
        qsort(c,start,j-1);
        qsort(c,j+1,end);
    }

    public static void qsort(Comparable[] c){
        qsort(c,0,c.length-1);
    }
}
