package SQ;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lib.leetcode.TreeNode;

//102 107 103 199
public class QueuUsage {
    // 102 层序历遍，经典题目吧
    // 使用队列实现，记录每一层的剩余数和下一层要打印的数目
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret=new ArrayList<>();
        if(root==null)
            return ret;
        Queue<TreeNode> q=new LinkedList<>();
        q.add(root);
        int curLevel=1,nextLevel=0;
        List<Integer> level=new ArrayList<>();
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            level.add(node.val);
            curLevel--;

            

            if(node.left!=null){
                q.add(node.left);
                nextLevel++;
            }
            if(node.right!=null){
                q.add(node.right);
                nextLevel++;
            }


            if(curLevel==0){
                ret.add(level);
                level=new ArrayList<>();
                curLevel=nextLevel;
                nextLevel=0;
            }
    
        }
        // System.out.println(ret.toString());
        return ret;

    }
    //107 从上往下层序历遍，使用LinkedList的addFirst即可
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> ret=new LinkedList<>();
        if(root==null)
            return ret;
        Queue<TreeNode> q=new LinkedList<>();
        q.add(root);
        int curLevel=1,nextLevel=0;
        List<Integer> level=new ArrayList<>();
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            level.add(node.val);
            curLevel--;

            

            if(node.left!=null){
                q.add(node.left);
                nextLevel++;
            }
            if(node.right!=null){
                q.add(node.right);
                nextLevel++;
            }


            if(curLevel==0){
                ret.addFirst(level);//唯一不同的地方
                level=new ArrayList<>();
                curLevel=nextLevel;
                nextLevel=0;
            }
    
        }
        // System.out.println(ret.toString());
        return ret;
    }
    //103 齿轮历遍，判定层数翻转(Colletions.reverse)，或者使用linkedList的add first（这样可以省去reverse）
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ret=new ArrayList<>();
        if(root==null)
            return ret;
        Queue<TreeNode> q=new LinkedList<>();
        q.add(root);
        int curLevel=1,nextLevel=0;int levelMark=1;
        LinkedList<Integer> level=new LinkedList<>();
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            if(levelMark%2==1)
                level.add(node.val);
            else
                level.addFirst(node.val);
            curLevel--;
            if(node.left!=null){
                q.add(node.left);
                nextLevel++;
            }
            if(node.right!=null){
                q.add(node.right);
                nextLevel++;
            }


            if(curLevel==0){
                ret.add(level);
                levelMark++;
                level=new LinkedList<>();
                curLevel=nextLevel;
                nextLevel=0;
            }
    
        }
        System.out.println(ret.toString());
        return ret;
    }
    void reverseList(List<Integer> iList){
        int left=0,right=iList.size()-1;
        while(left<right){
            int temp=iList.get(left);
            iList.set(left, iList.get(right));
            iList.set(right ,temp);
            left++;
            right--;
        }
    }

    // 199 右视图，层序遍历的最右节点
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret=new ArrayList<>();
        if(root==null)
            return ret;
        Queue<TreeNode> q=new LinkedList<>();
        q.add(root);
        int curLevel=1,nextLevel=0;
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            curLevel--;

            

            if(node.left!=null){
                q.add(node.left);
                nextLevel++;
            }
            if(node.right!=null){
                q.add(node.right);
                nextLevel++;
            }


            if(curLevel==0){
                ret.add(node.val);
                curLevel=nextLevel;
                nextLevel=0;
            }
    
        }
        // System.out.println(ret.toString());
        return ret;
    }
    public static void main(String[] args) {
        QueuUsage qu=new QueuUsage();
        TreeNode root=TreeNode.createTestData("[1,2,3,null,5,null,4]");
        root.pprint();
        qu.rightSideView(root);
    }
}
