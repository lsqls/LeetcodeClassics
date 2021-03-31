package List;

// import java.util.HashSet;

import lib.leetcode.ListNode;

//当编码逻辑对头节点不起作用时，设置虚拟头节点
//203 82 21
public class DummyHead {
    //203 递归解法 迭代解法
    public ListNode removeElements(ListNode head, int val) {
        // if(head==null)
        //     return null;
        // if(head.val==val)
        //     return removeElements(head.next, val);
        // else{
        //     head.next=removeElements(head.next,val);
        //     return head;
        // }
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;
        ListNode cur=head,pre=dummyHead;
        while(cur!=null){
            if(cur.val==val){
                pre.next=cur.next;
            }
            else{
                pre=cur;
            }
            cur=cur.next;        
        }
        return dummyHead.next;
    }
    //82 删除重复值 1.暴力解法使用hashset记录重复值（没有利用排序性质）2.利用有序性，遇到重复值，直接移动到下一个不同值
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null)
            return head;
        ListNode dummyHead=new ListNode(Integer.MAX_VALUE/2);
        dummyHead.next=head;
        ListNode cur=head,pre=dummyHead,next=head.next;
        while(next!=null){
            if(cur.val==next.val){
                int duplicateVal=cur.val;
                cur=next.next;
                while(cur!=null&&cur.val==duplicateVal){
                    cur=cur.next;
                }
                pre.next=cur;
                if(cur==null)
                    break;
                next=cur.next;
            }
            else{
                pre=cur;
                cur=next;
                next=next.next;
            }
        }
        return dummyHead.next;
    }
    //21 链表合并问题，虚拟头节点
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyHead=new ListNode(-1);
        ListNode l1cur=l1,l2cur=l2;
        ListNode nh=dummyHead;
        while(l1cur!=null||l2cur!=null){
            
            int val1=l1cur==null?Integer.MAX_VALUE:l1cur.val;
            int val2=l2cur==null?Integer.MAX_VALUE:l2cur.val;
            if(val1<val2){
                nh.next=l1cur;
                nh=l1cur;
                l1cur=l1cur.next;
            }
            else{
                nh.next=l2cur;
                nh=l2cur;
                l2cur=l2cur.next;
            }
            
        }
        return dummyHead.next;
    }
    
    public static void main(String[] args) {
        DummyHead dh=new DummyHead();
        ListNode head1=ListNode.createTestData("[1,1,2,3,4,4,5,5]"),head2=ListNode.createTestData("[1,3,56,212]");
        // ListNode.print(head1);
        ListNode ret=dh.mergeTwoLists(head1,head2);
        ListNode.print(ret);

    }
}
