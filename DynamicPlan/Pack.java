package DynamicPlan;

import java.util.Arrays;
import java.util.HashSet;

//背包问题
/*

https://www.kancloud.cn/kancloud/pack/70125
背包问题具备的特征：给定一个target，target可以是数字也可以是字符串，再给定一个数组nums，nums中装的可能是数字，也可能是字符串，问：能否使用nums中的元素做各种排列组合得到target。
组合问题公式
dp[i] += dp[i-num]

True、False问题公式
dp[i] = dp[i] or dp[i-num]

最大最小问题公式
dp[i] = min(dp[i], dp[i-num]+1)或者dp[i] = max(dp[i], dp[i-num]+1)


背包问题技巧：
1.如果是0-1背包，即数组中的元素【不可重复使用】，nums放在外循环，target在内循环，且内循环【倒序】；
for num in nums:
    for i in range(target, nums-1, -1):

2.如果是完全背包，即数组中的元素【可重复使用】，nums放在外循环，target在内循环。且内循环【正序】。
for num in nums:
    for i in range(nums, target+1):

3.如果组合问题需考虑元素之间的【顺序】，需将target放在外循环，将nums放在内循环。
for i in range(1, target+1):
    for num in nums:

https://leetcode-cn.com/problems/combination-sum-iv/solution/xi-wang-yong-yi-chong-gui-lu-gao-ding-bei-bao-wen-/
*/
//416 322 377 
//TODO 背包九讲 474 139 494
//背包问题往往不已背包为形态出现
/*
用子问题定义状态：即f[i][v]表示前i件物品恰放入一个容量为v的背包可以获得的最大价值。则其状态转移方程便是：

f[i][v]=max{f[i-1][v],f[i-1][v-c[i]]+w[i]}
可以转化为
for i=1..N
    for v=V..0
        f[v]=max{f[v],f[v-c[i]]+w[i]};

因为现在的f[v-c[i]]就相当于原来的f[i-1][v-c[i]]

procedure ZeroOnePack(cost,weight)
    for v=V..cost
        f[v]=max{f[v],f[v-cost]+weight}


费用为cost的物品不会影响状态f[0..cost-1]，这是显然的（观察f[v-cost]）


完全背包问题：
for i=1..N
    for v=0..V
        f[v]=max{f[v],f[v-cost]+weight}
*/
public class Pack {
    //F(n,C)把n个物品放入容量为C的背包中，使得价值最大
    //F(i,c)=max(f(i-1,c),f(i-1,c-weight[i])+value[i])
    

    int pack(int[] weight,int[] value,int C){
        return pack(weight, value, weight.length-1,C);
    }
    //用nums 的[0...index],填充容积为c的背包的最大价值 
    //参数有背包大小，这是最重要的
    int pack(int[] weight,int[] value,int index,int c){
        if(index<0||c<=0)
            return 0;
        int ret=pack(weight, value, index-1, c);
        if(weight[index]<=c){
            ret=Math.max(ret, value[index]+pack(weight, value, index-1, c-weight[index]));
        }
        return ret;
        
    }
    //记忆化搜索
    int packRS(int[] weight,int[] value,int index,int c,int[][] memo){
        if(index<0||c<=0)
            return 0;
        if(memo[index][c]!=-1)
            return memo[index][c];
        int ret=pack(weight, value, index-1, c);
        if(weight[index]<=c){
            ret=Math.max(ret, value[index]+pack(weight, value, index-1, c-weight[index]));
        }
        memo[index][c]=ret;
        return ret;
        
    }
    int packDP1(int[] weight,int[] value,int C){
        
        int n=weight.length;
        int [][] memo=new int[n][C+1];
        //忘记的话可以画表格
        
        for(int j=0;j<=C;j++)
            memo[0][j]=weight[0]<=j?value[0]:0;
        for(int i=1;i<n;i++){
            for(int j=0;j<=C;j++){
                memo[i][j]=memo[i][j-1];
                if(weight[i]<=j)
                    memo[i][j]=Math.max(memo[i-1][j],memo[i-1][j-weight[i]]);
                    //if 条件已经保证了j-weight[i]>=0
                
            }
        }
        
        return memo[n-1][C];
        
    }

    //背包问题DP解的优化版本
    //只需要上一行的数据：保留两行即可
    //只需要利用左边的数据：转为1行，从右向左刷新
    int packDP2(int[] weight,int[] value,int C){
        
        int n=weight.length;
        int [] memo=new int[C+1];
        //忘记的话可以画表格
        
        for(int j=0;j<=C;j++)
            memo[j]=weight[0]<=j?value[0]:0;//初始化
        for(int i=1;i<n;i++){
            //对数组循环刷新n-1次
            //从右向左刷新j
            for(int j=C;j>=weight[i];j--){
                memo[j]=Math.max(memo[j],memo[j-weight[i]]);//状态转移函数
                    //for 条件已经保证了j-weight[i]>=0
            }
        }
        
        return memo[C];
        
    }

    //416 
    //在n个物品中选出一定的物品，填满sum/2的背包
    //f(i,c)=f(i-1,c)||f(i-1,c-weight(i))
    public boolean canPartition(int[] nums) {
        int sum=0;
        for(int val:nums){
            sum+=val;
        }
        if(sum%2==1)
            return false;
        return packDP(nums, sum/2);

    }
    //
    boolean packDP(int[] nums,int C){
        
        int n=nums.length;
        boolean [] memo=new boolean[C+1];
        //忘记的话可以画表格
        
        for(int j=0;j<=C;j++)
            memo[j]=(nums[0]==j);//能否用0号元素填满j
        for(int i=1;i<n;i++){//nums在外循环
            //从右向左刷新j
            for(int j=C;j>=nums[i];j--){ //target在内循环
                //能否用[0...i]填满j,j的最后一个元素是C
                memo[j]=memo[j]||memo[j-nums[i]];
                    //for 条件已经保证了j-weight[i]>=0
            }
        }
        
        return memo[C];
        
    }


    //332
    public int coinChange(int[] coins, int amount) {
        int[] memo=new int[amount+1];
        Arrays.fill(memo, -1);
        int ret=coinChangeRS(coins, amount, memo);
        return ret==Integer.MAX_VALUE/2?-1:ret;        
    } 
    //背包物品无限供应，转化为循环
    //f(s),组成金额目标s的最小数量 
    //f(s)=min(f(s-val0),f(s-val1),...)+1
    public int coinChangeRS(int[] coins, int s,int [] memo) {
        
        if(s==0)
            return 0;
        if(memo[s]!=-1)
            return memo[s];
        int min=Integer.MAX_VALUE/2;
        for(int val:coins){
            if(val<=s)
                min=Math.min(coinChangeRS(coins, s-val,memo)+1, min);
        }
        memo[s]=min;
        return min;
    } 
    //完全背包问题
    public int coinChangeDP(int[] coins, int amount) {
        final int MYMAX=Integer.MAX_VALUE/2;
        int[] memo=new int[amount+1];
        Arrays.fill(memo,MYMAX);
        memo[0]=0;
        for(int val:coins){
            for(int i=val;i<=amount;i++){
                memo[i]=Math.min(memo[i-val]+1,memo[i]);
            }
        }
        System.out.println(Arrays.toString(memo));
        return memo[amount]==MYMAX?-1:memo[amount];
        

    }
    //377

    //f(i)表示和为给定目标正整数的组合的个数【往往题目要求的解就是状态】
    //for val in nums:
    //  f(i)+=f(i-val)
    //组合背包问题
    public int combinationSum4DP(int[] nums, int target) {
        int[] memo=new int[target+1];
        HashSet<Integer> iset=new HashSet<>();
        for(int val:nums){
            iset.add(val);
        }
        for(int i=0;i<=target;i++){
            memo[i]=iset.contains(i)?1:0;
        }
        for(int i=1;i<=target;i++){
            int add=0;
            for(int val:nums){
                if(i>=val)
                    add+=memo[i-val];
            }
            memo[i]+=add;
        }

        return  memo[target];
    }
    
    public static void main(String[] args) {
        Pack p=new Pack();
        int []nums={1,2,3};
        int ret=p.combinationSum4DP(nums,4);
        System.out.println(ret);
    }

}
