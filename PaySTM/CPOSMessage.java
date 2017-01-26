/* $Id: CPOSMessage.java,v 1.1 2000/03/30 13:48:28 anonymous Exp $ */
package PaySTM;

import java.lang.*;
import java.util.*;

import JUtils.*; //for success tokens


/*
String sTestIn = "Field1=Value1;Field2=Value2;";
Hashtable oHashtable = new Hashtable();
long lInnerResult = JUtils.LRStringToHashTable(sTestIn,oHashtable);
System.out.println(oHashtable.toString());
StringBuffer sTestOut = new StringBuffer();
lInnerResult = JUtils.HashtableToLRString(oHashtable, sTestOut);
System.out.println(sTestOut.toString());
*/


public class CPOSMessage  //wrapper for message implementation
{
  private Hashtable m_oHashtable;

  //#instance ErrorLogStream.Member
  protected static ErrorLogStream dbg=new ErrorLogStream("CPOSMessage", false);
  //#end ErrorLogStream.Member

  public boolean Initialize()
  {
    boolean result = false;

    dbg.Enter("Initialize()");

    TRY:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      m_oHashtable = new Hashtable();
      //End method body

      result = true;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);
      return result;
    }
  }


  public boolean Initialize(String sMessage)
  {
    boolean result = false;

    dbg.Enter("Initialize(string)");

    TRY:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      m_oHashtable = new Hashtable();

      lInnerResult = LRStringToHashTable(sMessage,m_oHashtable);
      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {
        break TRY;
      }
      //End method body

      result = true;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);
      return result;
    }
  }


  public long GetValue(StringBuffer sbFieldValue, String sFieldName)
  {
    long result = JUtils.RESULT_FAILURE;
    dbg.Enter("GetValue");

    TRY:try
    {
      //Begin method body
      Object o = m_oHashtable.get(sFieldName);

      if (null == o){
        break TRY;
      }

      sbFieldValue.append((String)o);
      //End method body

      result = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);
      return result;
    }
  }


  public long SetValue(String sFieldName, String sFieldValue)
  {
    long result = JUtils.RESULT_FAILURE;

    dbg.Enter("SetValue");

    TRY:try
    {
      m_oHashtable.put(sFieldName, sFieldValue);
      result = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);
      return result;
    }
  }


  public long GetMessageTransmissionString(StringBuffer sbFullString)
  {
    long result = JUtils.RESULT_FAILURE;

    dbg.Enter("GetMessageTransmissionString");

    TRY:try
    {
      //Begin method body
      long lInnerResult = HashtableToLRString(m_oHashtable, sbFullString);
      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {
        break TRY;
      }
      //End method body

      result = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);
      return result;
    }
  }


  private long LRStringToHashTable(String input_message, Hashtable htable_name)
  {
    long result = JUtils.RESULT_FAILURE;

    dbg.Enter("LRStringToHashTable");

    TRY:try
    {
      //Begin method body
      int token_count, lindex;
      String input_string, temp_string, key_entry, contents_entry;
      htable_name.clear();
      input_string = input_message;
      token_count = 0;
      StringTokenizer tokens = new StringTokenizer( input_string );
      while (tokens.hasMoreTokens())
      {
        token_count++;
        temp_string = tokens.nextToken( ";" ) ;  //TBD: Use CISOMessage.m_ISO_FieldSeparator
        lindex = temp_string.indexOf( (int) '=' );  //TBD: Use CISOMessage.m_ISO_LRSeparator
        key_entry = temp_string.substring( 0, lindex );
        contents_entry = temp_string.substring( lindex + 1 ) ;
        Object val = htable_name.put( key_entry, contents_entry );
      }
      //End method body

      result = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);

      return result;
    }
  }


  private long HashtableToLRString(Hashtable htable_name, StringBuffer output_message)
  {
    long result = JUtils.RESULT_FAILURE;

    dbg.Enter("HashtableToLRString");

    TRY:try
    {
      //Begin method body
      String temp_keyx, tempstringx;
      //output_message = "";
      for ( Enumeration enum = htable_name.keys(); enum.hasMoreElements(); )
      {
        temp_keyx = enum.nextElement().toString() ;
        Object o_tempstringx = htable_name.get( temp_keyx );
        tempstringx = o_tempstringx.toString();
        output_message.append(temp_keyx + "=" + tempstringx + ";");  //TBD: Use CISOMessage constants
      }
      //End method body

      result = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(result);
      return result;
    }
  }
}
//$Id: CPOSMessage.java,v 1.1 2000/03/30 13:48:28 anonymous Exp $
