package cli.input.patterns;

import cli.input.KeyPattern;
import cli.input.KeyStroke;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a predefined sequence of characters.
 */
public class SequencePattern implements KeyPattern {
    private final KeyStroke result;
    private final char[] pattern;

    public SequencePattern(KeyStroke result, char... pattern) {
        this.result = result;
        this.pattern = pattern;
    }

    /**
     * Returns the characters that makes up this pattern, as an array that is a copy of the array used internally
     * @return Array of characters that defines this pattern
     */
    public char[] getPattern() {
        return Arrays.copyOf(pattern, pattern.length);
    }

    /**
     * Returns the keystroke that this pattern results in
     * @return The keystoke this pattern will return if it matches
     */
    public KeyStroke getResult() {
        return result;
    }

    @Override
    public Match match(List<Character> seq) {
        int size = seq.size();

        // Sequence is longer than pattern, impossible match.
        if(size > pattern.length) {
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (pattern[i] != seq.get(i)) {
                return null;
            }
        }

        if (size == pattern.length) {
            // Perfect Match
            return new Match(this.getResult());
        } else {
            // Pattern has matched sequence, but it still requires more characters.
            return Match.INCOMPLETE;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SequencePattern other)) {
            return false;
        }

        return Arrays.equals(this.pattern, other.pattern);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Arrays.hashCode(this.pattern);
        return hash;
    }
}
