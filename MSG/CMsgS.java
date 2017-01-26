package Msg;
  
import java.io.*;
import java.net.*;
import java.util.*;

import JUtils.*;

/*
Note: If data is sent back down the same socket, that data (except for routine acknowledgements) will
be forwarded to m_oResponseTarget.
*/

public class CMsgS implements Runnable, CIMsgT  //Message-Sender class
{
  private boolean l_bLogAll = false;
  private int m_nPortToSendMessageTo = -1;
  private String m_sIPAddressToSendMessageTo = null;
  private Vector m_oMsgQueue = null;
  private CIMsgT m_oResponseTarget = null;

  
  public boolean Initialize(CIMsgT oResponseTarget, String sIPAddressToSendMessageTo, int nPortToSendMessageTo)
  {
    boolean bResult = false;
    
    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", Initialize(), Thread = " + Thread.currentThread() + ", Entry");}
    
    GiveUp:try
    {
      //Begin method body
      m_oMsgQueue = new Vector();
      
      m_oResponseTarget = oResponseTarget; 
      
      m_sIPAddressToSendMessageTo = sIPAddressToSendMessageTo;
      m_nPortToSendMessageTo = nPortToSendMessageTo;
      //End method body
      
      bResult = true;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", Initialize(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }
    
    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", Initialize(), Thread = " + Thread.currentThread() + ", Finally, bResult = " + bResult);}
  
      return bResult;
    } 
  }
  
  
  public void run()
  {
    ProcessMessages();
  }
  
  
  private void ProcessMessages()
  {
    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", Entry");}
    
    GiveUp:try
    {
      //Begin method body
      boolean bInnerResult = false;
      
      while (true)
      {
        if (false == m_oMsgQueue.isEmpty())
        {
          Object oMessage = m_oMsgQueue.firstElement();
          
          if (true == oMessage instanceof String)
          {
            String sMessage = (String)oMessage;
            
            Socket oSocket = new Socket(m_sIPAddressToSendMessageTo, m_nPortToSendMessageTo);
            
            if (null != oSocket)
            {
              bInnerResult = true;
            
              PrintStream oPrintStream = new PrintStream(oSocket.getOutputStream());
              DataInputStream oInStream = new DataInputStream(oSocket.getInputStream());
              
              oPrintStream.println(sMessage);  //TBD: Refine to proper format and number of lines
              oPrintStream.flush();
              
              if ((true == JUtils.g_bLogMessages) && (null != JUtils.g_oJUtils))
              {              
                JUtils.g_oJUtils.AddLogEntry("<<" + sMessage + ">> sent by " + m_oResponseTarget.getClass().getName());
              }

              String sResponse = oInStream.readLine();  //TBD: Refine for complex messages
              
              //System.out.println(sResponse + " received by CMsgS");
              
              if (false == sResponse.equals(CMsg.MSGCONTROL_ACKNOWLEDGEMENT_OF_MESSAGE_RECEIPT))  //ignore routine acknowledgement message
              {
                m_oResponseTarget.IPostMessage(sResponse);

                if ((true == JUtils.g_bLogMessages) && (null != JUtils.g_oJUtils))
                {              
                  JUtils.g_oJUtils.AddLogEntry(">>" + sResponse + "<< received by " + m_oResponseTarget.getClass().getName());
                }
                
              }

              oSocket.close();
              oSocket = null;


              if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) 
              {
                JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", [" + sMessage + "] sent to " + m_sIPAddressToSendMessageTo + " on Port " + m_nPortToSendMessageTo);
              }
            }
          }
          
          if (false == bInnerResult)
          {
            if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) 
            {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", Invalid message received and discarded.");}
          }

          m_oMsgQueue.removeElementAt(0);
          
          bInnerResult = false;
        }
        
        JUtils.YieldProcessor();
      }
      //End method body
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }
    
    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", Finally");}
    } 
  }

  
  public synchronized long IPostMessage(String sMsg)
  {
    long lResult = JUtils.RESULT_FAILURE;
    
    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", IPostMessage(), Thread = " + Thread.currentThread() + ", Entry");}
    
    GiveUp:try
    {
      //Begin method body
      m_oMsgQueue.addElement(sMsg);
      //End method body
      
      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", IPostMessage(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }
    
    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", IPostMessage(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}
  
      return lResult;
    } 
  }

}