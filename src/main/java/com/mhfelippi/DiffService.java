package com.mhfelippi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service used to perform diff on files.
 */
@Service
public class DiffService {

    @Autowired
    private StorageService storageService;

    public DiffService(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Finds the differences between two files.
     *
     * @param leftFile The left diff file to be compared.
     * @param rightFile The right diff file to be compared with.
     * @return A DiffResult containing detailed information.
     */
    public DiffResult diff(@NonNull DiffFile leftFile, @NonNull DiffFile rightFile) {
        // Checking if both files exits
        if (!storageService.exists(leftFile)) {
            throw new DiffFileNotFoundException(leftFile);
        }
        if (!storageService.exists(rightFile)) {
            throw new DiffFileNotFoundException(rightFile);
        }

        // If the size of the files don't match, diff is not performed.
        if (storageService.size(leftFile) != storageService.size(rightFile)) {
            return DiffResult.DIFFERENT_SIZE;
        }

        try (InputStream leftStream = this.storageService.read(leftFile, StorageService.StreamType.BINARY); InputStream rightStream = this.storageService.read(rightFile, StorageService.StreamType.BINARY)) {
            List<DiffInsight> insights = new ArrayList<>();
            // Iterate over the files finding differences
            for(int offset = 0; ; offset++) {
                int leftByte = leftStream.read();
                int rightByte = rightStream.read();

                // If the end of the file is reached, the job here is done.
                if (leftByte == -1) break;

                // If we find a difference, figure out when the files become equal again.
                if (leftByte != rightByte) {
                    int length = this.diffLenght(leftStream, rightStream);
                    insights.add(new DiffInsight(offset, length));
                    offset += length;
                }
            }

            // If no insight is found, both files are equal.
            if (insights.isEmpty()) {
                return DiffResult.EQUAL;
            }

            return new DiffResult(insights);
        } catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }

    /**
     * Finds the length of a difference.
     *
     * <p>After a difference has been spotted, this method iterates on both streams to find when they become equal
     * again. Both streams must have the same length and be on the same position.</p>
     *
     * @param leftStream The input stream from the left file.
     * @param rightStream The input stream from the right file.
     * @return The number of bytes differing in both streams.
     * @throws IOException If an IO error occurs.
     */
    private int diffLenght(InputStream leftStream, InputStream rightStream) throws IOException {
        for (int length = 1; ; length++) {
            int left = leftStream.read();
            int right = rightStream.read();

            // If both streams are equal, return the number of bytes from the beginning of the difference.
            // Since both streams should have the same size, both bytes will be -1 at the same time.
            if (left == right) {
                return length;
            }
        }
    }

}
