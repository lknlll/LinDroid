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

    }
}