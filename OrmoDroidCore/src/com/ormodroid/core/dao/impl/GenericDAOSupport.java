package com.ormodroid.core.dao.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ormodroid.core.annotation.SQLiteColumn;
import com.ormodroid.core.annotation.SQLitePrimaryKey;
import com.ormodroid.core.annotation.SQLiteTable;
import com.ormodroid.core.dao.GenericDAO;
import com.ormodroid.core.exception.ObjectNotFoundException;
import com.ormodroid.core.exception.SQLiteMappingException;

public abstract class GenericDAOSupport<T> implements GenericDAO<T> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    private Class<T> persistentClass;
    private SQLiteDatabase mDb;
    private List<Field> fields;
    private String pk;

    public GenericDAOSupport(final Class<T> persistentClass, SQLiteDatabase mDb) {
        this.persistentClass = persistentClass;
        this.mDb = mDb;
        fields = new ArrayList<Field>();
        for (Field field : persistentClass.getDeclaredFields()) {
			if(field.isAnnotationPresent(SQLiteColumn.class)) {
				fields.add(field);
				if(field.isAnnotationPresent(SQLitePrimaryKey.class)) {
					pk = field.getAnnotation(SQLiteColumn.class).name();
				}
			}
		}
    }

    public T get(Object id) throws ObjectNotFoundException {
    	String selection = pk + " = ?";
    	Cursor cursor = mDb.query(getTableName(), getProjection(), selection, new String[]{id.toString()}, null, null, null);
    	if(cursor.moveToNext()) {
    		try {
				return fromCursor(cursor);
				
			} catch (IllegalAccessException e) {
				throw new SQLiteMappingException(e);
				
			} catch (InstantiationException e) {
				throw new SQLiteMappingException(e);
				
			} catch (ParseException e) {
				throw new SQLiteMappingException(e);
				
			}
    	} else {
    		throw new ObjectNotFoundException("No object found for class " + persistentClass.getSimpleName() + " and pk = " + id);
    	}
    }
    
    @Override
	public T get(String column, String value) throws ObjectNotFoundException {
		List<T> items = find(column, value);

		if(!items.isEmpty()) {
    		return items.get(0);
    	} else {
    		throw new ObjectNotFoundException("No object found for class " + persistentClass.getSimpleName() + " and " + column + " = " + value);
    	}
	}
    
    @Override
	public T get(Map<String, String> values) throws ObjectNotFoundException {
		List<T> items = find(values);

		if(!items.isEmpty()) {
    		return items.get(0);
    	} else {
    		throw new ObjectNotFoundException("No object found for class " + persistentClass.getSimpleName() + " and " + values);
    	}
	}
    
	@Override
	public List<T> find(String column, String value) {
		List<T> items = new ArrayList<T>();
		String selection = column + " = ?";
		Cursor cursor = mDb.query(getTableName(), getProjection(), selection, new String[]{value}, null, null, null);
    	if(cursor.moveToNext()) {
    		try {
    			items.add(fromCursor(cursor));
				
			} catch (IllegalAccessException e) {
				throw new SQLiteMappingException(e);
				
			} catch (InstantiationException e) {
				throw new SQLiteMappingException(e);
				
			} catch (ParseException e) {
				throw new SQLiteMappingException(e);
				
			}
    	}
    	
    	return items;
	}
	
	@Override
	public List<T> find(Map<String, String> values) {
		List<T> items = new ArrayList<T>();
		String selection = "1 ";
		List<String> selectionArgs = new ArrayList<String>();
		if(!values.isEmpty()) {
			for (String column : values.keySet()) {
				selection = selection + "AND " + column + " = ?";
				selectionArgs.add(values.get(column));
			}
		}
		
		Cursor cursor = mDb.query(getTableName(), getProjection(), selection, selectionArgs.toArray(new String[values.size()]), null, null, null);
    	if(cursor.moveToNext()) {
    		try {
    			items.add(fromCursor(cursor));
				
			} catch (IllegalAccessException e) {
				throw new SQLiteMappingException(e);
				
			} catch (InstantiationException e) {
				throw new SQLiteMappingException(e);
				
			} catch (ParseException e) {
				throw new SQLiteMappingException(e);
				
			}
    	}

		return items;
	}

	@Override
    public List<T> all() {
    	List<T> items = new ArrayList<T>();
    	Cursor cursor = mDb.query(getTableName(), getProjection(), null, new String[]{}, null, null, null);
    	
    	try {
    		while(cursor.moveToNext()) {
				items.add(fromCursor(cursor));
    		}
    		
    	} catch (InstantiationException e) {
			throw new SQLiteMappingException(e);
			
		} catch (IllegalAccessException e) {
			throw new SQLiteMappingException(e);
			
		} catch (ParseException e) {
			throw new SQLiteMappingException(e);
			
		}
    	
        return items;
    }
    
    @Override
    public long count() {
    	long count = DatabaseUtils.queryNumEntries(mDb, getTableName());
    	return count;
    }
    
    @Override
	public long count(String column, String value) {
		String selection = column + " = ?";
		long count = DatabaseUtils.queryNumEntries(mDb, getTableName(), selection, new String[]{value});
    	return count;
	}
    
    @Override
	public long count(Map<String, String> values) {
		String selection = "1 ";
		List<String> selectionArgs = new ArrayList<String>();
		if(!values.isEmpty()) {
			for (String column : values.keySet()) {
				selection = selection + "AND " + column + " = ?";
				selectionArgs.add(values.get(column));
			}
		}
		
		long count = DatabaseUtils.queryNumEntries(mDb, getTableName(), selection, selectionArgs.toArray(new String[selectionArgs.size()]));
		return count;
	}
    
	@Override
	public long save(T object) throws SQLiteMappingException {
		try {
			return mDb.insert(getTableName(), null, toContentValues(object));
			
		} catch (IllegalArgumentException e) {
			throw new SQLiteMappingException(e);
			
		} catch (IllegalAccessException e) {
			throw new SQLiteMappingException(e);
			
		}
	}
	
	@Override
	public int update(T object, Object id) {
		try {
			ContentValues values = toContentValues(object);
			String whereClause = pk + " = ?";
			
			return mDb.update(getTableName(), values, whereClause, new String[]{id.toString()});
			
		} catch (IllegalArgumentException e) {
			throw new SQLiteMappingException(e);
			
		} catch (IllegalAccessException e) {
			throw new SQLiteMappingException(e);
			
		}
	}
	
	@Override
	public int delete(Object id) {
    	String whereClause = pk + " = ?";
    	int deletedItems = mDb.delete(getTableName(), whereClause, new String[]{id.toString()});
    	return deletedItems;
    }

    private String getTableName() throws SQLiteMappingException {
        SQLiteTable tableName = persistentClass.getAnnotation(SQLiteTable.class);
        if(tableName == null) throw new SQLiteMappingException("No mapping found for class " + persistentClass.getName());
        return tableName.name();
    }
    
    private ContentValues toContentValues(T object) throws IllegalAccessException {
    	ContentValues values = new ContentValues();
    	for (Field field : fields) {
    		field.setAccessible(true);
			if(field.getType().equals(Integer.class)) {
				values.put(field.getAnnotation(SQLiteColumn.class).name(), (Integer) field.get(object));
			} else if(field.getType().equals(Long.class)) {
				values.put(field.getAnnotation(SQLiteColumn.class).name(), (Long) field.get(object));
			} else if(field.getType().equals(Date.class)) {
				values.put(field.getAnnotation(SQLiteColumn.class).name(), sdf.format((Date) field.get(object)));
			} else {
				values.put(field.getAnnotation(SQLiteColumn.class).name(), (String) field.get(object));
			}
		}
    	return values;
    }
    
    private T fromCursor(Cursor cursor) throws IllegalAccessException, InstantiationException, ParseException {
    	T item = persistentClass.newInstance();
    	for (Field field : fields) {
			field.setAccessible(true);
			if(field.getType().equals(Integer.class)) {
				field.set(item, cursor.getInt(cursor.getColumnIndexOrThrow(field.getAnnotation(SQLiteColumn.class).name())));
			} else if(field.getType().equals(Long.class)) {
				field.set(item, cursor.getLong(cursor.getColumnIndexOrThrow(field.getAnnotation(SQLiteColumn.class).name())));
			} else if(field.getType().equals(Date.class)) {
				String date = cursor.getString(cursor.getColumnIndexOrThrow(field.getAnnotation(SQLiteColumn.class).name()));
				if(date != null) {
					Date parsedDate = sdf.parse(date);
					field.set(item, parsedDate);
				}
				
			} else {
				field.set(item, cursor.getString(cursor.getColumnIndexOrThrow(field.getAnnotation(SQLiteColumn.class).name())));
			}
		}
    	
    	return item;
    }
    
    private String[] getProjection() {
    	String[] projection = new String[fields.size()];
    	for (int i = 0; i < fields.size(); i++) {
    		projection[i] = fields.get(i).getAnnotation(SQLiteColumn.class).name();
		}
    	return projection;
    }

}