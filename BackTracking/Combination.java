package BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

//回溯常见优化方法：剪枝
// 77 39 40 216 78 90 401
public class Combination {
    int gn,gk;
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ret=new ArrayList<>();
        Deque<Integer> path=new LinkedList<>();
        gn=n;gk=k;
        combine(1,k, path, ret);
        return ret;
    }
    // 在[i,n] 中选 j个数 形成组合
    public void combine(int i,int j,Deque<Integer> path,List<List<Integer>> ret) {
        if(j==0){
            ret.add(new ArrayList<>(path));
            return;
        }
        
        for(int x=i;x<=gn-j+1;x++){
            path.add(x);
            combine(x+1,j-1, path, ret);
            path.removeLast();
        }

    }
    //
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Deque<Integer> path=new LinkedList<>();
        List<List<Integer>> ret=new ArrayList<>();
        combinationSum(candidates, target, 0, 0,path, ret);
        return ret;
    }
    //在 candidates[start,size-1]中寻找目标值
    public void combinationSum(int[] candidates, int target,int curSum,int start,Deque<Integer> path,List<List<Integer>> ret) {
        if(curSum==target){
            ret.add(new ArrayList<>(path));
            return;
        }
        if(curSum>target)
            return;
        for(int i=start;i<candidates.length;i++){
            path.add(candidates[i]);
            combinationSum(candidates, target, curSum+candidates[i],i, path, ret);//不重复的限制条件
            path.removeLast();
        }
        
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Deque<Integer> path=new LinkedList<>();
        List<List<Integer>> ret=new ArrayList<>();
        if(candidates.length==0)
            return ret;
        Arrays.sort(candidates);
        combinationSum2(candidates, target,0,path,ret);
        return ret;
    }
    //在 candidates[start,size-1]中寻找目标值
    //检测重复的值：排序数组
    public void combinationSum2(int[] candidates, int target,int start,Deque<Integer> path,List<List<Integer>> ret) {
        // System.out.println(path.toString());
        if(target==0){
            ret.add(new ArrayList<>(path));
            return;
        }
        for(int i=start;i<candidates.length;i++){
            if(target<candidates[i])
                break;//大剪枝
            if(i>start&&candidates[i]==candidates[i-1])
                continue;//小剪枝
            path.add(candidates[i]);
            combinationSum2(candidates, target-candidates[i],i+1, path, ret);//传入的是i+1不是start+1
            path.removeLast();
            
        }
    }
    public List<List<Integer>> combinationSum3(int k, int n) {
        Deque<Integer> path=new LinkedList<>();
        List<List<Integer>> ret=new ArrayList<>();
        combinationSum3(1,k,n,path,ret);
        return ret;
    }

    public void combinationSum3(int start,int select,int target,Deque<Integer> path,List<List<Integer>> ret) {
        if(select==0){
            if(target==0)
                ret.add(new ArrayList<>(path));
            return;
        }
        for(int i=start;i<=9;i++){
            if(target<i)
                break;//大剪枝
            path.add(i);
            combinationSum3(i+1,select-1, target-i,path, ret);
            path.removeLast();
        }

    }
    //nums 中的所有元素 互不相同
    public List<List<Integer>> subsets(int[] nums) {
        Deque<Integer> path=new LinkedList<>();
        List<List<Integer>> ret=new ArrayList<>();
        ret.add(new ArrayList<>(path));
        subsets(nums, 0, path, ret);
        return ret;
    }
    void subsets(int[] nums,int start,Deque<Integer> path,List<List<Integer>> ret ){
        
        for(int i=start;i<nums.length;i++){
            path.add(nums[i]);
            ret.add(new ArrayList<>(path));
            subsets(nums, i+1, path, ret);
            path.removeLast();
            
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Deque<Integer> path=new LinkedList<>();
        List<List<Integer>> ret=new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(nums, 0, path, ret);
        return ret;
    }
    //[start,szie-1]的子集
    void subsetsWithDup(int[] nums,int start,Deque<Integer> path,List<List<Integer>> ret ){
        ret.add(new ArrayList<>(path));
        for(int i=start;i<nums.length;i++){
            if(i>start&&nums[i]==nums[i-1])
                continue;
            path.add(nums[i]);
            subsetsWithDup(nums, i+1, path, ret);
            path.removeLast();
            
        }
    }

    public List<String> readBinaryWatch(int num) {
        Deque<Integer> path=new LinkedList<>();
        List<List<Integer>> ret=new ArrayList<>();
        readBinaryWatch(num, 0, path, ret);
        
        System.out.println(ret.toString());
        List<String> res=new ArrayList<String>();
        for(List<Integer> bits:ret){
            String time=tran2Time(bits);
            if(!time.equals("")){
                System.out.println(bits.toString()+"->"+time);
                res.add(time);
            }
                
        }
        Collections.sort(res);
        return res;
    }
    String tran2Time(List<Integer> bits){
        int hour=0,minute=0;
        for(int bit:bits){
            if(bit<=3){
                hour+=1<<bit;
                if(hour>11)
                    return "";
            }
            else{
                minute+=1<<(bit-4);
                if(minute>59)
                    return "";
            }
        }
        String hString=String.valueOf(hour),mString=minute<10?"0"+String.valueOf(minute):String.valueOf(minute); 
        return hString+":"+mString;
    }
    //在[0..9] 中选num位数 ,代表 哪几位亮着
    //0~3 表示小时 4-9 表示
    public void readBinaryWatch(int num,int start,Deque<Integer> path,List<List<Integer>> ret ) {
        if(num==0){
            ret.add(new ArrayList<>(path));
            return;
        }
        for(int i=start;i<=9;i++){
            path.add(i);
            readBinaryWatch(num-1, i+1, path, ret);
            path.removeLast();
        }
    }
    public static void main(String[] args) {
        Combination c=new Combination();
        List<String> ret=c.readBinaryWatch(9);
        
        System.out.println(ret.toString());
    }
}
