package SQ;

// import java.util.Arrays;
// import java.util.Comparator;
import java.util.HashMap;
// import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;

// import lib.function;
import lib.leetcode.ListNode;

//347 23
/*
默认的PriorityQueue并非保证了整个队列都是有序的，只是保证了队头是最小的
最大堆
PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> {
    return b - a;
});
*/
public class PQueue {
    
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> iMap=new HashMap<>();
        PriorityQueue<Entry<Integer,Integer>> priorityQueue = new PriorityQueue<>(k,(a,b)->{
            return b.getValue()-a.getValue();
        });
        for(int num:nums){
            if(iMap.containsKey(num))
                iMap.put(num, iMap.get(num)+1);
            else
                iMap.put(num,1);
        }
        for(Entry<Integer,Integer> e:iMap.entrySet()){
            // System.out.println(e.getKey()+" "+e.getValue());
            priorityQueue.add(e);
        }
        int[] ret=new int[k];
        for(int i=0;i<k;i++){
            ret[i]=priorityQueue.poll().getKey();
        }
        // System.out.println(Arrays.toString(ret));
        return ret;

    }
    //23 多路归并问题
    //使用堆和Entry 完成当前归并的链表选取（类似于双路排序中的两值比较）
    //事实上 entry完全可以使用ListNode替代 
    class myEntry{
        int key;
        int value;
        myEntry(int k,int v){
            key=k;
            value=v;
        }
    }
    public ListNode mergeKLists(ListNode[] lists) {
        
        PriorityQueue<myEntry> priorityQueue = new PriorityQueue<>(lists.length+1,(a,b)->{
            return a.value-b.value;
        });
        for(int i=0;i<lists.length;i++){
            if(lists[i]!=null)
                priorityQueue.add(new myEntry(i,lists[i].val));
            // lists[i]=lists[i].next; 这里不需要去除链首

        }
        ListNode dummyHead=new ListNode(-1);

        ListNode p=dummyHead;
        while(!priorityQueue.isEmpty()){
            myEntry mEntry=priorityQueue.poll();
            ListNode node=new ListNode(mEntry.value);
            p.next=node;
            p=node;
            int selectList=mEntry.key;
            lists[selectList]=lists[selectList].next;

            if(lists[selectList]!=null){
                priorityQueue.add(new myEntry(selectList,lists[selectList].val));
            }
        }
        p.next=null;
        return dummyHead.next;
    }
    public static void main(String[] args) {
        PQueue pq=new PQueue();

        ListNode[] lists={ListNode.createTestData("[1,4,5]"),ListNode.createTestData("[1,3,4]"),ListNode.createTestData("[2,6]")};
        ListNode ret=pq.mergeKLists(lists);
        ListNode.print(ret);
    }
    
}