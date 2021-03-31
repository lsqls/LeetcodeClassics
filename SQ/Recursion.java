package SQ;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import lib.leetcode.TreeNode;

//144 94 145 341
//144 94 145 递归操作可以轻松转化为命令栈,这与系统栈是更匹配的（使用枚举变量表示操作，以及压入顺序是与递归逻辑相反的）
public class Recursion {
    enum CommandType{
        GO,
        PRINT
    }
    class Command{
        TreeNode node;
        CommandType commandType;
        Command(TreeNode n,CommandType ct){
            node=n;
            commandType=ct;

        }
        void execute(Stack<Command> sc,List<Integer> ret){
            if(node==null)
                return;
            switch (commandType){
                case GO:{//不用使用CommandType枚举名
                    if(node.right!=null)
                        sc.push(new Command(node.right, CommandType.GO));
                    
                    sc.push(new Command(node, CommandType.PRINT));

                    if(node.left!=null)
                        sc.push(new Command(node.left, CommandType.GO));
                    
                    break;
                }
                case PRINT:{
                    // System.out.println(node.val);
                    ret.add(node.val);
                }
				default:
					break;
                
                }
        }
            
        
    }
    
    public List<Integer> preorderTraversal(TreeNode root){
        
        List<Integer> ret=new LinkedList<>();
        Stack<Command> sc=new Stack<>();
        sc.push(new Command(root, CommandType.GO));
        while(!sc.isEmpty()){
            sc.pop().execute(sc, ret);
        }
        return ret;
        
    }
    public static void main(String[] args) {
        Recursion rc=new Recursion();
        
        TreeNode root=TreeNode.createTestData("[1,3,4,6,74]");
        root.pprint();
        rc.preorderTraversal(root);
    }
}   
