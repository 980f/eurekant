package PaySTM;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date.*;
import JUtils.*;
import Msg.*;

public class CPaySTM implements CIMsgT
{


  //Begin Port Constants
  public final static int PORT_THAT_REMOTE_ECHO_LISTENS_ON = 4444;  //Well-known port for this application.
  public final static int PORT_THAT_JPOS_LISTENS_ON = 4445;
  public final static int PORT_THAT_AUTHORIZER_LISTENS_ON = 4446;
  //public final static int PORT_THAT_APPROVAL_AGENCY_LISTENS_ON = 4446;
  public final static int PORT_THAT_STM_LISTENS_ON = 4447;
  //End Port Constants

  //Begin MSR Constants
  public final static String PrimaryAccountNumber = CISOMessage.m_ISO_PrimaryAccountNumber;
  public final static String ExpirationDate = CISOMessage.m_ISO_ExpirationDate;
  public final static String Track1DiscretionaryData = "Track1DiscretionaryData";
  public final static String Track2DiscretionaryData = "Track2DiscretionaryData";
  public final static String ServiceCode = "ServiceCode";
  public final static String MessageType = "MessageType";
  public final static String RejectionType = "RejectionType";
  public final static String Track1Data = CISOMessage.m_ISO_Track1Data;
  public final static String Track2Data = CISOMessage.m_ISO_Track2Data;
  public final static String Title = "Title";
  public final static String FirstName = "FirstName";
  public final static String MiddleInitial = "MiddleInitial";
  public final static String SurName = "SurName";
  public final static String Suffix = "Suffix";
  public final static String PaymentAmount="PaymentAmount";
  public final static String PaymentMethod="PaymentMethod";
  public final static String TransactionType="TransactionType";
  public final static String PinNumber="PinNumber";
  public final static String SecurityInformation="SecurityInformation";
  public final static String KeyManagementInformation="KeyManagementInformation";
  //End MSR Constants

   //Begin JPOS Messages
  public final static String MSGTYPE_JPOS_SHOW_FORM_AMOUNT = "ShowFormAmount";
  public final static String MSGTYPE_JPOS_SHOW_FORM_CHOOSE_TRANSACTION_TYPE = "ShowFormChooseTransactionType";
  public final static String MSGTYPE_JPOS_SHOW_FORM_CHOOSE_PAYMENT_TYPE = "ShowFormChoosePaymentType";
  public final static String MSGTYPE_JPOS_SHOW_FORM_CHOOSE_RETURN_TYPE = "ShowFormChooseReturnType";
  public final static String MSGTYPE_JPOS_GET_CARD_DATA = "GetCardData";
  public final static String MSGTYPE_JPOS_SHOW_ADD = "ThankYouAdd";
  public final static String MSGTYPE_JPOS_SHOW_SIGNATURE_CAPTURE="ShowSignatureCapture";
  public final static String MSGTYPE_JPOS_GET_CARD_DATA_AGAIN = "GetCardDataAgain";
  public final static String MSGTYPE_JPOS_PRINT_RECEIPT="PrintReceipt";
  public final static String MSGTYPE_JPOS_CAPTURE_PIN_NUMBER="CapturePinNumber";
  public final static String MSGTYPE_JPOS_SHOW_FORM_REVERSE="ShowFormReversal";
  public final static String MSGTYPE_JPOS_CARD_MATCH="CardMatch";
  public final static String MSGTYPE_JPOS_NO_CARD_MATCH="NoCardMatch";
  public final static String ABORT_TRANSACTION="AbortTransaction";
  public final static String MSGTYPE_STM_RESPONSE_NOT_APPROVED="NotApproved";
  public final static String MSGTYPE_STM_CHECK_WAS_SCANNED="CheckWasScanned";
 //End JPOS Messages

  //Begin STM Messages
  public final static String MSGTYPE_STM_SYSTEM_START = "SystemStart";
  public final static String MSGTYPE_STM_TRANSACTION_TYPE_IS_SALE = "TransactionTypeIsSale";
  public final static String MSGTYPE_STM_TRANSACTION_TYPE_IS_RETURN = "TransactionTypeIsReturn";
  public final static String MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL = "TransactionTypeIsReversal";
  public final static String MSGTYPE_STM_PAYMENT_METHOD_IS_CREDIT_CARD = "PaymentMethodIsCreditCard";
  public final static String MSGTYPE_STM_PAYMENT_METHOD_IS_CHECK = "PaymentMethodIsCheck";
  public final static String MSGTYPE_STM_PAYMENT_METHOD_IS_DEBIT_CARD = "PaymentMethodIsDebitCard";
  public final static String MSGTYPE_STM_AMOUNT_FROM_CLERK = "PaymentAmountFromClerk";
  public final static String MSGTYPE_STM_AMOUNT_DISAPPROVED_BY_CUSTOMER = "PaymentAmountDisapprovedByCustomer";
  public final static String MSGTYPE_STM_AMOUNT_APPROVED_BY_CUSTOMER = "PaymentAmountApprovedByCustomer";
  public final static String MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL_APPROVED = "ReversalApprovedByCustomer";
  public final static String MSGTYPE_STM_INVALID_CREDIT_CARD_SWIPE = "InvalidCreditCardSwipe";
  public final static String MSGTYPE_STM_INVALID_DEBIT_CARD_SWIPE = "InvalidDebitCardSwipe";
  public final static String MSGTYPE_STM_VALID_CREDIT_CARD_SWIPE = "ValidCreditCardSwipe";
  public final static String MSGTYPE_STM_VALID_DEBIT_CARD_SWIPE = "ValidDebitCardSwipe";
  public final static String MSGTYPE_STM_MANUAL_CREDIT_CARD_INFO_ENTRY = "ManualCreditCardInfoEntry";
  public final static String MSGTYPE_STM_CREDIT_CARD_SIGNATURE="CreditCardSignature";
  public final static String MSGTYPE_STM_PIN_NUMBER="PinNumber";
  public final static String MSGTYPE_STM_LOG_IN_SUCCESS="LogInSuccess";
  public final static String MSGTYPE_STM_RESPONSE_FROM_NTN="ResponseFromNTN";
  public final static String MSGTYPE_STM_CLERK_VARIFIED_REVERSAL_INFO="ClerkVerifiedReversalInfo";

  //End STM Messages

  //Begin State Constants
  private final static int STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD = 1;
  private final static int STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD = 2;
  private final static int STATE_WAITING_FOR_TRANSACTION_TYPE = 3;
  private final static int STATE_WAITING_FOR_AMOUNT_FROM_CLERK = 4;
  private final static int STATE_WAITING_FOR_PAYMENT_METHOD = 5;
  private final static int STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER = 6;
  private final static int STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE = 7;
  private final static int STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE = 8;
  private final static int STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE = 9;
  private final static int STATE_WAITING_FOR_MANUAL_CREDIT_CARD_INFO_ENTRY = 10;
  private final static int STATE_WAITING_FOR_CREDIT_CARD_SIGNATURE = 11;
  private final static int STATE_WAITING_FOR_AUTOMATED_CREDIT_CARD_APPROVAL = 12;
  private final static int STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL = 13;
  private final static int STATE_WAITING_FOR_DEBIT_CARD_SWIPE = 14;
  private final static int STATE_WAITING_FOR_REVERSAL_APPROVAL_FROM_CUSTOMER = 15;
  private final static int STATE_WAITING_FOR_PIN_NUMBER=16;
  private final static int STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE_REVERSAL=17;
  private final static int STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE_REVERSAL=18;
  private final static int STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE_REVERSAL=19;
  private final static int STATE_WAITING_FOR_CLERK_TO_VERIFY=20;
  private final static int STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL_REVERSAL=21;
  private final static int STATE_WAITING_FOR_REVERSAL_APPROVAL=22;
  private final static int STATE_WAITING_FOR_CLERK_TO_SCAN_CHECK=23;
  //End State Constants

  private int m_nState = 0;
  public static CPaySTM g_oPaySTM = null;

  private final String IP_ADDRESS_TO_SEND_JPOS_MESSAGES_TO = "localhost";

  private final int QUEUE_SIZE_FOR_RECEIVING_JPOS_MESSAGES = 6;
  private final String IP_ADDRESS_TO_SEND_AUTHORIZER_MESSAGES_TO = "localhost";
  private final int QUEUE_SIZE_FOR_RECEIVING_APPROVAL_AGENCY_MESSAGES = 6;

  private CMsgR m_oMsgR_FromJPOS = null;
  private CMsgS m_oMsgS_ToJPOS = null;
  private CNTNParser m_oNTNParser=null;
  // For simulation
  //private CMsgS m_oMsgS_ToAuthorizer=null;
  // For NTN live
  private CMsgSSecure m_MsgSSecure;
  private Vector m_oMsgVector;
  private boolean l_bLogAll = false;

  //Variables from POS
  private String POSTransactionType;
  private String POSPaymentMethod;
  private String POSPrimaryAccountNumber;
  private String POSExpirationDate;
  private String POSTrack1DiscretionaryData;
  private String POSTrack2DiscretionaryData;
  private String POSServiceCode;
  private String POSTrack1Data;
  private String POSTrack2Data;
  private String POSTitle;
  private String POSFirstName;
  private String POSMiddleInitial;
  private String POSSurName;
  private String POSSuffix;
  private String POSPaymentAmount;
  private String POSPinNumber;
  private String POSSecurityInformation;
  private String POSKeyManagementInformation;
  private Date today;
  private int month, day, hour, minute, second, SystemTraceAuditNumber;
  private String date;
  private String time;
  private String timeAndDate, STAN;
  private String ProcessingCode;
  private String PointOfServiceEntryMode;
  private String RetrievalReferenceNumber;
  private String CardAcceptorIdentificationCode;
  private String CardAcceptorTerminalIdentification;
  private String CardAcceptorNameLocation;
  private boolean isDebit;
  private boolean isReturn;


  public static void main(String argv[])
  {
    boolean bResult = false;

    GiveUp:try
    {
      //Begin method body
      boolean bInnerResult = false;

      JUtils.g_oJUtils = new JUtils();
      //JUtils.g_oJUtils.SetEchoOn("localhost", PORT_THAT_REMOTE_ECHO_LISTENS_ON);
      //JUtils.g_oJUtils.SetEchoOn("192.168.129.106", PORT_THAT_REMOTE_ECHO_LISTENS_ON);

      g_oPaySTM = new CPaySTM();
      if (null == g_oPaySTM)
      {
        break GiveUp;
      }

      bInnerResult = g_oPaySTM.Initialize();
      if (true != bInnerResult)
      {
        break GiveUp;
      }

      CPOSMessage oPOSMessage = new CPOSMessage();
      oPOSMessage.Initialize();
      long lInnerResult = GetMsg_SystemStart(oPOSMessage);

      StringBuffer sbMsg = new StringBuffer();
      lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult = g_oPaySTM.IPostMessage(sbMsg.toString());

      g_oPaySTM.ProcessMessages();

      bResult = true;
      //End method body
    }
    catch(Throwable oThrowable)
    {
      System.out.println((new Date()).toString() + ", main(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      System.out.println((new Date()).toString() + ", main(), Thread = " + Thread.currentThread() + ", Finally, bResult = " + bResult);
    }
  }


  private static long GetMsg_SystemStart(CPOSMessage oPOSMessage)
  {
    long lResult = JUtils.RESULT_FAILURE;

    if ((true == JUtils.g_bLogAll) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + ", GetMsg_SystemStart(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_STM_SYSTEM_START);

      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {break GiveUp;}
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if ((true == JUtils.g_bLogAll) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + ", GetMsg_SystemStart(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if ((true == JUtils.g_bLogAll) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + ", GetMsg_SystemStart(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
  }


  private long SendJPOSMsg_ShowFormAmount()
  {
    long lResult = JUtils.RESULT_FAILURE;

    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      CPOSMessage oPOSMessage = new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_FORM_AMOUNT);

      StringBuffer sbMsg = new StringBuffer();
      lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

      lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {break GiveUp;}
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
  }

private long SendJPOSMsg_ShowPaymentType()
  {
    long lResult = JUtils.RESULT_FAILURE;

    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      CPOSMessage oPOSMessage = new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_FORM_CHOOSE_PAYMENT_TYPE);

      StringBuffer sbMsg = new StringBuffer();
      lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

      lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {break GiveUp;}
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
  }

  private long SendJPOSMsg_ShowReturnType()
    {
      long lResult = JUtils.RESULT_FAILURE;

      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

      GiveUp:try
      {
        //Begin method body
        long lInnerResult = JUtils.RESULT_FAILURE;

        CPOSMessage oPOSMessage = new CPOSMessage();
        oPOSMessage.Initialize();
        lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_FORM_CHOOSE_RETURN_TYPE);

        StringBuffer sbMsg = new StringBuffer();
        lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

        lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
        if (JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        //End method body

        lResult = JUtils.RESULT_SUCCESS;
      }
      catch(Throwable oThrowable)
      {
        if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
        oThrowable.printStackTrace(System.out);
      }

      finally
      {
        if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

        return lResult;
      }
  }

 private void showRejectionMessage(String rMessage){};

 private long SendJPOSMsg_getCardData()
   {
     long lResult = JUtils.RESULT_FAILURE;

     if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

     GiveUp:try
     {
       //Begin method body
       long lInnerResult = JUtils.RESULT_FAILURE;

       CPOSMessage oPOSMessage = new CPOSMessage();
       oPOSMessage.Initialize();
       lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_GET_CARD_DATA);

       StringBuffer sbMsg = new StringBuffer();
       lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

       lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
       if (JUtils.RESULT_SUCCESS != lInnerResult)
       {break GiveUp;}
       //End method body

       lResult = JUtils.RESULT_SUCCESS;
     }
     catch(Throwable oThrowable)
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
       oThrowable.printStackTrace(System.out);
     }

     finally
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

       return lResult;
     }
  }


  private long SendJPOSMsg_SwipeCardAgain()
    {
      long lResult = JUtils.RESULT_FAILURE;

      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

      GiveUp:try
      {
        //Begin method body
        long lInnerResult = JUtils.RESULT_FAILURE;

        CPOSMessage oPOSMessage = new CPOSMessage();
        oPOSMessage.Initialize();
        lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_GET_CARD_DATA_AGAIN);

        StringBuffer sbMsg = new StringBuffer();
        lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

        lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
        if (JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        //End method body

        lResult = JUtils.RESULT_SUCCESS;
      }
      catch(Throwable oThrowable)
      {
        if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
        oThrowable.printStackTrace(System.out);
      }

      finally
      {
        if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

        return lResult;
      }
  }


  private long SendJPOSMsg_ShowFormChooseTransactionType()
  {
    long lResult = JUtils.RESULT_FAILURE;

    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      CPOSMessage oPOSMessage = new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_FORM_CHOOSE_TRANSACTION_TYPE);

      StringBuffer sbMsg = new StringBuffer();
      lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

      lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {break GiveUp;}
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
  }

   private long SendJPOSMsg_ShowReversalType()
    {
      long lResult = JUtils.RESULT_FAILURE;

      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

      GiveUp:try
      {
        //Begin method body
        long lInnerResult = JUtils.RESULT_FAILURE;

        CPOSMessage oPOSMessage = new CPOSMessage();
        oPOSMessage.Initialize();
        lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_FORM_REVERSE);

        StringBuffer sbMsg = new StringBuffer();
        lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

        lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
        if (JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        //End method body

        lResult = JUtils.RESULT_SUCCESS;
      }
      catch(Throwable oThrowable)
      {
        if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
        oThrowable.printStackTrace(System.out);
      }

      finally
      {
        if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

        return lResult;
      }
  }

  private long SendJPOSMsg_showThankYouAdd()
   {
     long lResult = JUtils.RESULT_FAILURE;

     if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

     GiveUp:try
     {
       //Begin method body
       long lInnerResult = JUtils.RESULT_FAILURE;

       CPOSMessage oPOSMessage = new CPOSMessage();
       oPOSMessage.Initialize();
       lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_ADD);

       StringBuffer sbMsg = new StringBuffer();
       lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

       lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
       if (JUtils.RESULT_SUCCESS != lInnerResult)
       {break GiveUp;}
       //End method body

       lResult = JUtils.RESULT_SUCCESS;
     }
     catch(Throwable oThrowable)
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
       oThrowable.printStackTrace(System.out);
     }

     finally
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

       return lResult;
     }
   }

 private long SendJPOSMsg_PrintReciept()
  {
    long lResult = JUtils.RESULT_FAILURE;

   if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      CPOSMessage oPOSMessage = new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult = oPOSMessage.SetValue(CISOMessage.m_ISO_MessageType, MSGTYPE_JPOS_PRINT_RECEIPT);
      //lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_FORM_CHOOSE_TRANSACTION_TYPE);
      StringBuffer sbMsg = new StringBuffer();
      lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

      lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
      if (JUtils.RESULT_SUCCESS != lInnerResult)
      {break GiveUp;}
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}
      return lResult;
    }
 }


private long SendJPOSMsg_clearAmount()
   {
     long lResult = JUtils.RESULT_FAILURE;

     if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

     GiveUp:try
     {
       //Begin method body
       long lInnerResult = JUtils.RESULT_FAILURE;

       CPOSMessage oPOSMessage = new CPOSMessage();
       oPOSMessage.Initialize();
       lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_STM_AMOUNT_DISAPPROVED_BY_CUSTOMER);

       StringBuffer sbMsg = new StringBuffer();
       lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

       lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
       if (JUtils.RESULT_SUCCESS != lInnerResult)
       {break GiveUp;}
       //End method body

       lResult = JUtils.RESULT_SUCCESS;
     }
     catch(Throwable oThrowable)
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
       oThrowable.printStackTrace(System.out);
     }

     finally
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

       return lResult;
     }
   }

 private long SendJPOSMsg_showSignatureCapture()
   {
     long lResult = JUtils.RESULT_FAILURE;

     if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

     GiveUp:try
     {
       //Begin method body
       long lInnerResult = JUtils.RESULT_FAILURE;

       CPOSMessage oPOSMessage = new CPOSMessage();
       oPOSMessage.Initialize();
       lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_SHOW_SIGNATURE_CAPTURE);

       StringBuffer sbMsg = new StringBuffer();
       lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

       lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
       if (JUtils.RESULT_SUCCESS != lInnerResult)
       {break GiveUp;}
       //End method body

       lResult = JUtils.RESULT_SUCCESS;
     }
     catch(Throwable oThrowable)
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
       oThrowable.printStackTrace(System.out);
     }

     finally
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

       return lResult;
     }
   }

private long SendJPOSMsg_CapturePinNumber()
{
 long lResult = JUtils.RESULT_FAILURE;
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

 GiveUp:try
 {
  //Begin method body
  long lInnerResult = JUtils.RESULT_FAILURE;
  CPOSMessage oPOSMessage = new CPOSMessage();
  oPOSMessage.Initialize();
  lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_CAPTURE_PIN_NUMBER);

  StringBuffer sbMsg = new StringBuffer();
  lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

  lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
  if (JUtils.RESULT_SUCCESS != lInnerResult)
 {break GiveUp;}
  //End method body

 lResult = JUtils.RESULT_SUCCESS;
 }
 catch(Throwable oThrowable)
 {
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
 oThrowable.printStackTrace(System.out);
 }
 finally
 {
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}
 return lResult;
 }
}

private long SendJPOSMsg_DoManualEntry()
{
     long lResult = JUtils.RESULT_FAILURE;

     if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Entry");}

     GiveUp:try
     {
       //Begin method body
       long lInnerResult = JUtils.RESULT_FAILURE;

       CPOSMessage oPOSMessage = new CPOSMessage();
       oPOSMessage.Initialize();
       lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_STM_MANUAL_CREDIT_CARD_INFO_ENTRY);

       StringBuffer sbMsg = new StringBuffer();
       lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

       lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
       if (JUtils.RESULT_SUCCESS != lInnerResult)
       {break GiveUp;}
       //End method body

       lResult = JUtils.RESULT_SUCCESS;
     }
     catch(Throwable oThrowable)
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
       oThrowable.printStackTrace(System.out);
     }

     finally
     {
       if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormChooseTransactionType(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

       return lResult;
     }
   }


public void setCardAcceptorIdentificationCode(String theCode)
{
 CardAcceptorIdentificationCode=new String(theCode);
}

private String getCardAcceptorIdentificationCode()
{
 return CardAcceptorIdentificationCode;
}

public void setCardAcceptorTerminalIdentification(String termID)
{
 CardAcceptorTerminalIdentification=new String(termID);
}

private String getCardAcceptorTerminalIdentification()
{
 return CardAcceptorTerminalIdentification;
}

private void setRetrievalReferenceNumber()
{
 StringBuffer sb=new StringBuffer();
 sb.setLength(0);
 CISOMessage.convert_integer_to_stringbuffer_and_pad_with_zeros(Integer.parseInt(STAN), 5, sb);
 RetrievalReferenceNumber=new String(date+sb.toString()+"001");
}

private String getRetrievalReferenceNumber()
{
 return RetrievalReferenceNumber;
}

public void setNameAndLocation(String store, String city, String state, String country)
{
 CardAcceptorNameLocation=new String(store+city+state+country);
}


private void doLogIn()
{
  long lInnerResult = JUtils.RESULT_FAILURE;
  Hashtable oHashtable = new Hashtable();
  oHashtable.clear();
  StringBuffer sb=new StringBuffer();
  sb.setLength(0);
  StringBuffer toNTN=new StringBuffer();
  toNTN.setLength(0);
  setDate();
  setTime();
  setTimeAndDate();
  setCardAcceptorIdentificationCode("000000000076001");
  lInnerResult=FillInISO800Fields(oHashtable);
  lInnerResult=CISOMessage.rekey_htable_from_long_name_to_short_name(oHashtable);
  lInnerResult=CISOMessage.convert_HashTable_to_raw_bit_map(oHashtable, sb);
  lInnerResult=CISOMessage.convert_raw_bit_map_to_internet_formatted_bit_map(sb.toString(),toNTN);
  System.out.println(toNTN.toString());
  //lInnerResult=m_oMsgS_ToAuthorizer.IPostMessage(toNTN.toString());
  lInnerResult=m_MsgSSecure.IPostMessage("GET /test/cgi/paymate2mainsail.cgi?"+toNTN.toString());

}

private void do200()
{
 long lInnerResult = JUtils.RESULT_FAILURE;
 Hashtable oHashtable = new Hashtable();
 oHashtable.clear();
 StringBuffer sb=new StringBuffer();
 sb.setLength(0);
 StringBuffer toNTN=new StringBuffer();
 toNTN.setLength(0);
 setCardAcceptorTerminalIdentification("TERM0001");
 setNameAndLocation("Eureka                 ","Houston      ","TX","US");
 setRetrievalReferenceNumber();
 lInnerResult=FillInISO200Fields(oHashtable);
 lInnerResult=CISOMessage.rekey_htable_from_long_name_to_short_name(oHashtable);
 lInnerResult=CISOMessage.convert_HashTable_to_raw_bit_map(oHashtable, sb);
 lInnerResult=CISOMessage.convert_raw_bit_map_to_internet_formatted_bit_map(sb.toString(),toNTN);
 System.out.println(toNTN.toString());
 //lInnerResult=m_oMsgS_ToAuthorizer.IPostMessage(toNTN.toString());
 lInnerResult=m_MsgSSecure.IPostMessage("GET /test/cgi/paymate2mainsail.cgi?"+toNTN.toString());
}

private void do400()
{
 long lInnerResult = JUtils.RESULT_FAILURE;
 Hashtable oHashtable = new Hashtable();
 oHashtable.clear();
 StringBuffer sb=new StringBuffer();
 sb.setLength(0);
 StringBuffer toNTN=new StringBuffer();
 toNTN.setLength(0);
 setCardAcceptorTerminalIdentification("TERM0001");
 setNameAndLocation("Eureka                 ","Houston      ","TX","US");
 setRetrievalReferenceNumber();
 lInnerResult=FillInISO400Fields(oHashtable);
 lInnerResult=CISOMessage.rekey_htable_from_long_name_to_short_name(oHashtable);
 lInnerResult=CISOMessage.convert_HashTable_to_raw_bit_map(oHashtable, sb);
 lInnerResult=CISOMessage.convert_raw_bit_map_to_internet_formatted_bit_map(sb.toString(),toNTN);
 System.out.println(toNTN.toString());
 //lInnerResult=m_oMsgS_ToAuthorizer.IPostMessage(toNTN.toString());
 lInnerResult=m_MsgSSecure.IPostMessage("GET /test/cgi/paymate2mainsail.cgi?"+toNTN.toString());
}

private void do100()
{
 long lInnerResult = JUtils.RESULT_FAILURE;
 Hashtable oHashtable = new Hashtable();
 oHashtable.clear();
 StringBuffer sb=new StringBuffer();
 sb.setLength(0);
 StringBuffer toNTN=new StringBuffer();
 toNTN.setLength(0);
 setCardAcceptorTerminalIdentification("TERM0001");
 setNameAndLocation("Eureka                 ","Houston      ","TX","US");
 setRetrievalReferenceNumber();
 lInnerResult=FillInISO100Fields(oHashtable);
 lInnerResult=CISOMessage.rekey_htable_from_long_name_to_short_name(oHashtable);
 lInnerResult=CISOMessage.convert_HashTable_to_raw_bit_map(oHashtable, sb);
 lInnerResult=CISOMessage.convert_raw_bit_map_to_internet_formatted_bit_map(sb.toString(),toNTN);
 System.out.println(toNTN.toString());
 //lInnerResult=m_oMsgS_ToAuthorizer.IPostMessage(toNTN.toString());
 lInnerResult=m_MsgSSecure.IPostMessage("GET /test/cgi/paymate2mainsail.cgi?"+toNTN.toString());
}

public void getNameAndLocation(){}
public void setEmployeeNumber(){}
public void getEmployeeNumber(){}

private long FillInISO200Fields(Hashtable oHashtable)
{
 long lResult = JUtils.RESULT_FAILURE;
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}
 GiveUp:try
    {

      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;
      oHashtable.put(CISOMessage.m_ISO_TransactionType, "0200");
      oHashtable.put(CISOMessage.m_ISO_PrimaryAccountNumber, POSPrimaryAccountNumber);
      oHashtable.put(CISOMessage.m_ISO_ExpirationDate, POSExpirationDate);
      oHashtable.put(CISOMessage.m_ISO_ProcessingCode, ProcessingCode );
      oHashtable.put(CISOMessage.m_ISO_TransactionAmount,POSPaymentAmount);
      oHashtable.put(CISOMessage.m_ISO_TransmissionDateTime, timeAndDate);
      oHashtable.put(CISOMessage.m_ISO_SystemTraceAuditNumber, STAN);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionTime, time);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionDate, date);
      oHashtable.put(CISOMessage.m_ISO_PointOfServiceEntryMode, PointOfServiceEntryMode );
      oHashtable.put(CISOMessage.m_ISO_PointOfServiceConditionCode, "00");
      oHashtable.put(CISOMessage.m_ISO_RetrievalReferenceNumber, getRetrievalReferenceNumber());
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorTerminalIdentification, getCardAcceptorTerminalIdentification());
      //oHashtable.put(CISOMessage.m_ISO_Track2Data, POSTrack2Data );
      if(isDebit)
      {
       oHashtable.put(CISOMessage.m_ISO_PersonalIdentificationNumberData, POSPinNumber);
       oHashtable.put(CISOMessage.m_ISO_SecurityRelatedControlInformation, "0000010000000000");
       oHashtable.put(CISOMessage.m_ISO_Track2Data, POSTrack2Data );
      }
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorIdentificationCode, getCardAcceptorIdentificationCode());
      oHashtable.put(CISOMessage.m_ISO_Track1Data, POSTrack1Data);
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorNameLocation, CardAcceptorNameLocation);
      oHashtable.put(CISOMessage.m_ISO_CashbackAmount, "000000000000");
      oHashtable.put(CISOMessage.m_ISO_EmployeeNumber, "123456789MANSFDG");
      oHashtable.put(CISOMessage.m_ISO_ControllerType, "001");
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
}

private long FillInISO400Fields(Hashtable oHashtable)
{
 long lResult = JUtils.RESULT_FAILURE;
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}
 GiveUp:try
    {

      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;
      oHashtable.put(CISOMessage.m_ISO_TransactionType, "0400");
      oHashtable.put(CISOMessage.m_ISO_PrimaryAccountNumber, POSPrimaryAccountNumber);
      oHashtable.put(CISOMessage.m_ISO_ExpirationDate, POSExpirationDate);
      oHashtable.put(CISOMessage.m_ISO_ProcessingCode, ProcessingCode );
      oHashtable.put(CISOMessage.m_ISO_TransactionAmount, POSPaymentAmount);
      oHashtable.put(CISOMessage.m_ISO_TransmissionDateTime, timeAndDate);
      oHashtable.put(CISOMessage.m_ISO_SystemTraceAuditNumber, STAN);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionTime, time);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionDate, date);
      oHashtable.put(CISOMessage.m_ISO_PointOfServiceEntryMode, PointOfServiceEntryMode );
      oHashtable.put(CISOMessage.m_ISO_PointOfServiceConditionCode, "00");
      oHashtable.put(CISOMessage.m_ISO_RetrievalReferenceNumber, getRetrievalReferenceNumber());
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorTerminalIdentification, getCardAcceptorTerminalIdentification());
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorIdentificationCode, getCardAcceptorIdentificationCode());
      oHashtable.put(CISOMessage.m_ISO_Track1Data, POSTrack1Data);
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorNameLocation, CardAcceptorNameLocation);
      oHashtable.put(CISOMessage.m_ISO_CashbackAmount, "000000000000");
      oHashtable.put(CISOMessage.m_ISO_EmployeeNumber, "123456789MANSFDG");
      oHashtable.put(CISOMessage.m_ISO_ControllerType, "001");
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
}

private long FillInISO100Fields(Hashtable oHashtable)
{
 long lResult = JUtils.RESULT_FAILURE;
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}
 GiveUp:try
    {

      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;
      oHashtable.put(CISOMessage.m_ISO_TransactionType, "0100");
      oHashtable.put(CISOMessage.m_ISO_PrimaryAccountNumber, POSPrimaryAccountNumber);
      //oHashtable.put(CISOMessage.m_ISO_ExpirationDate, POSExpirationDate);
      oHashtable.put(CISOMessage.m_ISO_ProcessingCode, ProcessingCode );
      oHashtable.put(CISOMessage.m_ISO_TransactionAmount, POSPaymentAmount);
      oHashtable.put(CISOMessage.m_ISO_TransmissionDateTime, timeAndDate);
      oHashtable.put(CISOMessage.m_ISO_SystemTraceAuditNumber, STAN);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionTime, time);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionDate, date);
      oHashtable.put(CISOMessage.m_ISO_PointOfServiceEntryMode, PointOfServiceEntryMode );
      oHashtable.put(CISOMessage.m_ISO_PointOfServiceConditionCode, "00");
      oHashtable.put(CISOMessage.m_ISO_RetrievalReferenceNumber, getRetrievalReferenceNumber());
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorTerminalIdentification, getCardAcceptorTerminalIdentification());
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorIdentificationCode, getCardAcceptorIdentificationCode());
      //oHashtable.put(CISOMessage.m_ISO_Track1Data, POSTrack1Data);
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorNameLocation, CardAcceptorNameLocation);
      oHashtable.put(CISOMessage.m_ISO_CashbackAmount, "000000000000");
      oHashtable.put(CISOMessage.m_ISO_EmployeeNumber, "123456789MANSFDG");
      oHashtable.put(CISOMessage.m_ISO_ControllerType, "001");
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
}

private void setTime()
{
  String shour, sminute, ssecond;
  Date today=new Date();
  hour=(today.getHours());
  if(hour<=9)
  {
  shour=new String("0"+hour);
  }
  else
  {
  shour=new String(""+hour);
  }
  minute=today.getMinutes();
  if(minute<=9)
  {
  sminute=new String("0"+minute);
  }
  else
  {
  sminute=new String(""+minute);
  }
  second=today.getSeconds();
  if(second<=9)
  {
  ssecond=new String("0"+second);
  }
  else
  {
  ssecond=new String(""+second);
  }
  time=new String(shour+sminute+ssecond);
 }

private void setDate()
{
  String smonth, sday;
  Date today=new Date();
  month=(today.getMonth()+1);
  if(month<=9)
  {
   smonth=new String("0"+month);
  }
  else
  {
   smonth=new String(""+month);
  }
  day=today.getDate();
  if(day<=9)
  {
   sday=new String("0"+day);
  }
  else
  {
   sday=new String(""+day);
  }
  date=new String(smonth+sday);
 }

private void setTimeAndDate()
{
 timeAndDate=new String(date+time);
}

private String setSTAN()
{
 SystemTraceAuditNumber++;
 StringBuffer sb=new StringBuffer();
 sb.setLength(0);
 CISOMessage.convert_integer_to_stringbuffer_and_pad_with_zeros(SystemTraceAuditNumber, 6, sb);
 STAN=new String(sb.toString());
 return STAN;
}

private long FillInISO800Fields(Hashtable oHashtable)
{
 long lResult = JUtils.RESULT_FAILURE;
 if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}
 GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;
      oHashtable.put(CISOMessage.m_ISO_TransactionType, "0800");
      oHashtable.put(CISOMessage.m_ISO_TransmissionDateTime, timeAndDate);
      oHashtable.put(CISOMessage.m_ISO_SystemTraceAuditNumber, setSTAN());
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionTime, time);
      oHashtable.put(CISOMessage.m_ISO_LocalTransactionDate, date);
      oHashtable.put(CISOMessage.m_ISO_CardAcceptorIdentificationCode, getCardAcceptorIdentificationCode());
      oHashtable.put(CISOMessage.m_ISO_NetworkManagementInformationCode, "001");
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
}

private long getCardDataForMatch(CPOSMessage oPOSMessage)
{
  long lResult = JUtils.RESULT_FAILURE;

  if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

  GiveUp:try
  {
   //Begin method body
   long lInnerResult = JUtils.RESULT_FAILURE;
   String temp;
   StringBuffer sb=new StringBuffer();
   oPOSMessage.GetValue(sb, PrimaryAccountNumber);
   temp=new String(sb.toString());
   if(temp.equals(POSPrimaryAccountNumber))
   {
	 lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_CARD_MATCH);

	 StringBuffer sbMsg = new StringBuffer();
	 lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

     lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
   }
   else
   {
    lInnerResult = oPOSMessage.SetValue(MessageType, MSGTYPE_JPOS_NO_CARD_MATCH );

    StringBuffer sbMsg = new StringBuffer();
    lInnerResult = oPOSMessage.GetMessageTransmissionString(sbMsg);

    lInnerResult = m_oMsgS_ToJPOS.IPostMessage(sbMsg.toString());
   }

   if (JUtils.RESULT_SUCCESS != lInnerResult)
   {break GiveUp;}
    //End method body

    lResult = JUtils.RESULT_SUCCESS;
  }
  catch(Throwable oThrowable)
  {
   if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
   oThrowable.printStackTrace(System.out);
  }

 finally
 {
  if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

  return lResult;
 }
}

private long StoreCardInfo(CPOSMessage oPOSMessage)
  {
    long lResult = JUtils.RESULT_FAILURE;

    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

     StringBuffer sb=new StringBuffer();
	 oPOSMessage.GetValue(sb, PrimaryAccountNumber);
	 POSPrimaryAccountNumber=new String(sb.toString());
     sb.setLength(0);
     oPOSMessage.GetValue(sb,ExpirationDate);
     POSExpirationDate=new String(sb.toString());
     sb.setLength(0);
     oPOSMessage.GetValue(sb, TransactionType);
     POSTransactionType=new String(sb.toString());
     sb.setLength(0);
     oPOSMessage.GetValue(sb, PaymentMethod);
	 POSPaymentMethod=new String(sb.toString());
	 sb.setLength(0);
     oPOSMessage.GetValue(sb,Track1Data);
     POSTrack1Data=new String(sb.toString());
     sb.setLength(0);
     if(ProcessingCode.equals("000000"))
     {
	 oPOSMessage.GetValue(sb,Track2Data);
	 POSTrack2Data=new String(sb.toString());
	 sb.setLength(0);
     }
     oPOSMessage.GetValue(sb,ServiceCode);
	 POSServiceCode=new String(sb.toString());
	 sb.setLength(0);
	 oPOSMessage.GetValue(sb,Title);
	 POSTitle=new String(sb.toString());
	 sb.setLength(0);
	 oPOSMessage.GetValue(sb,FirstName);
	 POSFirstName=new String(sb.toString());
	 sb.setLength(0);
	 oPOSMessage.GetValue(sb,MiddleInitial);
	 POSMiddleInitial=new String(sb.toString());
	 sb.setLength(0);
	 oPOSMessage.GetValue(sb,SurName);
	 POSSurName=new String(sb.toString());
	 sb.setLength(0);
	 oPOSMessage.GetValue(sb,Suffix);
	 POSSuffix=new String(sb.toString());
	 sb.setLength(0);
     oPOSMessage.GetValue(sb,PaymentAmount);
     POSPaymentAmount=new String(sb.toString());
     sb.setLength(0);
     if (JUtils.RESULT_SUCCESS != lInnerResult)
      {break GiveUp;}
      //End method body

      lResult = JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", ERROR = " + oThrowable.toString());}
      oThrowable.printStackTrace(System.out);
    }

    finally
    {
      if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", SendJPOSMsg_ShowFormAmount(), Thread = " + Thread.currentThread() + ", Finally, lResult = " + lResult);}

      return lResult;
    }
  }


  public synchronized long IPostMessage(String sMsg)
  {
    //Note: Called on external thread.

    long lResult = JUtils.RESULT_FAILURE;

    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", IPostMessage(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      m_oMsgVector.addElement(sMsg);
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


  private void ProcessMessages()
  {
    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;

      while (true)
      {
        if (false == m_oMsgVector.isEmpty())
        {
          Object oMessage = m_oMsgVector.firstElement();

          if (true == oMessage instanceof String)
          {
            String sMessage = (String)oMessage;

            CPOSMessage oPOSMessage = new CPOSMessage();
            boolean bInnerResult = oPOSMessage.Initialize(sMessage);

            if (true == bInnerResult)
            {
              StringBuffer sbMsgType = new StringBuffer();

              lInnerResult = oPOSMessage.GetValue(sbMsgType, CISOMessage.m_ISO_MessageType );
              if (JUtils.RESULT_SUCCESS == lInnerResult)
              {
                String sMsgType = sbMsgType.toString();


                if (true == sMsgType.equals(MSGTYPE_STM_SYSTEM_START))
                {
                  m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD;

                  lInnerResult = SendJPOSMsg_ShowFormChooseTransactionType();
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_TRANSACTION_TYPE_IS_SALE))
                {
                  boolean bTransactionTypeIsSaleHandled = false;

                  if (STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD;

                    bTransactionTypeIsSaleHandled = true;
                  }
                  else if (m_nState == STATE_WAITING_FOR_TRANSACTION_TYPE)
                  {
                    m_nState = STATE_WAITING_FOR_AMOUNT_FROM_CLERK;
                  }

                  if (bTransactionTypeIsSaleHandled)
                  {
                    POSTransactionType = new String(CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_SALE);
                    lInnerResult = SendJPOSMsg_ShowPaymentType();
                  }
                }

               else if (true == sMsgType.equals(MSGTYPE_STM_TRANSACTION_TYPE_IS_RETURN))
			   {
				 boolean bTransactionTypeIsSaleHandled = false;

			  	 if (STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD == m_nState)
			 	 {
				  m_nState = STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD;
				  bTransactionTypeIsSaleHandled = true;
				 }

				 else if (m_nState == STATE_WAITING_FOR_TRANSACTION_TYPE)
				 {
				 m_nState = STATE_WAITING_FOR_AMOUNT_FROM_CLERK;
				 }

				 if (bTransactionTypeIsSaleHandled)
				 {
				  POSTransactionType = new String(CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_RETURN);
				  lInnerResult = SendJPOSMsg_ShowReturnType();
				  isReturn=true;
				 }
                }

                else if (true == sMsgType.equals(MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL))
				{
				  if (STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD == m_nState)
				  {
				  m_nState = STATE_WAITING_FOR_REVERSAL_APPROVAL_FROM_CUSTOMER;
				  POSTransactionType = new String(CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL);
				  ProcessingCode=new String("003000");
				  lInnerResult = SendJPOSMsg_ShowReversalType();
				 }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL_APPROVED))
				{
				 if(STATE_WAITING_FOR_REVERSAL_APPROVAL_FROM_CUSTOMER == m_nState)
				 {
				  m_nState = STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE_REVERSAL;
				  lInnerResult = SendJPOSMsg_getCardData();
				 }
                }

              else if (true == sMsgType.equals(ABORT_TRANSACTION))
			  {
			   POSTransactionType = new String(CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_SALE);
               lInnerResult = SendJPOSMsg_ShowPaymentType();
			  }

              else if (true == sMsgType.equals(MSGTYPE_STM_PAYMENT_METHOD_IS_CREDIT_CARD))
                {
                  boolean bPaymentMethodIsCreditCardHandled = false;

                  if (STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE;
                  }
                  else if (STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD == m_nState)
                  {
                    //m_nState = STATE_WAITING_FOR_AMOUNT_FROM_CLERK;
                    bPaymentMethodIsCreditCardHandled = true;
                  }
                  else if (STATE_WAITING_FOR_PAYMENT_METHOD == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER;
                  }

                  if (bPaymentMethodIsCreditCardHandled)
                  {
                   m_nState=STATE_WAITING_FOR_AMOUNT_FROM_CLERK;
                   POSPaymentMethod = new String(CPaySTM.MSGTYPE_STM_PAYMENT_METHOD_IS_CREDIT_CARD);
                   if(isReturn)
                   {
                   ProcessingCode=new String("200030");
			       }
			       else
			       {
                   ProcessingCode=new String("003000");
			       }
                  }
                }


                 else if (true == sMsgType.equals(MSGTYPE_STM_PAYMENT_METHOD_IS_DEBIT_CARD))
				 {
				   boolean bPaymentMethodIsDebitCardHandled = false;
				   if (STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD == m_nState)
				   {
				    m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE;
				   }
				   else if (STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD == m_nState)
				   {
				    //m_nState = STATE_WAITING_FOR_AMOUNT_FROM_CLERK;
				    bPaymentMethodIsDebitCardHandled = true;
				   }
				   else if (STATE_WAITING_FOR_PAYMENT_METHOD == m_nState)
				   {
				    m_nState = STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER;
				   }
				   if (bPaymentMethodIsDebitCardHandled)
				  {
				  m_nState=STATE_WAITING_FOR_AMOUNT_FROM_CLERK;
				  ProcessingCode=new String("000000");
				  POSPaymentMethod = new String(CPaySTM.MSGTYPE_STM_PAYMENT_METHOD_IS_DEBIT_CARD);
				  }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_PAYMENT_METHOD_IS_CHECK))
				{
				  boolean bPaymentMethodIsCheckHandled = false;
				   if (STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD == m_nState)
				   {
				    m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE;
				   }
				   else if (STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD == m_nState)
				   {
				     bPaymentMethodIsCheckHandled = true;
				   }
				   else if (STATE_WAITING_FOR_PAYMENT_METHOD == m_nState)
				  {
				   m_nState = STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER;
				   }
				  if (bPaymentMethodIsCheckHandled)
				  {
					///
				   ProcessingCode=new String("040000");
				   m_nState=STATE_WAITING_FOR_CLERK_TO_SCAN_CHECK;
                   POSPaymentMethod = new String(CPaySTM.MSGTYPE_STM_PAYMENT_METHOD_IS_CHECK);
				   }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_AMOUNT_FROM_CLERK))
                {
                  boolean bAmountFromClerkHandled = false;

                  if (STATE_WAITING_FOR_AMOUNT_FROM_CLERK_OR_PAYMENT_METHOD == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_PAYMENT_METHOD;
                  }
                  else if (STATE_WAITING_FOR_AMOUNT_FROM_CLERK == m_nState)
                  {
                    bAmountFromClerkHandled = true;
                    m_nState = STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER;
                  }

                  if (bAmountFromClerkHandled)
                  {
                   lInnerResult = SendJPOSMsg_ShowFormAmount();
                  }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_AMOUNT_DISAPPROVED_BY_CUSTOMER))
                {
                  if (STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER;
                    lInnerResult = SendJPOSMsg_clearAmount();
                  }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_AMOUNT_APPROVED_BY_CUSTOMER))
                {
                  if (STATE_WAITING_FOR_AMOUNT_APPROVAL_FROM_CUSTOMER == m_nState)
                  {
                    if( ProcessingCode.equals("003000"))
                    {
                    isDebit=false;
                    m_nState = STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE;
				    }
                    else if( ProcessingCode.equals("200030"))
					{
					System.out.println("Return");
					isDebit=false;
					m_nState = STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE;
				    }
                    else if( ProcessingCode.equals("000000"))
                    {
					isDebit=true;
					m_nState = STATE_WAITING_FOR_DEBIT_CARD_SWIPE;
					}
                    lInnerResult = SendJPOSMsg_getCardData();
                  }
                }



                else if (true == sMsgType.equals(MSGTYPE_STM_INVALID_CREDIT_CARD_SWIPE))
                {
                  if (STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE;
                    SendJPOSMsg_SwipeCardAgain();
                  }
                  else if (STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE;
                    SendJPOSMsg_SwipeCardAgain();
                  }
                  else if (STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE_REVERSAL == m_nState)
				  {
				   m_nState = STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE_REVERSAL;
				   SendJPOSMsg_SwipeCardAgain();
                  }
                  else if (STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE_REVERSAL == m_nState)
				  {
				   m_nState = STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE_REVERSAL;
				   SendJPOSMsg_SwipeCardAgain();
                  }
                  else if (STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE == m_nState)
                  {
                   m_nState = STATE_WAITING_FOR_MANUAL_CREDIT_CARD_INFO_ENTRY;
                   SendJPOSMsg_DoManualEntry();
                  }
                  else if (STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE_REVERSAL == m_nState)
				  {
				   m_nState = STATE_WAITING_FOR_MANUAL_CREDIT_CARD_INFO_ENTRY;
				   SendJPOSMsg_DoManualEntry();
                  }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_INVALID_DEBIT_CARD_SWIPE))
				{
				 if (STATE_WAITING_FOR_DEBIT_CARD_SWIPE == m_nState)
				 {
				  SendJPOSMsg_SwipeCardAgain();
				 }
			    }


             else if (true == sMsgType.equals(MSGTYPE_STM_VALID_DEBIT_CARD_SWIPE))
		   	  {
			   if (STATE_WAITING_FOR_DEBIT_CARD_SWIPE == m_nState)
		   	   {
		   	    System.out.println("Debit swipe valid");
		   	    PointOfServiceEntryMode=new String("021");
		   	    m_nState = STATE_WAITING_FOR_PIN_NUMBER;
		   	    StoreCardInfo(oPOSMessage);
		   	    SendJPOSMsg_CapturePinNumber();
		   	   }
			 }



              else if (true == sMsgType.equals(MSGTYPE_STM_VALID_CREDIT_CARD_SWIPE))
                {
                  boolean bValidCreditCardInfoHandled = false;
                  boolean bValidReversalInfoHandled = false;

                  if (STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_CREDIT_CARD_SIGNATURE;
                    bValidCreditCardInfoHandled = true;
                  }
                  else if (STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_CREDIT_CARD_SIGNATURE;
                    bValidCreditCardInfoHandled = true;
                  }
                  else if(STATE_WAITING_FOR_FIRST_CREDIT_CARD_SWIPE_REVERSAL == m_nState)
                  {
				    m_nState = STATE_WAITING_FOR_CLERK_TO_VERIFY;
				    bValidReversalInfoHandled = true;
				  }
                  else if(STATE_WAITING_FOR_SECOND_CREDIT_CARD_SWIPE_REVERSAL == m_nState)
				  {
				   	m_nState = STATE_WAITING_FOR_CLERK_TO_VERIFY;
				   	bValidReversalInfoHandled = true;
				  }
                  else if(STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE_REVERSAL == m_nState)
				  {
				  	m_nState = STATE_WAITING_FOR_CLERK_TO_VERIFY;
				  	bValidReversalInfoHandled = true;
				  }
                  else if (STATE_WAITING_FOR_THIRD_CREDIT_CARD_SWIPE == m_nState)
                  {
                    m_nState = STATE_WAITING_FOR_CREDIT_CARD_SIGNATURE;
                    bValidCreditCardInfoHandled = true;
                  }

                  if (bValidCreditCardInfoHandled)
                  {
					PointOfServiceEntryMode=new String("022");
					StoreCardInfo(oPOSMessage);
                    if(!isReturn)
                    {
					lInnerResult=SendJPOSMsg_showSignatureCapture();
					}
                    else if(isReturn)
                    {
					lInnerResult=SendJPOSMsg_PrintReciept();
					}

                  }
                  if (bValidReversalInfoHandled)
                  {
				   PointOfServiceEntryMode=new String("022");
				   lInnerResult=getCardDataForMatch(oPOSMessage);
				  }
                }


                else if (true == sMsgType.equals(MSGTYPE_STM_MANUAL_CREDIT_CARD_INFO_ENTRY))
                {
                  if (STATE_WAITING_FOR_MANUAL_CREDIT_CARD_INFO_ENTRY == m_nState)
                  {
                    PointOfServiceEntryMode=new String("012");
                    StoreCardInfo(oPOSMessage);
                    m_nState = STATE_WAITING_FOR_AUTOMATED_CREDIT_CARD_APPROVAL;
                  }
                }

               else if (true == sMsgType.equals(MSGTYPE_STM_CHECK_WAS_SCANNED))
               {
				 if (STATE_WAITING_FOR_CLERK_TO_SCAN_CHECK == m_nState)
				 {
					//m_nState=STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL;
					System.out.println("scan check");
					//doLogIn();
				 }
			   }

                else if (true == sMsgType.equals(MSGTYPE_STM_CLERK_VARIFIED_REVERSAL_INFO))
			    {
			     if (STATE_WAITING_FOR_CLERK_TO_VERIFY == m_nState)
			     {
			      m_nState=STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL_REVERSAL;
			      doLogIn();
			      SendJPOSMsg_showThankYouAdd();
			     }
                }

               else if (true == sMsgType.equals(MSGTYPE_STM_PIN_NUMBER))
               {
			     if (STATE_WAITING_FOR_PIN_NUMBER==m_nState)
			     {
				  m_nState=STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL;
				  StringBuffer sb=new StringBuffer();
				  oPOSMessage.GetValue(sb, PinNumber);
				  POSPinNumber=new String(sb.toString());
				  System.out.println(POSPinNumber);
				  doLogIn();
			      SendJPOSMsg_showThankYouAdd();
			  	}
			   }


               else if (true == sMsgType.equals(MSGTYPE_STM_CREDIT_CARD_SIGNATURE))
			   {
			     if (STATE_WAITING_FOR_CREDIT_CARD_SIGNATURE == m_nState)
			     {
			      m_nState=STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL;
			      doLogIn();
			      SendJPOSMsg_showThankYouAdd();
			     }
               }


               else if (true == sMsgType.equals(MSGTYPE_STM_LOG_IN_SUCCESS))
               {
                 if (STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL == m_nState)
                 {
                  m_nState =STATE_WAITING_FOR_AUTOMATED_CREDIT_CARD_APPROVAL;
                  do200();
                 }
                 if (STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL_REVERSAL == m_nState)
				 {
				  m_nState =STATE_WAITING_FOR_REVERSAL_APPROVAL;
				  do400();
				 }
                 //if (STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL_CHECK == m_nState)
				 //{
				  //m_nState =STATE_WAITING_FOR_AUTOMATED_CHECK_APPROVAL;
				 // do100();
                 //}

               }


                else if (true == sMsgType.equals(MSGTYPE_STM_RESPONSE_FROM_NTN))
				{
				 if (STATE_WAITING_FOR_AUTOMATED_CREDIT_CARD_APPROVAL == m_nState)
				 {
				  m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD;
				  SendJPOSMsg_PrintReciept();
				 }
				 if (STATE_WAITING_FOR_REVERSAL_APPROVAL == m_nState)
				 {
				  m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD;
				  SendJPOSMsg_PrintReciept();
				 }
                }

                else if (true == sMsgType.equals(MSGTYPE_STM_RESPONSE_NOT_APPROVED))
                {
				 StringBuffer sb=new StringBuffer();
				 if (STATE_WAITING_FOR_AUTOMATED_CREDIT_CARD_APPROVAL == m_nState)
				 {
				  m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD;
				  oPOSMessage.GetValue(sb, RejectionType);
	              showRejectionMessage(sb.toString());
	              sb.setLength(0);
	             }
				 else if(STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL == m_nState)
				 {
				  oPOSMessage.GetValue(sb, RejectionType);
				  showRejectionMessage(sb.toString());
				  sb.setLength(0);
				 }
				 else if(STATE_WAITING_FOR_REVERSAL_APPROVAL == m_nState)
				 {
				  m_nState = STATE_WAITING_FOR_TRANSACTION_TYPE_OR_PAYMENT_METHOD;
				  oPOSMessage.GetValue(sb, RejectionType);
				  showRejectionMessage(sb.toString());
				  sb.setLength(0);
				 }
				 else if(STATE_WAITING_FOR_AUTOMATED_LOG_IN_APPROVAL_REVERSAL == m_nState)
				 {
				  oPOSMessage.GetValue(sb, RejectionType);
				  showRejectionMessage(sb.toString());
				  sb.setLength(0);
				 }
				}

   //////////////////////////////////
             }
           }

          }
          else
          {
            if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", ProcessMessages(), Thread = " + Thread.currentThread() + ", Invalid message received and discarded.");}
          }

          m_oMsgVector.removeElementAt(0);

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


  private boolean Initialize()
  {
    boolean bResult = false;

    if (((true == JUtils.g_bLogAll) || (true == l_bLogAll)) && (null != JUtils.g_oJUtils)) {JUtils.g_oJUtils.AddLogEntry((new Date()).toString() + ", " + this.getClass().getName() + ", Initialize(), Thread = " + Thread.currentThread() + ", Entry");}

    GiveUp:try
    {
      //Begin method body
      m_oMsgVector = new Vector();

      //Begin creating m_oMsgR_FromJPOS
      m_oMsgR_FromJPOS = new CMsgR();
      m_oMsgR_FromJPOS.Initialize(PORT_THAT_STM_LISTENS_ON, QUEUE_SIZE_FOR_RECEIVING_JPOS_MESSAGES, this);
      new Thread(m_oMsgR_FromJPOS).start();
      //End creating m_oMsgR_FromJPOS

      //Begin creating m_oMsgS_ToJPOS
      m_oMsgS_ToJPOS = new CMsgS();
      m_oMsgS_ToJPOS.Initialize(this, IP_ADDRESS_TO_SEND_JPOS_MESSAGES_TO, PORT_THAT_JPOS_LISTENS_ON);
      new Thread(m_oMsgS_ToJPOS).start();
      //End creating m_oMsgS_ToJPOS

      m_oNTNParser=new CNTNParser();
      m_oNTNParser.Initialize(this);

      // This is the live transfer
      m_MsgSSecure = new CMsgSSecure();
	  m_MsgSSecure.Initialize(m_oNTNParser, "208.58.21.60", 8443);
      new Thread(m_MsgSSecure).start();



      // This is the NTN tester

      //m_oMsgS_ToAuthorizer=new CMsgS();
      //m_oMsgS_ToAuthorizer.Initialize(m_oNTNParser, IP_ADDRESS_TO_SEND_AUTHORIZER_MESSAGES_TO, PORT_THAT_AUTHORIZER_LISTENS_ON);
      //new Thread(m_oMsgS_ToAuthorizer).start();
      //End creating NTN tester


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

} // class CPaySTM
