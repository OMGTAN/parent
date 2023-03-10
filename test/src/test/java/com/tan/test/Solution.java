package com.tan.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int[] dp = new int[s.length()+1];
        dp[0]=1;
        for(int i=1;i<=s.length();i++){
            for(String st : wordDict ){
                if(dp[i]==0&&i>=st.length()&&s.indexOf(st,i-st.length())==i-st.length()){
                    dp[i]=dp[i-st.length()];
                }
            }
        }

        return dp[s.length()]==1;
    }

    public void moveZeroes(int[] nums) {

        int left=0,right=0;
        while(left<nums.length){
            if(nums[left]==0){
                if(right==nums.length){
                    return;
                }
                if(right<=left){
                    right=left+1;
                }else
                    right++;
                while(right<nums.length){
                    if(nums[right]!=0){
                        int tmp = nums[left];
                        nums[left]=nums[right];
                        nums[right]=tmp;
                        break;
                    }else{

                        right++;
                    }
                }
            }
            left++;

        }
    }

    public static void main(String[] args) {
        Solution solution  =new Solution();

//        String s = "abc";
//        String[] strings = {"leet", "code"};
        int[] s = {0,1,0,3,12};
         solution.moveZeroes(s);
        Arrays.stream(s).forEach(i->
                System.out.print(i+" ")
        );


//        String s="leetcode";
//        String[] strings = {"leet", "code"};
//        List<String> collect = Arrays.stream(strings).collect(Collectors.toList());
//        boolean cars = solution.wordBreak(s, collect);
//        System.out.println(s);
//        System.out.println(collect);
//        System.out.println(cars);


    }

}