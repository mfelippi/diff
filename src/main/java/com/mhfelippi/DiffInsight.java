package com.mhfelippi;

import java.util.Objects;

/**
 * Describes a difference between two files.
 */
public class DiffInsight {

    private int offset;
    private int lenght;

    /**
     * Creates a new <code>DiffInsight</code>
     * @param offset The offset of the difference in bytes.
     * @param lenght The lenght of the difference in bytes.
     */
    public DiffInsight(int offset, int lenght) {
        this.offset = offset;
        this.lenght = lenght;
    }

    /**
     * Returns the offset of the difference in bytes.
     * @return The offset of the difference in bytes.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns the lenght of the difference in bytes.
     * @return The lenght of the difference in bytes.
     */
    public int getLenght() {
        return lenght;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiffInsight insight = (DiffInsight) o;
        return offset == insight.offset &&
                lenght == insight.lenght;
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, lenght);
    }
}
