package com.mhfelippi;

/**
 * Exception thrown when a file is not found.
 */
public class DiffFileNotFoundException extends RuntimeException {

    private final DiffFile file;

    /**
     * Creates a new <code>DiffFileNotFoundException</code>.
     * @param file The file not found.
     */
    public DiffFileNotFoundException(DiffFile file) {
        this.file = file;
    }

    /**
     * @return The file that was not found.
     */
    public DiffFile getFile() {
        return file;
    }
}
