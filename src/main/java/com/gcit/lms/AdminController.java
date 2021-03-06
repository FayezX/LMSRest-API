package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;

/**
 * Handles requests for the application home page.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	Author author;
	
	@Autowired
	Book book;

	@Autowired
	BookDAO bdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BookLoansDAO bldao;

	@Autowired
	LibraryBranchDAO ldao;

	@Autowired
	LibraryBranch libraryBranch;

	@Autowired
	BookLoans bookLoans;

	private static final Logger logger = LoggerFactory
			.getLogger(AdminController.class);
	// /////////////////////////////////////////////Author Methods/////////////////////////////////////////////////////////
	@RequestMapping(value = "/Authors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthor(@RequestParam(value="pageNo",defaultValue="0") Integer pageNo,
			@RequestParam(value="searchString",defaultValue="") String searchString) {
		List<Author> authors = new ArrayList<>();
		try {
			if (searchString != null && searchString.length() > 0) {
				authors = adao.readAllAuthorsNoPage(searchString);
			} else {
				authors = adao.readAllAuthors(pageNo);
			}
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/Authors", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author) throws SQLException {
		Integer aId = null;
		try {
			aId = adao.addAuthorWithID(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		author.setAuthorId(aId);
		return new ResponseEntity<Author>(author, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Authors", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public Integer updateAuthor(@RequestBody Author author) {
		List<Author> authors = new ArrayList<>();
		try {
			adao.updateAuthor(author);
			authors = adao.readAllAuthors();
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;

	}

	@RequestMapping(value = "/Authors/{authorId}", method = RequestMethod.DELETE)
	public Integer deleteAuthor(@PathVariable("authorId") Integer authorId) {
		author.setAuthorId(authorId);
		List<Author> authors = new ArrayList<>();
		try {
			adao.deleteAuthor(author);
			authors = adao.readAllAuthors();
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	// /////////////////////////////////////////////Book
	// Methods/////////////////////////////////////////////////////////
	@RequestMapping(value = "/Books", method = RequestMethod.GET, produces = "application/json")
	public List<Book> viewBooks() {
		List<Book> books = new ArrayList<>();
		try {
			books = bdao.readAllBooks();
			for (Book b : books) {
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
			}
			for (Book b : books) {
				b.setGenres(gdao.readAllGenreByBookId(b.getBookId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@RequestMapping(value = "/Books", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Integer bId = null;
		try {
			//bdao.addBook(book);
			bId = bdao.addBookWithID(book);
			;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		book.setBookId(bId);
		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Books", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<Book> updateBook(@RequestBody Book book) {
		List<Book> books = new ArrayList<>();
		try {
			bdao.updateBook(book);
			;
			books = bdao.readAllBooks();
			for (Book b : books) {
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
				;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;

	}

	@RequestMapping(value = "/Books/{bookId}", method = RequestMethod.DELETE)
	public List<Book> deleteBook(@PathVariable("bookId") Integer bookId) {
		book.setBookId(bookId);
		List<Book> books = new ArrayList<>();
		try {
			bdao.deleteBook(book);
			for (Book b : books) {
				b.setAuthors(adao.readAllAuthoreByBookId(b.getBookId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	// /////////////////////////////////////////////Library Branch
	// Methods/////////////////////////////////////////////////////////
	@RequestMapping(value = "/Branches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> viewBranch() {
		List<LibraryBranch> branch = new ArrayList<>();
		try {
			branch = ldao.readAllLibraryBranchs();
			for (LibraryBranch l : branch) {
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return branch;
	}

	@RequestMapping(value = "/Branches", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<LibraryBranch> addBranch(@RequestBody LibraryBranch branch) {
		Integer brId = null;
		try {
			//ldao.addLibraryBranch(branch);
			brId = ldao.addLibraryBranchWithID(branch);
			libraryBranch.setBranchId(brId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LibraryBranch>(branch, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/Branches", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<LibraryBranch> updateBranch(@RequestBody LibraryBranch branch) {
		List<LibraryBranch> libraryBranch = new ArrayList<>();
		try {
			ldao.updateLibraryBranch(branch);
			libraryBranch = ldao.readAllLibraryBranchs();
			for (LibraryBranch l : libraryBranch) {
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libraryBranch;

	}

	@RequestMapping(value = "/Branches/{branchID}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public List<LibraryBranch> deleteBranch(@PathVariable("branchId") Integer branchId) {
		libraryBranch.setBranchId(branchId);
		List<LibraryBranch> libBranch = new ArrayList<>();
		try {
			ldao.deleteLibraryBranch(libraryBranch);
			libBranch = ldao.readAllLibraryBranchs();
			for (LibraryBranch l : libBranch) {
				l.setBooks(bdao.readAllBooksByBranchId(l.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libBranch;
	}
	// /////////////////////////////////////////////Publisher
	// Methods/////////////////////////////////////////////////////////

	// /////////////////////////////////////////////Borrower
	// Methods/////////////////////////////////////////////////////////

	///////////////////////////////////////////////Override
	//Method/////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/overrideBook/{bookId}/{branchId}/{cardNo}/{days}", method = RequestMethod.PUT)
	public Integer overrideBook(@PathVariable("bookId") String bookId,@PathVariable("branchId") String branchId,@PathVariable("cardNo") String cardNo,@PathVariable("days") String days) {
		try {
			bldao.alterLoans(cardNo, bookId, branchId, days);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}	

}
