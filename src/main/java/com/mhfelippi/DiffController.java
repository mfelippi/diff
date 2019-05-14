package com.mhfelippi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("v1/diff/{id}")
public class DiffController {

    @Autowired
    private DiffService diffService;

    @RequestMapping(method = GET)
    public ResponseEntity<DiffResult> getDiff(@PathVariable Integer id) {
        DiffFile left = new DiffFile(id, DiffFile.Side.LEFT);
        DiffFile right = new DiffFile(id, DiffFile.Side.RIGHT);

        DiffResult result = this.diffService.diff(left, right);
        return new ResponseEntity<>(result, OK);
    }

}
