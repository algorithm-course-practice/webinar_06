package org.example.stage2;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PrefixTree {

    Node root = new Node(' ');

    public Result autoComplete(String word, int maxSuggestions) {
        word = word.toLowerCase();
        List<String> suggestions = new ArrayList<>();

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            cur = cur.findChar(curChar);
            if (cur == null) {
                return new Result(false, suggestions);
            }
        }
        suggestions.add(word);
        cur.findSuggestions(suggestions, word.substring(0, word.length()-1), new AtomicInteger(maxSuggestions));
        return new Result(cur.isCompleted, suggestions);
    }

    public void add(String word) {
        word = word.toLowerCase();
        Node current = root;

        for (char curChar : word.toCharArray()) {
            current = current.addChar(curChar);
        }
        current.isCompleted = true;
    }

    public static class Result {
        boolean isCompleted;

        List<String> suggestions = new ArrayList<>();

        public Result(boolean isCompleted, List<String> suggestions) {
            this.isCompleted = isCompleted;
            this.suggestions.addAll(suggestions);
        }
    }

    @RequiredArgsConstructor
    static class Node implements Comparable<Node> {

        Node parent;

        @NonNull
        char value;

        boolean isCompleted;
        List<Node> subs = new ArrayList<>();

        Node addChar(char curChar) {
            Node sub = findChar(curChar);
            if (sub != null) return sub;
            Node next = new Node(curChar);
            subs.add(next);
            Collections.sort(subs);
            next.parent = this;
            return next;
        }

        Node findChar(char curChar) {
            for (Node sub : subs) {
                if (sub.value == curChar) {
                    return sub;
                }
            }
            return null;
        }

        public void findSuggestions(List<String> suggestions, String word, AtomicInteger max) {
            word = word + value;
            if(isCompleted && max.get() > 0){
                suggestions.add(word);
                max.decrementAndGet();
            }
            for (Node sub : subs) {
                if (max.get() == 0) {
                    return;
                }
                sub.findSuggestions(suggestions, word, max);
            }

        }

        @Override
        public String toString() {
            return String.format("%s %s %s", value, isCompleted ? "+" : "-", subs.stream().map(s -> s.value).collect(Collectors.toList()));

        }

        @Override
        public int compareTo(Node o) {
            return Character.compare(value, o.value);
        }
    }
}
