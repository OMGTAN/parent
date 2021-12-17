package org.example;

import com.sun.deploy.util.StringUtils;
import sun.reflect.generics.tree.Tree;

import java.util.*;

class Solution {

    public int[] sortArrayByParity(int[] nums) {
        int i=0,j=nums.length-1;
        while(i<j){
            while(i<=j){
                if(nums[i]%2==0){
                    i++;
                }else{
                    swap(i,j, nums);
                    break;
                }
            }
            while(j>=i){
                if(nums[j]%2!=0){
                    j--;
                }else{
                    swap(i,j, nums);
                    break;
                }
            }
        }
        return nums;
    }

    private void swap(int i, int j,int[] nums) {
        int tmp = nums[i];
        nums[i]=nums[j];
        nums[j] = tmp;
    }


    public static void main(String[] args) {
        Solution s= new Solution();
//        System.out.println(i);
    }
}



