package lib.leetcode;

import java.util.Arrays;

public class UFfinal {
    int[] parent;
    //基于size 优化，并添加路径压缩
    int[] size;
    int cnt;
    public UFfinal(int n){
        parent=new int[n];
        size=new int[n];
        cnt=n;
        for(int i=0;i<cnt;i++){
            parent[i]=i;
        }
        Arrays.fill(size, 1);
    }
    int find(int p){
        if(p>cnt||p<0)
            return -1;
        
        if(parent[p]!=p)
            parent[p]=find(parent[p]);
        return parent[p];
    }

    public boolean  union(int p,int q){
        if(p>cnt||q>cnt)
            return false;
        int pRoot=find(p),qRoot=find(q);
        if(pRoot==qRoot)
            return true;
        if(size[pRoot]<size[qRoot]){
            parent[pRoot]=qRoot;
            size[qRoot]+=size[pRoot];
            return false;
        }
        else{
            parent[qRoot]=pRoot;
            size[pRoot]+=size[qRoot];
            return false;
        }
        
    }
    
    boolean isConnected(int p,int q){
        return find(p)==find(q);
    }
    
}