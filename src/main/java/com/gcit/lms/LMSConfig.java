package com.gcit.lms;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;
@Configuration
public class LMSConfig {
	public String driver = "com.mysql.jdbc.Driver";
	public String url = "jdbc:mysql://databasetest2.cswmfnxnrnuj.us-east-1.rds.amazonaws.com:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public String username = "databasetest2";
	public String password = "databasetest2";
	
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	@Bean
	public JdbcTemplate template(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	@Bean
	public BorrowerDAO bodao(){
		return new BorrowerDAO();
	}
	
	@Bean
	public BookLoansDAO bldao(){
		return new BookLoansDAO();
	}
	
	@Bean
	public GenreDAO gdao(){
		return new GenreDAO();
	}
	@Bean
	public LibraryBranchDAO ldao(){
		return new LibraryBranchDAO();
	}
	@Bean
	public Author author(){
		return new Author();
	}
	
	@Bean
	public LibraryBranch libraryBranch(){
		return new LibraryBranch();
	}
	
	@Bean
	public Book book(){
		return new Book();
	}
	
	@Bean
	public BookLoans bookLoans(){
		return new BookLoans();
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	}

}
