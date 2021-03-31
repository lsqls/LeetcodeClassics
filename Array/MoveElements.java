package Array;

import java.util.Arrays;

import lib.function;

//283 27 26 80
public class MoveElements{
    //283
    public void moveZeroes(int[] nums) {
        //cal notZeroNum 计算非0元素，该解法比较直观，很容易想到，由此可以引出第二种交换解法
        // int notZeroNum=0;
        // for(int i=0;i<nums.length;i++){
        //     if(nums[i]!=0){
        //         nums[notZeroNum]=nums[i];
        //         notZeroNum++;
        //     }
        // }
        // for(int i=notZeroNum;i<nums.length;i++){
        //     nums[i]=0;
        // }
        int nextPut=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                if(nextPut==i)
                    nextPut++;
                else
                    function.swap(nums ,nextPut++, i);
            }
        }

    } 
    //27 
    public int removeElement(int[] nums, int val) {

        //和删除0元素一样，将0改为其他值即可
        // int nextPut=0;
        // for(int i=0;i<nums.length;i++){
        //     if(nums[i]!=val){
        //         if(nextPut==i)
        //             nextPut++;
        //         else
        //             function.swap(nums ,nextPut++, i);
        //     }
            
        // }
        //题目返回长度，这时候使用覆盖法更好
        int nextPut=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=val){
                nums[nextPut++]=nums[i];
            }
            
        }

        return nextPut;

    }
    //26 删除排序数组重复元素,注意排序条件
    public int removeDuplicates(int[] nums) {
        //题目返回长度，这时候使用覆盖法更好
        int curIndex=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=nums[curIndex]){     
                nums[++curIndex]=nums[i];
            }
        }    
        return curIndex+1;
    }

    //80 
    public int removeDuplicates2(int[] nums) {
        //覆盖法，使用一个值记录相同值的个数,把第一个第二个相同的数放入合适的位置
        int curVal=nums[0];
        int nextInsertIndex=1;
        int same=1;
        for(int i=1;i<nums.length;i++){
            if(nums[i]!=curVal){
                nums[nextInsertIndex++]=nums[i];
                same=1;
                curVal=nums[i];
            }
            
            else{
                same++;
                if(same==2)
                    nums[nextInsertIndex++]=nums[i];
            }
            
            
        }    
        return nextInsertIndex;
        
        // 巧妙的条件
        // int i = 0;
        // for (int val : nums) {
        //     if (i < 2 || val > nums[i-2]) nums[i++] = val;
        // //    如果重复数字个数不大于2，那么这个判断肯定是成立的
        // //    如果重复个数大于2，那么nums[L-2] < nums[R]就是不成立的
        // //     即多余重复项都被忽视了，直到遇到新的数字或者数字结束

        // }
        // return i;
    }

    


    public static void main(String[] args) {
        MoveElements mv=new MoveElements();
        int[] testData={0,0,1,1,1,2,3,3,3,3,4,4,4};
        int ret=mv.removeDuplicates2(testData);
        System.out.println(ret+Arrays.toString(testData));
    }
}
