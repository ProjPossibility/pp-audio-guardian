package projectpossibility;

import javax.microedition.rms.*;

public class COptionsDb 
{
	private RecordStore m_recordStore;
	
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
	
	public double LoadDouble(String tag, double defaultVal)
	{
		String doubleString = LoadString(tag, Double.toString(defaultVal));
		return Double.parseDouble(doubleString);
	}
	
	public int LoadInt(String tag, int defaultVal)
	{
		String intString = LoadString(tag, Integer.toString(defaultVal));
		return Integer.parseInt(intString);
	}
	
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
	
	public void SaveBool(String tag, boolean val)
	{
		int intVal = 0;
		if(val == true)
			intVal = 1;
		
		SaveInt(tag, intVal);
	}
	
	public void SaveDouble(String tag, double val)
	{
		SaveString(tag, Double.toString(val));
	}
	
	public void SaveInt(String tag, int val)
	{
		SaveString(tag, Integer.toString(val));
	}
	
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
