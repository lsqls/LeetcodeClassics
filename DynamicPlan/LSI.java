package DynamicPlan;


//300 376
public class LSI {
    //300 最长递增子序列
    //LIS(i)表示已第i个数结尾的最长上升子序列的长度（也就是在[0,i]的范围内，选择数字nums[i]可以获得的最长上升子序列的长度
    //LIS(i)=max(LSI(j) if(nums[i]>nums[j]))+1 for 0=<j<i
    //定义状态后，多列几个base example有助于寻找状态方程

    public int lengthOfLIS(int[] nums) {
        int [] memo=new int[nums.length];
        memo[0]=1;
        int max=memo[0];
        for(int i=1;i<nums.length;i++){
            int ret=1;
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j])
                    ret=Math.max(ret, memo[j]+1);
            }
            memo[i]=ret;
            max=Math.max(ret, max);
        }
        return max; 
    }

    
    //376
    //最长子序列问题,
    //使用两个状态可以复杂度将为O(n)
    public int wiggleMaxLength(int[] nums) {
        int l=nums.length;
        if(l<2)
            return l;
        int [] memo=new int[l];
        int [] diff=new int[l];
        memo[0]=1;
        diff[1]=nums[1]-nums[0];
        memo[1]=diff[1]==0?1:2;
        int max=memo[1];
        for(int i=2;i<l;i++){
            
            int Gdiff=nums[i]-nums[0];
            int ret=Gdiff==0?1:2;
            for(int j=1;j<i;j++){
                if((nums[i]-nums[j])*diff[j]<0&&ret<memo[j]+1){
                    ret=memo[j]+1;
                    Gdiff=nums[i]-nums[j];                    
                }
            }
            memo[i]=ret;
            diff[i]=Gdiff;
            max=Math.max(ret, max);
        }
        return max; 
    }
    public static void main(String[] args) {
        LSI lsi=new LSI();
        int[] nums={1,3,6,7,9,4,10,5,6};
        lsi.lengthOfLIS(nums);
    }
}   
