package com.mhfelippi;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Contains the result of the diff between two files.
 *
 * <p>Files can be equal, different size and non equal. Insights are only provided if files are non equal.</p>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DiffResult {

    static final DiffResult EQUAL = new DiffResult(Match.EQUAL);
    static final DiffResult DIFFERENT_SIZE = new DiffResult(Match.DIFFERENT_SIZE);

    private Match match;
    private Collection<DiffInsight> insights = new ArrayList<>();

    /**
     * Creates a new <code>DiffResult</code>
     */
    public DiffResult() {}

    /**
     * Creates a new <code>DiffResult</code>
     * @param match The type of the match.
     */
    public DiffResult(Match match) {
        this.match = match;
    }

    /**
     * Creates a new <code>DiffResult</code> with non equal diff result with insights of the differences.
     * @param insights The insights of the diff.
     */
    public DiffResult(@NonNull Collection<DiffInsight> insights) {
        this.match = Match.NON_EQUAL;
        this.insights = insights;
    }

    /**
     * Gets the type of the match between two files.
     * @return The type of match.
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Sets the type of the match between two files.
     * @param match The type of match.
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Gets the insights of the diff between two files.
     * @return The insights of the diff.
     */
    public Collection<DiffInsight> getInsights() {
        return insights;
    }

    /**
     * Sets the insights of the diff between two files.
     * @param insights The insights of the diff.
     */
    public void setInsights(Collection<DiffInsight> insights) {
        this.insights = insights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiffResult result = (DiffResult) o;
        return match == result.match &&
                Objects.deepEquals(insights, result.insights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(match, insights);
    }

    public enum Match {
        EQUAL, NON_EQUAL, DIFFERENT_SIZE;
    }

}
