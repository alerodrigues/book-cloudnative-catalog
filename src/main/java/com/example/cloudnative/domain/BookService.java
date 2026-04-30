package com.example.cloudnative.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {
	private final BookRepository repository;

	public BookService(BookRepository repository) {
		this.repository = repository;
	}

	public Iterable<Book> viewBookList() {
		return repository.findAll();
	}
	
	public Book viewBookDetails(String isbn) {
		return repository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
	}
	
	public Book addBookToCatalog(Book book) {
		if (repository.existsByIsbn(book.isbn())) {
			throw new BookAlreadyExistsException(book.isbn());
			
		}
		
		return repository.save(book);
	}
	
	public void removeBookFromCatalog(String isbn) {
		repository.deleteByIsbn(isbn);
	}
	
	public Book editBookDetails(String isbn, Book book) {
		return repository.findByIsbn(isbn).map(
			existingBook -> {
				var bookToUpdate = new Book(
						existingBook.isbn(), 
						book.title(), 
						book.author(), 
						book.price());
				
				return repository.save(bookToUpdate);
		}).orElseGet(() -> addBookToCatalog(book));
	}
		
}