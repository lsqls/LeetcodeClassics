package BT;

import java.util.ArrayList;
import java.util.List;

import lib.leetcode.TreeNode;

// 104 
// 226 100 101 222 110
// 112 111 404
// 257 113 129
// 437
public class BT {
    //104 二叉树最大深度：经典递归问题
    public int maxDepth(TreeNode root) {
        if(root==null)
            return 0;
        return 1+(Math.max(maxDepth(root.left),maxDepth(root.right)));
    }
    // 226 翻转二叉树，经典题
    public TreeNode invertTree(TreeNode root) {
        if(root==null)  return null;
        TreeNode newLeft=invertTree(root.right),newRigth=invertTree(root.left);
        TreeNode ret=new TreeNode(root.val);
        ret.left=newLeft;
        ret.right=newRigth;
        return ret;
    }
    //100 相同的树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        return (p==null&q==null)||(p!=null&&q!=null&&p.val==q.val&&isSameTree(p.left, q.left)&&isSameTree(p.right, q.right));
    }
    // 101 对称树

    //另一种想法：如果一个树的左子树与右子树镜像对称，那么这个树是对称的。如果同时满足下面的条件，两个树互为镜像：1.它们的两个根结点具有相同的值 2.每个树的右子树都与另一个树的左子树镜像对称
    public boolean isSymmetric(TreeNode root) {
        TreeNode nroot=invertTree(root);
        return isSameTree(root , nroot);

    }
    //222 利用完全二叉树的性质，满二叉树的节点个数可以直接由层数计算得出
    public int countNodes(TreeNode root) {
        int fullLevel=full(root);
        if(fullLevel!=-1)
            return (int)Math.pow(2, fullLevel)-1;
        else{
            return countNodes(root.left)+countNodes(root.right)+1;
        }
    }
    public int  full(TreeNode node){
        int leftNum=0,rightNum=0;
        TreeNode left=node,right=node;
        while(left!=null){
            leftNum++;
            left=left.left;
        }
        while(right!=null){
            rightNum++;
            right=right.right;
        }
        if(leftNum==rightNum)
            return leftNum;
        else
            return -1;

    }
    //110 平衡二叉树的判断
    //一个二叉树每个节点 的左右两个子树的高度（使用maxDepth函数）差的绝对值不超过 1 。
    //当然，自底向上的递归更好
    public boolean isBalanced(TreeNode root) {
        if(root==null)  return true;
        return (Math.abs(maxDepth(root.left)-maxDepth(root.right))<=1)&&isBalanced(root.left)&&isBalanced(root.right);
    }
    //112 根节点到叶子节点 的路径 ,不要想的过于复杂，注意退出条件
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null)
            return false;
        if(root.left==null&&root.right==null)
            return root.val==targetSum;
        return hasPathSum(root.left, targetSum-root.val)||hasPathSum(root.right, targetSum-root.val);
    }
    //111 注意left或者right是空的特殊情况
    public int minDepth(TreeNode root){
        if(root==null)
            return 0;
        if(root.left==null)
            return minDepth(root.right)+1;
        if(root.right==null)
            return minDepth(root.left)+1;
        return 1+(Math.min(minDepth(root.left),minDepth(root.right)));
    }
    //404  左叶子 多路分支退出
    public int sumOfLeftLeaves(TreeNode root) {
        if(root==null)
            return 0;
        TreeNode left=root.left;
        if(left==null)
            return sumOfLeftLeaves(root.right);
        if(left.left==null&&left.right==null)
            return sumOfLeftLeaves(root.right)+left.val;
        return sumOfLeftLeaves(left)+sumOfLeftLeaves(root.right);
    }
    //257 注意叶子节点
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ret=new ArrayList<>();
        if(root==null)
            return null;
        if(root.left==null&&root.right==null){
            ret.add(String.valueOf(root.val));
        }
        List<String> left=binaryTreePaths(root.left),right=binaryTreePaths(root.right);
        for(String str:left){
            ret.add(root.val+"->"+str);
        }
        for(String str:right){
            ret.add(root.val+"->"+str);
        }
        return ret;
    }
    //113 回溯经典题目，
    List<List<Integer>> ret=new ArrayList<>();
    //onepath从根节点到root父亲节点路径，root当前处理节点，targetSum需要在root为根节点的子树中搜索的解
    public void pathSumCore(TreeNode root, int targetSum,List<Integer> onePath) {
        if(root==null)
            return;
        System.out.println("当前路径"+onePath.toString()+"添加节点"+root.val); 

        
        //在路径中添加
        onePath.add(root.val);
        if(root.left!=null)
            pathSumCore(root.left, targetSum-root.val,onePath);
        if(root.right!=null)
            pathSumCore(root.right, targetSum-root.val,onePath);
        
        
        
        if(root.left==null&&root.right==null&&targetSum==root.val){
            List <Integer> qualified=new ArrayList<>(onePath);//这里需要复制符合的路径
            //全局变量记录符合解
            ret.add(qualified);
            
            System.out.println("找到一条路径："+qualified.toString());                    
        }

        System.out.println("节点"+onePath.get(onePath.size()-1)+"已经处理完成");
        onePath.remove(onePath.size()-1);
        
    }
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<Integer> onePath=new ArrayList<>();

        pathSumCore(root,targetSum,onePath);
        return ret;
    }

    //129 其实可以使用presum*10 +root.val计算，这样速度更快
    //一般来说，自底向上的递归需要回溯
    //onepath从根节点到root父亲节点路径，root当前处理节点
    //List<Integer> 和 StringBuffer的速度差不多
    int sum=0;
    void  sumNumbersCore(TreeNode root,StringBuffer onePath) {
        if(root==null)
            return;
        // onePath.add(root.val);
        onePath.append((char)('0'+root.val));//这里要进行强制转换，否者+会被解释为连接
        if(root.left!=null)
            sumNumbersCore(root.left,onePath);
        if(root.right!=null)
            sumNumbersCore(root.right,onePath);
        if(root.left==null&&root.right==null){
            // System.out.println("找到一个根-叶子节点树："+toNum(onePath));   

            //全局变量记录符合解
            // sum+=toNum(onePath);

            System.out.println("找到一个根-叶子节点树："+onePath);
            sum+=Integer.parseInt(onePath.toString());   
            
                
        }
    
        System.out.println("节点"+root.val+"已经处理完成");
        // onePath.remove(onePath.size()-1);
        onePath.deleteCharAt(onePath.length()-1);
    }
    int toNum(List<Integer> onePath){
        int num=0;
        int size=onePath.size();
        for(int i=0;i<size;i++){
            num+=onePath.get(i)*Math.pow(10, size-1-i);
        }
        return num;
    }
    public int sumNumbers(TreeNode root) {
        // List<Integer> onePath=new ArrayList<>();
        StringBuffer onePath=new StringBuffer();
        sumNumbersCore(root, onePath);
        return sum;
    }
    int cnt=0;
    //437 任意节点路径和
    //onepath从根节点到root父亲节点路径，root当前处理节点，targetSum需要在root为根节点的子树中搜索的解
    public void pathSumCore(TreeNode root, int targetSum,int onePathSum) {
        if(root==null)
            return;
        System.out.println("当前和"+onePathSum+"添加节点"+root.val); 

        
        //在路径中添加
        onePathSum+=root.val;
        //这个if判断是否是符合条件的解，只要放在onePathSum+=root.val;（进入）和onePathSum-=root.val（回溯）之间就能正常执行


        if(onePathSum==targetSum){
            //全局变量记录符合解
            cnt++;
            System.out.println("找到一条路径");                    
        }
        if(root.left!=null)
            pathSumCore(root.left, targetSum,onePathSum);
        if(root.right!=null)
            pathSumCore(root.right, targetSum,onePathSum);
        
        
        

        System.out.println("节点"+root.val+"已经处理完成");
        onePathSum-=root.val;
        
    }
    //node 是起始节点，sum是目标值,双重递归
    public void pathSumBase(TreeNode node, int sum) {
        System.out.println("根节点选定为"+node.val);

        int onePathSum=0;
        pathSumCore(node,sum,onePathSum);
        if(node.left!=null)
            pathSumBase(node.left, sum);
        if(node.right!=null)
            pathSumBase(node.right, sum);
    }
    public int pathSum2(TreeNode root, int sum) {
        if(root==null)
            return 0;
        pathSumBase(root, sum);
        return cnt;
    }
    public static void main(String[] args) {
        BT bt=new BT();
        TreeNode root=TreeNode.createTestData("[1,null,2,null,null,null,3,null,null,4,null,5]");
        root.pprint();
        int ret=bt.pathSum2(root,3);
        System.out.println(ret);

    }
    
}
