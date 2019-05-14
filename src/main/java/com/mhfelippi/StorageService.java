package com.mhfelippi;

import org.apache.commons.codec.binary.Base64InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;

/**
 * Service used to manipulate diff files.
 */
@Service
public class StorageService {

    @Autowired
    private StorageProperties properties;

    public StorageService() {}

    /**
     * Creates a new <code>StorageService</code>.
     * @param properties The properties of the storage.
     */
    public StorageService(StorageProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates a file
     * @param file the file to be created.
     * @param inputStream The input stream containg the data of the file.
     * @param streamType The encoding of the input stream.
     */
    public void create(DiffFile file, InputStream inputStream, StreamType streamType) {
        Path path = this.getPath(file);
        try {
            if (streamType == StreamType.BASE64) {
                inputStream = new Base64InputStream(inputStream);
            }
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }

    /**
     * Reads a file.
     * @param diffFile The file to be read.
     * @param streamType The encoding of the returned input stream.
     * @return An input stream to read the contents of the file.
     * @throws DiffFileNotFoundException If the file does not exists.
     */
    public InputStream read(DiffFile diffFile, StreamType streamType) {
        File file = this.getFile(diffFile);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            switch (streamType) {
                case BASE64:
                    return new Base64InputStream(inputStream, true);
                case BINARY:
                default:
                    return inputStream;
            }
        } catch (FileNotFoundException ex) {
            throw new DiffFileNotFoundException(diffFile);
        }
    }

    /**
     * Deletes a file.
     * @param file The file to be deleted.
     * @throws DiffFileNotFoundException If the file does not exists.
     * @throws UnhandledException If the file cannot be deleted.
     */
    public void delete(DiffFile file) {
        Path path = this.getPath(file);
        try {
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            throw new DiffFileNotFoundException(file);
        } catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }

    /**
     * Checks if a diff file exists in the storage location.
     * @param file The diff file to be checked.
     * @return If the file exists, <code>true</code>. Otherwise <code>false</code>.
     */
    public boolean exists(DiffFile file) {
        return this.getFile(file).exists();
    }

    /**
     * Gets the size of file in the storage location.
     * @param file The diff file to be sized.
     * @return The size of the file in bytes. If the file does not exists, returns <code>0</code>.
     */
    public long size(DiffFile file) {
        return this.getFile(file).length();
    }

    /**
     * Gets the actual file with the real location for a diff file.
     * @param file The diff file.
     * @return The file for a diff file.
     */
    public File getFile(DiffFile file) {
        return new File(this.properties.getLocation() +"/"+ file);
    }

    /**
     * Gets the path with the real location for a diff file.
     * @param file The diff file.
     * @return The path for a diff file.
     */
    public Path getPath(DiffFile file) {
        return Paths.get(this.properties.getLocation() +"/"+ file);
    }

    /**
     * Indicates the type of data being streamed to and from a file.
     */
    public enum StreamType {
        /**
         * Indicates a binary file that can be read or write without any coding.
         */
        BINARY,
        /**
         * Indicates a Base64 encoded file that must be read or write applying the specified coding.
         */
        BASE64
    }

}
