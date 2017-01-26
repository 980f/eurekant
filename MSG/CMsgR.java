package Msg;

import java.io.*;
import java.net.*;
import java.util.*;
import JUtils.*;


public class CMsgR implements Runnable  //Message-Receiver class
{
  private boolean l_bLogAll = false;
  private ServerSocket m_oServerSocket = null;
  private int m_nPortToListenOn = -1;
  private int m_nQueueLength = -1;
  private CIMsgT m_oIMsgT = null;
  
  public boolean Initialize(int nPortToListenOn, int nQueueLength, CIMsgT oIMsgT)
  {
    boolean bResult = false;
    
    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", Initialize(), Thread = " + Thread.currentThread() + ", Entry");}
    
    GiveUp:try
    {
      //Begin method body
      m_oIMsgT = oIMsgT;
      
      m_oServerSocket = new ServerSocket(nPortToListenOn, nQueueLength);
      
      if (null == m_oServerSocket)
      {
        break GiveUp;
      }
      
      m_nPortToListenOn = nPortToListenOn;
      m_nQueueLength = nQueueLength;
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
    AcceptAndPostMessages();
  }
  
  
  private void AcceptAndPostMessages()
  {
    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", AcceptAndPostMessages(), Thread = " + Thread.currentThread() + ", Entry");}
    
    GiveUp:try
    {
      //Begin method body
      Socket oSocket = null;
      
      while (true)
      {
        oSocket = m_oServerSocket.accept();
      
        PrintStream oPrintStream = new PrintStream(oSocket.getOutputStream());  
        DataInputStream oInStream = new DataInputStream(oSocket.getInputStream());
        String sMessage = oInStream.readLine();  //TBD: Refine this for complex messages

        m_oIMsgT.IPostMessage(sMessage);

        oPrintStream.println(CMsg.MSGCONTROL_ACKNOWLEDGEMENT_OF_MESSAGE_RECEIPT);  //TBD: Refine
        oPrintStream.flush();
        
        if ((true == JUtils.g_bLogMessages) && (null != JUtils.g_oJUtils))
        {              
          JUtils.g_oJUtils.AddLogEntry(">>" + sMessage + "<< received by " + m_oIMsgT.getClass().getName());
        }
        
        JUtils.YieldProcessor();
      }
      //End method body
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", AcceptAndPostMessages(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }
    
    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", AcceptAndPostMessages(), Thread = " + Thread.currentThread() + ", Finally");}
    } 
  }
  
}