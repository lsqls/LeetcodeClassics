package List;

import java.util.Stack;

import lib.leetcode.ListNode;

/*
1.链表的问题经常要设立多指针保存信息
2.访问val和next要保证node不为null

*/
//206 92 83 86 328 2 445

//206 递归解法，多指针解法,注意头节点和尾节点的处理
public class Reverse {
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null)
            return head;
        //递归版本
        // ListNode headNext=head.next;
        // ListNode ret=reverseList(head.next);
        // headNext.next=head;
        // head.next=null;
        // return ret;
        ListNode dummyHead=new ListNode(-1);//添加一个虚假的节点方便很多
        dummyHead.next=head;
        ListNode pre=dummyHead,cur=pre.next,next=cur.next;
        while(next!=null){
            cur.next=pre;
            pre=cur;
            cur=next;
            next=next.next;
        }
        head.next=null;
        cur.next=pre;
        return cur;
        
    }
    //92 翻转部分链表 ，使用一个变量记录当前节点是第几个
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummyHead=new ListNode(-1);//添加一个虚假的节点方便很多
        dummyHead.next=head;
        ListNode reverseHead=head,preRH=dummyHead;
        for(int i=1;i<m;i++){
            preRH=reverseHead;
            reverseHead=reverseHead.next;

        }
        
        ListNode pre=preRH,cur=pre.next,next=cur.next;
        int reverseNum=0;
        while(reverseNum<=(n-m)){
            if(next==null){
                //对【m==链表长度】和【m==链表长度-1】进行处理
                cur.next=pre;
                pre=cur;
                cur=next;
                break;
            }
            cur.next=pre;
            pre=cur;
            cur=next;
            next=next.next;
            reverseNum++;
        }
        
        preRH.next=pre;
        reverseHead.next=cur;
        return dummyHead.next;

    }
    //83 1.暴力解法 使用set,保留第一个出现的(没有使用排序条件) 2.使用排序xingzhi，选最后一个出现的值 3.递归，由于是排序的，可以将head和deleteDuplircates(head.next)的返回值比较
    public ListNode deleteDuplircates(ListNode head) {
        if(head==null)
            return null;
        ListNode dummyHead=new ListNode(-1);
        ListNode cur=head,next=cur.next;
        ListNode newLink=dummyHead;
        while(next!=null){
            
            if(cur.val!=next.val){
                newLink.next=cur;
                newLink=cur;
            }
            
            cur=cur.next;
            next=next.next;
            
        }
        //最后一个数是必定选入的
        newLink.next=cur;
        return dummyHead.next;
    }
    //86 一般的分类问题都可以通过加入虚拟头节点解决
    public ListNode partition(ListNode head, int x) {
        ListNode smallHead=new ListNode(x-1),largeHead=new ListNode(x+1);//equalHead=new ListNode(x);
        ListNode sh=smallHead,lh=largeHead;//eh=equalHead;
        ListNode cur=head;
        while(cur!=null){
            if(cur.val<x){
                sh.next=cur;
                sh=cur;
            }
            else{
                lh.next=cur;
                lh=cur;
            }
            cur=cur.next;
        }
      
        sh.next=largeHead.next;
        lh.next=null;
        return smallHead.next;
    }
    //328 依旧是分类问题，虚头节点
    public ListNode oddEvenList(ListNode head) {
        ListNode oddHead=new ListNode(101),evenHead=new ListNode(100);
        int i=1;
        ListNode cur=head,oh=oddHead,eh=evenHead;
        while(cur!=null){
            if(i%2==1){
                oh.next=cur;
                oh=cur;
            }
            else{
                eh.next=cur;
                eh=cur;
            }
            cur=cur.next;
            i++;
        }
        // ListNode.print(oddHead);
        // ListNode.print(evenHead);
        oh.next=evenHead.next;
        eh.next=null;//记得指向null，否则会在奇数长度出错
        return oddHead.next;
        
    }
    //2 链表加法 模拟加法，注意最后是否有进位就行
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // ListNode l1reverse=reverseList(l1),l2reverse=reverseList(l2);
        ListNode l1cur=l1,l2cur=l2;
        int c=0;
        ListNode dummyHead=new ListNode(-1);
        ListNode nh=dummyHead;
        while(l1cur!=null||l2cur!=null){
            int val1=l1cur==null?0:l1cur.val;
            int val2=l2cur==null?0:l2cur.val;
            int sum=val1+val2+c;
            
            c=sum/10;

            ListNode node=new ListNode(sum>9?sum-10:sum);//减法比取余快一些
            nh.next=node;
            nh=node;
            if(l1cur!=null)
                l1cur=l1cur.next;
            if(l2cur!=null)
                l2cur=l2cur.next;
        }
       
        if(c!=0){
            ListNode node=new ListNode(c);
            nh.next=node;
            nh=node;
        }
        return dummyHead.next;

    }
    //445 由于不能改变链表结构，需要使用堆栈模拟加法【处理链表问题，在允许使用堆栈的时候经常会用到堆栈】
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        Stack<Integer> si1=new Stack<>(),si2=new Stack<>();
        ListNode l1cur=l1,l2cur=l2;
        while(l1cur!=null){
            si1.push(l1cur.val);
            l1cur=l1cur.next;
        }
        while(l2cur!=null){
            si2.push(l2cur.val);
            l2cur=l2cur.next;
        }
        ListNode dummyHead=new ListNode(-1);

        int c=0;
        while(!si1.isEmpty()||!si2.isEmpty()||c!=0){
            int val1=si1.isEmpty()?0:si1.pop();
            int val2=si2.isEmpty()?0:si2.pop();
            int sum=val1+val2+c;
            
            c=sum/10;

            ListNode node=new ListNode(sum>9?sum-10:sum);//减法比取余快一些
            node.next=dummyHead.next;
            dummyHead.next=node;
        }
        
        return dummyHead.next;

    }
    public static void main(String[] args) {
        Reverse r=new Reverse();
        ListNode head1=ListNode.createTestData("[7,2,4,3]"),head2=ListNode.createTestData("[5,6,4]");
        ListNode ret=r.addTwoNumbers2(head1,head2);
        ListNode.print(ret);
    } 
}
