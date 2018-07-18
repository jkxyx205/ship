package com.yodean.oa.module.note.repository;

import com.yodean.oa.module.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/18/18.
 */
public interface NoteRepository extends JpaRepository<Note, Long> {
}
