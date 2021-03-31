package List;

import lib.leetcode.ListNode;

//19 61 143 234
public class TwoPointers {
    // 19 删除倒数第n个节点，需要找到倒数第n+1 个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;
        //有可能删除头节点，所以需要添加虚拟头节点
        ListNode fast=dummyHead,slow=dummyHead;
        
        //倒数第n+1个节点到null需要n+1步,所以迭代n+1次
        for(int i=0;i<n+1;i++){
            fast=fast.next;
        }
        while(fast!=null){
            slow=slow.next;
            fast=fast.next;
        }
        slow.next=slow.next.next;
        return dummyHead.next;
    }
    //61 旋转 也是要找到倒数第n=k+1 个节点
    public ListNode rotateRight(ListNode head, int k) {
        if(k==0||head==null||head.next==null)
            return head;
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;
        //有可能删除头节点，所以需要添加虚拟头节点
        ListNode fast=dummyHead,slow=dummyHead,last;
        
        //倒数第k+1个节点到null需要k+1步,所以迭代k+1次
        //加入k>size
        int i;
        for(i=0;i<k+1;i++){
            fast=fast.next;
            if(fast==null)
                break;
        }
        //k的限制是非负整数,注意处理，事实上可以理解为环
        if(fast==null){
            k=k%i;
            return rotateRight(head, k);
        }

        while(fast!=null){
            slow=slow.next;
            fast=fast.next;
        }
        ListNode newHead=slow.next;
        last=newHead;

        while(last.next!=null){
            last=last.next;
        }
        slow.next=null;
        last.next=dummyHead.next;
        return newHead;
    }
    //143 寻找中间节点(这里找到的是奇数链表的中间节点,或者偶数链表两个中间节点偏[后]那一个)
    public void reorderList(ListNode head) {
        if(head==null||head.next==null||head.next.next==null)
            return;
        ListNode fast=head,slow=head;
        while(fast!=null){
            fast=fast.next;
            if(fast==null)
                break;
            fast=fast.next;
            slow=slow.next;
        }

        ListNode node=slow.next;
        
        slow.next=null;
        node=reverseList(node);
        ListNode insertPre=head;
        while(node!=null){
            ListNode nextToInsert=node.next;
            //插入
            ListNode next=insertPre.next;
            insertPre.next=node;
            node.next=next;
            insertPre=next;
            //插入介素，移动到下一个插入点
            node=nextToInsert;
        }
    }
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null)
            return head;
        // 递归版本
        ListNode headNext=head.next;
        ListNode ret=reverseList(head.next);
        headNext.next=head;
        head.next=null;
        return ret;
    }
    //234 回文 寻找中间节点(这里找到的是奇数链表的中间节点,或者偶数链表两个中间节点偏[前]那一个)
    public boolean isPalindrome(ListNode head) {
        if(head==null||head.next==null)
            return true;
        ListNode fast=head,slow=head;
        while(true){//前面已经避免了head==null的情况
            fast=fast.next;
            if(fast==null)
                break;
            fast=fast.next;
            if(fast==null)//寻找中间节点(这里找到的是奇数链表的中间节点,或者偶数链表两个中间节点偏前那一个)
                break;
            slow=slow.next;
        }
        ListNode head2=slow.next;
        
        slow.next=null;
        head2=reverseList(head2);

        ListNode cur1=head,cur2=head2;
        while(cur2!=null){//只检测cur2就可以了
            if(cur1.val!=cur2.val)
                return false;
            cur1=cur1.next;
            cur2=cur2.next;
        }
        

        return true;
    }
    public static void main(String[] args) {
        TwoPointers tp=new TwoPointers();
        ListNode head=ListNode.createTestData("[1,2,2,2,1]");
        
        boolean ret=tp.isPalindrome(head);
        System.out.println(ret);

    }
}
