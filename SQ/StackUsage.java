package SQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


//20 150 71
public class StackUsage {
    //20 括号
    public boolean isValid(String s) {
        
        Stack<Character> cStack=new Stack<>();
        HashMap<Character,Character> ccMap=new HashMap<>(Map.of('{', '}','(', ')','[', ']'));
        // ccMap.putAll(Map.of('{', '}','(', ')','[', ']'));
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ccMap.containsKey(ch)){
                cStack.push(ch);
            }
            else{
                if(cStack.isEmpty())
                    return false;
                if(ch!=ccMap.get(cStack.pop()))
                    return false;
            }
        }
        return cStack.isEmpty();
    }
    //150 波兰表达式 检测的时候可以使用逆向思维
    public int evalRPN(String[] tokens) {
        //其实可以使用数组模拟栈，速度会更快
        Stack<Integer> cStack=new Stack<>();
        int ret=0;
        for(String s:tokens){
            if(isNumber(s)){
                cStack.push(Integer.parseInt(s));
            }
            else{
                Integer val1=cStack.pop(),val2=cStack.pop();
                switch(s){
                    case("*"):{
                        ret=val2*val1;
                        break;
                    }
                    case("/"):{
                        ret=val2/val1;
                        break;
                    }
                    case("+"):{
                        ret=val1+val2;
                        break;
                    }
                    case("-"):{
                        ret=val2-val1;
                        break;
                    }
                }
                cStack.push(ret);
            }
        }
        return cStack.pop().intValue();
    }
     // 逆向思维
    boolean isNumber(String s) {
        return !s.equals("+") && !s.equals("-") && !s.equals("*") && !s.equals("/");
    }

    //71 栈中储存的是索引，类似于堆索引排序的思想
    public int[] dailyTemperatures(int[] T) {
        int length=T.length;
        int[] ret=new int[length];
        if(length==0)
            return ret;
        
        Stack<Integer> iStack=new Stack<>();
        iStack.push(0);
        
        for(int i=1;i<T.length;i++){
            while(!iStack.isEmpty()&&T[iStack.peek()]<T[i]){
                int popIndex=iStack.pop();
                ret[popIndex]=i-popIndex;
            }
            iStack.push(i);
        }
        while(!iStack.isEmpty()){
            ret[iStack.pop()]=0;
        }
        return ret;
    }
    public String simplifyPath(String path) {
        String[] parts=path.split("/");
        
        // System.out.println(Arrays.toString(parts));
        List<String> sList=new ArrayList<>(); //使用数组模拟堆栈，速度是快一些，不过Stack的继承的是Vector，速度差距不大
        for(String str:parts){
            if(str.equals("")||str.equals(" ")||str.equals(".")){
                continue;
            }
            else if(str.equals("..")){
                if(sList.isEmpty())
                    continue;//是continue而不是break
                sList.remove(sList.size()-1);
            }
            else
                sList.add(str);                
        }
        if(sList.isEmpty())
            return "/";
        StringBuilder ret=new StringBuilder();
        for(int i=0;i<sList.size();i++){  
            ret.append("/");
            ret.append(sList.get(i));
        }

        return ret.toString();

    }
    public static void main(String[] args) {
        StackUsage su=new StackUsage();
        String str=su.simplifyPath("/a/../../b/../c//.//");
        System.out.println(str);
        
    }
}
