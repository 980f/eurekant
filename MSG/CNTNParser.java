/* $Id: CNTNParser.java,v 1.3 2000/03/30 13:48:27 anonymous Exp $ */
package Msg;

import PaySTM.*;

import java.io.*;
import java.net.*;
import java.util.*;

import JUtils.*;


public class CNTNParser implements CIMsgT
{
  private CIMsgT m_oResponseTarget = null;
  private boolean approved;
  private String responseMessage;

  //#instance ErrorLogStream.Member
  protected static ErrorLogStream dbg;

  CNTNParser(){
    dbg.Init("CNTNParser", false);
  }
  //#end ErrorLogStream.Member

  public void Initialize(CIMsgT oMsgT)
  {
    m_oResponseTarget=oMsgT;
  }


  public synchronized long IPostMessage(String sMsg)
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("IPostMessage");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;

      onMessage(sMsg);

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit(lResult);
      return lResult;
    }
  }

  private void onMessage(String sMsg)
  {
    long lInnerResult=JUtils.RESULT_FAILURE;
    CPOSMessage oPOSMessage = new CPOSMessage();
    oPOSMessage.Initialize();
    Hashtable oHashtable = new Hashtable();
    Hashtable eHashtable = new Hashtable();
    Hashtable cHashtable = new Hashtable();
    StringBuffer buf=new StringBuffer();
    oHashtable.clear();
    // For live transaction to remove null
    lInnerResult=CISOMessage.convert_raw_bit_map_to_HashTable(sMsg.substring(4), oHashtable);
          //lInnerResult=CISOMessage.convert_raw_bit_map_to_HashTable(sMsg, oHashtable);
          //lInnerResult=CISOMessage.Obtain_hash_table_of_errors(buf, oHashtable, eHashtable);
          //System.out.println(buf.toString());
          //buf.setLength(0);
          ///eHashtable.clear();
          //lInnerResult=CISOMessage.Obtain_htable_of_correct_bit_presence(buf, oHashtable, cHashtable ,eHashtable );
          //System.out.println(buf.toString());
    lInnerResult=CISOMessage.rekey_htable_from_short_name_to_long_name(oHashtable);
    Object val=oHashtable.get(CISOMessage.m_ISO_TransactionType);
    System.out.println(val.toString());

    int transType=val.intValue();

    switch(transType){
      case 810:
      {
        val=oHashtable.get(CISOMessage.m_ISO_ResponseCode);
        System.out.println(val.toString());
        checkResponse(val);
        if(approved)
        {
          lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType, CPaySTM.MSGTYPE_STM_LOG_IN_SUCCESS);
        }
        else
        {
          System.out.println("Response = Not Approved");
          lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_NOT_APPROVED);
          lInnerResult=oPOSMessage.SetValue(CPaySTM.RejectionType, responseMessage);
        }

      } break;

      case 210:
      {
        val=oHashtable.get(CISOMessage.m_ISO_ResponseCode);
        System.out.println(val.toString());
        checkResponse(val);
        if(approved)
        {
          System.out.println("Response = Success");
          lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_FROM_NTN);
        }
        else
        {
          System.out.println("Response = Not Approved");
          lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_NOT_APPROVED);
          lInnerResult=oPOSMessage.SetValue(CPaySTM.RejectionType, responseMessage);
        }

      } break;

      case 410:
      {
        val=oHashtable.get(CISOMessage.m_ISO_ResponseCode);
        checkResponse(val);
        if(approved)
        {
          System.out.println("Response = Success");
          lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_FROM_NTN);
        }
        else
        {
          System.out.println("Response = Not Approved");
          lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_NOT_APPROVED);
          lInnerResult=oPOSMessage.SetValue(CPaySTM.RejectionType, responseMessage);
        }
      } break;

      //////////////////////////// For NTN simulator//////////////////////////////
      case 200:
      {
        lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_FROM_NTN);
      } break;

      case 400:
      {
        lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_RESPONSE_FROM_NTN);
      } break;
      ///////////////////////////////////////////////////////////////////////////
    } //end transaction type

    StringBuffer sbMsg=new StringBuffer();
    lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
    lInnerResult=m_oResponseTarget.IPostMessage(sbMsg.toString());
  }

  private void checkResponse(Object obj)
  {
    int responseCode=obj.intValue();

    switch(responseCode){
      case 0:
      case 8: {
        approved=true;
      } break;

      case 03:
      case 12:
      case 13:
      case 14:{
        responseMessage="Data Invalid";
        approved=false;
      } break;

      case 4: {
        approved=false;
        responseMessage="Pick up card";
      } break;

      case 5:
      case 10:
      case 15:
      {
        approved=false;
        responseMessage="Rejected";
      } break;

      case 41:
      case 43:
      {
        approved=false;
        responseMessage="Card lost or stolen";
      } break;

      default:
      {
        approved=false;
        responseMessage="Generic";
      } break;
    }

  }
}
//$Id: CNTNParser.java,v 1.3 2000/03/30 13:48:27 anonymous Exp $
