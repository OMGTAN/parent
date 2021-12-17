package org.example;

import java.util.ArrayList;
import java.util.List;

class RLEIterator {

    private int[][] A ;
    private  int i=0;
    private  int val=0;

    public RLEIterator(int[] encoding) {
        A=new int[encoding.length/2][2];
        for (int j = 0; j < A.length; j++) {
            int a = 0;
            if(j>0){
                a=A[j-1][0];
            }
            A[j][0]=encoding[2*j]+a;
            A[j][1]=encoding[2*j+1];
        }
    }

    public int next(int n) {
        val+=n;
        if(val>A[A.length-1][0]){
            return -1;
        }
        if(val<=A[i][0]){
            return A[i][1];
        }

        int min = i,max=A.length-1;
        while(true){
            int a = (min+max)/2;
            int s = 0;
            if(a>0){
                s=A[a-1][0];
            }
            if(val>s&&val<=A[a][0]){
                i=a;
                return A[a][1];
            }else if(val<=s){
                max = a-1;
            }else{
                min = a+1;
            }
        }
    }

    public static void main(String[] args) {
        RLEIterator rleIterator = new RLEIterator(new int[]{923381016,843,898173122,924,540599925,391,705283400,275,811628709,850,895038968,590,949764874,580,450563107,660,996257840,917,793325084,82});
        int[] a = new int[]{612783106,486444202,630147341,845077576,243035623,731489221,117134294,220460537,794582972,332536150,815913097,100607521,146358489,697670641,45234068,573866037,519323635,27431940,16279485,203970};
        for (int i = 0; i < a.length; i++) {
            int i1 = a[i];
            int next = rleIterator.next(i1);
            System.out.println(next);
        }
    }
}