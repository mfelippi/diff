package com.mhfelippi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

public class DiffServiceTest {

    protected static final String HELLO_WORLD_1 = "{\"hello\": \"world\"}";
    protected static final String HELLO_WORLD_2 = "[\"HELLO\": \"WOrld\"]";
    protected static final String HI_WORLD = "{\"hi\": \"world\"}";

    private final DiffFile equalLeftFile = new DiffFile(1, DiffFile.Side.LEFT);
    private final DiffFile equalRightFile = new DiffFile(1, DiffFile.Side.RIGHT);

    private final DiffFile nonEqualLeftFile = new DiffFile(2, DiffFile.Side.LEFT);
    private final DiffFile nonEqualRightFile = new DiffFile(2, DiffFile.Side.RIGHT);

    private final DiffFile differentSizeLeftFile = new DiffFile(3, DiffFile.Side.LEFT);
    private final DiffFile differentSizeRightFile = new DiffFile(3, DiffFile.Side.RIGHT);

    private final DiffFile notFoundLeftFile = new DiffFile(4, DiffFile.Side.LEFT);
    private final DiffFile notFoundRightFile = new DiffFile(4, DiffFile.Side.RIGHT);

    private DiffService diffService;

    private StorageService storageService;

    @Before
    public void setUp() throws Exception {
        StorageProperties properties = new StorageProperties();
        this.storageService = new StorageService(properties);
        this.diffService = new DiffService(this.storageService);

        // Setup equal
        try (InputStream inputStream = new ByteArrayInputStream(HELLO_WORLD_1.getBytes(UTF_8))) {
            this.storageService.create(this.equalLeftFile, inputStream, StorageService.StreamType.BINARY);
        }
        try (InputStream inputStream = new ByteArrayInputStream(HELLO_WORLD_1.getBytes(UTF_8))) {
            this.storageService.create(this.equalRightFile, inputStream, StorageService.StreamType.BINARY);
        }

        // Setup non equal
        try (InputStream inputStream = new ByteArrayInputStream(HELLO_WORLD_1.getBytes(UTF_8))) {
            this.storageService.create(this.nonEqualLeftFile, inputStream, StorageService.StreamType.BINARY);
        }
        try (InputStream inputStream = new ByteArrayInputStream(HELLO_WORLD_2.getBytes(UTF_8))) {
            this.storageService.create(this.nonEqualRightFile, inputStream, StorageService.StreamType.BINARY);
        }

        //Setup differente size
        try (InputStream inputStream = new ByteArrayInputStream(HELLO_WORLD_1.getBytes(UTF_8))) {
            this.storageService.create(this.differentSizeLeftFile, inputStream, StorageService.StreamType.BINARY);
        }
        try (InputStream inputStream = new ByteArrayInputStream(HI_WORLD.getBytes(UTF_8))) {
            this.storageService.create(this.differentSizeRightFile, inputStream, StorageService.StreamType.BINARY);
        }
    }

    @After
    public void tearDown() {
        this.storageService.delete(this.equalLeftFile);
        this.storageService.delete(this.equalRightFile);
        this.storageService.delete(this.nonEqualLeftFile);
        this.storageService.delete(this.nonEqualRightFile);
        this.storageService.delete(this.differentSizeLeftFile);
        this.storageService.delete(this.differentSizeRightFile);
    }

    @Test
    public void diffEqual() {
        DiffResult result = this.diffService.diff(this.equalLeftFile, this.equalRightFile);
        assertEquals(DiffResult.EQUAL, result);
    }

    @Test
    public void diffNonEqual() {
        Collection<DiffInsight> insights = new ArrayList<>();
        insights.add(new DiffInsight(0, 1));
        insights.add(new DiffInsight(2, 5));
        insights.add(new DiffInsight(11, 2));
        insights.add(new DiffInsight(17, 1));

        DiffResult result = this.diffService.diff(this.nonEqualLeftFile, this.nonEqualRightFile);
        assertEquals(new DiffResult(insights), result);
    }

    @Test
    public void diffDifferentSize() {
        DiffResult result = this.diffService.diff(this.differentSizeLeftFile, this.differentSizeRightFile);
        assertEquals(DiffResult.DIFFERENT_SIZE, result);
    }

    @Test(expected = DiffFileNotFoundException.class)
    public void notFoundLeftFile() {
        this.diffService.diff(this.notFoundLeftFile, this.equalRightFile);
    }

    @Test(expected = DiffFileNotFoundException.class)
    public void setNotFoundRightFile() {
        this.diffService.diff(this.equalLeftFile, this.notFoundRightFile);
    }

}