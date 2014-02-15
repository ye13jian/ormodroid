package com.ormodroid.app.manager;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ormodroid.app.dao.AuthorDAO;
import com.ormodroid.app.dao.BookDAO;
import com.ormodroid.app.model.Author;
import com.ormodroid.app.model.Book;
import com.ormodroid.core.exception.TransactionException;
import com.ormodroid.core.transaction.TransactionCallback;
import com.ormodroid.core.transaction.TransactionCallbackWithoutResult;
import com.ormodroid.core.transaction.TransactionTemplate;

public class LibraryManager {
	private AuthorDAO mAuthorDAO;
	private BookDAO mBookDAO;
	private TransactionTemplate transactionTemplate;
	
	public LibraryManager(SQLiteDatabase mDb) {
		this.mAuthorDAO = new AuthorDAO(mDb);
		this.mBookDAO = new BookDAO(mDb);
		transactionTemplate = new TransactionTemplate(mDb);
	}
	
	public void saveBookWithoutResult(final String authorName, final String bookTitle) throws LibraryManagerException {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult() {
					List<Author> authors = mAuthorDAO.find("name", authorName);
					long authorId;
					
					if(authors.isEmpty()) {
						Author author = new Author(authorName, new Date());
						author.incrementNumOfBooks();
						authorId = mAuthorDAO.save(author);
						
					} else {
						Author author = authors.get(0);
						author.incrementNumOfBooks();
						mAuthorDAO.update(author, author.getId());
						authorId = author.getId();

					}
					
					Book book = new Book(bookTitle, authorId);
					mBookDAO.save(book);
				}
			});
		} catch (Exception e) {
			Log.e("TRANSACTION", e.getMessage());
			throw new LibraryManagerException(e);
		}
	}
	
	public long saveBookWithResult(final String authorName, final String bookTitle) throws LibraryManagerException {
		try {
			return transactionTemplate.execute(new TransactionCallback<Long>() {

				@Override
				public Long doInTransaction() throws TransactionException {
					List<Author> authors = mAuthorDAO.find("name", authorName);
					long authorId;
					
					if(authors.isEmpty()) {
						Author author = new Author(authorName, new Date());
						author.incrementNumOfBooks();
						authorId = mAuthorDAO.save(author);
						
					} else {
						Author author = authors.get(0);
						author.incrementNumOfBooks();
						mAuthorDAO.update(author, author.getId());
						authorId = author.getId();

					}
					
					Book book = new Book(bookTitle, authorId);
					return mBookDAO.save(book);
				}
				
			});
			
		} catch (Exception e) {
			Log.e("TRANSACTION", e.getMessage());
			throw new LibraryManagerException(e);
		}
	}

}
