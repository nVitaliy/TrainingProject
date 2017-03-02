package com.bookreader.repositories;

import com.bookreader.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingBookRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);

}
