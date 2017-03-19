package com.controllers;

import com.bookreader.models.Book;
import com.bookreader.repositories.ReadingBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/readingBooks")
public class ReadingBookController {

    private ReadingBookRepository readingBook;

    @Autowired
    public ReadingBookController(ReadingBookRepository readingBook) {
        this.readingBook = readingBook;
    }

    @RequestMapping(value = "/readingBooks/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {

        List<Book> booksByReaders = readingBook.findByReader(reader);
        if (booksByReaders != null) {
            model.addAttribute("books", booksByReaders);
        }
        return "book/readingBook";
    }

    @RequestMapping(value = "readingBooks/{reader}", method = RequestMethod.POST)
    public String addToReading(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingBook.save(book);
        return "redirect:/book/{reader}";
    }
}
