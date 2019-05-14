package com.mhfelippi;

import org.springframework.lang.NonNull;

/**
 * A diff file is a combination between id and side. Diff is calculated between to files named left & right. It contains
 * the logical location of the file.
 */
public class DiffFile {

    private final Integer id;
    private final Side side;

    /**
     * Creates a new <code>DiffFile</code>.
     * @param id The id of the file.
     * @param side The side of the file.
     */
    public DiffFile(@NonNull Integer id, @NonNull Side side) {
        this.id = id;
        this.side = side;
    }

    /**
     * Creates a new <code>DiffFile</code>.
     * @param id The id of the file.
     * @param side The side of the file.
     * @throws IllegalArgumentException If <code>side</code> is invalid.
     */
    public DiffFile(@NonNull Integer id, @NonNull String side) {
        this.id = id;
        this.side = Side.valueOf(side.toUpperCase());
    }

    /**
     * Gets the id of the file.
     * @return The id of the file.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the side of file.
     * @return The side of the file.
     */
    public Side getSide() {
        return side;
    }

    @Override
    public String toString() {
        return this.id +"."+ this.side;
    }

    /**
     * Describes the side of a diff file.
     */
    public enum Side {
        LEFT, RIGHT;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

}
