package BT;


import lib.leetcode.TreeNode;

//235 98 450 108 230 236
//要利用二叉树独有的性质对递归过程进行剪枝
public class BST {
    //235 搜索二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(p.val>root.val&&q.val>root.val)
            return lowestCommonAncestor(root.right, p, q);
        if(p.val<root.val&&q.val<root.val)
            return lowestCommonAncestor(root.left, p, q);
        return root;
    }
    //98 判断二叉搜索树
    //二叉搜索树的中序遍历有序，所以修改中序遍历可得解法，注意下边界值[-2147483648] 测试用例即可
    // void inOrder(TreeNode node){
    //     if(node==null)
    //         return;
    //     inOrder(node.left);
    //     codr for  cur node 
    //     inOrder(node.right);
    // }
    long preVal=Long.MIN_VALUE;
    public boolean isValidBST(TreeNode root) {
        if(root==null)
            return true;
        if(!isValidBST(root.left))
            return false;
        
        //检查中序遍历的有序性
        if(preVal>=(long)root.val){
            return false;
        }
        preVal=root.val;
        
        if(!isValidBST(root.right))
            return false;
        return true;
    }

    
    //450 删除二叉搜索树的节点（递归思想，把删除和搜索融合在一个递归函数中）
    //搜索以root 为根的二叉搜索树中的最小节点，返回最小节点
    TreeNode mininum(TreeNode root){
        if(root==null)
            return null;
        if(root.left==null)
            return root;
        else
            return mininum(root.left);
    }
    
    //删除掉以root 为根的二叉搜索树中的最小节点，返回新树根
    TreeNode deleteMin(TreeNode root){
        if(root==null)
            return null;
        
        if(root.left==null){
            return root.right;
        }
        root.left=deleteMin(root.left);//如果左子树不为空，最小节点必定在左子树中
        return root;
    }

    //删除掉以root为根的二叉搜索树中的值为val的节点，返回新树根
    //把搜索和删除融合在一起
    TreeNode deleteNode(TreeNode root,int val){
        if(root==null)
            return null;
        if(root.val==val){
            if(root.left==null){
                return root.right;
            }
            else if(root.right==null){
                return root.left;
            }
            else{
                TreeNode successor=mininum(root.right);//搜索后继节点
                successor.right=deleteMin(root.right);
                successor.left=root.left;
                return successor;
            }

        }
        else if(root.val>val)
            root.left=deleteNode(root.left, val);
        else
            root.right=deleteNode(root.right, val);
        return root;

        
    }
    //108 平衡就是元素个数均分
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums,0,nums.length-1);
    }
    public TreeNode sortedArrayToBST(int[] nums,int left,int right) {
        if(left>right)
            return null;
        int mid=(left+right)/2;
        TreeNode root=new TreeNode(nums[mid]);
        root.left=sortedArrayToBST(nums,left,mid-1);
        root.right=sortedArrayToBST(nums,mid+1,right);
        return root;
        
    }
    //230 中序遍历，记录顺序
    int cnt=0;
    int kRet;
    void inOrder(TreeNode node,int k){
        if(node==null)
            return;
        inOrder(node.left,k);
        if(++cnt==k){
            kRet=node.val;
            return;
        }
        inOrder(node.right, k);
    }
    public int kthSmallest(TreeNode root, int k) {
        inOrder(root, k);
        return kRet;
    }
    //236 二叉树的最近公共祖先
    //root 当前根节点，p和q要搜索的的节点，返回最近的公共祖先
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null)
            return null;
        // if(root==q||root==p)不能直接使用==符号判断
        //     return root;
        if(root.val==q.val||root.val==p.val)
            return root;
        
        TreeNode PorQinLeft=lowestCommonAncestor2(root.left, p, q);
        
        TreeNode PorQinRight=lowestCommonAncestor2(root.right, p, q);
        //version 1 
        // if(PorQinLeft==null)
        //     return PorQinRight;
        // else if(PorQinRight==null)
        //     return PorQinLeft;
        // else
        //     return root;
        //一句话
        return PorQinLeft==null?PorQinRight:(PorQinRight==null?PorQinLeft:root);


        
        //version 2 这样判断速度比version1慢
        // if(PorQinLeft!=null&&PorQinRight!=null)
        //     return root;
        // else
        //     return PorQinLeft!=null?PorQinLeft:PorQinRight;
        //根据题目的限制，PorQinLeft==null&&PorQinRight==null的情况不会出现
    }
    public static void main(String[] args) {
        BST bst=new BST();
        TreeNode root=TreeNode.createTestData("[3,5,1,6,2,0,8,null,null,7,4]");
        root.pprint();
        TreeNode p=new TreeNode(4),q=new TreeNode(0);
        TreeNode ret=bst.lowestCommonAncestor2(root, p,q);
        System.out.println(ret.val);

    }
}
