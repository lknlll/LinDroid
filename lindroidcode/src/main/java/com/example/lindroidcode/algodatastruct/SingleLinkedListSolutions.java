package com.example.lindroidcode.algodatastruct;

import android.util.Log;
import android.widget.TextView;

public class SingleLinkedListSolutions {

    private static final String TAG = SingleLinkedListSolutions.class.getSimpleName();

    /**
     * 从头开始反转
     * @param head
     * @return
     */
    public static ListNode ReverseListFromHead(ListNode head) {
        if(head == null){
            return null;
        }

        ListNode pre = head;
        ListNode current = head.next;
        ListNode temp;
        pre.next = null;

        while(current != null){
            Log.e(TAG, "ReverseList entry: " + current.val);
            //原链中当前节点的下一节点先做缓存备用
            temp = current.next;
            //当前节点指向原链的前一节点
            current.next = pre;//
            //前节点变为指向已经变化的当前节点，为下次交换所用
            pre = current;//
            //当前节点变成缓存好的原链的下一节点
            current = temp;
            if (current != null) {
                Log.e(TAG, "ReverseList end: " + current.val);
            }else {
                Log.e(TAG, "ReverseList end with null current");
            }
        }

        return pre;
    }

    /**
     * 从尾开始反转
     * @param head
     * @return
     */
    public static ListNode ReverseListFromTail(ListNode head) {
        if(head == null){
            return null;
        }
        Log.e(TAG, "ReverseListFromTail node val: " + head.val );

        if (head.next == null) {
            Log.e(TAG, "ReverseListFromTail find tail: " + head.val);
            return head;
        }
        ListNode reHead = ReverseListFromTail(head.next);
        Log.e(TAG, "ReverseListFromTail reHead: " + reHead.val);

        head.next.next = head;
        head.next = null;
        return reHead;
    }



    static ListNode headToTail(ListNode head){
        ListNode nHead = head.next;
        ListNode tempNode = head;

        while(tempNode.next != null){
            tempNode = tempNode.next;
        }
        tempNode.next = head;
        head.next = null;
        return nHead;
    }

    static void showList(TextView tv, ListNode head){
        ListNode h = head;
        while (h != null){
            tv.append(h.val + ", ");
            h = h.next;
        }
    }
}
