package com.mhfelippi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("v1/diff/{id}/{side}")
public class DiffFileController {

    @Autowired
    private StorageService storageService;

    @ResponseBody
    @RequestMapping(method = GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<InputStreamResource> readFile(@PathVariable Integer id, @PathVariable String side) {
        DiffFile file = new DiffFile(id, side);
        InputStream inputStream = this.storageService.read(file, StorageService.StreamType.BASE64);
        return new ResponseEntity<>(new InputStreamResource(inputStream), HttpStatus.OK);
    }

    @RequestMapping(method = PUT)
    public ResponseEntity putFile(@PathVariable Integer id, @PathVariable String side, HttpServletRequest request) {
        DiffFile diffFile = new DiffFile(id, side);
        try {
            this.storageService.create(diffFile, request.getInputStream(), StorageService.StreamType.BASE64);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity deleteFile(@PathVariable Integer id, @PathVariable String side) {
        DiffFile diffFile = new DiffFile(id, side);
        this.storageService.delete(diffFile);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleException() {
        // Handles IllegalArgumentException thrown by DiffFile if an invalid side is used.
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
