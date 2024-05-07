package cli.input;

import java.util.List;

public interface KeyPattern {
    /**
     * Given a list of characters, determine whether it exactly matches any known KeyStroke from this pattern, and
     * whether a longer sequence could possibly match.
     *
     * @param seq A sequence of {@link Character} to check.
     * @return A {@link Match} for the sequence.
     */
    Match match(List<Character> seq);

    /*+
     * Represents a Pattern Match.
     *
     * A Match can either be full or partial:
     * - Full Match:
     *   The resulting KeyStroke if the pattern matched, otherwise null.
     *   - Example: if the tested sequence is `Esc [ A`, and if the pattern recognized this as {@code ArrowUp}, 
     *      then this field has a value of {@code new KeyStroke(KeyType.ArrowUp)}</dd>
     * - Partial Match
     *   {@code true}, if appending appropriate characters at the end of the sequence _can_ produce a match.
     *   - Example: if the tested sequence is `Esc [`, and the Pattern would match `Esc [ A`, then this field would be 
     *      set to {@code true}.</dd>
     * </dl>
     */
    public class Match {
        public final KeyStroke fullMatch;
        public final boolean partial;

        public static final Match INCOMPLETE = new Match( true, null );

        public Match(KeyStroke fullMatch) {
            this(false,fullMatch);
        }
        /**
         * For mismatches use {@code null} and for partial matches use {@link Match#INCOMPLETE}.
         *
         * @param partial  true if further characters could lead to a match
         * @param fullMatch     The perfectly matching KeyStroke
         */
        public Match(boolean partial, KeyStroke fullMatch) {
            this.partial = partial;
            this.fullMatch = fullMatch;
        }

        @Override
        public String toString() {
            return "Match{" + "partial=" + partial + ", fullMatch=" + fullMatch + '}';
        }
    }
}
