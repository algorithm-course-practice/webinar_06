package org.example.stage2;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class PrefixTreeTest {

    PrefixTree trie = new PrefixTree();


    @Test
    void fastCheck() {
        assertFalse(isCorrect("age"));
        trie.add("age");
        assertTrue(isCorrect("a"));
        assertTrue(isCorrect("ag"));
        assertTrue(isCorrect("age"));
        assertFalse(isCorrect("agex"));
        trie.add("aged");
        trie.add("agency");
        trie.add("agent");

        PrefixTree.Result res = trie.autoComplete("a", 10);
        assertFalse(res.isCompleted);
        assertEquals(asList("a", "age", "aged", "agency", "agent"), res.suggestions);
    }

    @Test
    void checkSuggestion(){

        trie.add("baduhenna");
        trie.add("bae");
        trie.add("baedeker");
        PrefixTree.Result result = trie.autoComplete("ba", 10);
        assertEquals(asList("ba","baduhenna", "bae", "baedeker"), result.suggestions);
    }

    private boolean isCorrect(String word) {
        return !trie.autoComplete(word, 0).suggestions.isEmpty();
    }
}