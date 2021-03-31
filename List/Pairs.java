package List;


import lib.leetcode.ListNode;

//24 25 147 148
public class Pairs {
    //24 两两交换 1.递归 2迭代需要设立虚拟头节点（和四个指针），事实上有对头节点进行操作的算法都建议设置一个头节点
    public ListNode swapPairs(ListNode head) {
        if(head==null||head.next==null)
            return head;
        ListNode cur=head,next=head.next,toSwapPais=next.next;
        next.next=cur;
        cur.next=swapPairs(toSwapPais);
        return next;
    }
    //25 比较复杂的穿针引线，迭代需要设立虚拟头节点和和k+2个指针，
    //事实上可以用O(1)空间，实现翻转链表【reverse函数参数（起始点节点，k）】【k<=length 返回翻转链表的头指针和之后的指针】【k>length返回原本链表】反转后要保持原链表连续结构】，
    //一个头节点用于合并数组
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;
        ListNode[] nodes=new ListNode[k];
        ListNode p=dummyHead,cur=p,kNodesNext;
        while(p!=null){
            cur=p;
            for(int i=0;i<k;i++){
                cur=cur.next;
                if(cur==null){
                    break;
                }
                nodes[i]=cur;
            }
            //加上判断
            if(cur==null)
                break;
            kNodesNext=nodes[k-1].next;
            p.next=nodes[k-1];
            for(int i=k-1;i>=1;i--){
                nodes[i].next=nodes[i-1];
            }
            nodes[0].next=kNodesNext;
            p=nodes[0];
        }
        return dummyHead.next;
    }
    //147 插入排序 
    public ListNode insertionSortList(ListNode head) {
        if(head==null||head.next==null)
            return head;
        
        ListNode dummyHead=new ListNode(-Integer.MAX_VALUE);
        ListNode node=head.next;//node:待插入node 
        dummyHead.next=head;
        head.next=null;
        // ListNode sorted=head;//优化。记录一个排序好部分的最后节点
        while(node!=null){
            ListNode cmppre=dummyHead;
            ListNode cmp=dummyHead.next;
            while(cmp!=null){
                if(cmp.val>node.val)
                    break;
                cmppre=cmppre.next;
                cmp=cmp.next;
                
            }
            
            ListNode saveNext=node.next;
            
            cmppre.next=node;
            node.next=cmp;
            // sorted=node;
            node=saveNext;
        }  
        return dummyHead.next;

    }
    public static void main(String[] args) {
        Pairs p=new Pairs();
        ListNode head=ListNode.createTestData("[100,2,7,4,3,6,3]");
        ListNode ret=p.insertionSortList(head);
        ListNode.print(ret);
    }
    
}
