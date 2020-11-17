package com.example.lindroidcode.algodatastruct;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    class ListNode{
        ListNode pre;
        ListNode next;
        int key;
        int value;

        ListNode(){

        }
        ListNode(int k, int v){
            key = k;
            value = v;
        }
    }
    Map<Integer,ListNode> map = new HashMap<>();
    int size;
    int capacity;
    ListNode head;
    ListNode tail;

    public LRUCache(int capacity){
        size = 0;
        this.capacity = capacity;
        head = new ListNode();
        tail = new ListNode();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        ListNode currentNode = map.get(key);
        if (currentNode == null) {
            return -1;
        }
        moveToHead(currentNode);
        return currentNode.value;
    }

    public void put(int key, int value) {

        ListNode temp = map.get(key) ;
        if (temp == null) {
            ListNode node = new ListNode(key,value);
            map.put(key,node);
            add(node);
            size ++;
            if (size > capacity) {
                ListNode lruNode = tail.pre;
                map.remove(lruNode.key);
                remove(lruNode);
            }
        }else {
            temp.value = value;
            moveToHead(temp);
        }
    }

    //队首加一个新节点
    void add(ListNode listNode){
        listNode.pre = head;
        listNode.next = head.next;
        head.next.pre = listNode;
        head.next = listNode;
    }

    //移除一个已存在节点
    void remove(ListNode listNode){
        listNode.pre.next = listNode.next;
        listNode.next.pre = listNode.pre;
    }
    //将一个已存在节点移至队首
    void moveToHead(ListNode listNode){
        remove(listNode);
        add(listNode);
    }
}
