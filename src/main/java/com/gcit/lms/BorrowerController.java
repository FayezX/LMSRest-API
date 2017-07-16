package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;

/**
 * Handles requests for the application home page.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/borrower")
public class BorrowerController {
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	BorrowerDAO bodao;
	
	@Autowired
	LibraryBranchDAO ldao;
	
	@Autowired
	LibraryBranch libraryBranch;
	
	@Autowired
	BookLoans bookLoans;
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);
	
	@RequestMapping(value = "/BorrowersMenu", method = RequestMethod.GET, produces="application/json")
	public List<LibraryBranch> librarianMenu(){
		List<LibraryBranch> libraryBranch = new ArrayList<>();
		try {
			libraryBranch = ldao.readAllLibraryBranchs();
			for(LibraryBranch a : libraryBranch){
				a.setBooks(bdao.readAllBooksByBranchId(a.getBranchId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libraryBranch;
		
	}
	
	 @RequestMapping(value = "/ValidCard/{cardNo}", method = RequestMethod.GET)
	public Integer checkCardNo(@PathVariable("cardNo") String cardNo){
		try {
			return bodao.checkBorId(cardNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
		
	 @RequestMapping(value = "/CheckOut/{bookId}/{branchId}", method = RequestMethod.PUT)
	public Integer checkOut(@PathVariable("bookId") String bookId,@PathVariable("branchId") String branchId){
		try {
		     bodao.checkOut(bookId,branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		return null;
	}
	 
	@RequestMapping(value = "/Return/{bookId}/{branchId}", method = RequestMethod.PUT)
	public Integer returnBook(@PathVariable("bookId") String bookId,@PathVariable("branchId") String branchId ){
		try {
		     bodao.returnBook(bookId,branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
}
