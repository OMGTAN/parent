package com.tan.test;

import java.lang.instrument.ClassDefinition;
import java.util.Arrays;
import java.util.Comparator;

public class SortUtil {

    static  int heapLength;

    public static void heapSort(int[] a){
        heapLength = a.length;
        buildMaxHeap(a, a.length-1);

        for(int i = a.length-1;i>= 0;i--){
            swap(a, 0, i);
            heapLength--;
            heapify(a, 0);
        }

    }

    private static void buildMaxHeap(int[] a, int last) {
        for(int i=(last-1)/2;i>=0;i--){
            heapify(a, i);
        }
    }

    private static void heapify(int[] a, int i) {
        int left = 2*i+1;
        int right = 2*i+2;
        int largest = i;
        if(right<heapLength&& a[right]>a[largest]){
            largest=right;
        }
        if(left<heapLength&& a[left]>a[largest]){
            largest=left;
        }
        if(largest!=i){
            swap(a, largest, i);
            heapify(a, largest);
        }
    }

    public static void fastSort(int[] a, int start, int end){
        if(start>=end){
            return;
        }
        int mid = (start+end)/2;
        int left=start,right=end;
        while(left< right){
            while(left <mid){
                if(a[left] > a[mid]){
                    swap(a, left, mid);
                    mid=left;
                    break;
                }else{
                    left++;
                }
            }
            while(mid< right){
                if(a[right] < a[mid]){
                    swap(a, right, mid);
                    mid=right;
                    break;
                }else{
                    right--;
                }
            }


        }
        fastSort(a, start,mid-1);
        fastSort(a, mid+1,end);
    }

    private static void swap(int[] a, int right, int mid) {
        int tmp = a[right];
        a[right]=a[mid];
        a[mid]=tmp;
    }


    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int pointer = low;
        for (int i = low; i < high; i++) {
            if (array[i] <= pivot) {
                int temp = array[i];
                array[i] = array[pointer];
                array[pointer] = temp;
                pointer++;
            }
            System.out.println(Arrays.toString(array));
        }
        int temp = array[pointer];
        array[pointer] = array[high];
        array[high] = temp;
        return pointer;
    }
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int position = partition(array, low, high);
            quickSort(array, low, position - 1);
            quickSort(array, position + 1, high);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 2, 45,67,8,9};
//        heapSort(a);

        Arrays.sort(a, new Comparator<Integer>(){
            public int compare(Integer a , Integer b){
                return (int)(-a+b);
            }
        });

        for (int i : a) {
            System.out.print(i +" ");
        }
    }

}
