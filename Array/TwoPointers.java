package Array;

import java.util.Arrays;
import java.util.HashSet;

//对撞指针 167 125 344 345 11
public class TwoPointers {
    //167 两数之和 
    public int[] twoSum(int[] numbers, int target) {
        int left=0,right=numbers.length-1;
        int[] ret=new int[2];
        Arrays.fill(ret, -1);
        while(left<right){
            //两个指针不能碰撞
            int sum=numbers[left]+numbers[right];
            if(sum==target){
                ret[0]=left;
                ret[1]=right;
                break;
            }
            else if(sum>target){
                right--;
            }
            else{
                //sum<target
                left++;
            }

                
        }

        return ret;
    }
    //125 回文字符串
    public boolean isPalindrome(String s) {
        boolean ret=true;
        // s=s.toLowerCase();
        int left=0,right=s.length()-1;
        while(left<right){
            //两个指针不能碰撞
            char leftchar=Character.toLowerCase(s.charAt(left)),rightchar=Character.toLowerCase(s.charAt(right));
            if(!Character.isAlphabetic(leftchar)&&!Character.isDigit(leftchar)){
                left++;
                continue;
            }
            if(!Character.isAlphabetic(rightchar)&&!Character.isDigit(rightchar)){
                right--;
                continue;
            }            
            if(rightchar!=leftchar){
                ret=false;
                break;
            }
            left++;
            right--;
        }

        return ret;

    }
    //344 翻转字符串 简单
    public void reverseString(char[] s) {
        int left=0,right=s.length-1;
        while(left<right){
            char temp=s[left];
            s[left]=s[right];
            s[right]=temp;
            left++;
            right--;
        }

    }
    //345 交换元音
    public String reverseVowels(String s) {
        
        Character[] vowelsArray={'a','e','i','o','u'};
        HashSet<Character> vowels=new HashSet<>(Arrays.asList(vowelsArray));
        
        char[] chs=s.toCharArray();
        int left=0,right=chs.length-1;

        while(left<right){
            //两个指针不能碰撞
            char leftchar=Character.toLowerCase(s.charAt(left)),rightchar=Character.toLowerCase(s.charAt(right));
            if(!vowels.contains(leftchar)){
                left++;
                continue;
            }
            if(!vowels.contains(rightchar)){
                right--;
                continue;
            }            
            char temp=chs[left];
            chs[left]=chs[right];
            chs[right]=temp;
            left++;
            right--;
            
        }
        return String.valueOf(chs);
    }
    //11盛水 移动矮的一方
    public int maxArea(int[] height) {
        int max=0;
        int left=0,right=height.length-1;
        while(left<right){
            int area=(right-left)*Math.min(height[left], height[right]);
            max=Math.max(max, area);
            //关键点：理解抛弃短板是安全的。
            if(height[left]<height[right]){
                left++;
            }
            else{
                right--;
            }
            
        }
        return max;
    } 
    public static void main(String[] args) {
        TwoPointers tp=new TwoPointers();
        // String s="A man, a plan, a canal: P anama";
        int[] height={1,8,6,2,5,4,8,3,7};
        int ret=tp.maxArea(height);
        System.out.println(ret);
        
    }
}
