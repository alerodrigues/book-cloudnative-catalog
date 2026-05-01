package com.example.cloudnative.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.cloudnative.domain.Book;
import com.example.cloudnative.domain.BookRepository;

@Repository
public class JdbcBookRepository implements BookRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Iterable<Book> findAll() {
		return jdbcTemplate.query("select isbn, author, title, price from Book", new RowMapper<Book>() {
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Book(
					rs.getString("isbn"),
					rs.getString("author"),
					rs.getString("title"),
					rs.getDouble("price")
				);
			};
		});
	}

	@Override
	public Optional<Book> findByIsbn(String isbn) {
		List<Book> results = jdbcTemplate.query(
				"select isbn, author, title, price from Book where isbn = ?", this::mapRowToBook, isbn);

		return results.size() == 0 ? Optional.empty() :	Optional.of(results.get(0));
	}

	private Book mapRowToBook(ResultSet row, int rowNum) throws SQLException {
		return new Book(
				row.getString("isbn"),
				row.getString("author"),
				row.getString("title"),
				row.getDouble("price"));
	}
	
	@Override
	public boolean existsByIsbn(String isbn) {
		return this.findByIsbn(isbn).isPresent() ? true : false;
	}

	@Override
	public Book save(Book book) {
		jdbcTemplate.update("insert into Book (isbn, author, title, price) values (?, ?, ?, ?)",
				book.isbn(), book.author(), book.title(), book.price());

		return book;
	}

	@Override
	public void deleteByIsbn(String isbn) {
		jdbcTemplate.update("delete from Book where isbn = ?", isbn);		
	}
	
}
