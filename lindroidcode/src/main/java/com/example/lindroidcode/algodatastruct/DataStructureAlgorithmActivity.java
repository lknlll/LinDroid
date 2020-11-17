package com.example.lindroidcode.algodatastruct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.R;

import static com.example.lindroidcode.algodatastruct.SingleLinkedListSolutions.ReverseListFromHead;
import static com.example.lindroidcode.algodatastruct.SingleLinkedListSolutions.ReverseListFromTail;

public class DataStructureAlgorithmActivity extends AppCompatActivity {

    ListNode head = new ListNode(10);
    ListNode a = new ListNode(20);
    ListNode b = new ListNode(30);
    ListNode c = new ListNode(40);
    ListNode tail = new ListNode(50);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structure_algorithm);

        TextView tvOrigin = findViewById(R.id.tv_origin);
        TextView tvResult = findViewById(R.id.tv_result);
        TextView tvResultB = findViewById(R.id.tv_result_path_b);
        head.next = a;
        a.next = b;
        b.next = c;
        c.next = tail;

        SingleLinkedListSolutions.showList(tvOrigin,head);

        ListNode nHead = ReverseListFromHead(head);
        /**
         * f(10) -> f(20) pause
         * f(20) -> f(30) pause
         * f(30) -> f(40) pause
         * f(40) -> f(50) pause
         * f(50) = N(50)
         * f(40) continue: N(40).next.next = N(40); N(40).next = null; f(40) = N(50);
         * f(30) continue: N(30).next.next = N(30); N(30).next = null; f(30) = N(50);
         * f(20) continue: N(20).next.next = N(20); N(20).next = null; f(20) = N(50);
         * f(10) continue: N(10).next.next = N(10); N(10).next = null; f(10) = N(50);
         */

        SingleLinkedListSolutions.showList(tvResult,nHead);

        ListNode nHeadB = ReverseListFromTail(nHead);

        SingleLinkedListSolutions.showList(tvResultB,nHeadB);

        TextView tvLru = findViewById(R.id.tv_lru);

        tvLru.append("\nLRU 最近最少使用");
        tvLru.append("\nget时视为被使用");
        tvLru.append("\nput新元素时若缓存已满则移除最近最少使用的元素");
        tvLru.append("\n思路：HashMap + 双链表");
        tvLru.append("\nHashMap用于缓存key和节点");
        tvLru.append("\n初始化size为0，head和tail不计算size");
        tvLru.append("\n每使用（get）一个移至队首");
        tvLru.append("\nput时先通过map缓存看表内是否存在");
        tvLru.append("\n存在时就将该节点移至队首");
        tvLru.append("\n不存在就创建该节点,增加到map，添加到队首后，将tail前节点（真实的队尾）移除");
        tvLru.append("\n");

        LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

        cache.put(1, 1);
        cache.put(2, 2);
        tvLru.append("\ncache.get(1) = " + cache.get(1));       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        tvLru.append("\ncache.get(2) = " + cache.get(2));       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        tvLru.append("\ncache.get(1) = " + cache.get(1));       // 返回 -1 (未找到)
        tvLru.append("\ncache.get(3) = " + cache.get(3));       // 返回  3
        tvLru.append("\ncache.get(4) = " + cache.get(4));       // 返回  4

        TextView tvReverseDigitsOnly = findViewById(R.id.tv_reverse_digits_in_string);
        tvReverseDigitsOnly.append("\nString str = \"abc123cde456fgh\"");
        String str = ReverseOnlyDigits.reverseOnlyDigits("abc123cde456fgh");
        tvReverseDigitsOnly.append("\n" + str);
        String strB = ReverseOnlyDigits.reverseOnlyDigitsSaveMemory(str);
        tvReverseDigitsOnly.append("\n" + strB);
    }
}