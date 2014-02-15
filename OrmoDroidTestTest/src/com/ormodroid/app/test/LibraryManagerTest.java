package com.ormodroid.app.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.ormodroid.app.dao.AuthorDAO;
import com.ormodroid.app.manager.LibraryManager;
import com.ormodroid.app.manager.LibraryManagerException;
import com.ormodroid.app.model.Author;
import com.ormodroid.app.schema.DbHelper;
import com.ormodroid.core.exception.ObjectNotFoundException;

public class LibraryManagerTest extends AndroidTestCase {
	private DbHelper mDbHelper;
	private LibraryManager mLibraryManager;
	private AuthorDAO mAuthorDAO;

	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), null);
		mDbHelper = new DbHelper(context);
		mLibraryManager = new LibraryManager(mDbHelper.getWritableDatabase());
		mAuthorDAO = new AuthorDAO(mDbHelper.getWritableDatabase());
		assertNotNull(mLibraryManager);
		assertNotNull(mAuthorDAO);
	}

	public void testLibraryManagerTransaction() throws ObjectNotFoundException {
		long ret;
		try {
			ret = mLibraryManager.saveBookWithResult("my-author-1", "my-book-1");
			assertNotNull(ret);
			assertEquals(1, ret);
		} catch (LibraryManagerException e) {
			Log.e("TRANSACTION", e.getMessage());
			fail();
		}
		
		try {
			ret = mLibraryManager.saveBookWithResult("my-author-2", "my-book-2");
			assertNotNull(ret);
			assertEquals(2, ret);
		} catch (LibraryManagerException e) {
			Log.e("TRANSACTION", e.getMessage());
			fail();
		}
		
		List<Author> authors = mAuthorDAO.find("name", "my-author-1");
		assertFalse(authors.isEmpty());
		assertEquals(1, authors.size());
		
		Author author2 = mAuthorDAO.find("name", "my-author-2").get(0);
		assertNotNull(author2);
		assertEquals(1, author2.getNumOfBooks().intValue());
		
		try {
			ret = mLibraryManager.saveBookWithResult("my-author-2", "my-book-2");
			assertNotNull(ret);
			assertEquals(3, ret);
		
		} catch (LibraryManagerException e) {
			Log.e("TRANSACTION", e.getMessage());
			fail();
		}
		
		author2 = mAuthorDAO.find("name", "my-author-2").get(0);
		assertNotNull(author2);
		assertEquals(2, author2.getNumOfBooks().intValue());
		
		try {
			mLibraryManager.saveBookWithoutResult("my-author-3", "my-book-3");
		} catch (LibraryManagerException e) {
			Log.e("TRANSACTION", e.getMessage());
			fail();
		}
		
		Author author3 = mAuthorDAO.find("name", "my-author-3").get(0);
		assertNotNull(author3);
		assertEquals(1, author3.getNumOfBooks().intValue());
	}

	public void tearDown() throws Exception {
		mDbHelper.close();
		super.tearDown();
	}

}
