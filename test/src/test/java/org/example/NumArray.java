package org.example;

class NumArray {

    int[] a ;
    public NumArray(int[] nums) {
        a=nums;
    }
    
    public int sumRange(int left, int right) {
        int count =0;
        for (int i = left; i <=right ; i++) {
            count+=a[i];
        }
        return count;
    }
}