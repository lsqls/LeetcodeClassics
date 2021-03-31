package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//209 3 438 76
//涉及多指针的画一个表分析最好
public class SlideWindows {
    public int minSubArrayLen(int s, int[] nums) {
        
        // 209 连续正整数子数组 [left...right]，注意正整数条件
        int min=nums.length+1;
        int left=0,right=-1;
        int sum=0;
        while(left<nums.length){
            
            //设定某一状态分析
            if(right+1<nums.length&&sum<s){
                    sum+=nums[++right];
            }
            else{
                sum-=nums[left++];
            }

            if(sum>=s){//满足问题的解
                min=Math.min(min, right-left+1);
            }
        }



        if(min==nums.length+1){
            return 0;
        }
        else
            return min;
    }
    //3 最长无重复字串 滑动窗口解法，加入用动态规划，一定要列表，从f(0) 开始分析
    public int lengthOfLongestSubstring(String s) {
        int[] fre=new int[128];
        int max=0;
        //[left...right]
        int left=0,right=-1;
        
        while(left<s.length()){
            if(right+1<s.length()&&fre[s.charAt(right+1)]==0){
                right++;
                fre[s.charAt(right)]++;
            }
            else{
                fre[s.charAt(left)]--;
                left++;
            }

            max=Math.max(max, right-left+1);//不要写颠倒
            
            
        }
        return max;

    }
    // 438 固定滑动窗口 
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ret=new ArrayList<>();
        if(s.length()<p.length()||p.length()==0)
            return ret;
        int left=0,right=p.length()-1;
        //初始化
        int[] windowsfre=new int[128];
        int[] pfre=new int[128];
        for(int i=left;i<=right;i++){
            windowsfre[s.charAt(i)]++;
        }
        for(int i=0;i<p.length();i++){
            pfre[p.charAt(i)]++;
        }

        while(true){
            if(Arrays.equals(windowsfre, pfre)){
                ret.add(left);
                //使用数组比较,事实上可以使用valid(目前相等字符的个数)==need(需要相等的字符，也就是p的长度) 判断
            }
            
            
            windowsfre[s.charAt(left++)]--;
            if(right==s.length()-1)
                break;
            windowsfre[s.charAt(++right)]++;
            
        }
        return ret;

        
    }
    
    //76 tfre[i]>0相当于hashset的作用，tfre.length为128，所以时间复杂度是O(128n)
    public String minWindow(String s, String t) {
        // if(s.length()<t.length())
        //     return "";
        int left=0,right=-1;
        //初始化 
        int[] windowsfre=new int[128];
        int[] tfre=new int[128];//改为hashmap
        
        int retLeft=0,retRight=-1;//retRight初始化为-1而不是0
        int min=s.length()+1;

        for(int i=0;i<t.length();i++){
            tfre[t.charAt(i)]++;
        }
        while(left<s.length()){
            if(moreChar(windowsfre, tfre)){
                int subLength=right-left+1;
                if(subLength<min){
                    min=subLength;
                    retLeft=left;
                    retRight=right;
                }
                
                windowsfre[s.charAt(left++)]--;
                

            }
            else{
                if(right+1>=s.length())
                    break;
                windowsfre[s.charAt(++right)]++;
            }
        }
        // if(min==s.length()+1)
        //     return "";
        // else{
            return s.substring(retLeft, retRight+1);
        // }
    }
    boolean moreChar(int[] windowsfre,int[] tfre){
        boolean more=true;
        for(int i=0;i<tfre.length;i++){
            if(tfre[i]>0&&tfre[i]>windowsfre[i]){//tfre[i]>0相当于hashset的作用，tfre.length为128，所以时间复杂度仍然是O(n)
                more=false;
                break;
            }
        }
        return more;
    }
    public static void main(String[] args) {
        SlideWindows sl=new SlideWindows();
        // int[] nums={2,3,1,2,4,3};
        // int ret=sl.lengthOfLongestSubstring("bbbb");
        // System.out.println(ret);
        
        // List<Integer> ilist=sl.findAnagrams("cbaebabacd", "ab");
        // System.out.println(ilist.toString());
        String ret=sl.minWindow("a", "aa");
        System.out.println(ret);



    }
}
