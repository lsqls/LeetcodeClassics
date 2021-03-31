package BackTracking;
//79
public class TwoD {
    public boolean exist(char[][] board, String word) {
        int rows=board.length,cols=board[0].length;

        char[] words=word.toCharArray();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(exist(board, words, i, j, 0))
                    return true;
            }
        }
        return false;
    }
    int[][] dirs={{0,1},{0,-1},{1,0},{-1,0}};
    public boolean exist(char[][] board, char[] words,int x,int y,int index) {
        if(index==words.length)
            return true;
        if(!inArea(x, y,board)||board[x][y]!=words[index])
            return false;
        
        // used[x][y]=true;
        char temp=board[x][y];
        board[x][y]='0';
        for(int[] dir:dirs){
            int newx=x+dir[0],newy=y+dir[1];
            if(exist(board, words, newx, newy, index+1))
                return true;
        }
        board[x][y]=temp;//记得回溯
        return false;
    }
    boolean inArea(int x,int y,char[][] board){
        return x>=0&&y>=0&&x<board.length &&y<board[0].length;
    }
    public static void main(String[] args) {
        
    }
}
