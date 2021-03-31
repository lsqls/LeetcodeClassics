package BackTracking;

import java.util.ArrayList;
import java.util.List;

//200 130 417 
//dfs模拟墨水扩散蔓延
//有时候得注意方向的顺序
public class FloodFill {
    
    
    //200  dfs算法模拟墨水扩散
    public int numIslands(char[][] grid) {
        int numofIslands=0;
        int rows=grid.length,cols=grid[0].length;
        boolean[][] visited=new boolean[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(!visited[i][j]&&grid[i][j]=='1'){
                    dfs(grid, visited, i, j);
                    numofIslands++;
                }
            }
        }
        return  numofIslands;
            
    }
    boolean isValid(char[][] grid,boolean[][] visited,int x,int y){
        if(x<0||x>=grid.length||y<0||y>=grid[0].length)
            return false;
        if(grid[x][y]=='0')
            return false;
        if(visited[x][y])
            return false;
        return true;
    }
    //x 行 y 列
    int [][] dirs={{1,0},{-1,0},{0,1},{0,-1}};
    void dfs(char[][] grid,boolean[][] visited,int x,int y){
        if(!isValid(grid,visited,x,y))
            return;
        visited[x][y]=true;
        for(int[] dir:dirs){
            dfs(grid, visited, x+dir[0], y+dir[1]);
        }
    }

    //x 行 y 列
    // int [][] dirs={{1,0},{-1,0},{0,1},{0,-1}};
    void dfsSolve(char[][] board,boolean[][] visited,int x,int y){
        if(x<0||x>=board.length||y<0||y>=board[0].length)
            return;
        if(board[x][y]=='X')
            return;
        if(visited[x][y])
            return;
        visited[x][y]=true;
        for(int[] dir:dirs){
            dfsSolve(board, visited, x+dir[0], y+dir[1]);
        }
    }
    //130. 被围绕的区域
    //对边界的O进行FLoodFill算法，记录不能被修改的O区域
    public void solve(char[][] board) {
        int rows=board.length,cols=board[0].length;
        boolean[][] visited=new boolean[rows][cols];
        int[] selectRow={0,rows-1};
        int[] selectCol={0,cols-1};
        for(int i:selectRow){
            for(int j=0;j<cols;j++){
                if(board[i][j]=='O'&&!visited[i][j]){
                    dfsSolve(board, visited, i, j);
                }
            }
        }
        for(int j:selectCol){
            for(int i=1;i<rows-1;i++){
                if(board[i][j]=='O'&&!visited[i][j]){
                    dfsSolve(board, visited, i, j);
                }
            }
        }

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(board[i][j]=='O'&&!visited[i][j])
                    board[i][j]='X';

            }
        }
                

    }

    int cols,rows;
    // 417，130的变体，对边界使用FloodFill，要注意墨水往高处流


    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> ret=new ArrayList<>();
        rows=matrix.length;

        if(rows==0)
            return ret;
        cols=matrix[0].length;
        boolean[][] toPacific=new boolean[rows][cols];
        boolean[][] toAtlantic=new boolean[rows][cols];
        for(int i=0;i<rows;i++){
            paDFS(matrix, toPacific, i, 0);
            paDFS(matrix, toAtlantic, i, cols-1);
        }
        for(int j=0;j<cols;j++){
            paDFS(matrix, toPacific, 0, j);
            paDFS(matrix, toAtlantic, rows-1, j);
        }
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++){
                if(toAtlantic[i][j]&&toPacific[i][j]){
                    List<Integer> ans=new ArrayList<>();
                    ans.add(i);ans.add(j);
                    ret.add(ans);
                }
            }
        return ret;
        
        
    }

    
    void paDFS(int[][] matrix,boolean[][] to,int i,int j){
        
        to[i][j]=true;
        for(int[] dir:dirs){
            int newi=i+dir[0],newj=j+dir[1];
            if(inArea(newi, newj)&&!to[newi][newj]&&matrix[i][j]<=matrix[newi][newj])
                paDFS(matrix, to, newi, newj);
        }
    }
    boolean inArea(int x,int y){
        return x<rows&&y<cols&&x>=0&&y>=0;
    }
    public static void main(String[] args) {
        FloodFill ff=new FloodFill();
        int[][] matrix={{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        ff.pacificAtlantic(matrix);
    }

}
