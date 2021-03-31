package Array;

import java.util.Arrays;

import lib.function;

//75 88 215
public class Partition {
    //75 计数排序或者三路快排
    public void sortColors(int[] nums) {
        // int[] cnt=new int[3];
        // for(int val:nums){
        //     cnt[val]++;
        // }
        // int start=0;
        // //记录 第一个放 0 第一个放 1 第一个放 2 的位置
        // for(int j=0;j<cnt.length;j++){
        //     int i=0;
        //     while(i<cnt[j]){
        //         nums[i+start]=j;
        //         i++;
        //     }
        //     start+=cnt[j];
        // }



        // partiton3Ways
            //[0...zero] 存放 0  [two...nums.length-1]存放2,中间放1，检查i,维持循环不变量
        int  zero=-1,two=nums.length;
        int i=0;
        while(i<two){
            if(nums[i]==1)
                i++;
            else if(nums[i]==0){
                function.swap(nums, i++, ++zero);
            }
            else{
                //num[i]==2
                function.swap(nums, i, --two);
            }
        }

        
    }
    //88 指针碰撞
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //index1 index2 用来历遍nums1 和nums2
        int index1=m-1,index2=n-1;
        //[k...m+n-1]是成功合并的子数组
        int k=m+n;
        while(index1>=0&&index2>=0){
            if(nums1[index1]>nums2[index2])
                nums1[--k]=nums1[index1--];
            else
                nums1[--k]=nums2[index2--];
        }
        //不用考虑nums1是因为合并后的数组就是nums1
        while(index2>=0){
            nums1[--k]=nums2[index2--];
        }
        
    }
    
    //215 经典 kth 使用partition求解
    public int findKthLargest(int[] nums, int k) {
        //排序后第n大的
        int findIndex=k-1;
        int start=0,end=nums.length-1;
        
        while(start<=end){
            int correctIndex=partition(nums, start, end);
            if(correctIndex==findIndex)
                return nums[correctIndex];
            else if(correctIndex>findIndex)
                end=correctIndex-1;
            else
                start=correctIndex+1;
        }
        return -1;
            
    }
    //维持[start+1,large]>cmp
    public int partition(int[] arr,int start,int end){
        function.swap(arr, start, (int)(Math.random()*(end-start+1))+start);
        int cmp=arr[start];
        int large=start;
        for(int i=start+1;i<=end;i++){
            if(arr[i]>cmp)
                function.swap(arr, ++large, i);
        }
        //在这之前维持[start+1,large]>cmp

        function.swap(arr, start, large);
        return large;
    }
    public int partition2(int[] arr,int start,int end){
        function.swap(arr, start, (int)(Math.random()*(end-start+1))+start);//这里容易出现bug,可以嫌麻烦可以不随机取值
        
        int cmp=arr[start];


        int i=start+1,j=end;
        //存在大量重复元素，可以使用双路快排，重复的比较元素大概率均分在两侧，使得快排的平衡性得以维持
        //维持arr[start+1...i)> =cmp 和arr(j...end]<=cmp         
        while(true){
            //倒序，
            while(i<=end&&arr[i]>cmp)//使用方括号访问数组元素必须检查有效性
                i++;
            while(j>=start+1&&arr[j]<cmp)
                j--;
            if(i>j)
                break;
            function.swap(arr, i, j);
            i++;
            j--;

        }
        function.swap(arr, start, j);
        return j;
    }

    
    public static void main(String[] args) {
        int[] testData=function.generateTestArray(10, 0, 100);
        // int[] testData1={1,2,3,0,0,0},testData2={2,5,6};
        Partition p=new Partition();
        int ret=p.findKthLargest(testData,1);
        
        System.out.println(ret);
        Arrays.sort(testData);
        System.out.println(Arrays.toString(testData));
    }
}
