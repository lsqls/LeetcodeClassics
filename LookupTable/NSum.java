package LookupTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


//1  15 18  454 16 
public class NSum {
    //1  2sum 使用hashmap 逐个加入，也可以先排序，然后使用碰撞指针
    public int[] twoSum(int[] nums, int target) {
        int[] ret=new int[2];
        HashMap<Integer,Integer> order=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(order.containsKey(target-nums[i])){
                ret[0]=i;
                ret[1]=order.get(target-nums[i]);
                break;
            }
            order.put(nums[i], i);
        }
        return ret;
    }
    //15 3sum 排序，双指针
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ret=new LinkedList<>();
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        for(int i=0;i<=nums.length-3;i++){
            if(nums[i]>0)
                break;
            //相同数字，只对第一个数字操作
            if(i>0&&nums[i-1]==nums[i])
                continue;
            int start=i+1,end=nums.length-1,twoSum=-nums[i];
            //求twosum
            int [] pre={nums[i]};
            getTwoSum(nums,pre,start,end,twoSum,ret);
        }
        return ret;
    }
    //在有序数组求解TwoSum的多个不重复解
    void getTwoSum(int[] nums,int[] pre,int start,int end,int target,List<List<Integer>> list){
        int left=start,right=end;
        while(left<right){
            int sum=nums[left]+nums[right];
            if(sum==target){
                List<Integer> iList=new LinkedList<>();
                for(int val:pre)
                    iList.add(val);
                iList.add(nums[left]);
                iList.add(nums[right]);
                list.add(iList);
                //有可能有多个解，不能break
                while(left<right&&nums[left]==nums[left+1]) 
                    left++;
                while(left<right&&nums[right]==nums[right-1])
                    right--;
                //跳过重复解
                left++;
                right--;
            }
            else if(sum>target)
                right--;
            else
                left++;
        }   
    }
    
    //[1,-2,-5,-4,-3,3,3,5]
    //-11
    //18
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ret=new LinkedList<>();
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        for(int i=0;i<=nums.length-4;i++){
            if(i>0&&nums[i-1]==nums[i])
                continue;
            //求3sum
            for(int j=i+1;j<=nums.length-3;j++){
                if(nums[j]>target-nums[i])
                    break;
                if(j>i+1&&nums[j-1]==nums[j])
                    continue;
                int start=j+1,end=nums.length-1,twoSum=target-nums[i]-nums[j];
                //求2sum
                int [] pre={nums[i],nums[j]};
            	getTwoSum(nums,pre,start,end,twoSum,ret);
            }
        }
        
        return ret;
    }
    //464
    //两两一组进行处理
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        HashMap<Integer,Integer> iMap=new HashMap<>();
        for(int valC:C){
            for(int valD:D){
                int cdSum=valC+valD;
                iMap.put(cdSum, iMap.getOrDefault(cdSum,0)+1);
            }
        }
        int cnt=0;
        for(int valA:A){
            for(int valB:B){
                cnt+=iMap.getOrDefault(-valA-valB, 0);
            }
        }
        return cnt;
    }
    //16 和3sum差不多
    //这个版本写得比较简洁，之前的两个也可以根据版本这个改简洁些
    public int threeSumClosest(int[] nums, int target) {
        // 排序
        Arrays.sort(nums);
        int closestNum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int l = i + 1, r = nums.length - 1;
            while (l < r){
                int threeSum = nums[l] + nums[r] + nums[i];
                if (Math.abs(threeSum - target) < Math.abs(closestNum - target)) {
                    closestNum = threeSum;
                }
                if (threeSum > target) {
                    r--;
                } else if (threeSum < target) {
                    l++;
                } else {
                    // 如果已经等于target的话, 肯定是最接近的
                    return target;
                }

            }

        }
        return closestNum;
    }
    public static void main(String[] args) {
        NSum ns=new NSum();
        int[] nums={1,-2,-5,-4,-3,3,3,5};
        List<List<Integer>> ret=ns.fourSum(nums,-11);
        System.out.println(ret.toString());
    }
}
