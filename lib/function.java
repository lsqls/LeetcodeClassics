package lib;

import java.util.*;

public class function {
    //[min,max)
    static public int[] generateTestArray(int length,int min,int max){
        
        if(length<1)
            length=(int)(Math.random()*0+100);
        
        int[] ret = new int[length];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = (int)(Math.random()*(max-min))+min;
        }
        System.out.println("TestData:"+Arrays.toString(ret));
        return ret;
    }
    static public void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
