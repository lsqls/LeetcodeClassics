package BackTracking;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

//51 52 37
public class NQueue {
    //51 一行行考虑，难点是剪枝：同一列，对角线，
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ret=new ArrayList<>();
        List<List<Integer>> res=new ArrayList<>();
        boolean[][] used=new boolean[3][2*n-1];
        Deque<Integer> path=new LinkedList<>();
        solveNQueens(n, 0, path, res, used);
        // System.out.println(res.toString());
        
        for(List<Integer> ans:res){
            List<String> ansList=new ArrayList<>();
            for(int bit:ans){
                ansList.add(int2str(bit,n));
            }    
            ret.add(ansList);
        }
        // System.out.println(ret.toString());
        return ret;
    }

    String int2str(int bit,int n){
        char[] chs=new char[n];
        for(int i=0;i<n;i++){
            chs[i]='.';
        }
        chs[bit]='Q';
        return String.valueOf(chs);
    }
    //考虑start行开始，放下n个皇后的问题
    //剪枝：cols[j]记录j列被占用，dia1[j] 记录j左斜对角线被占用，dia2记录j右对角线被占用
    public void solveNQueens(int n,int index,Deque<Integer> path,List<List<Integer>> res,boolean[][] used) {
        if(path.size()==n){
            res.add(new ArrayList<>(path));
            return;
        }
        for(int j=0;j<n;j++){
            int col=j,dial1=index+j,dial2=index-j+n-1;
            if(used[0][col]||used[1][dial1]||used[2][dial2])
                continue;
            path.add(j);
            used[0][col]=true;
            used[1][dial1]=true;
            used[2][dial2]=true;
            solveNQueens(n, index+1, path, res, used);
            path.removeLast();
            used[0][col]=false;
            used[1][dial1]=false;
            used[2][dial2]=false;
        }
    }
    public int totalNQueens(int n) {
        boolean[][] used=new boolean[3][2*n-1];
        cnt=0;
        totalNQueens(n, 0, used);
        return cnt;
    }
    int cnt;
    public void totalNQueens(int n,int index,boolean[][] used) {
        if(index==n){
            cnt++;
            return;
        }
        for(int j=0;j<n;j++){
            int col=j,dial1=index+j,dial2=index-j+n-1;
            if(used[0][col]||used[1][dial1]||used[2][dial2])
                continue;
            used[0][col]=true;
            used[1][dial1]=true;
            used[2][dial2]=true;
            totalNQueens(n, index+1,used);
            used[0][col]=false;
            used[1][dial1]=false;
            used[2][dial2]=false;
        }
    }

    public void solveSudoku(char[][] board) {
        int R=board.length,C=board[0].length;
        boolean[][][] square=new boolean[R/3][C/3][10];
        boolean[][] rows=new boolean[R][10],cols=new boolean[C][10];
        // initial
        List<int[]> space=new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                char ch=board[i][j];
                if(ch=='.'){
                    space.add(new int[]{i,j});
                }
                else{
                    int num=ch-'0';
                    rows[i][num]=true;
                    cols[j][num]=true;
                    square[i/3][j/3][num]=true;
                }
            }
        }
        for(int[] loc:space){
            System.out.println(loc[0]+" "+loc[1]);
        }
        solveSudoku(board, square, rows, cols, space, 0);
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }
    boolean valid=false;//唯一解必须有
    public void solveSudoku(char [][] board,boolean[][][] square,boolean[][] rows,boolean[][] cols,List<int[]> space,int start) {
            if(start==space.size()){
                System.out.println(start+"->退出");
                valid=true;
                return;
            }
            int[] loc=space.get(start);
            int i=loc[0],j=loc[1];
            for(int num=1;num<=9&&!valid;num++){//当前找到可能解，必须中断,否则会影响唯一解的输出
                System.out.print(String.format("尝试在%d,%d填入%d", i,j,num));
                if(rows[i][num]||cols[j][num]||square[i/3][j/3][num]){
                    System.out.println(String.format(" 行：%b,列：%b,九：%b -> continue",rows[i][num],cols[j][num],square[i/3][j/3][num]));
                    continue;
                }
                
                board[i][j]=(char)(num+'0');
                System.out.println("->成功");
                rows[i][num]=cols[j][num]=square[i/3][j/3][num]=true;
                solveSudoku(board, square, rows, cols, space,start+1);
                rows[i][num]=cols[j][num]=square[i/3][j/3][num]=false;
            }
    }
    public static void main(String[] args) {
        NQueue nq=new NQueue();
        char [][] board={{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        nq.solveSudoku(board);
    }
}
