package DynamicPlan;

import java.util.Arrays;
import java.util.HashMap;

import lib.leetcode.TreeNode;

//状态以及状态转移
//状态：函数的定义
//状态转移方程
//198 213 337 309

public class State {

    public int rob(int[] nums) {
        int[] memo=new int[nums.length];
        Arrays.fill(memo, -1);
        return robRS(nums,0,memo);
    }
    //f(i)考虑偷取[i,size-1]
    //f(i)=max(f(i+2)+val[i],f(i+3)+val[i+1],...,f(size-1)+val[size-3],val[size-2],f(size)+val[size-1])
    public int robRS(int[] nums,int i,int[] memo) {
        if(i>=nums.length)
            return 0;
        if(memo[i]!=-1)
            return memo[i];
        int max=-1;
        for(int j=i;j<=nums.length-1;j++)
            max=Math.max(max, nums[j]+robRS(nums, j+2,memo));
        memo[i]=max;
        return max;
        
    }
    public int robDP(int[] nums) {
        int l=nums.length;
        int[] memo=new int[l+2];
        memo[l+1]=0;memo[l]=0;
        for(int i=l-1;i>=0;i--){
            int max=-1;
            for(int j=i;j<=l-1;j++)
                max=Math.max(max, nums[j]+memo[j+2]);
            memo[i]=max;
        }
        return memo[0];
    }

    public int rob2(int[] nums){
        if(nums.length==0)
            return 0;
        if(nums.length==1)
            return nums[0];
        // int[] memo=new int[nums.length];
        // Arrays.fill(memo, -1);
        // int plan1=rob2RS(nums, 0, nums.length-2, memo);
        // Arrays.fill(memo, -1);
        // int plan2=rob2RS(nums, 1, nums.length-1, memo);
        // return Math.max(plan1, plan2);
        return Math.max(rob2DP(nums, 0, nums.length-2),rob2DP(nums, 1, nums.length-1) );
    }
    public int rob2RS(int[] nums,int i,int end,int[] memo) {
    //f(i)考虑偷取[i,end]
    // 环状排列意味着第一个房子和最后一个房子中只能选择一个偷窃，因此可以把此环状排列房间问题约化为【两个单排排列】房间子问题：
    // 在不偷窃第一个房子的情况下即 f(0:size-1)，最大金额是 plan1 
    // 在不偷窃最后一个房子的情况下即 f(1:size-2)，最大金额是 plan2 
        if(i>end||end<0)
            return 0;
        if(memo[i]!=-1)
            return memo[i];
        int max=-1;
        for(int j=i;j<=end;j++){
            max=Math.max(max, nums[j]+rob2RS(nums, j+2,end,memo));
        }
        memo[i]=max;
        return max;
    }
    //考虑偷取nums 的[start,end]
    public int rob2DP(int[] nums,int start,int end){
        int l=end+1;
        int[] memo=new int[l+2];
        memo[l+1]=0;memo[l]=0;
        for(int i=l-1;i>=start;i--){
            int max=-1;
            for(int j=i;j<=end;j++)
                max=Math.max(max, nums[j]+memo[j+2]);
            memo[i]=max;
        }
        return memo[start];
    }


    public int rob(TreeNode root) {
        HashMap<TreeNode,Integer> tMap=new HashMap<>();
        return rob3RS(root, tMap);

    }
    //f:考虑从root 节点开始偷
    //f(root)=max[val(root)+f(left.left)+f(left.right)+f(r.l)+f(r.r)，f(left)+f(right)]
    public int rob3RS(TreeNode root,HashMap<TreeNode,Integer> tMap) {
        if(root==null)
            return 0;
        if(tMap.containsKey(root))
            return tMap.get(root);

        int plan1=root.val,plan2=0;
        TreeNode left=root.left,right=root.right;
        if(left!=null){
            plan2+=rob3RS(left,tMap);
            plan1+=rob3RS(left.left,tMap)+rob3RS(left.right,tMap);
        }
        if(right!=null){
            plan2+=rob3RS(right,tMap);
            plan1+=rob3RS(right.left,tMap)+rob3RS(right.right,tMap);
        }

        int ret=Math.max(plan1, plan2);
        tMap.put(root, ret);
        return ret;

    }
    //https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/solution/dp-zhuang-tai-de-ding-yi-you-liang-chong-fang-fa-b/
    public int maxProfit(int[] prices) {
        int len=prices.length;
        if(len==0||len==1)
            return 0;
        int[] hold=new int[len];  //第i天持有股票的最大收益
        int[] unhold=new int[len]; //第i天不持有股票的最大收益
        hold[0]=-prices[0]; //// 第0天 买了股票的收益
        unhold[0]=0;
        hold[1]=Math.max(-prices[0],-prices[1]);
        unhold[1]=Math.max(unhold[0],hold[0]+prices[1]);
        for(int i=2;i<len;i++){
            hold[i]=Math.max(hold[i-1],unhold[i-2]-prices[i]);
            unhold[i]=Math.max(unhold[i-1],hold[i-1]+prices[i]);
        }
        return unhold[len-1];
    }   
    /*状态转移方程
    hold[i] : 第 i 天，手中持有股票，这时的最大收益。
    有两种可能：
        昨天就持有股票，今天休息。
        前天卖了股票，今天买了股票。
    hold[i] = Math.max(hold[i - 1], unhold[i - 2] - prices[i])
    
    unhold[i] : 第 i 天，手中没有股票，此时的最大收益。
    有两种可能：今天休息、或卖了股票
        昨天也没有持有，今天休息。
        昨天持有股票，今天卖了股票。
    unhold[i] = Math.max(unhold[i -1], hold[i - 1] + prices[i])
    
    目标是求 unhold[n-1] ( n：0 1 2 3 ... )
    
    base case
    hold[0] = -prices[0] 第0天买股票，收益-prices[0]元
    hold[1] = Math.max(-prices[0], -prices[1]) 第1天持有着股票，可能是昨天买的，今天休息，也可能是昨天休息，今天买的
    unhold[0] = 0 第0天没有持有股票，就是休息，收益 0 元
    */ 
    
    public static void main(String[] args) {
        State s=new State();

        TreeNode root=TreeNode.createTestData("[3,2,3,null,3,null,1]");
        root.pprint();
        s.rob(root);
    }
} 
