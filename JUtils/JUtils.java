/* $Id$ */
package JUtils;

import java.io.*;
import java.net.*;
import java.util.*;


public class JUtils 
{
  public static final long RESULT_FAILURE = 0;
  public static final long RESULT_SUCCESS = 1;
  public static final long RESULT_SUCCESS_TRUE = 2;
  public static final long RESULT_SUCCESS_FALSE = 3;

  public static boolean g_bLogAll = false;
  public static boolean g_bLogMessages = true;

  public static JUtils g_oJUtils;


  //  private boolean l_bLogAll = false;
  //#instance ErrorLogStream.Member
  protected static ErrorLogStream dbg=new ErrorLogStream("JUtils", false);
  //#end ErrorLogStream.Member


  private boolean m_bEchoOn = false;
  private String m_sIPAddressToSendMessagesTo = null;
  private int m_nPortToSendEchoTo = -1;


  public static synchronized void YieldProcessor()
  {
    try
    {
      Thread.currentThread().yield();
      Thread.currentThread().sleep(10);
    }
    catch(Throwable t)
    {
      if (null != JUtils.g_oJUtils)
      {
        dbg.Message("YieldProcessor():ERROR"); //
      }
    }
  }


  public synchronized void AddLogEntry(String sLogEntry)
  {
    try
    {
      System.out.println(sLogEntry);

      if ( m_bEchoOn)
      {
        Socket oSocket = new Socket(m_sIPAddressToSendMessagesTo, m_nPortToSendEchoTo);

        if (null != oSocket)
        {
          PrintStream oPrintStream = new PrintStream(oSocket.getOutputStream());
          oPrintStream.println(sLogEntry);
          oPrintStream.flush();
          oSocket.close();
          oSocket = null;
        }
      }
    }
    catch (Throwable t)
    {
      /* empty */
    }
  }


  public boolean SetEchoOn(String sIPAddressToSendMessagesTo, int nPortToSendEchoTo)
  {
    boolean bResult = false;

    dbg.Enter("SetEchoOn");

    GiveUp:try
    {
      //Begin method body
      m_bEchoOn = true;
      m_nPortToSendEchoTo = nPortToSendEchoTo;
      m_sIPAddressToSendMessagesTo = sIPAddressToSendMessagesTo;
      //End method body

      bResult = true;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(bResult);
    }

    return bResult;
  }
} // class JUtils
//$Id$
