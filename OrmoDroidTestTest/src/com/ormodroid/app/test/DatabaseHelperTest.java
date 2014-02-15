package com.ormodroid.app.test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.ormodroid.app.dao.AuthorDAO;
import com.ormodroid.app.dao.EventDAO;
import com.ormodroid.app.model.Author;
import com.ormodroid.app.model.Event;
import com.ormodroid.app.schema.DbHelper;
import com.ormodroid.core.exception.ObjectNotFoundException;

public class DatabaseHelperTest extends AndroidTestCase {
	private Calendar calendar = Calendar.getInstance();
	
	private DbHelper dbHelper;
	private EventDAO eventDAO;
	private AuthorDAO authorDAO;
	private SQLiteDatabase mDb;

	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), null);
		dbHelper = new DbHelper(context);
		mDb = dbHelper.getWritableDatabase();
		
		eventDAO = new EventDAO(mDb);
		authorDAO = new AuthorDAO(mDb);
		assertNotNull(dbHelper);
		assertNotNull(eventDAO);
		assertNotNull(authorDAO);
	}

	public void testEventDAO() throws ObjectNotFoundException {
		// add one event
		Event testEvent = new Event("test-1");
		long generatedId = eventDAO.save(testEvent);
		assertNotNull(generatedId);
		assertEquals(1, generatedId);
		
		// add another event
		testEvent = new Event("test-2");
		generatedId = eventDAO.save(testEvent);
		
		// get all events
		List<Event> events = eventDAO.all();
		assertEquals(2, events.size());
		assertTrue(events.get(0) instanceof Event);
		for (Event event : events) {
			assertNotNull(event.getId());
			assertNotNull(event.getTitle());	
		}
		
		// get event with _id = 1
		Event event = eventDAO.get(1L);
		assertNotNull(event);
		assertNotNull(event.getId());
		assertNotNull(event.getTitle());
		event = eventDAO.get(2L);
		assertNotNull(event);
		assertNotNull(event.getId());
		assertNotNull(event.getTitle());
		
		// get event with title = test-2
		events = eventDAO.find("title", "test-2");
		assertNotNull(events.get(0));
		assertNotNull(events.get(0).getId());
		assertNotNull(events.get(0).getTitle());
		assertEquals("test-2", events.get(0).getTitle());
		
		// get event with title = test-2 and _id = 2
		Map<String, String> values = new HashMap<String, String>();
		values.put("title", "test-2");
		values.put("_id", "2");
		events = eventDAO.find(values);
		assertNotNull(events.get(0));
		assertNotNull(events.get(0).getId());
		assertNotNull(events.get(0).getTitle());
		assertEquals("test-2", events.get(0).getTitle());
		
		// delete event with _id = 2
		int deleted = eventDAO.delete(2L);
		assertEquals(1, deleted);
		try {
			event = eventDAO.get(2L);
			assertNull(event);
		} catch (ObjectNotFoundException e) {
			assertTrue("should raise", true);
		}
		
		// update event with _id = 1
		event = eventDAO.get(1L);
		assertEquals("test-1", event.getTitle());
		event.setTitle("test-modified");
		int updated = eventDAO.update(event, event.getId());
		assertEquals(1, updated);
		event = eventDAO.get(1L);
		assertEquals("test-modified", event.getTitle());
	}
	
	public void testAuthorDAO() throws ObjectNotFoundException {
		Author author = new Author("author-name", calendar.getTime());
		long generatedId = authorDAO.save(author);
		assertEquals(1, generatedId);
		
		author = authorDAO.get(1);
		assertNotNull(author);
		assertNotNull(author.getDob());
		assertEquals(0, author.getNumOfBooks().intValue());
		
		long count = authorDAO.count();
		assertEquals(1, count);
		
		count = authorDAO.count("name", "author-name");
		assertEquals(1, count);
		
		count = authorDAO.count("name", "unexisting-name");
		assertEquals(0, count);
		
		author = authorDAO.get("name", "author-name");
		assertNotNull(author);
		
		try {
			authorDAO.get("name", "unexisting-name");
		} catch (ObjectNotFoundException e) {
			assertTrue("should raise this exception", true);
		}
		
		Map<String, String> values = new HashMap<String, String>();
		values.put("name", "author-name");
		values.put("num_of_books", "0");
		author = authorDAO.get(values);
		assertNotNull(author);
	}

	public void tearDown() throws Exception {
		dbHelper.close();
		super.tearDown();
	}

}
