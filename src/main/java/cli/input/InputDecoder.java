package cli.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import cli.input.KeyPattern.Match;

public class InputDecoder {
    private final Reader source;
    private final List<KeyPattern> keyPatterns;
    private final List<Character> currentMatching;
    private boolean reachedEOF;
    private int timeoutUnits;

    /**
     * Creates a new input decoder using a specified Reader as the source to read characters from
     * @param source Reader to read characters from, will be wrapped by a BufferedReader
     */
    public InputDecoder(final Reader source) {
        this.source = new BufferedReader(source);
        this.keyPatterns = new ArrayList<>();
        this.currentMatching = new ArrayList<>();
        this.reachedEOF = false;
        this.timeoutUnits = 0;
    }

    /**
     * Adds another key decoding profile to this InputDecoder, which means all patterns from the profile will be used
     * when decoding input.
     * @param profile The profile to add.
     */
    public void addProfile(KeyProfile profile) {
        for (KeyPattern pattern : profile.getPatterns()) {
            synchronized(keyPatterns) {
                //If an equivalent pattern already exists, remove it first
                keyPatterns.remove(pattern);
                keyPatterns.add(pattern);
            }
        }
    }

    /**
     * Returns a collection of all patterns registered in this InputDecoder.
     */
    public synchronized Collection<KeyPattern> getPatterns() {
        synchronized(keyPatterns) {
            return new ArrayList<>(keyPatterns);
        }
    }

    /**
     * Removes one pattern from the list of patterns in this InputDecoder
     * @param pattern Pattern to remove
     * @return {@code true} if the supplied pattern was found and was removed, otherwise {@code false}
     */
    public boolean removePattern(KeyPattern pattern) {
        synchronized(keyPatterns) {
            return keyPatterns.remove(pattern);
        }
    }

    public synchronized KeyStroke getNextCharacter(boolean blockingIO) throws IOException {
        KeyStroke bestMatch = null;
        int bestLen = 0;
        int curLen = 0;

        while(true) {
            if (curLen < currentMatching.size()) {
                // Consume characters previously read
                curLen++;
            }
            else {
//                // If we already have a bestMatch but a chance for a longer match then we poll for the configured number
//                // of timeout units in 1/4 second units.
//                if (bestMatch != null) {
//                    int timeout = getTimeoutUnits();
//                    while (timeout > 0 && !source.ready()) {
//                        try {
//                            timeout--;
//
//                            //noinspection BusyWait
//                            Thread.sleep(250);
//                        } catch (InterruptedException e) { timeout = 0; }
//                    }
//                }

                // if input is available, read a char without waiting, otherwise, for readInput() with no bestMatch
                // found yet, wait blocking for more input:
                if (source.ready() || (blockingIO && bestMatch == null)) {
                    int readChar = source.read();

                    // Reached EOF
                    if (readChar == -1) {
                        this.reachedEOF = true;
                        if(this.currentMatching.isEmpty()) return new KeyStroke(KeyKind.EOF);
                        break;
                    }

                    this.currentMatching.add((char)readChar);
                    curLen++;
                } else {
                    // No more available input at this time.

                    if (bestMatch != null) break;
                    return null;
                }
            }

            List<Character> curSeq = currentMatching.subList(0, curLen);
            Match match = getBestMatch(curSeq);

            // Full Match found
            if (match.fullMatch != null) {
                bestMatch = match.fullMatch;
                bestLen = curLen;

                if (!match.partial) {
                    // Reached a partial match. Do not attempt to read new matches from buffer.
                    break;
                } else {
                    // Checked for continuous matches in the buffer.
                    //noinspection UnnecessaryContinue
                    continue;
                }
            } else if (match.partial) {
                // No match found yet, but can exist further down the line
                //noinspection UnnecessaryContinue
                continue;
            } else {
                // Matches no longer possible beyond this point
                if (bestMatch != null ) {
                    break;
                } else {
                    // Invalid input. Flush consumed characters.
                    curSeq.clear();
                    curLen = 0;
                    continue;
                }
            }
        }

        if(bestMatch == null) {
            // Reached EOF, explicitly return it
            if(this.reachedEOF) {
                currentMatching.clear();
                return new KeyStroke(KeyKind.EOF);
            }

            // Nothing found.
            return null;
        }

        // Return the match
        List<Character> bestSeq = currentMatching.subList(0, bestLen);
        bestSeq.clear();
        return bestMatch;
    }

    private Match getBestMatch(List<Character> characterSequence) {
        boolean partialMatch = false;
        KeyStroke bestMatch = null;

        synchronized(this.keyPatterns) {
            for(KeyPattern pattern : this.keyPatterns) {
                Match res = pattern.match(characterSequence);
                if (res != null) {
                    if (res.partial) partialMatch = true;
                    if (res.fullMatch != null) bestMatch = res.fullMatch;
                }
            }
        }

        return new Match(partialMatch, bestMatch);
    }
}
