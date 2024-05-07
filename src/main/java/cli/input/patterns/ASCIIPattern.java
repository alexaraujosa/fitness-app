package cli.input.patterns;

import cli.input.KeyPattern;
import cli.input.KeyStroke;

import java.util.List;

/**
 * Pattern that represents a printable ASCII Character.
 */
public class ASCIIPattern implements KeyPattern {
    @Override
    public Match match(List<Character> seq) {
        if (seq.size() != 1) {
            return null; // nope
        }

        char ch = seq.get(0);

        if (isPrintableChar(ch)) {
            KeyStroke ks = new KeyStroke(ch, false, false);
            return new Match( ks );
        } else {
            return null; // nope
        }
    }

    /**
     * From https://stackoverflow.com/a/418560/10787496
     *
     * @param c The character to test
     * @return True if this is a printable character, false if not.
     */
    private static boolean isPrintableChar(char c) {
        if (Character.isISOControl(c)) return false;

        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return block != null && block != Character.UnicodeBlock.SPECIALS;
    }
}
