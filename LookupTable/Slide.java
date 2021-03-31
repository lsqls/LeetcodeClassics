package LookupTable;
//219 217 220

import java.util.HashMap;
import java.util.HashSet;

public class Slide {
    //219 可以设立滑动窗口，也可以保存上一个值索引
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // if(k<=0)
        //     return false;
        // HashSet<Integer> iSet=new HashSet<>();
        // int left=0,right=-1;
        
        // while(right+1<nums.length){
        //     if(iSet.contains(nums[right+1]))
        //         return true;
        //     else{
        //         if(right-left+1>=k)
        //             iSet.remove(nums[left++]);
        //         iSet.add(nums[++right]);

        //     }
        // }
        // return false;
        HashMap<Integer,Integer> iiMap=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(iiMap.containsKey(nums[i])&&(i-iiMap.get(nums[i]))<=k)
                return true;
            iiMap.put(nums[i], i);
        }
        return false;

    }
    //217 是否存在重复元素 ,219的简化版本
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> iSet=new HashSet<>();
        for(int val:nums){
            if(iSet.contains(val))
                return true;
            iSet.add(val);
        }
        return false;

    }
    //220 219的变体,比较有意思的是根据t或者k的取值，选择查找的方式，最好的方法是使用桶排序（任何不在【同一个桶或相邻桶】的两个元素之间的距离一定是大于 tt 的。）
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(k<=0||t<0)
            return false;
        HashSet<Long> iSet=new HashSet<>();
        int left=0,right=-1;
        
        while(right+1<nums.length){
            long curval=nums[right+1];
            //两种查找方法，时间复杂度降为o（min(k,t)*len)
            if(k>t){
                //如果滑动窗口值k大于差的绝对值t。绝对值i从0开始递增到t，在滑动窗口中搜索curval+i和cuval-i
                for(long i=0;i<=t;i++){
                if(iSet.contains(curval+i)||iSet.contains(curval-i))
                    return true;
                }
            }
            else{
                //如果滑动窗口值大于要比较的绝对值，在遍历滑动窗口中，搜索差的绝对值小于t的元素。
                for(long val:iSet){
                    if(Math.abs(curval-val)<=t)
                        return true;
                }
            }
            if(right-left+1>=k)
                iSet.remove((long)nums[left++]);
            iSet.add((long)nums[++right]);
        }
        return false;
    }
    
    public static void main(String[] args) {
        Slide sl=new Slide();
        
        int[] nums={2433,3054,9952,6470,2509,8502,-8802,-5276,6559,-9555,-4765,6399,6543,2027,1723,-3032,-3319,-7726,-1425,-7431,-7492,4803,7952,-6603,-784,-8011,6161,-6955,5800,5834,-6310,1097,2327,-4007,8664,-9619,5922,518,9740,9976,4257,-7803,6023,4189,-5167,-4699,2441,5941,3663};
        boolean ret=sl.containsNearbyAlmostDuplicate(nums, 10000,0);
        System.out.println(ret);

    }
}
