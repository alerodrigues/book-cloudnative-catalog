package com.example.cloudnative.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudnative.domain.Book;
import com.example.cloudnative.domain.BookService;

import jakarta.validation.Valid;

@RestController("book")
@RequestMapping("api/v1/books")
public class BookController {
	private final BookService service;
	
	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping
	public Iterable<Book> getAll() {
		return service.viewBookList();
	}
	
	@GetMapping("{isbn}")
	public Book getByIsbn(@PathVariable String isbn) {
		return service.viewBookDetails(isbn);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book post(@Valid @RequestBody Book book) {
		return service.addBookToCatalog(book);
	}
	
	@PutMapping("{isbn}")
	public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
		return service.editBookDetails(isbn, book);
	}
	
	@DeleteMapping("{isbn}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String isbn) {
		service.removeBookFromCatalog(isbn);
	}
	
}
