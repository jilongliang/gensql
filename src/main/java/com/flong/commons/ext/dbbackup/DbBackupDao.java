package com.flong.commons.ext.dbbackup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

public abstract interface DbBackupDao {
	
	/***
	 * 
	 * @return
	 */
	public abstract String getSchema();

	/**
	 * 
	 * @param paramString
	 * @return
	 */
	public abstract List<String> getTables(String paramString);

	/**
	 * 
	 * @param paramString1
	 * @param paramString2
	 * @return
	 */
	public abstract String getTableDDL(String paramString1, String paramString2);

	/**
	 * 
	 * @param paramString1
	 * @param paramString2
	 * @param paramBufferedWriter
	 */
	public abstract void writeData(String paramString1, String paramString2, BufferedWriter paramBufferedWriter);

	/***
	 * 
	 * @param paramBufferedReader
	 * @throws DataAccessException
	 * @throws IOException
	 */
	public abstract void restore(BufferedReader paramBufferedReader) throws DataAccessException, IOException;
}