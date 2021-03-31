package DynamicPlan;


//动态规划求具体解（类似于D最短路算法的倒推具体路径过程）
//总体上，面试对动态规划的要求并不高
//最长公共子序列
//1143
//LCS(m,n) 表示 S1[0,m]和S2[0,n]的最长公共子序列的长度
//S1[m]==S2[n] LCS(m,n)=LCS(m-1,n-1)+1;
//S1[m]!=S2[n] LCS(m,n)=max(LCS(m-1,n),LCS(m,n-1))
public class LCS {
    //二维的需要画方格图分析
    public int longestCommonSubsequence(String text1, String text2) {
        int len1=text1.length(),len2=text2.length();
        int[][] dp=new int[len1+1][len2+1];
        
        
        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        
        return dp[len1][len2];

    }
    
    public static void main(String[] args) {
        LCS lcs=new LCS();
        lcs.longestCommonSubsequence("abcde", "ace");
    }
}
