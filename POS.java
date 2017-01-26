/* $Id$ */
//package PT;

import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import JUtils.*;
import Msg.*;
import PaySTM.*;

import jpos.*;
import jpos.events.*;
import ivicm.svc.Commands5992;
import ivicm.ec3K.*;


public class POS extends Frame implements  ActionListener,
DataListener,
ErrorListener,
CIMsgT


{
  public static POS g_oPT = null;

  private final int QUEUE_SIZE_FOR_RECEIVING_STM_MESSAGES = 6;

  private String m_sIPAddressToSendSTMMessagesTo = null;
  private int m_nPortAddressToSendSTMMessagesTo = -1;
  private int m_nPortAddressToReceiveSTMMessagesFrom = -1;

  private CMsgR m_oMsgR_FromSTM = null;
  private CMsgS m_oMsgS_ToSTM = null;
  private Vector m_oMsgVector;

  //private boolean l_bLogAll = false;
  //#instance ErrorLogStream.Member
  protected static ErrorLogStream dbg=new ErrorLogStream("POS", false);
  //#end ErrorLogStream.Member


  private jpos.PINPad pPad;
  private jpos.MSR msr;
  private jpos.Form form;
  private jpos.SignatureCapture sCap;
  private jpos.MICR soMICR;
  private jpos.POSKeyboard soKbd;
  private jpos.LineDisplay soDisp;
  private String formName, amount, showAmt;
  private int amt;
  //MSR Data
  private String pin;
  private String accountNumber;
  private String expirationDate;
  private String Surname;
  private String trackOneDiscretionaryData;
  private String trackTwoDiscretionaryData;
  private String serviceCode;
  private String paymentType;
  private String transactionType;
  private String security;
  private boolean flag, isCheck;



  // Clerk GUI components
  java.awt.Button button1;
  java.awt.Button button2;
  java.awt.Button button3;
  java.awt.Button button4;
  java.awt.Button button5;
  java.awt.Button button6;
  java.awt.Button button7;
  java.awt.Button button8;
  java.awt.Button button9;
  java.awt.Button button10;
  java.awt.Button button11;
  java.awt.TextField textField1;
  java.awt.Button button12;
  java.awt.Button button13;
  java.awt.Button button14;
  java.awt.Label label1;
  java.awt.Button button15;
  java.awt.Button button16;

  public static void launch(String str)
  {
    boolean bResult = false;

    GiveUp:try
    {
      //Begin method body
      boolean bInnerResult = false;

      JUtils.g_oJUtils = new JUtils();
      g_oPT = new POS();

      if (null == g_oPT)
      {
        break GiveUp;
      }

      // Clerk GUI

      bInnerResult = g_oPT.Initialize("localhost", CPaySTM.PORT_THAT_STM_LISTENS_ON, CPaySTM.PORT_THAT_JPOS_LISTENS_ON);
      g_oPT.FormSetUp();
      g_oPT.MSRSetUp();
      g_oPT.SigCapSetUp();
      g_oPT.StartClerk();
      if (true != bInnerResult)
      {
        break GiveUp;
      }

      g_oPT.ProcessMessages();

      bResult = true;
      //End method body
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      System.out.println((new Date()).toString() + ", main(), Thread = " + Thread.currentThread() + ", Finally, bResult = " + bResult);
    }
  }


  public synchronized long IPostMessage(String sMsg)
  {
    //Note: Called on external thread.

    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("IPostMessage");

    GiveUp:try
    {
      //Begin method body
      m_oMsgVector.addElement(sMsg);
      //End method body

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


  private void ProcessMessages()
  {

    dbg.Enter("ProcessMessages");

    GiveUp:try
    {
      //Begin method body
      long lInnerResult = JUtils.RESULT_FAILURE;
      while (true)
      {
        if (!m_oMsgVector.isEmpty())
        {
          Object oMessage = m_oMsgVector.firstElement();

          if ( oMessage instanceof String)
          {
            String sMessage = (String)oMessage;

            CPOSMessage oPOSMessage = new CPOSMessage();
            oPOSMessage.Initialize(sMessage);

            StringBuffer sbMsgType=new StringBuffer();
            lInnerResult= oPOSMessage.GetValue(sbMsgType, CPaySTM.MessageType);

            if (JUtils.RESULT_SUCCESS==lInnerResult)
            { String sMsgType=sbMsgType.toString();

              //sendAuxEnable(true);

              if(true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_FORM_CHOOSE_TRANSACTION_TYPE))
              {
                textField1.setText("Choose Transaction Type");
              }
              else if(true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_FORM_CHOOSE_PAYMENT_TYPE ))
              {
                formToShow("First");
                showThisForm();
                textField1.setText("");
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_FORM_AMOUNT))
              {
                formToShow("Amt");
                showThisForm();
                form.displayTextAt(8,13,showAmt);
              }
              else if(true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_FORM_REVERSE))
              {
                formToShow("Rev");
                showThisForm();
              }
              else if(true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_FORM_CHOOSE_RETURN_TYPE))
              {
                textField1.setText("");
                formToShow("ret");
                showThisForm();
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_GET_CARD_DATA))
              {
                formToShow("Second");
                showThisForm();
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_CARD_MATCH))
              {
                System.out.println("Match");
                formToShow("Sum");
                showThisForm();
                form.displayTextAt(7,12,showAmt);
                //verifySummaryInfo();
                //MSGTYPE_STM_CLERK_VARIFIED_REVERSAL_INFO
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_NO_CARD_MATCH))
              {
                System.out.println("No Match");
                //MSGTYPE_STM_CLERK_VARIFIED_REVERSAL_INFO
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_CAPTURE_PIN_NUMBER))
              {
                System.out.println("waiting to get pin");
                pPad = new jpos.PINPad();
                pPad.addDataListener(this);
                pPad.open("PINPad");
                pPad.claim(100);
                pPad.setDeviceEnabled(true);
                pPad.setAccountNumber(accountNumber);
                pPad.setAmount(amt);
                pPad.beginEFTTransaction("DUKPT", 1);
                pPad.enablePINEntry();
                pPad.setDataEventEnabled(true);

              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_GET_CARD_DATA_AGAIN))
              {
                formToShow("ReDo");
                showThisForm();
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_STM_AMOUNT_DISAPPROVED_BY_CUSTOMER))
              {
                formToShow("Amt");
                showThisForm();
                form.displayTextAt(5,15,showAmt);
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_SIGNATURE_CAPTURE))
              {
                sCap.beginCapture("Sig");
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_STM_MANUAL_CREDIT_CARD_INFO_ENTRY))
              {
                formToShow("Manual");
                showThisForm();
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_SHOW_ADD))
              {
                formToShow("Third");
                showThisForm();
              }
              else if (true==sMsgType.equals(CPaySTM.MSGTYPE_JPOS_PRINT_RECEIPT))
              {
                haveSignature();
              }
              else if (sMsgType.equals("DoKeyPad"))
              {
                formToShow("Keypad");
                form.setKeypadPrompts("Enter the amount", "");
                form.displayKeypad();
              }
            }
          }
          else
          {
            dbg.Message"Invalid message received and discarded.");
          }
          m_oMsgVector.removeElementAt(0);
        }

        JUtils.YieldProcessor();
      }
      //End method body
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit();
    }
  }


  private void formToShow(String name){
    formName=name;
  }


  private void FormSetUp()
  {
    try
    {
      form=new jpos.Form();
      form.addDataListener(this);
      form.addErrorListener(this);
      form.open("Form");
      form.claim(1000);
      form.setDeviceEnabled(true);
      form.setDataEventEnabled(true);
      form.storeForm("First");
      form.storeForm("Second");
      form.storeForm("Third");
      form.storeForm("Amt");
      form.storeForm("ReDo");
      form.storeForm("Wait");
      form.storeForm("Manual");
      form.storeForm("ret");
      form.storeForm("Rev");
      form.storeForm("Sum");
      form.storeForm("Chk");
      System.out.println
    }catch(Exception ie)
  }

  private void MSRSetUp()
  {
    try
    {
      msr = new jpos.MSR();
      msr.addDataListener(this);
      msr.addErrorListener(this);
      msr.open("MSR");
      msr.claim(1000);
      msr.setDeviceEnabled(true);
      msr.setDataEventEnabled(true);
      msr.setParseDecodeData(true);
      msr.setTracksToRead(MSRConst.MSR_TR_1_2);
      System.out.println
    }catch(Exception ie)
  }

  private void SigCapSetUp()
  {
    try
    {
      sCap=new jpos.SignatureCapture();
      sCap.addDataListener(this);
      sCap.open("SigCap");
      sCap.claim(1000);
      sCap.setDeviceEnabled(true);
      sCap.setDataEventEnabled(true);
      System.out.println
    }catch(Exception ie)
  }

  private void StartClerk()
  {
    setLayout(null);
    setVisible(true);
    setSize(insets().left + insets().right + 405,insets().top + insets().bottom + 305);
    setBackground(new Color(255));
    button1 = new java.awt.Button();
    button1.setLabel("1");
    button1.setBounds(insets().left + 24,insets().top + 60,48,48);
    button1.setFont(new Font("Courier", Font.PLAIN, 12));
    button1.setForeground(new Color(65280));
    button1.setBackground(new Color(0));
    button1.addActionListener(this);
    add(button1);
    button1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button2 = new java.awt.Button();
    button2.setLabel("2");
    button2.setBounds(insets().left + 84,insets().top + 60,48,48);
    button2.setFont(new Font("Courier", Font.PLAIN, 12));
    button2.setForeground(new Color(65280));
    button2.setBackground(new Color(0));
    button2.addActionListener(this);
    add(button2);
    button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button3 = new java.awt.Button();
    button3.setLabel("3");
    button3.setBounds(insets().left + 144,insets().top + 60,48,48);
    button3.setFont(new Font("Courier", Font.PLAIN, 12));
    button3.setForeground(new Color(65280));
    button3.setBackground(new Color(0));
    button3.addActionListener(this);
    add(button3);
    button3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button4 = new java.awt.Button();
    button4.setLabel("4");
    button4.setBounds(insets().left + 24,insets().top + 120,48,48);
    button4.setFont(new Font("Courier", Font.PLAIN, 12));
    button4.setForeground(new Color(65280));
    button4.setBackground(new Color(0));
    button4.addActionListener(this);
    add(button4);
    button4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button5 = new java.awt.Button();
    button5.setLabel("5");
    button5.setBounds(insets().left + 84,insets().top + 120,48,48);
    button5.setFont(new Font("Courier", Font.PLAIN, 12));
    button5.setForeground(new Color(65280));
    button5.setBackground(new Color(0));
    button5.addActionListener(this);
    add(button5);
    button5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button6 = new java.awt.Button();
    button6.setLabel("6");
    button6.setBounds(insets().left + 144,insets().top + 120,48,48);
    button6.setFont(new Font("Courier", Font.PLAIN, 12));
    button6.setForeground(new Color(65280));
    button6.setBackground(new Color(0));
    button6.addActionListener(this);
    add(button6);
    button6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button7 = new java.awt.Button();
    button7.setLabel("7");
    button7.setBounds(insets().left + 24,insets().top + 180,48,48);
    button7.setFont(new Font("Courier", Font.PLAIN, 12));
    button7.setForeground(new Color(65280));
    button7.setBackground(new Color(0));
    button7.addActionListener(this);
    add(button7);
    button7.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button8 = new java.awt.Button();
    button8.setLabel("8");
    button8.setBounds(insets().left + 84,insets().top + 180,48,48);
    button8.setFont(new Font("Courier", Font.PLAIN, 12));
    button8.setForeground(new Color(65280));
    button8.setBackground(new Color(0));
    button8.addActionListener(this);
    add(button8);
    button8.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button9 = new java.awt.Button();
    button9.setLabel("9");
    button9.setBounds(insets().left + 144,insets().top + 180,48,48);
    button9.setFont(new Font("Courier", Font.PLAIN, 12));
    button9.setForeground(new Color(65280));
    button9.setBackground(new Color(0));
    button9.addActionListener(this);
    add(button9);
    button9.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button10 = new java.awt.Button();
    button10.setLabel("*");
    button10.setBounds(insets().left + 24,insets().top + 240,48,48);
    button10.setFont(new Font("Courier", Font.PLAIN, 12));
    button10.setForeground(new Color(65280));
    button10.setBackground(new Color(0));
    button10.addActionListener(this);
    add(button10);
    button10.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button11 = new java.awt.Button();
    button11.setLabel("ENTER");
    button11.setBounds(insets().left + 144,insets().top + 240,48,48);
    button11.setFont(new Font("Courier", Font.PLAIN, 12));
    button11.setForeground(new Color(65280));
    button11.setBackground(new Color(0));
    button11.addActionListener(this);
    add(button11);
    button11.setCursor(new Cursor(Cursor.HAND_CURSOR));
    textField1 = new java.awt.TextField();
    textField1.setBounds(insets().left + 24,insets().top + 12,170,26);
    textField1.setBackground(new Color(16777215));
    add(textField1);
    button12 = new java.awt.Button();
    button12.setLabel("SALE");
    button12.setBounds(insets().left + 228,insets().top + 120,144,48);
    button12.setFont(new Font("Courier", Font.PLAIN, 12));
    button12.setForeground(new Color(65280));
    button12.setBackground(new Color(0));
    button12.addActionListener(this);
    add(button12);
    button12.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button13 = new java.awt.Button();
    button13.setLabel("RETURN");
    button13.setBounds(insets().left + 228,insets().top + 180,144,48);
    button13.setFont(new Font("Courier", Font.PLAIN, 12));
    button13.setForeground(new Color(65280));
    button13.setBackground(new Color(0));
    button13.addActionListener(this);
    add(button13);
    button13.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button16 = new java.awt.Button();
    button16.setLabel("REVERSAL");
    button16.setBounds(insets().left + 228,insets().top + 240,144,48);
    button16.setFont(new Font("Courier", Font.PLAIN, 12));
    button16.setForeground(new Color(65280));
    button16.setBackground(new Color(0));
    button16.addActionListener(this);
    add(button16);
    button16.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button14 = new java.awt.Button();
    button14.setLabel("CLEAR");
    button14.setBounds(insets().left + 228,insets().top + 12,60,24);
    button14.setFont(new Font("Courier", Font.PLAIN, 12));
    button14.setForeground(new Color(65280));
    button14.setBackground(new Color(0));
    button14.addActionListener(this);
    add(button14);
    button14.setCursor(new Cursor(Cursor.HAND_CURSOR));
    label1 = new java.awt.Label("    TRANSACTION TYPE");
    label1.setBounds(insets().left + 228,insets().top + 84,140,24);
    label1.setForeground(new Color(16777215));
    add(label1);
    button15 = new java.awt.Button();
    button15.setLabel("0");
    button15.setBounds(insets().left + 84,insets().top + 240,48,48);
    button15.setFont(new Font("Courier", Font.PLAIN, 12));
    button15.setForeground(new Color(65280));
    button15.setBackground(new Color(0));
    button15.addActionListener(this);
    add(button15);
    button15.setCursor(new Cursor(Cursor.HAND_CURSOR));
    setTitle("Clerk Interface");
    soDisp = new jpos.LineDisplay();

  }

  private void showThisForm()
  {

    dbg.Enter("showThisForm);

    GiveUp:try
    {
      form.startForm(formName, true);
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally {
      dbg.Exit();
    }
  }

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==button1)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"1");
    }
    else if(e.getSource()==button2)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"2");
    }
    else if(e.getSource()==button3)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"3");
    }
    else if(e.getSource()==button4)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"4");
    }
    else if(e.getSource()==button5)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"5");
    }
    else if(e.getSource()==button6)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"6");
    }
    else if(e.getSource()==button7)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"7");
    }
    else if(e.getSource()==button8)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"8");
    }
    else if(e.getSource()==button9)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"9");
    }
    else if(e.getSource()==button10)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+".");
    }
    else if(e.getSource()==button11)
    {
      if(isCheck)
      {
        scanCheck();
      }
      else
      {
        showAmt=new String("$ "+textField1.getText());
        Double temp=new Double(textField1.getText());
        amt=(int)((temp.doubleValue()+.0001)*100);
        StringBuffer sb=new StringBuffer();
        CISOMessage.convert_integer_to_stringbuffer_and_pad_with_zeros(amt, 12, sb);
        amount=sb.toString();
        haveAmountFromClerk();
      }
    }
    else if(e.getSource()==button12)
    {
      transactionType=new String("Sale");
      typeIsSale();
      demoLineDisplay();
    }
    else if(e.getSource()==button13)
    {
      transactionType=new String("Return");
      typeIsReturn();
    }
    else if(e.getSource()==button14)
    {
      textField1.setText("");
    }
    else if(e.getSource()==button15)
    {
      String temp=new String(textField1.getText());
      textField1.setText(temp+"0");
    }
    else if(e.getSource()==button16)
    {
      transactionType=new String("Reversal");
      typeIsReversal();
    }

  }


  public void demoLineDisplay()
  {

    try
    {
      System.out.println("Line Display");
      soDisp.open("LineDisp");
      System.out.println("Display opened");
      soDisp.claim(100);
      soDisp.setDeviceEnabled(true);
      soDisp.displayTextAt
    } catch (Exception ie)

  }



  public void dataOccurred(DataEvent e)
  {

    dbg.Enter("");


    try
    {
      System.out.println(e.getSource());
      if(e.getSource() instanceof ivicm.svc.FormService)
      {
        byte[] buttonData=form.getButtonData();
        if(formName.equals("First"))
        {

          if(buttonData[3]==1)
          {
            flag=true;
            typeIsCard();
            formToShow("Wait");
            showThisForm();
          }
          else if(buttonData[1]==1)
          {
            flag=false;
            typeIsDebit();
            formToShow("Wait");
            showThisForm();
          }
          else if(buttonData[5]==1)
          {
            isCheck=true;
            textField1.setText("Scan Check");
            typeIsCheck();
            formToShow("Chk");
            showThisForm();
          }

        }
        else if(formName.equals("ret"))
        {
          if(buttonData[1]==0)
          {
            flag=true;
            typeIsCard();
            formToShow("Wait");
            showThisForm();
          }
          else if(buttonData[1]==1)
          {
            flag=false;
            typeIsDebit();
            formToShow("Wait");
            showThisForm();
          }
        }

        else if(formName.equals("Amt"))
        {
          if(buttonData[1]==0)
          {
            amountIsApproved();
          }
          else if(buttonData[1]==1)
          {
            amountIsNotApproved();
          }
        }
        else if(formName.equals("Rev"))
        {
          if(buttonData[1]==1)
          {
            doReversal();
          }
          else if(buttonData[1]==0)
          {
            abort();
          }

        }

        else if(formName.equals("Sum"))
        {
          if(buttonData[1]==1)
          {
            verifySummaryInfo();
          }
          else if(buttonData[1]==0)
          {
            abort();
          }

        }
        form.setDataEventEnabled(true);
      }

      else if(e.getSource() instanceof ivicm.svc.MSRService)
      {
        haveCardData();
        msr.setDataEventEnabled(true);
      }
      else if(e.getSource() instanceof ivicm.svc.SignatureCaptureService)
      {
        haveSignature();
        sCap.setDataEventEnabled(true);
      }
      else if(e.getSource() instanceof ivicm.svc.PINPadService)
      {
        System.out.println("Pin Event");
        pin=new String(pPad.getEncryptedPIN());
        //pPad.endEFTTransaction(1);
        //pPad.setDataEventEnabled(true);
        System.out.println("In POS "+pin);
        System.out.println("Have Pin Information");
        havePinNumber();
      }

    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit();
    }

  }

  private void abort()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");
    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.ABORT_TRANSACTION);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void havePinNumber()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_PIN_NUMBER);
      lInnerResult=oPOSMessage.SetValue(CPaySTM.PinNumber ,pin);
      pPad.release();
      pPad.close();
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }


  private void haveSignature()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_CREDIT_CARD_SIGNATURE);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void scanCheck()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_CHECK_WAS_SCANNED);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
      isCheck=false;
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }


  private void typeIsCard()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_PAYMENT_METHOD_IS_CREDIT_CARD);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
      paymentType=new String("CreditCard");
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void typeIsCheck()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_PAYMENT_METHOD_IS_CHECK);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
      paymentType=new String("Check");
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void typeIsDebit()
  {
    long lResult=JUtils.RESULT_FAILURE;
    dbg.Enter("");

    GiveUp:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_PAYMENT_METHOD_IS_DEBIT_CARD);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
      paymentType=new String("DebitCard");
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void typeIsSale()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_SALE);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void typeIsReturn()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_RETURN);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void typeIsReversal()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void doReversal()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_TRANSACTION_TYPE_IS_REVERSAL_APPROVED);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void haveCardData()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      if(flag)
      {
        lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_VALID_CREDIT_CARD_SWIPE);
      }
      else if(!flag)
      {
        System.out.println("Flag is false");
        lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_VALID_DEBIT_CARD_SWIPE);
        lInnerResult=oPOSMessage.SetValue(CPaySTM.Track2Data, new String(msr.getTrack2Data()));
      }
      lInnerResult=oPOSMessage.SetValue(CPaySTM.PrimaryAccountNumber ,msr.getAccountNumber());
      accountNumber=new String(msr.getAccountNumber());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.Track2Data, new String(msr.getTrack2Data()));
      lInnerResult=oPOSMessage.SetValue(CPaySTM.ExpirationDate,msr.getExpirationDate());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.SurName, msr.getSurname());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.FirstName,msr.getFirstName());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MiddleInitial, msr.getMiddleInitial());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.Suffix, msr.getSuffix());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.Title, msr.getTitle());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.Track1DiscretionaryData, new String(msr.getTrack1DiscretionaryData()));
      lInnerResult=oPOSMessage.SetValue(CPaySTM.ServiceCode, msr.getServiceCode());
      lInnerResult=oPOSMessage.SetValue(CPaySTM.Track1Data, new String(msr.getTrack1Data()));
      lInnerResult=oPOSMessage.SetValue(CPaySTM.PaymentAmount, amount);
      lInnerResult=oPOSMessage.SetValue(CPaySTM.PaymentMethod, paymentType);
      lInnerResult=oPOSMessage.SetValue(CPaySTM.TransactionType, transactionType);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());

        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void amountIsApproved()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_AMOUNT_APPROVED_BY_CUSTOMER);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void haveAmountFromClerk()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_AMOUNT_FROM_CLERK);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void amountIsNotApproved()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_AMOUNT_DISAPPROVED_BY_CUSTOMER);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void verifySummaryInfo()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType ,CPaySTM.MSGTYPE_STM_CLERK_VARIFIED_REVERSAL_INFO);
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void cardError()
  {
    long lResult=JUtils.RESULT_FAILURE;
    Givedbg.Enter("");

    Up:try
    {
      long lInnerResult=JUtils.RESULT_FAILURE;
      CPOSMessage oPOSMessage=new CPOSMessage();
      oPOSMessage.Initialize();
      if(flag)
      {
        lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_INVALID_CREDIT_CARD_SWIPE);
      }
      else
      {
        lInnerResult=oPOSMessage.SetValue(CPaySTM.MessageType,CPaySTM.MSGTYPE_STM_INVALID_DEBIT_CARD_SWIPE);
      }
      StringBuffer sbMsg=new StringBuffer();
      lInnerResult=oPOSMessage.GetMessageTransmissionString(sbMsg);
      lInnerResult=m_oMsgS_ToSTM.IPostMessage(sbMsg.toString());
        if(JUtils.RESULT_SUCCESS != lInnerResult)
        {break GiveUp;}
        lResult=JUtils.RESULT_SUCCESS;
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }
    finally
    {
      dbg.Exit();
    }
  }

  private void formDone()
  {

    dbg.Enter("");


    GiveUp:try
    {
      form.release();
      form.close();
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit();
    }

  }

  private void msrDone()
  {

    dbg.Enter("");


    GiveUp:try
    {
      msr.release();
      msr.close();
    }
    catch(Throwable oThrowable)
    {
      dbg.Caught(oThrowable);
    }

    finally
    {
      dbg.Exit();
    }
  }

  public void errorOccurred(ErrorEvent e)
  {
    try
    {
      if(e.getSource() instanceof ivicm.svc.MSRService)
      {
        System.out.println("Card error");
        msr.clearInput();
        msr.setDeviceEnabled(true);
        msr.setDataEventEnabled(true);
        cardError();
      }
    }
    catch (Exception ie)
  }


  private boolean Initialize(String sIPAddressToSendSTMMessagesTo, int nPortToSendSTMMessagesTo, int nPortToReceiveSTMMessagesOn)
  {
    boolean bResult = false;

    dbg.Enter("");


    GiveUp:try
    {


      m_sIPAddressToSendSTMMessagesTo = sIPAddressToSendSTMMessagesTo;
      m_nPortAddressToSendSTMMessagesTo = nPortToSendSTMMessagesTo;
      m_nPortAddressToReceiveSTMMessagesFrom = nPortToReceiveSTMMessagesOn;

      m_oMsgVector = new Vector();

      //Begin creating m_oMsgR_FromSTM
      m_oMsgR_FromSTM = new CMsgR();
      m_oMsgR_FromSTM.Initialize(m_nPortAddressToReceiveSTMMessagesFrom, QUEUE_SIZE_FOR_RECEIVING_STM_MESSAGES, this);
      new Thread(m_oMsgR_FromSTM).start();
      //End creating m_oMsgR_FromSTM

      //Begin creating m_oMsgS_ToSTM
      m_oMsgS_ToSTM = new CMsgS();
      m_oMsgS_ToSTM.Initialize(this, m_sIPAddressToSendSTMMessagesTo, m_nPortAddressToSendSTMMessagesTo);
      new Thread(m_oMsgS_ToSTM).start();
      //End creating m_oMsgS_ToSTM
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

      return bResult;
    }
  }

} // class CPT
//$Id$
