package GreedyAlgorithm;

import java.util.Arrays;


//区间的排序往往是按照起始点排序。但是这题要根据结尾大小排序（结尾越小，留给后面的空间越多，后面越有可能容纳更多的空间）
//贪心选择性质的证明：1.数学归纳法(显著的n逐渐增加)2.反证法
//首个区间就是所有可以选择的区间中右端点最小的那个区间。
// 435
//题目只是要求移除区间的个数，没有必要去真实的模拟删除区间！
//我来按照右边界排序，从左向右记录非交叉区间的个数。最后用区间总数减去非交叉区间的个数就是需要移除的区间个数了。
//此时问题就是要求非交叉区间的最大个数。
public class NonoverlappingInterval {

    public int eraseOverlapIntervals(int[][] intervals) {
        int size=intervals.length;
        if(size==0)
            return 0;
        Arrays.sort(intervals, (a,b)->{
            return a[1]-b[1];
        });
        
        int cnt=1;
        int end=intervals[0][1];
        for(int[] interval:intervals){
            System.out.println(Arrays.toString(interval));
            if(end<=interval[0]){
                end=interval[1];
                cnt++;
            }
        }
        System.out.println(cnt);
        return size-cnt;
    }
    public static void main(String[] args) {
        NonoverlappingInterval nli=new NonoverlappingInterval();
        int[][] intervals={{1,2}, {2,3}, {3,4}, {1,3} };
        nli.eraseOverlapIntervals(intervals);
    }
}
