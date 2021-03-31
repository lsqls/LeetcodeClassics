package BackTracking;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


//能够转换为树形问题的回溯，解决问题的结构隐藏在一棵树中，所以经常使用递归求解
//在递归问题中，需要明确【函数与变量的定义】，以及清晰的处理【过程】
//Tips： 打印中间变量，更好的理解回溯的过程，甚至比断点调试更好
// 递归调用的一个重要特征：要返回
//回溯可以用来查找树中的解
//回溯法是暴力解法的一个主要手段（往往是多重循环无法实现的暴力解法）
//17 93 131
public class Tree {
    //17 排列经典回溯问题，画出递归树，定义好递归函数的参数，全局变量，退出条件可解
    List <String> lRet=new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if(digits.length()==0)
            return lRet;//注意下leetcode的返回
        StringBuffer sb=new StringBuffer("");
        letterCombinations(digits,0,sb);
        System.out.println(lRet.toString());
        return lRet;
    }
    


    char [][] map={
        {'a','b','c'},
        {'d','e','f'},
        {'g','h','i'},
        {'j','k','l'},
        {'m','n','o'},
        {'p','q','r','s'},
        {'t','u','v'},
        {'w','x','y','z'}
    };

    //onepath [0,charIndex) 生成的字符串 ，charIndex 处理的字符索引,digits 处理的字符串
    void letterCombinations(String digits,int charIndex,StringBuffer onePath){       
        if(charIndex==digits.length()){
            //已经到null节点
            lRet.add(onePath.toString());
            return;
        } 
        for(char ch:map[digits.charAt(charIndex)-'2']){
            onePath.append(ch);
            letterCombinations(digits,charIndex+1,onePath);
            onePath.deleteCharAt(onePath.length()-1);
        }
    }
    // void letterCombinations(String digits,int charIndex,StringBuffer onePath){       
    //     for(char ch:map[digits.charAt(charIndex)-'2']){
    //         onePath.append(ch);
    //         if(charIndex==digits.length()-1){
    //             lRet.add(onePath.toString());//检查递归树的叶子节点 事实上由于该递归树的结构性，检测null节点更快
    //         }
    //         else{
    //             letterCombinations(digits,charIndex+1,onePath);
    //         }
    //         onePath.deleteCharAt(onePath.length()-1);
    //     }
    // }
    
    //93
    //不要用string保存 当前生成的ip段
    //for循环中,剪枝使用continue 
    public List<String> restoreIpAddresses(String s) {
        List<String> ret=new ArrayList<>();
        Deque<String> generateStr=new LinkedList<>();
        restoreIpAddressesCore(s,0,0,generateStr,ret);
        System.out.println(ret.toString());
        return ret;

    }
    // string s   
    // dot  添加的点数,start 当前的处理点

    public void restoreIpAddressesCore(String s,int dot,int start,Deque<String> generateStr,List<String> ret) {
        System.out.println("当前dot "+dot+"  start "+start+"  generateStr "+generateStr.toString());
        
        if(dot==4){
            if(start==s.length()){
                System.out.println(generateStr+"符合条件");
                ret.add(toIP(generateStr));
            }
            else{
                System.out.println(generateStr.toString()+"不符合条件");
            }
            System.out.println("dot==4,退出");
            return;
        }
        dot++;
        for(int i=1;i<=3;i++){
            int end=start+i;
            // 获取[start,end)之间的字符
            if(end>s.length()){
                System.out.println("超出范围退出");
                continue;
            }


            //isvalid 功能,用于剪枝
            String addStr=s.substring(start,end);
            System.out.println("待添加"+addStr);
            if(!isValid(addStr))
                continue;


            generateStr.addLast(addStr);
            System.out.println("添加成功，生成"+generateStr);
            restoreIpAddressesCore(s, dot, end, generateStr,ret); 
            generateStr.removeLast();
        }
    }
    boolean isValid(String addStr){
        if(addStr.charAt(0)=='0'&&addStr.length()>1){
            System.out.println("含有前导0，退出");
            return false;
        }
        if(Integer.parseInt(addStr)>255){
            System.out.println("数字大于255，退出");
            return false;
        }
        return true;
    }
    String toIP(Deque<String> generateStr){
        StringBuffer sb=new StringBuffer();
        for(String str:generateStr){
            sb.append(str).append('.');
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    //131  分割问题都可以使用回溯法解决
    //分割问题的标准的回溯写法
    public List<List<String>> partition(String s) {
        
        List<List<String>> ret=new ArrayList<>();
        if(s.length()==0)
            return ret;
        Deque<String> path=new LinkedList<>();
        partitionCore(s, 0, path, ret);
        System.out.println(ret.toString());
        return ret;
    }
    /**
     * @param s
     * @param start 起始字符的索引
     * @param path  记录从根结点到叶子结点的路径
     * @param res   记录所有的结果
     */
    public void partitionCore(String s,int start, Deque<String> path ,List<List<String>> ret) {

        if(start==s.length()){
            ret.add(new ArrayList<>(path));
            return;
        }

        for(int i=start;i<s.length();i++){

            if(!isValid(s,start,i))
                continue;
            //剪枝
            path.addLast(s.substring(start, i+1));
            partitionCore(s,i+1,path,ret);
            path.removeLast();

        }
        
    }
    
    /**
     * @param str
     * @param left  子串的左边界，可以取到
     * @param right 子串的右边界，可以取到
    */
    private boolean isValid(String str, int left, int right) {
        // 严格小于即可
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


    public static void main(String[] args) {
        Tree t=new Tree();
        t.restoreIpAddresses("010010");
    }
}
