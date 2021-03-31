package SQ;
//  279 127 126

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    //279
    //整个问题可以化为一个图论问题
    //从n到0，每个数字代表一个节点，如果两个数x，y相差一个完全平方数，则连接一条边，我们得到一个无权图，
    //原问题转化为求无权图中n到0的最短路径
    class Path<T>{
        T point;
        int len;
        Path(T point,int len){
            this.point=point;
            this.len=len;
        };
    }
    public int numSquares(int n){
        boolean[] visited=new boolean[n];
        Queue<Path<Integer>> eQueue=new LinkedList<>();
        eQueue.add(new Path<Integer>(n, 0));
        while(!eQueue.isEmpty()){
            Path<Integer> e=eQueue.poll();
            int point=e.point,len=e.len;
            if(point==0)
                return len;
            for(int i=1;point-i*i>=0;i++){
                if(!visited[point-i*i]){
                    visited[point-i*i]=true;
                    eQueue.add(new Path<Integer>(point-i*i, len+1));
                }
            }
        }
        
        return 0;

    }


    //127 转化为图的问题+BFS，Boolean数组比hashset高效很多
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        boolean[] visited=new boolean[wordList.size()];
        Queue<Path<String>> eQueue=new LinkedList<>();
        eQueue.add(new Path<String>(beginWord, 1));
        while(!eQueue.isEmpty()){
            Path<String> e=eQueue.poll();
            String point=e.point;int len=e.len;
            for(int i=0;i<wordList.size();i++){
                String word=wordList.get(i);
                if(!visited[i]&&canTrans(point,word)){
                    System.out.println(point+"到"+word+"距离是"+(len+1));
                    if(word.equals(endWord))
                        return len+1;
                    visited[i]=true;
                    eQueue.add(new Path<String>(word, len+1));
                }
            }
        }
        return 0;
    }
    boolean canTrans(String str1,String str2){
        int cnt=0;
        for(int i=0;i<str1.length();i++){
            if(str1.charAt(i)!=str2.charAt(i))
                cnt++;
            if(cnt>1)
                return false;
        }
        return true;
    }
    ArrayList<Integer>[] edges; // 图的边
    

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        int INF = 1 << 20;
        HashMap<String, Integer> wordId=new HashMap<>(); // 单词到id的映射
        ArrayList<String> idWord=new ArrayList<>(); // id到单词的映射


        int id = 0;
        // 将wordList所有单词加入wordId中 相同的只保留一个 // 并为每一个单词分配一个id
        for (String word : wordList) {
            if (!wordId.containsKey(word)) { 
                wordId.put(word, id++);
                idWord.add(word);
            }
        }
        // 若endWord不在wordList中 则无解
        if (!wordId.containsKey(endWord)) {
            return new ArrayList<>();
        }
        // 把beginWord也加入wordId中
        if (!wordId.containsKey(beginWord)) {
            wordId.put(beginWord, id++);
            idWord.add(beginWord);
        }

        // 初始化存边用的数组
        edges = new ArrayList[idWord.size()];
        for (int i = 0; i < idWord.size(); i++) {
            edges[i] = new ArrayList<>();
        }
        // 添加边
        for (int i = 0; i < idWord.size(); i++) {
            for (int j = i + 1; j < idWord.size(); j++) {
                // 若两者可以通过转换得到 则在它们间建一条无向边
                if (canTrans(idWord.get(i), idWord.get(j))) {
                    edges[i].add(j);
                    edges[j].add(i);
                }
            }
        }

        int dest = wordId.get(endWord); // 目的ID
        List<List<String>> res = new ArrayList<>(); // 存答案
        int[] cost = new int[id]; // 到每个点的代价
        for (int i = 0; i < id; i++) {
            cost[i] = INF; // 每个点的代价初始化为无穷大
        }

        // 将起点加入队列 并将其cost设为0
        Queue<ArrayList<Integer>> q = new LinkedList<>();
        ArrayList<Integer> tmpBegin = new ArrayList<>();
        tmpBegin.add(wordId.get(beginWord));
        q.add(tmpBegin);
        cost[wordId.get(beginWord)] = 0;

        // 开始广度优先搜索
        while (!q.isEmpty()) {
            ArrayList<Integer> now = q.poll();
            int last = now.get(now.size() - 1); // 最近访问的点
            if (last == dest) { // 若该点为终点则将其存入答案res中
                ArrayList<String> tmp = new ArrayList<>();
                for (int index : now) {
                    tmp.add(idWord.get(index)); // 转换为对应的word
                }
                res.add(tmp);
            } else { // 该点不为终点 继续搜索
                for (int i = 0; i < edges[last].size(); i++) {
                    int to = edges[last].get(i);
                    // 此处<=目的在于把代价相同的不同路径全部保留下来
                    if (cost[last] + 1 <= cost[to]) {
                        cost[to] = cost[last] + 1;
                        // 把to加入路径中
                        ArrayList<Integer> tmp = new ArrayList<>(now); tmp.add(to);
                        q.add(tmp); // 把这个路径加入队列
                    }
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        List<String> wordList=Arrays.asList(new String[]{"hot","dot","dog","lot","log","cog"});
        BFS bfs=new BFS();
        bfs.findLadders("hit", "cog", wordList);

    }

}
