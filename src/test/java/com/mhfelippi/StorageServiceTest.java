package com.mhfelippi;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class StorageServiceTest {

    private static final String BINARY = "{\"hello\": \"world\"}";
    private static final String BASE64_ENCODED = "eyJoZWxsbyI6ICJ3b3JsZCJ9\r\n";

    private final DiffFile file = new DiffFile(2, DiffFile.Side.LEFT);

    private StorageService storageService;

    @Before
    public void setUp() throws Exception {
        StorageProperties properties = new StorageProperties();
        this.storageService = new StorageService(properties);

        try (InputStream inputStream = IOUtils.toInputStream(BINARY)) {
            Files.copy(inputStream, this.storageService.getPath(file), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @After
    public void tearDown() throws Exception {
        this.storageService.delete(this.file);
    }

    @Test
    public void createBase64() {
        DiffFile binaryFile = new DiffFile(1, DiffFile.Side.LEFT);
        try(InputStream inputStream = new ByteArrayInputStream(BASE64_ENCODED.getBytes(UTF_8))) {
            this.storageService.create(binaryFile, inputStream, StorageService.StreamType.BASE64);
            assertEquals(18, this.storageService.size(binaryFile));
        } catch (IOException ex) {
            throw new RuntimeException(("Error running create test."));
        }
    }

    @Test
    public void createBinary() {
        DiffFile binaryFile = new DiffFile(1, DiffFile.Side.LEFT);
        try(InputStream inputStream = new ByteArrayInputStream(BASE64_ENCODED.getBytes(UTF_8))) {
            this.storageService.create(binaryFile, inputStream, StorageService.StreamType.BINARY);
            assertEquals(26, this.storageService.size(binaryFile));
        } catch (IOException ex) {
            throw new RuntimeException(("Error running create test."));
        }
    }

    @Test
    public void readBinary() throws IOException {
        try (InputStream inputStream = this.storageService.read(this.file, StorageService.StreamType.BINARY)) {
            String content = IOUtils.toString(inputStream);
            assertEquals(BINARY, content);
        }
    }

    @Test
    public void readBase64() throws IOException {
        try (InputStream inputStream = this.storageService.read(this.file, StorageService.StreamType.BASE64)) {
            String content = IOUtils.toString(inputStream);
            assertEquals(BASE64_ENCODED, content);
        }
    }

    @Test
    public void exists() {
        assertEquals(true, this.storageService.exists(this.file));
    }

    @Test
    public void size() {
        assertEquals(18, this.storageService.size(this.file));
    }

}