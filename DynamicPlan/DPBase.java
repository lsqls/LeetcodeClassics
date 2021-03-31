package DynamicPlan;
//动态规划的前提:递归树中存在大量的重复计算（相同的结构）


//记忆化搜索（自上而下的解决方法，思考更简单）：使用标识判断是否已经计算过
//动态规划：自下而上的解决方法
//动态规划：将原问题拆解为若干子问题，同时保存子问题的答案，使得每个问题只求解一次，最终获得原问题的答案
//通过求解子问题的最优解，可以获得原问题的最优解，可以称为最优子结构
//70 120 64 
//343 279 91 62 63

import java.util.Arrays;
import java.util.List;



public class DPBase {
    //70 爬楼梯，递归树画出来，发现有重复结构
    public int climbStairs(int n) {
        return climbStairsDP(n);
    }

    //记忆化搜索
    int climbStairsRS(int n,int[] memo){
        if(n<=2)
            return n;

        if(memo[n]!=-1)
            return memo[n];
        int res;
        res=climbStairsRS(n-1, memo)+climbStairsRS(n-2, memo);
        //1.先爬上n-1阶台阶，然后爬1阶 2.先爬上n-2阶台阶，然后爬2阶（事实上就是斐波那契数列）

        memo[n]=res;
        return  res;
    }
    //动态规划
    public int climbStairsDP(int n){
        int[] memo=new int[n+1];//长度是n+1
        memo[0]=1;memo[1]=1;
        for(int i=2;i<memo.length;i++){
            memo[i]=memo[i-1]+memo[i-2];
        }
        return memo[n];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        return minimumDP2(triangle);
    }
    
    //120
    //记忆化搜索
    //f(i,j) 表示以(i,j)为起点自顶向下的最小路径和。
    //f(i,j)=max(f(i+1,j)+f(i+1,j+1))+val[i][j]
    public int minimumRS(List<List<Integer>> triangle,int i,int j,List<List<Integer>> memo){
        if(i==triangle.size()-1)
            return triangle.get(i).get(j);
        if(memo.get(i).get(j)!=-1)
            return memo.get(i).get(j);
        int res=Math.min(minimumRS(triangle, i+1, j, memo), minimumRS(triangle, i+1, j+1, memo))+triangle.get(i).get(j);
        memo.get(i).set(j, res);
        return res;
    } 

    public int minimumDP(List<List<Integer>> triangle){
        int size=triangle.size();
        for(int i=size-2;i>=0;i--){
            List<Integer> line =triangle.get(i);
            List<Integer> nextLine =triangle.get(i);
            for(int j=0;j<line.size();j++){
                int val=line.get(j);
                line.set(j,  Math.min(nextLine.get(j), nextLine.get(j+1))+val);
            }
        }
        return triangle.get(0).get(0);        
    }
    public int minimumDP2(List<List<Integer>> triangle){
        int size=triangle.size();
        int[] memo=new int[size];
        List<Integer> lastLine=triangle.get(triangle.size()-1);
        for(int i=0;i<size;i++){
            memo[i]=lastLine.get(i);
        }
        for(int i=size-2;i>=0;i--){
            List<Integer> line =triangle.get(i);
            for(int j=0;j<line.size();j++){
                int val=line.get(j);
                memo[j]= Math.min(memo[j], memo[j+1])+val;
            }
        }
        return memo[0];        
    } 


    //64
    public int minPathSum(int[][] grid) {
        int rows=grid.length,cols=grid[0].length;
        int[][] memo=new int[rows][cols];

        for(int [] row:memo){
            for(int i=0;i<cols;i++)
                row[i]=-1;
        }
        return minPathSumRS(grid,0,0,memo);

    }
    //f(i,j)表示从(i,j) 出发到右下角的最短路径和
    //f(i,j)=min(f(i,j+1),f(i,j+1))+val(i,j)
    //记忆化搜索
    public int minPathSumRS(int[][] grid,int i,int j,int[][] memo) {
        if(i==grid.length-1&&j==grid[0].length-1)
            return grid[i][j];
        if(i>grid.length-1||j>grid[0].length-1)
            return Integer.MAX_VALUE;
        if(memo[i][j]!=-1)
            return memo[i][j];
        int res=Math.min(minPathSumRS(grid,i+1,j,memo),minPathSumRS(grid, i, j+1,memo))+grid[i][j];
        memo[i][j]=res;
        return res;
    }



    public int integerBreak(int n) {
        // int[] memo=new int[n+1];
        // Arrays.fill(memo, -1);
        // return integerBreakRS(n,memo);
        return integerBreakDP(n);
    }
    //f(i) 表示整数的乘积最大化的值
    //f(i)=max(f(i-1)*1,f(i-2)*2,...,f(2)*i-2,f(1)*i-1)
    public int integerBreakRS(int n,int[] memo) {
        if(n<=2)
            return 1;
        if(memo[n]!=-1)
            return memo[n];
        int max=0;
        for(int i=1;i<=n-1;i++){
            //integerBreakRS(i)*(n-i) 至少会被分成三部分
            max=Math.max(integerBreakRS(i,memo)*(n-i), Math.max(i*(n-i), max));
        }
        memo[n]=max;
        return max;
        
    }

    public int integerBreakDP(int n) {
        int[] memo=new int[n+1];
        memo[1]=1;memo[2]=1;
        
        for(int j=3;j<=n;j++){
            int max=0;
            for(int i=1;i<=j-1;i++){
                //integerBreakRS(i)*(j-i) 至少会被分成三部分
                max=Math.max(memo[i]*(j-i), Math.max(i*(j-i), max));
            }
            memo[j]=max;
        }
        
        return memo[n];
        
    }



    public int numSquares(int n) {
        return numSquaresDP(n);
    }


    //f(j)表示组成和j的完全平方数的最少个数
    //切割问题，可以使用回溯，由于递归树里有重复结构，考虑使用动态规划或记忆化搜索
    //使用动态规划的题往往不要求具体解
    //f(i)=[min(f(i-1^2),...,f(i-sqrt(i)))]【一个for循环解决】+1
    //f(0)=0;
    public int numSquaresRS(int i,int[] memo) {  
        if(i<=2)
            return i;
        if(memo[i]!=-1)
            return memo[i];
        int minCnt=Integer.MAX_VALUE;
        int sqrt=(int)Math.sqrt(i);
        if(i-(int)Math.pow(sqrt, 2)==0)
            minCnt=0;
        else{
            for(int j=1;j<=sqrt;j++){
                minCnt=Math.min(numSquaresRS(i-(int)Math.pow(j, 2),memo), minCnt);
            }
        }
        minCnt++;
        // System.out.println(i+" "+minCnt);
        memo[i]=minCnt;
        return minCnt;
    }
    public int numSquaresDP(int n){
        int[] memo=new int[n+1];
        memo[0]=0;
        
        for(int i=1;i<=n;i++){
            int minCnt=Integer.MAX_VALUE;
            int sqrt=(int)Math.sqrt(i);
            if(i-(int)Math.pow(sqrt, 2)==0)
                memo[i]=1;
            else{
                for(int j=1;j<=sqrt;j++){
                    minCnt=Math.min(memo[i-(int)Math.pow(j, 2)], minCnt);
                }
                memo[i]=minCnt+1;
            }
        }
        return memo[n];
        


    }

    public int numDecodings(String s) {
        int[] memo=new int[s.length()];
        Arrays.fill(memo, -1);
        return numDecodingsRS(s,0,memo);
    }

    //f(i) 代表[i,size-1] 的翻译总数
    //f(i)=f(i+1)【翻译一位数】+f(i+2)【翻译两位数，假如成立】
    //观察输出，有重复计算
    public int numDecodingsRS(String s,int i,int[] memo) {
        int ret=0;
        if(s.charAt(i)=='0')//0单独处理
            return 0;
        if(memo[i]!=-1)
            return memo[i];
        if(i==s.length()-1){
            ret=1;
            System.out.println(s.substring(i, s.length())+" 可能答案有 "+ret);
            memo[i]=ret;
            return ret;
        }
        if(i==s.length()-2){

            int num=Integer.parseInt(s.substring(i, i+2));
            if(num<10)
                ret=0;
            if(num==10||num==20)
                ret=1;
            else if(num%10==0||num<10)
                ret=0;
            else if(num<=26)
                ret=2;
            else
                ret=1;
            System.out.println(s.substring(i, s.length())+" 可能答案有 "+ret);
            memo[i]=ret;
            return ret;

        }   
        if(memo[i]!=-1)
            return memo[i];
        ret+=numDecodingsRS(s, i+1,memo);
        int num=Integer.parseInt(s.substring(i, i+2));
        if(num<=26&&num>=10)
            ret+=numDecodingsRS(s, i+2,memo);
        System.out.println(s.substring(i, s.length())+" 可能答案有 "+ret);
        memo[i]=ret;
        return ret;
    }
    public int uniquePaths(int m, int n) {
        int[][] memo=new int[m+1][n+1];
        for (int[] row: memo)
            Arrays.fill(row, -1);
        return uniquePaths(1, 1, m, n,memo);
    }



    
    //从i,j，出发，到m,n的路径数
    public int uniquePaths(int i,int j,int m, int n,int[][] memo) {
        if(i>m||j>n)
            return 0;
        if(i==m&&j==n)
            return 1;
        if(memo[i][j]==-1)
            memo[i][j]= uniquePaths(i, j+1, m, n,memo)+uniquePaths(i+1, j, m, n,memo);
        return memo[i][j];
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m=obstacleGrid.length,n=obstacleGrid[0].length;
        int[][] memo=new int[m][n];
        for (int[] row: memo)
            Arrays.fill(row, -1);
        return uniquePathsWithObstacles(obstacleGrid,0,0,memo);
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid,int i,int j,int[][] memo) {
        int m=obstacleGrid.length,n=obstacleGrid[0].length;
        if(i>m-1||j>n-1)
            return 0;
        if(obstacleGrid[i][j]==1)
            return 0;
        if(i==m-1&&j==n-1)
            return 1;
        if(memo[i][j]==-1)
            memo[i][j]= uniquePathsWithObstacles(obstacleGrid, i, j+1,memo)+uniquePathsWithObstacles(obstacleGrid,i+1, j,memo);
        return memo[i][j];
    }
    public static void main(String[] args) {

        DPBase dpbs=new DPBase();
        
        int[][] obstacleGrid={
            {0,0,0},
            {0,1,0},
            {0,0,0}
        };
        int ret=dpbs.uniquePathsWithObstacles(obstacleGrid);
        System.out.print(ret);
        

    }

}
