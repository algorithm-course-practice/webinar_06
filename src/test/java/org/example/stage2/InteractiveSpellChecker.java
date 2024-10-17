package org.example.stage2;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class InteractiveSpellChecker {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        PrefixTree trie = new PrefixTree();
        //loadDictionary(trie, "oxford_3000");
        loadDictionary(trie, "dictionary_alpha");
        long end = System.currentTimeMillis();
        System.out.println("Load time: " + (end - start));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter word[ maxSuggestions] for check or !exit:");
            String line = scanner.nextLine();
            if ("!exit".equals(line)) {
                return;
            }
            String[] cmd = line.split(" ");
            String word = cmd[0];
            int maxSuggestions = 10;
            if (cmd.length == 2) {
                maxSuggestions = Integer.valueOf(cmd[1]);
            }
            start = System.currentTimeMillis();
            PrefixTree.Result result = trie.autoComplete(word, maxSuggestions);
            end = System.currentTimeMillis();
            System.out.println("Load time: " + (end - start));
            System.out.println("Is completed: " + result.isCompleted);
            System.out.println("Suggestions:");
            result.suggestions.forEach(System.out::println);
            System.out.println("Returned count: "+result.suggestions.size());
        }

    }

    @SneakyThrows
    static void loadDictionary(PrefixTree trie, String dictName) {
        Files.lines(Path.of("src/test/resources/" + dictName + ".txt"))
                .filter((line) -> !line.startsWith("#"))
                .forEach(trie::add);
    }
}
