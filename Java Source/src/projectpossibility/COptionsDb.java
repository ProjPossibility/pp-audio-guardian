package projectpossibility;

import javax.microedition.rms.*;

/**
 * An adapter layer over the J2ME RecordStore that allows users to store data values as
 * name-value pairs. The values are persisted in the phone's setting database when the application
 * exits and can be restored the next time the application starts.
 * <p>
 * <b>WARNING</b> Only a single value of ANY type can be associated with a given name.  Storing a value
 * to an existing name, will overwrite that value (regardless of type).
 */
public class COptionsDb 
{
	private RecordStore m_recordStore;
	
	/**
	 * Construct a default options database.  By default, a COptionsDb stores its
	 * values in the "options" RecordStore.
	 */
	public COptionsDb()
	{
		// Set up the record store
		try
		{
			m_recordStore = RecordStore.openRecordStore("options", true);
		}
		catch (RecordStoreException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 
	 * Load boolean value stored with name "tag" from the options database
	 * @param tag The "name" of the boolean value to load
	 * @param defaultVal The default value to return for the option if it is not yet stored in the db
	 * @return The value of the specified boolean type
	 */
	public boolean LoadBool(String tag, boolean defaultVal)
	{
		int intVal = 0;
		if(defaultVal == true)
			intVal = 1;
		
		intVal = LoadInt(tag, intVal);
		
		if(intVal == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Load a double value stored with name "tag" from the options database
	 * @param tag The name of the double value to load
	 * @param defaultVal The default value to return if it is not available in the db
	 * @return The value of the specified double type
	 */
	public double LoadDouble(String tag, double defaultVal)
	{
		String doubleString = LoadString(tag, Double.toString(defaultVal));
		return Double.parseDouble(doubleString);
	}
	
	/**
	 * Load an integer value stored with name "tag" from the options database
	 * @param tag The name of the integer value to load
	 * @param defaultVal The default value to return if it is not available in the db
	 * @return The value of the specified integer type
	 */
	public int LoadInt(String tag, int defaultVal)
	{
		String intString = LoadString(tag, Integer.toString(defaultVal));
		return Integer.parseInt(intString);
	}
	
	/**
	 * Load a string value stored with name "tag" from the options database
	 * @param tag The name of the string value to load
	 * @param defaultVal The default string to return if it not available in the db
	 * @return The value of the specified string type
	 */
	public String LoadString(String tag, String defaultVal)
	{
		String recordVal = defaultVal;
		int recordId = locateRecord(tag);
		
		if(recordId != 0)
		{
			try
			{
				String recordString = new String(m_recordStore.getRecord(recordId));
				recordVal = recordString.substring(tag.length());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return recordVal;
	}
	
	/** 
	 * Store a boolean value into the options database
	 * @param tag The name of the value
	 * @param val The boolean value to store
	 */
	public void SaveBool(String tag, boolean val)
	{
		int intVal = 0;
		if(val == true)
			intVal = 1;
		
		SaveInt(tag, intVal);
	}
	
	/**
	 * Store a double value into the options database
	 * @param tag The name of the value
	 * @param val The double value to store
	 */
	public void SaveDouble(String tag, double val)
	{
		SaveString(tag, Double.toString(val));
	}
	
	/**
	 * Store an integer value into the options database
	 * @param tag The name of the value
	 * @param val The integer value to store
	 */
	public void SaveInt(String tag, int val)
	{
		SaveString(tag, Integer.toString(val));
	}
	
	/**
	 * Store a string value into the options database
	 * @param tag The name of the value
	 * @param val The integer value to store
	 */
	public void SaveString(String tag, String val)
	{
		String recordString = new String(tag);
		recordString = recordString + val;
		
		byte[] record = recordString.getBytes();
		int recordId = locateRecord(tag);
		
		try
		{
			if(recordId == 0)
			{
				m_recordStore.addRecord(record, 0, record.length);
			}
			else
			{
				m_recordStore.setRecord(recordId, record, 0, record.length);
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * (Protected) Locate the RecordStore ID of a previously stored record
	 * @param tag The name of the record to locate
	 * @return
	 */
	protected int locateRecord(String tag)
	{
		try
		{
			RecordEnumeration re = null;
			re = m_recordStore.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);
			
			while(re.hasNextElement())
			{
				int recordId = re.nextRecordId();
				byte [] record = m_recordStore.getRecord(recordId);
				String recordString = new String(record);
				
				if(recordString.startsWith(tag))
				{
					return recordId;
				}
			}
		}
		catch(Exception e)
		{
			// Print the exception error
			e.printStackTrace();
		}
		
		return 0;
	}
}
