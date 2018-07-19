package com.yodean.oa.module.note.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.module.note.entity.Note;
import com.yodean.oa.module.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/18/18.
 */
@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public Result<Long> save(@RequestBody Note note) {
        return ResultUtils.success(noteService.save(note).getId());
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Note note, @PathVariable Long id) {
        note.setId(id);
        noteService.save(note);
        return ResultUtils.success();
    }

    @GetMapping("/{id}")
    public Result<Note> findById(@PathVariable Long id) {
        return ResultUtils.success(noteService.findById(id));
    }

}
