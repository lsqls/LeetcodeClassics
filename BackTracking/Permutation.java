package BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

//46 47
//排列问题，把递归树画出来可以可以帮助理解分析
//在搜索的回溯过程中，有些变量需要手动恢复
//画递归树的时候可以把辅助变量标出来
public class Permutation {


    //46 不含重复元素的排列,直接用布尔数组剪枝
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ret=new ArrayList<>();
        if(nums.length==0)
            return ret;
        Deque<Integer> path=new LinkedList<>();
        boolean[] isUsed=new boolean[nums.length];
        
        permute(nums, 0, path, ret, isUsed);
        System.out.println(ret.toString());
        return ret;

    }
    
    public void permute(int[] nums,int index,Deque<Integer> path,List<List<Integer>> ret,boolean[] isUsed) {

        if(path.size()==nums.length){
            ret.add(new ArrayList<>(path));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(isUsed[i])
                continue;//剪枝
            path.addLast(nums[i]);
            isUsed[i]=true;
            permute(nums, index+1, path, ret, isUsed);
            isUsed[i]=false;
            path.removeLast();
        }



    }
    //47 含重复数组的排列，先排序,使用从左往右第一个未被填过的数字 剪枝
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ret=new ArrayList<>();
        if(nums.length==0)
            return ret;
        Arrays.sort(nums);
        Deque<Integer> path=new LinkedList<>();
        boolean[] isUsed=new boolean[nums.length];
        
        permuteUnique(nums, 0, path, ret,isUsed);
        System.out.println(ret.toString());
        return ret;

    }
    
    public void permuteUnique(int[] nums,int index,Deque<Integer> path,List<List<Integer>> ret,boolean[] isUsed) {

        if(path.size()==nums.length){
            ret.add(new ArrayList<>(path));
            return;
        }
       
        for(int i=0;i<nums.length;i++){
            if (isUsed[i] || (i > 0 && nums[i] == nums[i - 1] && !isUsed[i - 1])) {
                continue;//保证每次都是拿从左往右第一个未被填过的数字
            }
            //图解 https://pic.leetcode-cn.com/1605408511-mPmGWf-47.%E5%85%A8%E6%8E%92%E5%88%97II1.png
            path.addLast(nums[i]);
            isUsed[i]=true;
            permuteUnique(nums, index+1, path, ret,isUsed);
            isUsed[i]=false;
            path.removeLast();
        }
    }
    public static void main(String[] args) {
        Permutation pt=new Permutation();
        int[] nums={1,1,2};
        pt.permuteUnique(nums);
    }

    
}
