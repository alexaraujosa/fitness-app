package cli.input.profiles;

import cli.input.KeyKind;
import cli.input.KeyPattern;
import cli.input.KeyProfile;
import cli.input.KeyStroke;
import cli.input.patterns.ASCIIPattern;
import cli.input.patterns.EscapeSequencePattern;
import cli.input.patterns.SequencePattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DefaultKeyProfile implements KeyProfile {
    @Override
    public Collection<KeyPattern> getPatterns() {
        return new ArrayList<>(Arrays.asList(new KeyPattern[] {
                // Special Characters
                new SequencePattern(new KeyStroke(KeyKind.ESCAPE), EscapeSequencePattern.ESC_CODE),
                new SequencePattern(new KeyStroke(KeyKind.TAB), '\t'),
                new SequencePattern(new KeyStroke(KeyKind.ENTER), '\n'),
                new SequencePattern(new KeyStroke(KeyKind.BACKSPACE), (char)0x7f),
                new SequencePattern(new KeyStroke(KeyKind.BACKSPACE), (char)0x08),

                // Printable Characters
                new ASCIIPattern()
        }));
    }
}
