package org.example.stage1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BinarySearchTree<E extends Comparable> {

    Node<E> root = new Node<>();

    int size;

    public boolean add(E element) {
        Node<E> current = root;

        while (current.value != null) {
            if (current.value.equals(element)) {
                return false;
            }
            if (element.compareTo(current.value) > 0) {
                if (current.right == null) {
                    current.right = new Node<>();
                }
                current = current.right;
            } else {
                if (current.left == null) {
                    current.left = new Node<>();
                }
                current = current.left;
            }
        }
        current.value = element;
        ++size;
        return true;
    }

    public boolean contains(E element) {
        Node<E> current = root;

        while (current != null && current.value != null) {
            if (current.value.equals(element)) {
                return true;
            }
            if (element.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return false;
    }


    public boolean remove(E element) {
        Node<E> current = root;
        while (current != null && !element.equals(current.value)) {
            if (element.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        if (current == null) {
            return false;
        }
        // right
        E value = getMinWithRemove(current.right);
        if (value == null) {
            value = getMaxWithRemove(current.left);
        }


        current.value = value;
        --size;
        return true;
    }

    private E getMaxWithRemove(Node<E> left) {
        if (isNotExist(root)) {
            return null;
        }
        Node<E> current = root;
        while (current != null && current.value != null && current.right != null) {
            current = current.right;
        }
        E result = current.value;
        if (current.left != null) {
            Node<E> tmp = current.left;

            current.left = tmp.left;
            current.right = tmp.right;
            current.value = tmp.value;
        } else {
            current.value = null;
        }
        return result;
    }

    private E getMinWithRemove(Node<E> root) {
        if (isNotExist(root)) {
            return null;
        }
        Node<E> current = root;
        while (current != null && current.value != null && current.left != null) {
            current = current.left;
        }
        E result = current.value;
        if (current.right != null) {
            Node<E> tmp = current.right;

            current.left = tmp.left;
            current.right = tmp.right;
            current.value = tmp.value;
        } else {
            current.value = null;
        }
        return result;
    }

    public int size() {
        return size;
    }

    /**
     * Центрированный (симметричный) обход
     *
     * @return
     */
    public Iterable<E> leftParentRightIterate() {
        List<E> list = new ArrayList<>();
        leftParentRight(list, root);
        return list;
    }

    private void leftParentRight(List<E> list, Node<E> current) {
        if (isNotExist(current)) {
            return;
        }
        leftParentRight(list, current.left);
        list.add(current.value);
        leftParentRight(list, current.right);
    }

    /**
     * Обход в прямом порядке
     *
     * @return
     */
    public Iterable<E> parentLeftRight() {
        List<E> list = new ArrayList<>();
        parentLeftRight(list, root);
        return list;
    }

    private void parentLeftRight(List<E> list, Node<E> current) {
        if (isNotExist(current)) {
            return;
        }
        list.add(current.value);
        parentLeftRight(list, current.left);
        parentLeftRight(list, current.right);
    }

    /**
     * Обход в обратном порядке
     *
     * @return
     */
    public Iterable<E> leftRightParent() {
        List<E> list = new ArrayList<>();
        leftRightParent(list, root);
        return list;
    }

    private void leftRightParent(List<E> list, Node<E> current) {
        if (isNotExist(current)) {
            return;
        }
        leftRightParent(list, current.left);
        leftRightParent(list, current.right);
        list.add(current.value);
    }


    public List<E> getSubSet(E min, E max) {
        List<E> result = new ArrayList<>();
        getSubSet(min, max, result, root);
        return result;
    }

    private void getSubSet(E min, E max, List<E> result, Node<E> current) {
        if (isNotExist(current)) {
            return;
        }
        getSubSet(min, max, result, current.left);
        if (min.compareTo(current.value) < 0 && max.compareTo(current.getValue()) > 0) {
            result.add(current.value);
        }
        getSubSet(min, max, result, current.right);
    }

    public E getMin(){
        return getMin(root);
    }

    private E getMin(Node<E> current) {
        if(isNotExist(current)){
            return null;
        }
        if(current.left != null && current.left.value != null){
            return getMin(current.left);
        }
        return current.value;
    }

    public E getMax(){
        return getMax(root);
    }

    private E getMax(Node<E> current) {
        if(isNotExist(current)){
            return null;
        }
        if(current.right != null && current.right.value != null){
            return getMax(current.right);
        }
        return current.value;
    }

    public E getNext(E value){
        return getNext(value, root);
    }

    private E getNext(E find, Node<E> current) {
        if(isNotExist(current)){
            return null;
        }
        if(current.value.compareTo(find) > 0 ){
            if(current.left == null || current.left.value == null){
                return current.value;
            }
            return ifNull(getNext(find, current.left), current.value);
        }
        if(current.value.compareTo(find) < 0 ){
            return getNext(find, current.right);
        }
        if(current.value.compareTo(find) == 0){
            return getMin(current.right);
        }
        return current.value;
    }

    private E ifNull(E nullable, E defaultValue){
        if(nullable == null){
            return defaultValue;
        }
        return nullable;
    }

    private boolean isExist(Node<E> node) {
        return !isNotExist(node);
    }

    private static <E extends Comparable> boolean isNotExist(Node<E> current) {
        return current == null || current.value == null;
    }

    public E getPrev(E find) {
        return getPrev(find, root);
    }

    private E getPrev(E find, Node<E> current) {
        if(isNotExist(current)){
            return null;
        }
        if(current.value.compareTo(find) > 0 ){
            return getPrev(find, current.left);
        }
        if(current.value.compareTo(find) < 0 ){
            return ifNull(getPrev(find, current.right), current.value);
        }
        if(current.value.compareTo(find) == 0){
            return getMax(current.left);
        }
        return current.value;
    }

    public void clear() {
        root.value = null;
        root.right = null;
        root.left = null;
        size = 0;
    }

    @Getter
    @Setter
    static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;
    }
}
