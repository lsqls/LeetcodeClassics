package GreedyAlgorithm;
import java.util.Arrays;

//对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，
//都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。
//你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
// 链接：https://leetcode-cn.com/problems/assign-cookies

//解法：排序+贪心
//为了尽可能满足最多数量的孩子，应该按照孩子的胃口从小到大的顺序依次满足每个孩子，且对于每个孩子，应该选择可以满足这个孩子的胃口且尺寸最小的饼干。

public class AssignCookies {
    public int findContentChildren(int[] g, int[] s) {
        if(g==null||s==null)
            return 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int gi=0,si=0;
        while(gi<g.length&&si<s.length){
            if(g[gi]<=s[si])
                gi++;
            si++;
        }
        return gi;
        
    }

    
}
