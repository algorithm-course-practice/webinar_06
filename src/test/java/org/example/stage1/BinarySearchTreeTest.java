package org.example.stage1;

import org.example.stage1.BinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.vanyo.treePrinter.TreePrinter;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();
    TreePrinter<BinarySearchTree.Node<Integer>> printer = new TreePrinter<>((x) -> String.valueOf(x.value), BinarySearchTree.Node::getLeft, BinarySearchTree.Node::getRight);

    {
        printer.setHspace(1);
        printer.setHspace(1);
        printer.setSquareBranches(true);
    }

    void print() {
        printer.printTree(bst.getRoot());
    }

    @BeforeEach
    void setUp() {
        bst.add(5);
        bst.add(3);
        bst.add(1);
        bst.add(4);
        bst.add(2);

        bst.add(8);
        bst.add(6);
        bst.add(7);
        bst.add(10);
        bst.add(9);
        print();
    }

    void setUp100() {
        bst.add(50);
        bst.add(30);
        bst.add(10);
        bst.add(40);
        bst.add(20);

        bst.add(80);
        bst.add(60);
        bst.add(70);
        bst.add(100);
        bst.add(90);
        print();
    }

    @Test
    void leftParentRightIterate() {
        Iterable<Integer> lpr = bst.leftParentRightIterate();
        System.out.println(lpr);
        assertEquals(asList(1,2,3,4,5,6,7,8,9,10), lpr);
    }

    @Test
    void parentLeftRight() {
        Iterable<Integer> plr = bst.parentLeftRight();
        System.out.println(plr);
        assertEquals(asList(5,3,1,2,4,8,6,7,10,9), plr);
    }

    @Test
    void leftRightParent() {
        Iterable<Integer> lrp = bst.leftRightParent();
        System.out.println(lrp);
        assertEquals(asList(2,1,4,3,7,6,9,10,8,5), lrp);
    }

    @Test
    void getSubSet(){
        List<Integer> subSet = bst.getSubSet(2, 8);
        System.out.println(subSet);
        assertEquals(asList(3,4,5,6,7), subSet);
    }

    @Test
    void getMin(){
        assertEquals(1, bst.getMin());
    }

    @Test
    void getMax(){
        assertEquals(10, bst.getMax());
    }

    @Test
    void getNext(){
        checkNext();
        bst.clear();
        for(int i = 1; i < 11; i++){
            bst.add(i);
        }
        print();
        checkNext();

        bst.clear();
        for(int i = 10; i > 0; i--){
            bst.add(i);
        }
        print();
        checkNext();

        bst.clear();
        setUp100();
        checkNext100();
    }

    private void checkNext() {
        assertEquals(1,bst.getNext(-1));
        assertEquals(1,bst.getNext(0));
        assertEquals(2,bst.getNext(1));
        assertEquals(3,bst.getNext(2));
        assertEquals(4,bst.getNext(3));
        assertEquals(5,bst.getNext(4));
        assertEquals(6,bst.getNext(5));
        assertEquals(7,bst.getNext(6));
        assertEquals(8,bst.getNext(7));
        assertEquals(9,bst.getNext(8));
        assertEquals(10,bst.getNext(9));
        assertEquals(null,bst.getNext(10));
        assertEquals(null,bst.getNext(11));
    }

    private void checkNext100(){
        assertEquals(10,bst.getNext(-10));
        assertEquals(10,bst.getNext(5));
        assertEquals(20,bst.getNext(15));
        assertEquals(30,bst.getNext(25));
        assertEquals(40,bst.getNext(35));
        assertEquals(50,bst.getNext(45));
        assertEquals(60,bst.getNext(55));
        assertEquals(70,bst.getNext(65));
        assertEquals(80,bst.getNext(75));
        assertEquals(90,bst.getNext(85));
        assertEquals(100,bst.getNext(95));
        assertEquals(null,bst.getNext(100));
        assertEquals(null,bst.getNext(110));
    }

    @Test
    void getPrev(){
        checkPrev();
        bst.clear();
        for(int i = 1; i < 11; i++){
            bst.add(i);
        }
        print();
        checkPrev();

        bst.clear();
        for(int i = 10; i > 0; i--){
            bst.add(i);
        }
        print();
        checkPrev();
        bst.clear();
        setUp100();
        checkPrev100();
    }

    private void checkPrev() {
        assertEquals(null,bst.getPrev(0));
        assertEquals(null,bst.getPrev(1));
        assertEquals(1,bst.getPrev(2));
        assertEquals(2,bst.getPrev(3));
        assertEquals(3,bst.getPrev(4));
        assertEquals(4,bst.getPrev(5));
        assertEquals(5,bst.getPrev(6));
        assertEquals(6,bst.getPrev(7));
        assertEquals(7,bst.getPrev(8));
        assertEquals(8,bst.getPrev(9));
        assertEquals(9,bst.getPrev(10));
        assertEquals(10,bst.getPrev(11));
        assertEquals(10,bst.getPrev(12));
    }

    private void checkPrev100() {
        assertEquals(null,bst.getPrev(0));
        assertEquals(null,bst.getPrev(10));
        assertEquals(10,bst.getPrev(15));
        assertEquals(20,bst.getPrev(25));
        assertEquals(30,bst.getPrev(35));
        assertEquals(40,bst.getPrev(45));
        assertEquals(50,bst.getPrev(55));
        assertEquals(60,bst.getPrev(65));
        assertEquals(70,bst.getPrev(75));
        assertEquals(80,bst.getPrev(85));
        assertEquals(90,bst.getPrev(95));
        assertEquals(100,bst.getPrev(105));
        assertEquals(100,bst.getPrev(110));
    }
}