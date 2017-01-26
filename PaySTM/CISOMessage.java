/* $Id: CISOMessage.java,v 1.2 2000/03/30 13:48:27 anonymous Exp $ */
package PaySTM;

import java.util.*;
import java.io.*;
//import java.awt.*;
//import java.awt.event.*;

import JUtils.*;

public class CISOMessage
{

  //#instance ErrorLogStream.Member
  protected static ErrorLogStream dbg=new ErrorLogStream("<classname>", false);
  //#end ErrorLogStream.Member

  public static final String m_ISO_LRSeparator = "=";
  public static final String m_ISO_FieldSeparator = ";";

  public static final String m_ISO_MessageType = "MessageType";
  public static final String m_ISO_PrimaryAccountNumber = "PrimaryAccountNumber";
  public static final String m_ISO_ProcessingCode = "ProcessingCode";
  public static final String m_ISO_MessageLength = "MessageLength";
  public static final String m_ISO_IsoPrimaryBitMap = "IsoPrimaryBitMap";
  public static final String m_ISO_IsoSecondaryBitMap = "IsoSecondaryBitMap";
  public static final String m_ISO_IsoExtendedBitMap = "IsoExtendedBitMap";
  public static final String m_ISO_TransactionType = "TransactionType";
  public static final String m_ISO_TransactionAmount = "TransactionAmount";
  public static final String m_ISO_TransmissionDateTime = "TransmissionDateTime";
  public static final String m_ISO_SystemTraceAuditNumber = "SystemTraceAuditNumber";
  public static final String m_ISO_LocalTransactionTime = "LocalTransactionTime";
  public static final String m_ISO_LocalTransactionDate = "LocalTransactionDate";
  public static final String m_ISO_ExpirationDate = "ExpirationDate";
  public static final String m_ISO_SettlementDate = "SettlementDate";
  public static final String m_ISO_MerchantType = "MerchantType";
  public static final String m_ISO_PointOfServiceEntryMode = "PointOfServiceEntryMode";
  public static final String m_ISO_PointOfServiceConditionCode = "PointOfServiceConditionCode";
  public static final String m_ISO_Track2Data = "Track2Data";
  public static final String m_ISO_RetrievalReferenceNumber = "RetrievalReferenceNumber";
  public static final String m_ISO_AuthorizationIdentificationResponse = "AuthorizationIdentificationResponse";
  public static final String m_ISO_ResponseCode = "ResponseCode";
  public static final String m_ISO_CardAcceptorTerninalIdentification = "CardAcceptorTerninalIdentification";
  public static final String m_ISO_CardAcceptorIdentificationCode = "CardAcceptorIdentificationCode";
  public static final String m_ISO_CardAcceptorNameLocation = "CardAcceptorNameLocation";
  public static final String m_ISO_Track1Data = "Track1Data";
  public static final String m_ISO_AdditionalDataPrivate = "AdditionalDataPrivate";
  public static final String m_ISO_PersonalIdentificationNumberData = "PersonalIdentificationNumberData";
  public static final String m_ISO_SecurityRelatedControlInformation = "SecurityRelatedControlInformation";
  public static final String m_ISO_AdditionalAmount = "AdditionalAmount";
  public static final String m_ISO_AuthorizationLifeCycle = "AuthorizationLifeCycle";
  public static final String m_ISO_PrivateDataForISP = "PrivateDataForISP";
  public static final String m_ISO_SettlementCode = "SettlementCode";
  public static final String m_ISO_NetworkManagementInformationCode = "NetworkManagementInformationCode";
  public static final String m_ISO_TotalNumberCredits = "TotalNumberCredits";
  public static final String m_ISO_TotalNumberCreditRevesals = "TotalNumberCreditRevesals";
  public static final String m_ISO_TotalNumberDebits = "TotalNumberDebits";
  public static final String m_ISO_TotalNumberDebitReversals = "TotalNumberDebitReversals";
  public static final String m_ISO_TotalNumberAuthorizations = "TotalNumberAuthorizations";
  public static final String m_ISO_TotalAmountCredits = "TotalAmountCredits";
  public static final String m_ISO_TotalAmountCreditReversals = "TotalAmountCreditReversals";
  public static final String m_ISO_TotalAmountDebits = "TotalAmountDebits";
  public static final String m_ISO_TotalAmountDebitReversals = "TotalAmountDebitReversals";
  public static final String m_ISO_OriginalDataElements = "OriginalDataElements";
  public static final String m_ISO_MessageSecurityCode = "MessageSecurityCode";
  public static final String m_ISO_NetSettlementAmount = "NetSettlementAmount";
  public static final String m_ISO_ReceivingInstitutionIDCode = "ReceivingInstitutionIDCode";
  public static final String m_ISO_ANSIAdditionalTraceData2 = "ANSIAdditionalTraceData2";
  public static final String m_ISO_TransStartTime = "TransStartTime";
  public static final String m_ISO_TransEndTime = "TransEndTime";
  public static final String m_ISO_AuthorizationStartTime = "AuthorizationStartTime";
  public static final String m_ISO_AuthorizationEndTime = "AuthorizationEndTime";
  public static final String m_ISO_PaymentTypeCode = "PaymentTypeCode";
  public static final String m_ISO_EventNumber = "EventNumber";
  public static final String m_ISO_ServerName = "ServerName";
  public static final String m_ISO_OriginalSTAN = "OriginalSTAN";
  public static final String m_ISO_StandInIndicator = "StandInIndicator";
  public static final String m_ISO_LogToDiskTimer = "LogToDiskTimer";
  public static final String m_ISO_BatchNumber = "BatchNumber";
  public static final String m_ISO_ActionCode = "ActionCode";
  public static final String m_ISO_DemoModeIndicator = "DemoModeIndicator";
  public static final String m_ISO_TransGTRID = "TransGTRID";
  public static final String m_ISO_OriginatingPID = "OriginatingPID";
  public static final String m_ISO_OriginateNode = "OriginateNode";
  public static final String m_ISO_CashbackAmount = "CashbackAmount";
  public static final String m_ISO_EmployeeNumber = "EmployeeNumber";
  public static final String m_ISO_ControllerType = "ControllerType";
  public static final String m_ISO_CheckType = "CheckType";
  public static final String m_ISO_MessageMap = "MessageMap";
  public static final String m_ISO_ReconciliationType = "ReconciliationType";
  public static final String m_ISO_IndividualTotals = "IndividualTotals";
  public static final String m_ISO_KeyManagementInformation = "KeyManagementInformation";
  public static final String m_ISO_DriversLicense = "DriversLicense";
  public static final String m_ISO_TimeOutIndicator = "TimeOutIndicator";
  public static final String m_ISO_LateResponseIndicator = "LateResponseIndicator";
  public static final String m_ISO_AuthorizerServer = "AuthorizerServer";
  public static final String m_ISO_AuthorizerName = "AuthorizerName";
  public static final String m_ISO_EmployeePIN = "EmployeePIN";
  public static final String m_ISO_LogonTime = "LogonTime";
  public static final String m_ISO_LogonDate = "LogonDate";
  public static final String m_ISO_CheckRouting = "CheckRouting";
  public static final String m_ISO_CheckingAccountNumber = "CheckingAccountNumber";
  public static final String m_ISO_CheckNumber = "CheckNumber";
  public static final String m_ISO_SocialSecurityNumber = "SocialSecurityNumber";
  public static final String m_ISO_OtherCardNumber = "OtherCardNumber";
  public static final String m_ISO_IdentificationType = "IdentificationType";
  public static final String m_ISO_ResubmittalCount = "ResubmittalCount";
  public static final String m_ISO_HostSettlemnetDate = "HostSettlemnetDate";
  public static final String m_ISO_HostProcessingCode = "HostProcessingCode";
  public static final String m_ISO_SettleInstID = "SettleInstID";
  public static final String m_ISO_StateCode = "StateCode";
  public static final String m_ISO_ZipCode = "ZipCode";
  public static final String m_ISO_HostResponseCode = "HostResponseCode";
  public static final String m_ISO_MerchantID = "MerchantID";
  public static final String m_ISO_StoreAuthData = "StoreAuthData";
  public static final String m_ISO_SwitchDateTime = "SwitchDateTime";
  public static final String m_ISO_CourtesyCardNumber = "CourtesyCardNumber";
  public static final String m_ISO_StoreID = "StoreID";
  public static final String m_ISO_ManualVoucherNumber = "ManualVoucherNumber";
  public static final String m_ISO_Unknown_m_ISO_field_name_string_input = "Unknown_m_ISO_field_name_string_input";



  public long Initialize_ISO_Hashtable( Hashtable o_Hashtable )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("Initialize_ISO_Hashtable");

    GiveUp:try
    {
      //Begin method body
      o_Hashtable.put( m_ISO_MessageType, "" );
      o_Hashtable.put( m_ISO_PrimaryAccountNumber, "" );
      o_Hashtable.put( m_ISO_ProcessingCode, "" );
      o_Hashtable.put( m_ISO_MessageLength, "" );
      o_Hashtable.put( m_ISO_IsoPrimaryBitMap, "" );
      o_Hashtable.put( m_ISO_IsoSecondaryBitMap, "" );
      o_Hashtable.put( m_ISO_IsoExtendedBitMap, "" );
      o_Hashtable.put( m_ISO_TransactionType, "" );
      o_Hashtable.put( m_ISO_PrimaryAccountNumber, "" );
      o_Hashtable.put( m_ISO_ProcessingCode, "" );
      o_Hashtable.put( m_ISO_TransactionAmount, "" );
      o_Hashtable.put( m_ISO_TransmissionDateTime, "" );
      o_Hashtable.put( m_ISO_SystemTraceAuditNumber, "" );
      o_Hashtable.put( m_ISO_LocalTransactionTime, "" );
      o_Hashtable.put( m_ISO_LocalTransactionDate, "" );
      o_Hashtable.put( m_ISO_ExpirationDate, "" );
      o_Hashtable.put( m_ISO_SettlementDate, "" );
      o_Hashtable.put( m_ISO_MerchantType, "" );
      o_Hashtable.put( m_ISO_PointOfServiceEntryMode, "" );
      o_Hashtable.put( m_ISO_PointOfServiceConditionCode, "" );
      o_Hashtable.put( m_ISO_Track2Data, "" );
      o_Hashtable.put( m_ISO_RetrievalReferenceNumber, "" );
      o_Hashtable.put( m_ISO_AuthorizationIdentificationResponse, "" );
      o_Hashtable.put( m_ISO_ResponseCode, "" );
      o_Hashtable.put( m_ISO_CardAcceptorTerninalIdentification, "" );
      o_Hashtable.put( m_ISO_CardAcceptorIdentificationCode, "" );
      o_Hashtable.put( m_ISO_CardAcceptorNameLocation, "" );
      o_Hashtable.put( m_ISO_Track1Data, "" );
      o_Hashtable.put( m_ISO_AdditionalDataPrivate, "" );
      o_Hashtable.put( m_ISO_PersonalIdentificationNumberData, "" );
      o_Hashtable.put( m_ISO_SecurityRelatedControlInformation, "" );
      o_Hashtable.put( m_ISO_AdditionalAmount, "" );
      o_Hashtable.put( m_ISO_AuthorizationLifeCycle, "" );
      o_Hashtable.put( m_ISO_PrivateDataForISP, "" );
      o_Hashtable.put( m_ISO_SettlementCode, "" );
      o_Hashtable.put( m_ISO_NetworkManagementInformationCode, "" );
      o_Hashtable.put( m_ISO_TotalNumberCredits, "" );
      o_Hashtable.put( m_ISO_TotalNumberCreditRevesals, "" );
      o_Hashtable.put( m_ISO_TotalNumberDebits, "" );
      o_Hashtable.put( m_ISO_TotalNumberDebitReversals, "" );
      o_Hashtable.put( m_ISO_TotalNumberAuthorizations, "" );
      o_Hashtable.put( m_ISO_TotalAmountCredits, "" );
      o_Hashtable.put( m_ISO_TotalAmountCreditReversals, "" );
      o_Hashtable.put( m_ISO_TotalAmountDebits, "" );
      o_Hashtable.put( m_ISO_TotalAmountDebitReversals, "" );
      o_Hashtable.put( m_ISO_OriginalDataElements, "" );
      o_Hashtable.put( m_ISO_MessageSecurityCode, "" );
      o_Hashtable.put( m_ISO_NetSettlementAmount, "" );
      o_Hashtable.put( m_ISO_ReceivingInstitutionIDCode, "" );
      o_Hashtable.put( m_ISO_ANSIAdditionalTraceData2, "" );
      o_Hashtable.put( m_ISO_TransStartTime, "" );
      o_Hashtable.put( m_ISO_TransEndTime, "" );
      o_Hashtable.put( m_ISO_AuthorizationStartTime, "" );
      o_Hashtable.put( m_ISO_AuthorizationEndTime, "" );
      o_Hashtable.put( m_ISO_PaymentTypeCode, "" );
      o_Hashtable.put( m_ISO_TransactionType, "" );
      o_Hashtable.put( m_ISO_EventNumber, "" );
      o_Hashtable.put( m_ISO_ServerName, "" );
      o_Hashtable.put( m_ISO_OriginalSTAN, "" );
      o_Hashtable.put( m_ISO_StandInIndicator, "" );
      o_Hashtable.put( m_ISO_LogToDiskTimer, "" );
      o_Hashtable.put( m_ISO_BatchNumber, "" );
      o_Hashtable.put( m_ISO_ActionCode, "" );
      o_Hashtable.put( m_ISO_DemoModeIndicator, "" );
      o_Hashtable.put( m_ISO_TransGTRID, "" );
      o_Hashtable.put( m_ISO_OriginatingPID, "" );
      o_Hashtable.put( m_ISO_OriginateNode, "" );
      o_Hashtable.put( m_ISO_CashbackAmount, "" );
      o_Hashtable.put( m_ISO_EmployeeNumber, "" );
      o_Hashtable.put( m_ISO_ControllerType, "" );
      o_Hashtable.put( m_ISO_CheckType, "" );
      o_Hashtable.put( m_ISO_MessageMap, "" );
      o_Hashtable.put( m_ISO_ReconciliationType, "" );
      o_Hashtable.put( m_ISO_IndividualTotals, "" );
      o_Hashtable.put( m_ISO_KeyManagementInformation, "" );
      o_Hashtable.put( m_ISO_DriversLicense, "" );
      o_Hashtable.put( m_ISO_TimeOutIndicator, "" );
      o_Hashtable.put( m_ISO_LateResponseIndicator, "" );
      o_Hashtable.put( m_ISO_AuthorizerServer, "" );
      o_Hashtable.put( m_ISO_AuthorizerName, "" );
      o_Hashtable.put( m_ISO_EmployeePIN, "" );
      o_Hashtable.put( m_ISO_LogonTime, "" );
      o_Hashtable.put( m_ISO_LogonDate, "" );
      o_Hashtable.put( m_ISO_CheckRouting, "" );
      o_Hashtable.put( m_ISO_CheckingAccountNumber, "" );
      o_Hashtable.put( m_ISO_CheckNumber, "" );
      o_Hashtable.put( m_ISO_SocialSecurityNumber, "" );
      o_Hashtable.put( m_ISO_OtherCardNumber, "" );
      o_Hashtable.put( m_ISO_IdentificationType, "" );
      o_Hashtable.put( m_ISO_ResubmittalCount, "" );
      o_Hashtable.put( m_ISO_HostSettlemnetDate, "" );
      o_Hashtable.put( m_ISO_HostProcessingCode, "" );
      o_Hashtable.put( m_ISO_SettleInstID, "" );
      o_Hashtable.put( m_ISO_StateCode, "" );
      o_Hashtable.put( m_ISO_ZipCode, "" );
      o_Hashtable.put( m_ISO_HostResponseCode, "" );
      o_Hashtable.put( m_ISO_MerchantID, "" );
      o_Hashtable.put( m_ISO_StoreAuthData, "" );
      o_Hashtable.put( m_ISO_SwitchDateTime, "" );
      o_Hashtable.put( m_ISO_CourtesyCardNumber, "" );
      o_Hashtable.put( m_ISO_StoreID, "" );
      o_Hashtable.put( m_ISO_ManualVoucherNumber, "" );
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



  public long convert_raw_bit_map_to_HashTable( String input_string, Hashtable htable_key_by_shrtbit )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("convert_raw_bit_map_to_HashTable");

    GiveUp:try
    {
      //Begin method body
      {
        /* Strategy:
        i.)   Get a single iso field string that contains all of the
        iso fields concatenated together starting with iso1.
        ii.)  Go through the complete iso fields to ascertain bit presense.
        If a bit is present, then advance the ascii_char_pos variable
        and ascii_cnt_field_length variable accordingly.
        iii.) Assign a substring of input_string pointed to ascii_char_pos
        to a htable_key_by_shrtbit.  Simulataneously assign the key to
        this hash table as (e.g. "bit32").
        */
        int ascii_char_pos, number_of_ascii_chars, int_eval, iso_field_index;
        int subfield_length, hex_digit, ascii_cnt_field_length, iso_index;
        int returned_ascii_cnt_field_length[];
        char char_array_dummy[];
        String string_examine, iso_string_aggregate;
        StringBuffer str_buffer_exmn;
        String string_eval;
        boolean iso2_field_present, iso3_field_present, remain_in_loop;
        number_of_ascii_chars = input_string.length();

        iso_string_aggregate = "";
        ascii_char_pos =   1 ; ascii_cnt_field_length = 1; // Initial "#" sign
        {
          str_buffer_exmn = new StringBuffer();
          return_raw_bit_substring(input_string,ascii_char_pos,ascii_cnt_field_length,str_buffer_exmn);
          string_examine = str_buffer_exmn.toString();
          if ( string_examine != "#" )
          ;  // Print error
        }
        ascii_char_pos += ascii_cnt_field_length ; ascii_cnt_field_length = 4; // "MessageLength"
        {
          // System.out.println("The ascii_char_pos for MessageLength is " + ascii_char_pos);
          returned_ascii_cnt_field_length = new int [10];
          enter_field_into_HashTable(input_string, "len", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "MessageType"
        {
          // System.out.println("The ascii_char_pos for MessageType is " + ascii_char_pos);
          enter_field_into_HashTable(input_string, "tran", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }






        ascii_char_pos =  10 ; ascii_cnt_field_length = 16; // "IsoPrimaryBitMap", and
        //  (if exists) "IsoSecondaryBitMap"
        //  and "IsoExtendedBitMap"

        {
          // System.out.println("The ascii_char_pos for IsoPrimaryBitMap is " + ascii_char_pos);
          remain_in_loop = true;
          iso_index = 1;
          iso_string_aggregate = "";
          while ( remain_in_loop )
          {
            str_buffer_exmn = new StringBuffer();
            return_raw_bit_substring(input_string,ascii_char_pos,ascii_cnt_field_length,str_buffer_exmn);
            string_examine = str_buffer_exmn.toString();
            switch ( iso_index )
            {
              case 1 : htable_key_by_shrtbit.put("iso1", string_examine) ; break ;
              case 2 : htable_key_by_shrtbit.put("iso2", string_examine) ; break ;
              case 3 : htable_key_by_shrtbit.put("iso3", string_examine) ; break ;
            }
            iso_string_aggregate += string_examine;
            // Need to determine here if MSB of current iso_string is "1" to
            //   determine if remain_in_loop
            {
              //        int_eval = Character.Digit( String.toCharacter((string_examine.substring(0, 1))), 16);
              char_array_dummy = string_examine.toCharArray();
              int_eval = Character.digit( char_array_dummy[0], 16);
              if ( int_eval >= 8 )
              {
                remain_in_loop = true;
                iso_index++;
                ascii_char_pos += 16;
              }
              else
              {
                remain_in_loop = false;
              }
            }
          }
        }

        ascii_char_pos += ascii_cnt_field_length ; ascii_cnt_field_length = 4; // "PrimaryAccountNumber"
        {
          // System.out.println("The ascii_char_pos for PrimaryAccountNumber is " + ascii_char_pos);
          enter_field_into_HashTable(input_string, "bit2", "LLVAR", ascii_char_pos,
          19, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "ProcessingCode"
        {
          // System.out.println("The ascii_char_pos for ProcessingCode is " + ascii_char_pos);
          enter_field_into_HashTable(input_string, "bit3", "Standard", ascii_char_pos,
          6, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }

        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 12; // "TransactionAmount"
        {
          // System.out.println("The ascii_char_pos for TransactionAmount is " + ascii_char_pos);
          enter_field_into_HashTable(input_string, "bit4", "Standard", ascii_char_pos,
          12, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "TransmissionDate&Time"
        {
          // System.out.println("The ascii_char_pos for TransmissionDate&Time is " + ascii_char_pos);
          enter_field_into_HashTable(input_string, "bit7", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 6; // "SystemTraceAuditNumber"
        {
          // System.out.println("The ascii_char_pos for SystemTraceAuditNumber is " + ascii_char_pos);
          enter_field_into_HashTable(input_string, "bit11", "Standard", ascii_char_pos,
          6, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 6; // "LocalTransactionTime"
        {
          enter_field_into_HashTable(input_string, "bit12", "Standard", ascii_char_pos,
          6, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "LocalTransactionDate"
        {
          enter_field_into_HashTable(input_string, "bit13", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "ExpirationDate"
        {
          enter_field_into_HashTable(input_string, "bit14", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "SettlementDate"
        {
          enter_field_into_HashTable(input_string, "bit15", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "MerchantType"
        {
          enter_field_into_HashTable(input_string, "bit18", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 3; // "PointOfServiceEntryMode"
        {
          enter_field_into_HashTable(input_string, "bit22", "Standard", ascii_char_pos,
          3, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "PointOfServiceConditionCode"
        {
          enter_field_into_HashTable(input_string, "bit25", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "Track2Data"
        {
          enter_field_into_HashTable(input_string, "bit35", "LLVAR", ascii_char_pos,
          37, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 12; // "RetrievalReferenceNumber"
        {
          enter_field_into_HashTable(input_string, "bit37", "Standard", ascii_char_pos,
          12, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 6; // "AuthorizationIdentificationResponse"
        {
          enter_field_into_HashTable(input_string, "bit38", "Standard", ascii_char_pos,
          6, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "ResponseCode"
        {
          enter_field_into_HashTable(input_string, "bit39", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 8; // "CardAcceptorTerminalIdentification"
        {
          enter_field_into_HashTable(input_string, "bit41", "Standard", ascii_char_pos,
          8, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 15; // "CardAcceptorIdentificationCode"
        {
          enter_field_into_HashTable(input_string, "bit42", "Standard", ascii_char_pos,
          15, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 40; // "CardAcceptorNameLocation"
        {
          enter_field_into_HashTable(input_string, "bit43", "Standard", ascii_char_pos,
          40, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "Track1Data"
        {
          enter_field_into_HashTable(input_string, "bit45", "LLVAR", ascii_char_pos,
          76, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "AdditionalDataPrivate"
        {
          enter_field_into_HashTable(input_string, "bit48", "LLLVAR", ascii_char_pos,
          999, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "PersonalIdentificationNumberData"
        {
          enter_field_into_HashTable(input_string, "bit52", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "SecurityRelatedControlInformation"
        {
          enter_field_into_HashTable(input_string, "bit53", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "AdditionalAmount"
        {
          enter_field_into_HashTable(input_string, "bit54", "LLLVAR", ascii_char_pos,
          120, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 3; // "AuthorizationLifeCycle"
        {
          enter_field_into_HashTable(input_string, "bit57", "Standard", ascii_char_pos,
          3, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "PrivateDataForISP"
        {
          enter_field_into_HashTable(input_string, "bit63", "LLVAR", ascii_char_pos,
          99, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "SettlementCode"
        {
          enter_field_into_HashTable(input_string, "bit66", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 3; // "NetworkManagementInformationCode"
        {
          enter_field_into_HashTable(input_string, "bit70", "Standard", ascii_char_pos,
          3, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "TotalNumberCredits"
        {
          enter_field_into_HashTable(input_string, "bit74", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "TotalNumberCreditRevesals"
        {
          enter_field_into_HashTable(input_string, "bit75", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "TotalNumberDebits"
        {
          enter_field_into_HashTable(input_string, "bit76", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "TotalNumberDebitReversals"
        {
          enter_field_into_HashTable(input_string, "bit77", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "TotalNumberAuthorizations"
        {
          enter_field_into_HashTable(input_string, "bit81", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "TotalAmountCredits"
        {
          enter_field_into_HashTable(input_string, "bit86", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "TotalAmountCreditReversals"
        {
          enter_field_into_HashTable(input_string, "bit87", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "TotalAmountDebits"
        {
          enter_field_into_HashTable(input_string, "bit88", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "TotalAmountDebitReversals"
        {
          enter_field_into_HashTable(input_string, "bit89", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 42; // "OriginalDataElements"
        {
          enter_field_into_HashTable(input_string, "bit90", "Standard", ascii_char_pos,
          42, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 16; // "MessageSecurityCode"
        {
          enter_field_into_HashTable(input_string, "bit96", "Standard", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "NetSettlementAmount"
        {
          enter_field_into_HashTable(input_string, "bit97", "Standard", ascii_char_pos,
          17, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "ReceivingInstitutionIDCode"
        {
          enter_field_into_HashTable(input_string, "bit100", "LLVAR", ascii_char_pos,
          11, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "ANSIAdditionalTraceData2"
        {
          enter_field_into_HashTable(input_string, "bit116", "LLLVAR", ascii_char_pos,
          999, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 14; // "TransStartTime"
        {
          enter_field_into_HashTable(input_string, "bit130", "Standard", ascii_char_pos,
          14, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 14; // "TransEndTime"
        {
          enter_field_into_HashTable(input_string, "bit131", "Standard", ascii_char_pos,
          14, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 14; // "AuthorizationStartTime"
        {
          enter_field_into_HashTable(input_string, "bit132", "Standard", ascii_char_pos,
          14, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 14; // "AuthorizationEndTime"
        {
          enter_field_into_HashTable(input_string, "bit133", "Standard", ascii_char_pos,
          14, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "PaymentTypeCode"
        {
          enter_field_into_HashTable(input_string, "bit134", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "TransactionType"
        {
          enter_field_into_HashTable(input_string, "bit135", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 8; // "EventNumber"
        {
          enter_field_into_HashTable(input_string, "bit136", "Standard", ascii_char_pos,
          8, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 8; // "ServerName"
        {
          enter_field_into_HashTable(input_string, "bit137", "Standard", ascii_char_pos,
          8, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 6; // "OriginalSTAN"
        {
          enter_field_into_HashTable(input_string, "bit138", "Standard", ascii_char_pos,
          6, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "StandInIndicator"
        {
          enter_field_into_HashTable(input_string, "bit139", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 14; // "LogToDiskTimer"
        {
          enter_field_into_HashTable(input_string, "bit140", "Standard", ascii_char_pos,
          14, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 6; // "BatchNumber"
        {
          enter_field_into_HashTable(input_string, "bit141", "Standard", ascii_char_pos,
          6, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "ActionCode"
        {
          enter_field_into_HashTable(input_string, "bit142", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "DemoModeIndicator"
        {
          enter_field_into_HashTable(input_string, "bit143", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 12; // "TransGTRID"
        {
          enter_field_into_HashTable(input_string, "bit144", "Standard", ascii_char_pos,
          12, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 12; // "OriginatingPID"
        {
          enter_field_into_HashTable(input_string, "bit145", "Standard", ascii_char_pos,
          12, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 8; // "OriginateNode"
        {
          enter_field_into_HashTable(input_string, "bit146", "Standard", ascii_char_pos,
          8, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 12; // "CashbackAmount"
        {
          enter_field_into_HashTable(input_string, "bit147", "Standard", ascii_char_pos,
          12, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "EmployeeNumber"
        {
          enter_field_into_HashTable(input_string, "bit148", "LLVAR", ascii_char_pos,
          16, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 3; // "ControllerType"
        {
          enter_field_into_HashTable(input_string, "bit149", "Standard", ascii_char_pos,
          3, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "CheckType"
        {
          enter_field_into_HashTable(input_string, "bit150", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "MessageMap"
        {
          enter_field_into_HashTable(input_string, "bit153", "LLVAR", ascii_char_pos,
          99, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        // bit154 ("ReconcilliationType") is undefined and reserved for future use or internal to Mainsail.
        // bit155 ("IndividualTotals"   ) is undefined and reserved for future use or internal to Mainsail.
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "KeyManagementInformation"
        {
          enter_field_into_HashTable(input_string, "bit156", "LLVAR", ascii_char_pos,
          27, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "DriversLicense"
        {
          enter_field_into_HashTable(input_string, "bit157", "LLVAR", ascii_char_pos,
          99, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "TimeOutIndicator"
        {
          enter_field_into_HashTable(input_string, "bit158", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 1; // "LateResponseIndicator"
        {
          enter_field_into_HashTable(input_string, "bit159", "Standard", ascii_char_pos,
          1, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 8; // "AuthorizerServer"
        {
          enter_field_into_HashTable(input_string, "bit160", "Standard", ascii_char_pos,
          8, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 12; // "AuthorizerName"
        {
          enter_field_into_HashTable(input_string, "bit161", "Standard", ascii_char_pos,
          12, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "EmployeePIN"
        {
          enter_field_into_HashTable(input_string, "bit162", "LLVAR", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        // bit163 ("LogonTime") is undefined and reserved for future use or internal to Mainsail.
        // bit164 ("LogonDate") is undefined and reserved for future use or internal to Mainsail.
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies // "CheckRouting"
        {
          enter_field_into_HashTable(input_string, "bit165", "LLVAR", ascii_char_pos,
          9, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "CheckingAccountNumber"
        {
          enter_field_into_HashTable(input_string, "bit166", "LLVAR", ascii_char_pos,
          19, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; //ascii_cnt_field_length varies; // "CheckNumber"
        {
          enter_field_into_HashTable(input_string, "bit167", "LLVAR", ascii_char_pos,
          5, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 9; // "SocialSecurityNumber"
        {
          enter_field_into_HashTable(input_string, "bit168", "Standard", ascii_char_pos,
          9, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "OtherCardNumber"
        {
          enter_field_into_HashTable(input_string, "bit169", "LLVAR", ascii_char_pos,
          28, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "IdentificationType"
        {
          enter_field_into_HashTable(input_string, "bit170", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 10; // "ResubmittalCount"
        {
          enter_field_into_HashTable(input_string, "bit171", "Standard", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        // bit172 ("HostSettlementDate") is undefined and reserved for future use or internal to Mainsail.
        // bit173 ("HostProcessingCode") is undefined and reserved for future use or internal to Mainsail.
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 4; // "SettleInstID"
        {
          enter_field_into_HashTable(input_string, "bit174", "Standard", ascii_char_pos,
          4, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "StateCode"
        {
          enter_field_into_HashTable(input_string, "bit175", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "ZipCode"
        {
          enter_field_into_HashTable(input_string, "bit176", "LLVAR", ascii_char_pos,
          10, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 2; // "HostResponseCode"
        {
          enter_field_into_HashTable(input_string, "bit177", "Standard", ascii_char_pos,
          2, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 15; // "MerchantID"
        {
          enter_field_into_HashTable(input_string, "bit178", "Standard", ascii_char_pos,
          15, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "StoreAuthData"
        {
          enter_field_into_HashTable(input_string, "bit179", "LLVAR", ascii_char_pos,
          25, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; ascii_cnt_field_length = 14; // "SwitchDateTime"
        {
          enter_field_into_HashTable(input_string, "bit180", "Standard", ascii_char_pos,
          14, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
        // bit181 ("CourtesyCardNumber") is undefined and reserved for future use or internal to Mainsail.
        // bit182 ("StoreID"           ) is undefined and reserved for future use or internal to Mainsail.
        ascii_char_pos += returned_ascii_cnt_field_length[0] ; // ascii_cnt_field_length varies; // "ManualVoucherNumber"
        {
          enter_field_into_HashTable(input_string, "bit183", "LLVAR", ascii_char_pos,
          28, iso_string_aggregate, htable_key_by_shrtbit, returned_ascii_cnt_field_length);
        }
      }
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


  public long return_raw_bit_substring( String raw_string, int string_position, int string_length, StringBuffer return_StringBuffer)
  {
    long lResult = JUtils.RESULT_FAILURE;
    dbg.Enter("return_raw_bit_substring");


    GiveUp:try
    {
      //Begin method body
      return_StringBuffer.append( raw_string.substring( (string_position-1), ((string_position+string_length)-1)));
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

  public long convert_HashTable_to_raw_bit_map( Hashtable htable_key_by_shrtbit, StringBuffer raw_bit_map )
  {
    long lResult =JUtils.RESULT_FAILURE;
    dbg.Enter("convert_HashTable_to_raw_bit_map");

    GiveUp:try
    {
      //Begin method body
      /* This function accepts as input a hashtable that represents an NTN transaction, and
      returns a stringbuffer that is the equivilent NTN string for that transaction as
      specified by the NTN Mainsail Interface Specification (fourth edition 03/01/99).
      This function assumes that the hashtable input represents a valid transaction.
      In case the user cannot assume that the hashtable is in good order (in terms of
      data integrity and proper bit fields represented) then the corresponding JAVA
      validity checking functions should be called on the hashtable before being
      invoked with this function.  -timf 02-23-00.
      */
        int int_bit_number, loop_decrementor;
        int pref_subfield_lngth[] = { 0 };
        String string_bit_number, non_zero_padded_pref_subfield_contents;
      StringBuffer pref_subfield_contents, suff_subfield_contents;
      // raw_bit_map = new StringBuffer("");
      raw_bit_map.setLength(0);
      raw_bit_map.append( "#" );

      if ( htable_key_by_shrtbit.containsKey( "len" ) )
      {
        raw_bit_map.append( htable_key_by_shrtbit.get( "len" ));
      }

      if ( htable_key_by_shrtbit.containsKey( "tran" ) )
      {
        raw_bit_map.append( htable_key_by_shrtbit.get( "tran" ));
      }

      if ( htable_key_by_shrtbit.containsKey( "iso1" ) )
      {
        raw_bit_map.append( htable_key_by_shrtbit.get( "iso1" ));
      }

      if ( htable_key_by_shrtbit.containsKey( "iso2" ) )
      {
        raw_bit_map.append( htable_key_by_shrtbit.get( "iso2" ));
      }

      if ( htable_key_by_shrtbit.containsKey( "iso3" ) )
      {
        raw_bit_map.append( htable_key_by_shrtbit.get( "iso3" ));
      }


      for (int_bit_number=2;int_bit_number<=183;int_bit_number++)
      {
        pref_subfield_contents = new StringBuffer(); // Initialization
        suff_subfield_contents = new StringBuffer(); // Initialization
        string_bit_number = "bit" + Integer.toString( int_bit_number );
        pref_subfield_lngth[0] = -99;
        obtain_prefix_subfield_length(string_bit_number, pref_subfield_lngth);
        // If pref_subfield_lngth[0] > 0, then pref_subfield_contents must indicate
        //  the length of the suff_subfield_contents.  If necessary, the
        //  pref_subfield_contents must be padded with 0's in order to comply
        //  with the pref_subfield_lngth.
        // The following if statement makes use of the fact that the
        //  function, obtain_prefix_subfield_length returns not only the
        //  prefix_subfield_length, but also (depending on the return value)
        //  whether the particular bit field properly exists in (any) valid NTN
        //  transaction.
        if ( ( pref_subfield_lngth[0] != -1 ) && ( pref_subfield_lngth[0] != -99 ) )
        {
          if ( htable_key_by_shrtbit.containsKey( string_bit_number ) )
          {
            suff_subfield_contents = new StringBuffer(htable_key_by_shrtbit.get(string_bit_number).toString());
            non_zero_padded_pref_subfield_contents = ""; //Initialization
            if ( pref_subfield_lngth[0] > 0 )
            {
              non_zero_padded_pref_subfield_contents = Integer.toString(suff_subfield_contents.length());
            }
            loop_decrementor = pref_subfield_lngth[0] - non_zero_padded_pref_subfield_contents.length();
            while ( loop_decrementor > 0 )  // This while loop provides the 0 padding.
            {
              pref_subfield_contents.append("0");
              loop_decrementor--;
            }
            pref_subfield_contents.append(non_zero_padded_pref_subfield_contents);
          }
          raw_bit_map.append( pref_subfield_contents );
          raw_bit_map.append( suff_subfield_contents );
        }
      }
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

  public long obtain_prefix_subfield_length( String bit_number, int computed_p_s_l[] )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("obtain_prefix_subfield_length");

    GiveUp:try
    {
      //Begin method body
      if      ( bit_number.equals( "len"   ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "iso1"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "iso2"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "iso3"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "tran"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit2"  ) ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit3"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit4"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit7"  ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit11" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit12" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit13" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit14" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit15" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit18" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit22" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit25" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit35" ) ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit37" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit38" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit39" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit41" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit42" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit43" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit45" ) ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit48" ) ) computed_p_s_l[0] = 3;
      else if ( bit_number.equals( "bit52" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit53" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit54" ) ) computed_p_s_l[0] = 3;
      else if ( bit_number.equals( "bit57" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit63" ) ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit66" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit70" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit74" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit75" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit76" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit77" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit81" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit86" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit87" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit88" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit89" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit90" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit96" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit97" ) ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit100") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit116") ) computed_p_s_l[0] = 3;
      else if ( bit_number.equals( "bit130") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit131") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit132") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit133") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit134") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit135") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit136") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit137") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit138") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit139") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit140") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit141") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit142") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit143") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit144") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit145") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit146") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit147") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit148") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit149") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit150") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit153") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit154") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit155") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit156") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit157") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit158") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit159") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit160") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit161") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit162") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit163") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit164") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit165") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit166") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit167") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit168") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit169") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit170") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit171") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit172") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit173") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit174") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit175") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit176") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit177") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit178") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit179") ) computed_p_s_l[0] = 2;
      else if ( bit_number.equals( "bit180") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit181") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit182") ) computed_p_s_l[0] = 0;
      else if ( bit_number.equals( "bit183") ) computed_p_s_l[0] = 2;
      else                                     computed_p_s_l[0] = -1;
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

  public long convert_raw_bit_map_to_internet_formatted_bit_map(String raw_bit_map, StringBuffer internet_formatted_bit_map)
  {
    long lResult = JUtils.RESULT_FAILURE;
    dbg.Enter("convert_raw_bit_map_to_internet_formatted_bit_map");

    GiveUp:try
    {
      //Begin method body
      int raw_bit_map_index;
      Character one_char_ascii_entry;
      char char_array_raw_bit_map[] = raw_bit_map.toCharArray();
      String one_char_ascii_entry_string;
      internet_formatted_bit_map.setLength(0);  // Removes any garbage that may have been passed into function.

      // Note that raw_bit_map_index is initialized at 1 (not 0) to skip the initial #.
      for(raw_bit_map_index=1;raw_bit_map_index<raw_bit_map.length();raw_bit_map_index++)
      {
        if      ( char_array_raw_bit_map[raw_bit_map_index] == ' ' ) internet_formatted_bit_map.append("%20");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '<' ) internet_formatted_bit_map.append("%3C");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '>' ) internet_formatted_bit_map.append("%3E");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '^' ) internet_formatted_bit_map.append("%5E");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '%' ) internet_formatted_bit_map.append("%25");
          else if ( char_array_raw_bit_map[raw_bit_map_index] == '{' ) internet_formatted_bit_map.append("%7B");
          else if ( char_array_raw_bit_map[raw_bit_map_index] == '}' ) internet_formatted_bit_map.append("%7D");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '|' ) internet_formatted_bit_map.append("%7C");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '\\' ) internet_formatted_bit_map.append("%5C");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '~' ) internet_formatted_bit_map.append("%7E");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '[' ) internet_formatted_bit_map.append("%5B");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == ']' ) internet_formatted_bit_map.append("%5D");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '`' ) internet_formatted_bit_map.append("%60");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '?' ) internet_formatted_bit_map.append("%3F");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '@' ) internet_formatted_bit_map.append("%40");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == ':' ) internet_formatted_bit_map.append("%3A");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == ';' ) internet_formatted_bit_map.append("%3B");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '&' ) internet_formatted_bit_map.append("%26");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '"' ) internet_formatted_bit_map.append("%22"); //"
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '=' ) internet_formatted_bit_map.append("%3D");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '/' ) internet_formatted_bit_map.append("%2F");
        else if ( char_array_raw_bit_map[raw_bit_map_index] == '#' ) internet_formatted_bit_map.append("%23");
        else
        {
          one_char_ascii_entry = new Character(char_array_raw_bit_map[raw_bit_map_index]);
          one_char_ascii_entry_string = one_char_ascii_entry.toString();
          internet_formatted_bit_map.append(one_char_ascii_entry_string);
        }
      }
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

  public long convert_internet_formatted_bit_map_to_raw_bit_map(String internet_formatted_bit_map, StringBuffer raw_bit_map_output)
  {
    long lResult = JUtils.RESULT_FAILURE;
    dbg.Enter("convert_internet_formatted_bit_map_to_raw_bit_map");

    GiveUp:try
    {
      //Begin method body
      int internet_char_array_index;
      int percent_substring_char_count = 0;
      Character one_char_ascii_entry;
      char internet_char_array[] = internet_formatted_bit_map.toCharArray();
      boolean percent_sign_escape_substring_flag = false;
      StringBuffer percent_sign_escape_substring;
      percent_sign_escape_substring = new StringBuffer( "" );
      String one_char_ascii_entry_string;

      raw_bit_map_output.setLength(0); // Removes any garbage that may have been passed into function.
      raw_bit_map_output.append( "#" );
      for(internet_char_array_index=0;internet_char_array_index<internet_formatted_bit_map.length();internet_char_array_index++)
      {
        one_char_ascii_entry = new Character(internet_char_array[internet_char_array_index]);
        one_char_ascii_entry_string = one_char_ascii_entry.toString();
        if ( percent_sign_escape_substring_flag )
        {
          percent_sign_escape_substring.append( one_char_ascii_entry_string );
          percent_substring_char_count++;
          if ( percent_substring_char_count >= 3 )
          {
            percent_substring_char_count = 0;
            percent_sign_escape_substring_flag = false;
            if      ( percent_sign_escape_substring.toString().equals( "%20" ) ) raw_bit_map_output.append(" ");
            else if ( percent_sign_escape_substring.toString().equals( "%3C" ) ) raw_bit_map_output.append("<");
            else if ( percent_sign_escape_substring.toString().equals( "%3E" ) ) raw_bit_map_output.append(">");
            else if ( percent_sign_escape_substring.toString().equals( "%5E" ) ) raw_bit_map_output.append("^");
            else if ( percent_sign_escape_substring.toString().equals( "%25" ) ) raw_bit_map_output.append("%");
              else if ( percent_sign_escape_substring.toString().equals( "%7B" ) ) raw_bit_map_output.append("{");
              else if ( percent_sign_escape_substring.toString().equals( "%7D" ) ) raw_bit_map_output.append("}");
            else if ( percent_sign_escape_substring.toString().equals( "%7C" ) ) raw_bit_map_output.append("|");
            else if ( percent_sign_escape_substring.toString().equals( "%5C" ) ) raw_bit_map_output.append("\\");
            else if ( percent_sign_escape_substring.toString().equals( "%7E" ) ) raw_bit_map_output.append("~");
            else if ( percent_sign_escape_substring.toString().equals( "%5B" ) ) raw_bit_map_output.append("[");
            else if ( percent_sign_escape_substring.toString().equals( "%5D" ) ) raw_bit_map_output.append("]");
            else if ( percent_sign_escape_substring.toString().equals( "%60" ) ) raw_bit_map_output.append("`");
            else if ( percent_sign_escape_substring.toString().equals( "%3F" ) ) raw_bit_map_output.append("?");
            else if ( percent_sign_escape_substring.toString().equals( "%40" ) ) raw_bit_map_output.append("@");
            else if ( percent_sign_escape_substring.toString().equals( "%3A" ) ) raw_bit_map_output.append(":");
            else if ( percent_sign_escape_substring.toString().equals( "%3B" ) ) raw_bit_map_output.append(";");
            else if ( percent_sign_escape_substring.toString().equals( "%26" ) ) raw_bit_map_output.append("&");
            else if ( percent_sign_escape_substring.toString().equals( "%22" ) ) raw_bit_map_output.append("\"");   //"
            else if ( percent_sign_escape_substring.toString().equals( "%3D" ) ) raw_bit_map_output.append("=");
            else if ( percent_sign_escape_substring.toString().equals( "%2F" ) ) raw_bit_map_output.append("/");
            else if ( percent_sign_escape_substring.toString().equals( "%23" ) ) raw_bit_map_output.append("#");
          }
        }
        else  // percent_sign_escape_substring_flag == false
        {
          if ( one_char_ascii_entry_string.equals( "%" ) )
          {
            percent_sign_escape_substring_flag = true;
            percent_substring_char_count = 1;
            percent_sign_escape_substring = new StringBuffer();
            percent_sign_escape_substring.append("%");
          }
          else
          {
            raw_bit_map_output.append( one_char_ascii_entry_string );
          }
        }
      }
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

  public long enter_field_into_HashTable(String complete_record, String bit_number, String field_format,
  int ascii_char_pos, int attribute_magnitude, String aggregate_iso_fields, Hashtable o_hashtable, int output_calculated_field_length[])
  {
    long lResult = JUtils.RESULT_FAILURE;
    dbg.Enter("enter_field_into_HashTable");

    GiveUp:try
    {
      //Begin method body
      int prefix_subfield_length, suffix_subfield_length, total_field_length, loop_decrementor, exponent;
      int pos_cnt_within_subfield, decimal_digit;
        int loop_index;
        char prefix_chars_array[] = { };
        String bit_field_present_string;
      String string_examine, prefix_subfield_contents, suffix_subfield_contents;
      StringBuffer bit_field_present, str_buffer_exmn;
      bit_field_present = new StringBuffer("");

      total_field_length = 0; // Actually total_field_length is unknown here, but compiler insists on init.
      if ( ( bit_number == "len" ) || ( bit_number == "tran" ) || ( bit_number == "iso1" )
      || ( bit_number == "iso2") || ( bit_number == "iso3" ) )
      {
        bit_field_present.append( "true" );
      }
      else
      {
        bit_field_present = new StringBuffer("");
        determine_if_bit_present( aggregate_iso_fields, bit_number, bit_field_present );
        // System.out.println("determine_if_bit_present function returned bit_field_present = " + bit_field_present + " for bit number = " + bit_number);
      }
      prefix_subfield_length = 0;  //Actually prefix_subfield_length is unknown here, but
      // the compiler insists on an initializiation.
      suffix_subfield_length = 0;  //Actually suffix_subfield_length is unknown here, but
      // the compiler insists on an initializiation.
      // System.out.println("The contents of bit_field_present upon the entrance into the ->");
      // System.out.println("-> if statement is ->" + bit_field_present + "<-");
      bit_field_present_string = bit_field_present.toString();
      if ( bit_field_present_string.equals( "true" ) )
      {
        // System.out.println("bit_field_presence equals true is recognized.");
        if ( field_format.equals( "Standard" ) )
        {
          total_field_length = attribute_magnitude;
          prefix_subfield_length = 0;
          suffix_subfield_length = attribute_magnitude;
        }
        else if ( field_format.equals( "LVAR" ) )
        {
          prefix_subfield_length = 1;
        }
        else if ( field_format.equals( "LLVAR" ) )
        {
          prefix_subfield_length = 2;
        }
        else if ( field_format.equals( "LLLVAR" ) )
        {
          prefix_subfield_length = 3;
        }
        else
        {
          System.out.println("Improper input");
        }
        if ( ! ( field_format.equals( "Standard" ) ) )
        {
          // System.out.println("Point1");
          // ("complete_record = " + complete_record);
          // System.out.println("ascii_char_pos = " + ascii_char_pos + "; prefix_subfield_length = " + prefix_subfield_length);
          //      prefix_chars_array = complete_record.substring( (ascii_char_pos-1), prefix_subfield_length );
          prefix_subfield_contents = complete_record.substring( (ascii_char_pos-1), ((ascii_char_pos-1)+prefix_subfield_length) );
          // System.out.println("prefix_subfield_contents = " + prefix_subfield_contents);
          prefix_chars_array = prefix_subfield_contents.toCharArray();
          // System.out.println("Proceeding to invoke getChars function.");
          // System.out.println("ascii_char_pos = " + ascii_char_pos + "; prefix_subfield_length = " + prefix_subfield_length);
          //      System.out.println("prefix_chars_array = " + prefix_chars_array);
          //      complete_record.getChars( (ascii_char_pos-1), ((ascii_char_pos-1)+prefix_subfield_length),
          //                              prefix_chars_array, 1);
          // System.out.println("Point2");
          suffix_subfield_length = 0; // Not really zero; just initializing here prior to
          //                          //  entrance into loop for the computation of
          //                          //  suffix_subfield_length.
          for (loop_index=0;loop_index<prefix_subfield_length;loop_index++)
          {
            // suffix_subfield_length += Character.digit(prefix_chars_array[loop_index], 10);
            suffix_subfield_length
            +=  ( Character.digit(prefix_chars_array[loop_index], 10))
            * ( Math.pow( 10, (prefix_subfield_length-1-loop_index)));
          }
        }
        total_field_length = prefix_subfield_length + suffix_subfield_length;
        // System.out.println("Proceeding to compute suffix_subfield_contents");
        // System.out.println("ascii_char_pos = " + ascii_char_pos);
        // System.out.println("prefix_subfield_length = " + prefix_subfield_length);
        // System.out.println("suffix_subfield_length = " + suffix_subfield_length);
        // The following line of code gets the suffix_subfield_contents which is to be
        //  inserted into the hashtable.
        suffix_subfield_contents = complete_record.substring((ascii_char_pos-1+prefix_subfield_length),
        (ascii_char_pos-1+prefix_subfield_length+suffix_subfield_length));
        // System.out.println("suffix_subfield_contents = " + suffix_subfield_contents);
        o_hashtable.put(bit_number, suffix_subfield_contents);
      }
      else if ( bit_field_present_string.equals( "false" ) )
      {
        // System.out.println("bit_field_presence equals false is recognized.");
        total_field_length = 0;
      }
      else
      {
        System.out.println("Error: Contents of bit_field_present should be either true or false.");
        System.out.println("The actual contents of bit_field_present is " + bit_field_present);
      }
      // System.out.println("total_field_length = " + total_field_length);
      output_calculated_field_length[0] = total_field_length;
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
      return   lResult ;
    }
  }

  public long determine_if_bit_present( String aggregate_iso_fields, String bit_field, StringBuffer bit_present )
  {
    long lResult = JUtils.RESULT_FAILURE;
    dbg.Enter("determine_if_bit_present");
    GiveUp:try
    {
      //Begin method body
      int int_bit_number, number_of_bit_numerals, loop_decrementor, exponent, pos_cnt_within_subfield;
      int index, ascii_char_index, respective_bit_in_ascii_char;
      char indicative_ascii_char;
      String bit_numerals_proper, string_examine;
      StringBuffer str_buffer_exmn;
        int_bit_number = 0; // Not really zero, just initialized here to enable calculation.
          if      ( bit_field == "len"  ) { int_bit_number = 9999 ; bit_present.append( "true" ); }
            else if ( bit_field == "tran" ) { int_bit_number = 9999 ; bit_present.append( "true" ); }
            else if ( bit_field == "iso1" ) { int_bit_number = 9999 ; bit_present.append( "true" ); }
            else if ( bit_field == "iso2" ) { int_bit_number = 9999 ; bit_present.append( "true" ); }
          else if ( bit_field == "iso3" ) { int_bit_number = 9999 ; bit_present.append( "true" ); }
        else if ( bit_field.startsWith( "bit" ) )
      {
        // System.out.println("bit_field determined to start with bit.");
        bit_numerals_proper = bit_field.substring( 3 );
        // System.out.println("bit_numerals_proper = " + bit_numerals_proper);
        char bit_numerals_char_array[] = bit_numerals_proper.toCharArray();
        for (index=0;index<bit_numerals_char_array.length;index++)
        {
          // System.out.println("bit_numerals_char_array[" + index + "] = " + bit_numerals_char_array[index]);
          //      int_bit_number += Math.pow( Character.digit(bit_numerals_char_array[index], 10),
          //                                  bit_numerals_char_array.length-index);
          int_bit_number += Character.digit(bit_numerals_char_array[index], 10)
          * Math.pow( 10, bit_numerals_char_array.length-index-1);
        }
      }
      else
      {
        System.out.println("ERROR:  The bit_field passed to determine_if_bit_present (bit_field = " + bit_field + " is outside its valid range.");
      }
      // Bit number ==============> 123456789ABC
      // Equiv. ascii_char_index =>    1   2   3
      // System.out.println("int_bit_number = " + int_bit_number);
      ascii_char_index = ( ( int_bit_number - 1 ) / 4 ) + 1;
      // System.out.println("ascii_char_index = " + ascii_char_index);
      if ( ascii_char_index <= aggregate_iso_fields.length() )
      {
        //    indicative_ascii_char = aggregate_iso_fields.substring((ascii_char_index-1),ascii_char_index);
        char char_array_dummy[] = (aggregate_iso_fields.substring((ascii_char_index-1),ascii_char_index)).toCharArray();
        // char_array_dummy has a length of 1 by design.
        indicative_ascii_char = char_array_dummy[0];
        //    System.out.println("indicative_ascii_char = " + indicative_ascii_char);
        //    The intent of the if statement is to examine the information within indicative_ascii_char
        //     and int_bit_number to determine if the iso_field claims that the particular bit field
        //     is present.  The purpose of calculating int_bit_number up to this point is to make
        //     sure that every possible bit field that COULD appear in a transmission is examined.
        //     The indicative_ascii_char is the single ascii character from the aggregate_iso_string
        //     that houses the information as to whether this particular bit field is in the transmission.
        //     bit 45 => newbit 8
        //     bit 46 => newbit 4
        //     bit 47 => newbit 2
        //     bit 48 => newbit 1
        //     bit 49 => newbit 8
        //     bit 50 => newbit 4            newbit := pow(2, ((4 - bit mod 4) mod 4 ))
        //    if ( ( Character.digit(indicative_ascii_char, 16) | int_bit_number ) != 0 )
        respective_bit_in_ascii_char = (int) Math.pow(2, ((4 - int_bit_number % 4) % 4));
        if ( ( Character.digit(indicative_ascii_char, 16) & ( respective_bit_in_ascii_char ) ) != 0 )
        {
          bit_present.append( "true" );
        }
        else
        {
          bit_present.append( "false" );
        }
      }
      else
      {
        bit_present.append( "false" );
      }
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
      return   lResult ;
    }

  }

  public long ParseMessage ( String input_message, Hashtable htable_name )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("ParseMessage");

    GiveUp:try
    {
      //Begin method body
      int token_count, lindex;
      String input_string, temp_string, key_entry, contents_entry;
      htable_name.clear();
      input_string = input_message;
      token_count = 0;
      StringTokenizer tokens = new StringTokenizer( input_string );
      while ( tokens.hasMoreTokens()                             )
      {
        token_count++;
        temp_string = tokens.nextToken( ";" ) ;
        lindex = temp_string.indexOf( (int) '=' );
        key_entry = temp_string.substring( 0, lindex );
        contents_entry = temp_string.substring( lindex + 1 ) ;
        Object val = htable_name.put( key_entry, contents_entry );
      }
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
      return  lResult;
    }
  }

  public long UnParseMessage ( Hashtable htable_name, String output_message )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("UnParseMessage");

    GiveUp:try
    {
      //Begin method body
      String temp_keyx, tempstringx;
      output_message = "";
      for ( Enumeration enum = htable_name.keys(); enum.hasMoreElements(); )
      {
        temp_keyx = enum.nextElement().toString() ;
        Object o_tempstringx = htable_name.get( temp_keyx );
        tempstringx = o_tempstringx.toString() ;
        output_message = output_message + tempstringx;
      }
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
      return  lResult;
    }

  }

  public long bin_str_to_hex_str( String bin_str, StringBuffer hex_str )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("bin_str_to_hex_str");

    GiveUp:try
    {
      //Begin method body
      if      ( bin_str.equals( "0000" ) ) hex_str.append( "0" );
      else if ( bin_str.equals( "0001" ) ) hex_str.append( "1" );
      else if ( bin_str.equals( "0010" ) ) hex_str.append( "2" );
      else if ( bin_str.equals( "0011" ) ) hex_str.append( "3" );
      else if ( bin_str.equals( "0100" ) ) hex_str.append( "4" );
      else if ( bin_str.equals( "0101" ) ) hex_str.append( "5" );
      else if ( bin_str.equals( "0110" ) ) hex_str.append( "6" );
      else if ( bin_str.equals( "0111" ) ) hex_str.append( "7" );
      else if ( bin_str.equals( "1000" ) ) hex_str.append( "8" );
      else if ( bin_str.equals( "1001" ) ) hex_str.append( "9" );
      else if ( bin_str.equals( "1010" ) ) hex_str.append( "A" );
      else if ( bin_str.equals( "1011" ) ) hex_str.append( "B" );
      else if ( bin_str.equals( "1100" ) ) hex_str.append( "C" );
      else if ( bin_str.equals( "1101" ) ) hex_str.append( "D" );
      else if ( bin_str.equals( "1110" ) ) hex_str.append( "E" );
      else if ( bin_str.equals( "1111" ) ) hex_str.append( "F" );
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
      return  lResult;
    }

  }

  public long Generate_bit_map_string( Hashtable htable_name, StringBuffer output_bit_map )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("Generate_bit_map_string");

    GiveUp:try
    {
      //Begin method body
      int bit_index, bmaci, m, bit_map_field, max_bit_map_field, content_count, while_count;
      int computed_index;
      String iso_key, temp_keyx, key_string, string_iso_field_name;
      String content_string, bit_map_string;
      String binary_string_arg, content_cnt_str;
      String bit_present[]  = new String [ 250 ];
      StringBuffer iso_field_name, extracted_hex_str;
      Hashtable htable_key_by_iso_field;
      htable_key_by_iso_field = new Hashtable();

      //  Place tran contents into htable_key_by_iso_field
      key_string = "MessageType";
      if ( htable_key_by_iso_field.containsKey( key_string ) )
      {
        Object o_tempstringx = htable_name.get( key_string );
        content_string = o_tempstringx.toString() ;
        Object val = htable_key_by_iso_field.put( "tran", content_string );
      }

      //  The following for loop generates a new hash table keyed by short bit string (e.g. bit2)
      //  from a hash table keyed by long name (e.g. TransactionDate&Time).  The contents
      //  of each hash table are equivilent.
      for ( Enumeration enum = htable_name.keys(); enum.hasMoreElements(); )
      {
        temp_keyx = enum.nextElement().toString() ;
        //    System.out.println("The value of temp_keyx = " + temp_keyx);
        Object tempstringx = htable_name.get( temp_keyx );
        content_string = tempstringx.toString();
        //    System.out.println("The value of content_string = " + content_string);
        iso_field_name = new StringBuffer( "" );
        convert_long_name_to_iso_field_name(temp_keyx, iso_field_name);
        string_iso_field_name = iso_field_name.toString();
        //    System.out.println("The value of iso_field_name = " + iso_field_name);
        Object val = htable_key_by_iso_field.put( string_iso_field_name, content_string );
      }
      //  System.out.println("Entering check for key presense loop");
      for (bit_index=2;bit_index<=192;bit_index++)
      {
        key_string = "bit" + Integer.toString( bit_index );
        //    System.out.println("Examining presense of " + key_string + " in hash table.");
        if ( htable_key_by_iso_field.containsKey( key_string ) )
        bit_present[bit_index] = "1";
        else
        bit_present[bit_index] = "0";
      }
      //  System.out.println("Entering MSB bit_field calculation loop.");

      // The following approx. 21 lines compute the values of bit_index[1], and
      // bit_index[65].  Recall that bit_index[65] indicates whether
      // bit 130 through bit 192 contain any data.
      // Recall that bit_index[1] indicates whether bit 65 through bit 128 contain
      // any data.
      bit_present[1] = "0";
      bit_present[65] = "0";
      max_bit_map_field = 1;
      while_count = 130 ; // while_count must examine 130 through 192
      while ( ( bit_present[65] == "0" ) && ( while_count <= 192 ) )
      {
        if ( bit_present[while_count] == "1" )
        {
          bit_present[65] = "1";
          bit_present[1] = "1";
          max_bit_map_field = 3;
        }
        while_count++;
      }
      if ( bit_present[65] == "0" )
      {
        while_count = 66;
        while ( ( bit_present[1] == "0" ) && ( while_count <= 128 ) )
        {
          if ( bit_present[while_count] == "1" )
          {
            bit_present[1] = "1";
            max_bit_map_field = 2;
          }
          while_count++;
        }
      }

      // The following for loop makes entries for the
      //  iso fields in the hashtable, htable_key_by_iso_field
      for ( bit_map_field=1; bit_map_field<=max_bit_map_field; bit_map_field++ )
      {
        bit_map_string = "";
        // NOTE:  bmaci = bit_map_ascii_char_index
        for ( bmaci=1;bmaci<=16;bmaci++ )
        {
          // bmaci == 1 refers to bit numbers 1, 2, 3, and 4.
          // bmaci == 2 refers to bit numbers 5, 6, 7, and 8.
          // bmaci == 3 refers to bit numbers 9, 10, 11, and 12.
          // bmaci == n refers to bit numbers m+1,m+2,m+3, and m+4 for m=(n-1)*4
          //   Hence for bmaci == n
          //    refers to bit numbers ((n-1)*4)+1,((n-1)*4)+2,((n-1)*4)+3,((n-1)*4)+4
          m = ( bmaci - 1 ) * 4;
          computed_index = ( ( bit_map_field - 1 ) * 64 ) + m;
          binary_string_arg=bit_present[(computed_index+1)]+bit_present[(computed_index+2)]
          +bit_present[(computed_index+3)]+bit_present[(computed_index+4)];
          extracted_hex_str = new StringBuffer( "" );
          bin_str_to_hex_str(binary_string_arg, extracted_hex_str);
          //      System.out.println("The computed extracted_hex_str is " + extracted_hex_str );
          bit_map_string += extracted_hex_str.toString();
        }
        //    System.out.println("The computed bit_map_string for an iso field is " + bit_map_string);
        temp_keyx = "";
        if      ( bit_map_field == 1 )
        temp_keyx = "iso1";
        else if ( bit_map_field == 2 )
        temp_keyx = "iso2";
        else if ( bit_map_field == 3 )
        temp_keyx = "iso3";
        //    Object val htable_key_by_iso_field.put( temp_keyx, bit_map_string );
        htable_key_by_iso_field.put( temp_keyx, bit_map_string );
      }
      for ( bit_map_field=(max_bit_map_field+1);bit_map_field<=3; bit_map_field++ )
      {
        if ( bit_map_field == 2 )
        //    Object val = htable_key_by_iso_field.put( "iso2", "" );
        htable_key_by_iso_field.put( "iso2", "" );
        if ( bit_map_field == 3 )
        //    Object val = htable_key_by_iso_field.put( "iso3", "" );
        htable_key_by_iso_field.put( "iso3", "" );
      }

      //  The following code computes the length of the output bit string including iso_fields.

      content_count = 0;
      for ( Enumeration enum = htable_key_by_iso_field.keys(); enum.hasMoreElements(); )
      {
        temp_keyx = enum.nextElement().toString() ;
        Object tempstringx = htable_key_by_iso_field.get( temp_keyx );
        content_string = tempstringx.toString();
        content_count += content_string.length();
      }
      content_cnt_str = Integer.toString( content_count );
      Object val = htable_key_by_iso_field.put( "len", content_cnt_str );

      //  Commence construction of output_bit_map.

      output_bit_map.append( "http://208.58.21.60:8080/test/cgi/aFileName.cgi?" );
      output_bit_map.append( "len=" );
      output_bit_map.append( htable_key_by_iso_field.get( "len" ) );
      output_bit_map.append( "?" );

      //  Place tran contents into output_bit_map.
      key_string = "tran";
      if ( htable_key_by_iso_field.containsKey( key_string ) )
      {
        Object o_tempstringx = htable_key_by_iso_field.get( key_string );
        content_string = o_tempstringx.toString() ;
        output_bit_map.append( key_string );
        output_bit_map.append( "=" );
        output_bit_map.append( content_string );
        output_bit_map.append( "?" );
      }
      //  System.out.println("The contents of bit_present[177] = " + bit_present[177]);
      //  System.out.println("The contents of bit_present[183] = " + bit_present[183]);

      //  Place iso contents into output_bit_map.

      output_bit_map.append( "iso1=" );
      output_bit_map.append( htable_key_by_iso_field.get( "iso1" ));
      output_bit_map.append( "?" );
      output_bit_map.append( "iso2=" );
      output_bit_map.append( htable_key_by_iso_field.get( "iso2" ));
      output_bit_map.append( "?" );
      output_bit_map.append( "iso3=" );
      output_bit_map.append( htable_key_by_iso_field.get( "iso3" ) );

      for (bit_index=2;bit_index<=192;bit_index++)
      {
        key_string = "bit" + Integer.toString( bit_index ) ;
        //    System.out.println("The value of key_string = " + key_string);
        if ( htable_key_by_iso_field.containsKey( key_string ) )
        {
          Object o_tempstringx = htable_key_by_iso_field.get( key_string );
          content_string = o_tempstringx.toString() ;
          //      System.out.println("The corresponding value of content_string is " + content_string);
          output_bit_map.append( "?" );
          output_bit_map.append( key_string );
          output_bit_map.append( "=" );
          output_bit_map.append( content_string );
        }
      }
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
      return  lResult;
    }
  }

  public long ValidateData_keybybitstring(String bit_string, String contents_string, StringBuffer error_results)
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("ValidateData_keybybitstring");

    GiveUp:try
    {
      //Begin method body
      String temp_key, temp_string;
      StringBuffer result_string_buffer;
      result_string_buffer = new StringBuffer();
      temp_key = bit_string;
      temp_string = contents_string.toString();

      if      ( temp_key.equals( "len" )                          )    // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "tran" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "iso1" )                          )   // an 16
      evaluate_data_integrity(temp_string, "alphanumeric",     "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "iso2" )                          )   // an 16
      evaluate_data_integrity(temp_string, "alphanumeric",     "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "iso3" )                          )   // an 16
      evaluate_data_integrity(temp_string, "alphanumeric",     "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit2" )                          )   // n..19
      evaluate_data_integrity(temp_string, "strictly_numeric", "less_than_equal", 19, result_string_buffer);
      else if ( temp_key.equals( "bit3" )                          )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "bit4" )                          )   // n 12
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "bit7" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit11" )                          )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "bit12" )                          )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "bit13" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit14" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit15" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit18" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit22" )                          )   // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit25" )                          )   // n 2
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "bit35" )                          )   // z..37
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 37, result_string_buffer);
      else if ( temp_key.equals( "bit37" )                          )   // an 12
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "bit38" )                          )   // an 6
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "bit39" )                          )   // an 2
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "bit41" )                          )   // ans 8
      evaluate_data_integrity(temp_string, "anything", "exactly", 8, result_string_buffer);
      else if ( temp_key.equals( "bit42" )                          )   // ans 15
      evaluate_data_integrity(temp_string, "anything", "exactly", 15, result_string_buffer);
      else if ( temp_key.equals( "bit43" )                          )   // ans 40
      evaluate_data_integrity(temp_string, "anything", "exactly", 40, result_string_buffer);
      else if ( temp_key.equals( "bit45" )                          )   // ans..76
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 76, result_string_buffer);
      else if ( temp_key.equals( "bit48" )                          )   // ans..999
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 999, result_string_buffer);
      else if ( temp_key.equals( "bit52" )                          )   // an 16
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit53" )                          )   // n 16
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit54" )                          )   // an..120
      evaluate_data_integrity(temp_string, "alphanumeric", "less_than_equal", 120, result_string_buffer);
      else if ( temp_key.equals( "bit57" )                          )   // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "bit63" )                          )   // ans..99
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 99, result_string_buffer);
      else if ( temp_key.equals( "bit66" )                          )   // n 1
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit70" )                          )   // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "bit74" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit75" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit76" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit77" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit81" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit86" )                          )   // n 16
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit87" )                          )   // n 16
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit88" )                          )   // n 16
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit89" )                          )   // n 16
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit90" )                          )   // n 42
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 42, result_string_buffer);
      else if ( temp_key.equals( "bit96" )                          )   // an 16
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "bit97" )                          )   // x+n16 (=an17)
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 17, result_string_buffer);
      else if ( temp_key.equals( "bit100" )                          )   // n..11
      evaluate_data_integrity(temp_string, "strictly_numeric", "less_than_equal", 11, result_string_buffer);
      else if ( temp_key.equals( "bit116" )                          )   // ans..999
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 999, result_string_buffer);
      else if ( temp_key.equals( "bit130" )                          )   // n 14
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 14, result_string_buffer);
      else if ( temp_key.equals( "bit131" )                          )   // n 14
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 14, result_string_buffer);
      else if ( temp_key.equals( "bit132" )                          )   // n 14
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 14, result_string_buffer);
      else if ( temp_key.equals( "bit133" )                          )   // n 14
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 14, result_string_buffer);
      else if ( temp_key.equals( "bit134" )                          )   // an 2
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "bit135" )                          )   // an 2
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "bit136" )                          )   // an 8
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 8, result_string_buffer);
      else if ( temp_key.equals( "bit137" )                          )   // ans 8
      evaluate_data_integrity(temp_string, "anything", "exactly", 8, result_string_buffer);
      else if ( temp_key.equals( "bit138" )                          )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "bit139" )                          )   // an 1
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit140" )                          )   // n 14
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 14, result_string_buffer);
      else if ( temp_key.equals( "bit141" )                          )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "bit142" )                          )   // an 1
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit143" )                          )   // an 1
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit144" )                          )   // n 12
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "bit145" )                          )   // n 12
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "bit146" )                          )   // an 8
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 8, result_string_buffer);
      else if ( temp_key.equals( "bit147" )                          )   // n 12
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "bit148" )                          )   // an..16
      evaluate_data_integrity(temp_string, "alphanumeric", "less_than_equal", 16, result_string_buffer);
      else if ( temp_key.equals( "bit149" )                          )   // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "bit150" )                          )   // n 1
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit153" )                          )   // ans..99
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 99, result_string_buffer);
      else if ( temp_key.equals( "bit154" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit155" )                          )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit156" )                          )   // ans..27
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 27, result_string_buffer);
      else if ( temp_key.equals( "bit157" )                          )   // ans..99
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 99, result_string_buffer);
      else if ( temp_key.equals( "bit158" )                          )   // an 1
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit159" )                          )   // an 1
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "bit160" )                          )   // ans 8
      evaluate_data_integrity(temp_string, "anything", "exactly", 8, result_string_buffer);
      else if ( temp_key.equals( "bit161" )                          )   // ans 12
      evaluate_data_integrity(temp_string, "anything", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "bit162" )                          )   // ans..4
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 4, result_string_buffer);
      else if ( temp_key.equals( "bit165" )                          )   // ans..9
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 9, result_string_buffer);
      else if ( temp_key.equals( "bit166" )                          )   // an..19
      evaluate_data_integrity(temp_string, "alphanumeric", "less_than_equal", 19, result_string_buffer);
      else if ( temp_key.equals( "bit167" )                          )   // n..5
      evaluate_data_integrity(temp_string, "strictly_numeric", "less_than_equal", 5, result_string_buffer);
      else if ( temp_key.equals( "bit168" )                          )   // n 9
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 9, result_string_buffer);
      else if ( temp_key.equals( "bit169" )                          )   // an..28
      evaluate_data_integrity(temp_string, "alphanumeric", "less_than_equal", 28, result_string_buffer);
      else if ( temp_key.equals( "bit170" )                          )   // a 2
      evaluate_data_integrity(temp_string, "alphabetic", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "bit171" )                          )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "bit174" )                          )   // an 4
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "bit175" )                          )   // a 2
      evaluate_data_integrity(temp_string, "alphabetic", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "bit176" )                          )   // ans..10
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 10, result_string_buffer);
      else if ( temp_key.equals( "bit177" )                          )   // an 2
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "bit178" )                          )   // an 15
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 15, result_string_buffer);
      else if ( temp_key.equals( "bit179" )                          )   // ans..25
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 25, result_string_buffer);
      else if ( temp_key.equals( "bit180" )                          )   // n 14
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 14, result_string_buffer);
      else if ( temp_key.equals( "bit183" )                          )   // ans..28
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 28, result_string_buffer);
      else
      {
        result_string_buffer.append("StringNotRecongnized");
      }
      error_results.append( result_string_buffer );
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
      return  lResult;
    }
  }

  public long ValidateData(String key_string, String contents_string, StringBuffer error_results)
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("ValidateData()");

    GiveUp:try
    {
      //Begin method body
      String temp_key, temp_string;
      StringBuffer result_string_buffer;
      result_string_buffer = new StringBuffer();
      temp_key = key_string;
      temp_string = contents_string.toString();
      //  Attribute/Length
      //  System.out.println("The contents of temp_key = " + temp_key);
      if      ( temp_key.equals( "MessageType" )                       )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "PrimaryAccountNumber")               )   // n..19 (indicates up to 19)
      evaluate_data_integrity(temp_string, "strictly_numeric", "less_than_equal", 19, result_string_buffer);
      else if ( temp_key.equals( "ProcessingCode" )                    )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "TransactionAmount" )                 )   // n 12
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "TransactionDate&Time" )              )   // n 10
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 10, result_string_buffer);
      else if ( temp_key.equals( "SystemTraceAuditNumber" )            )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "LocalTransactionTime" )              )   // n 6
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "LocalTransactionDate" )            )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "ExpirationDate" )                   )   // n 4
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 4, result_string_buffer);
      else if ( temp_key.equals( "PointOfServiceEntryMode" )          )   // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "PointOfServiceConditionCode" )       )   // n 2
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 2, result_string_buffer);
      else if ( temp_key.equals( "Track2Data" )                       )   // z..37
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 37, result_string_buffer);
      else if ( temp_key.equals( "RetrievalReferenceNumber" )         )   // an 12
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "AuthorizationIdentificationResponse" ))  // an 6
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 6, result_string_buffer);
      else if ( temp_key.equals( "CardAcceptorTerminalIdentification"  ))  // ans 8
      evaluate_data_integrity(temp_string, "anything", "exactly", 8, result_string_buffer);
      else if ( temp_key.equals( "CardAcceptorIdentificationCode"      ))  // ans 15
      evaluate_data_integrity(temp_string, "anything", "exactly", 15, result_string_buffer);
      else if ( temp_key.equals( "CardAcceptorNameLocation"            ))  // ans 40
      evaluate_data_integrity(temp_string, "anything", "exactly", 40, result_string_buffer);
      else if ( temp_key.equals( "Track1Data"                          ))  // ans ..76
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 76, result_string_buffer);
      else if ( temp_key.equals( "PersonalIdentificationNumberData"    ))  // an 16
      evaluate_data_integrity(temp_string, "alphanumeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "SecurityRelatedControlInformation"   ))  // n 16
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 16, result_string_buffer);
      else if ( temp_key.equals( "AuthorizationLifeCycle"              ))  // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "PrivateDataForISP"                   ))  // ans..99
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 99, result_string_buffer);
      else if ( temp_key.equals( "ANSIAdditionalTraceData"             ))  // ans...999
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 999, result_string_buffer);
      else if ( temp_key.equals( "CashbackAmount"                      ))  // n 12
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 12, result_string_buffer);
      else if ( temp_key.equals( "EmployeeNumber"                      ))  // an..16
      evaluate_data_integrity(temp_string, "alphanumeric", "less_than_equal", 16, result_string_buffer);
      else if ( temp_key.equals( "ControllerType"                      ))  // n 3
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 3, result_string_buffer);
      else if ( temp_key.equals( "CheckType"                           ))  // n 1
      evaluate_data_integrity(temp_string, "strictly_numeric", "exactly", 1, result_string_buffer);
      else if ( temp_key.equals( "ManualVoucherNumber"                 ))  // ans..28
      evaluate_data_integrity(temp_string, "anything", "less_than_equal", 28, result_string_buffer);
      else
      result_string_buffer.append("StringNotRecongnized");
      error_results.append( result_string_buffer );
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
      return  lResult;
    }
  }

  public long evaluate_data_integrity ( String string_input, String string_rules, String exactness, int str_size, StringBuffer accumulated_error_string)
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("evaluate_data_integrity()");

    GiveUp:try
    {
      //Begin method body
      int i, string_length, length_of_string_plus_one, sub_string_index;
      boolean break_flag;
      char data_char_array[];
      StringBuffer char_quality;
      char_quality = new StringBuffer();
      string_length = string_input.length();
      length_of_string_plus_one = string_length + 1;
      data_char_array = string_input.toCharArray();
      string_input.getChars( 0, string_length, data_char_array, 0);

      if ( !( string_rules.equals( "strictly_numeric" ) ) && !( string_rules.equals( "alphanumeric" ) ) && !( string_rules.equals( "alphabetic" ) ) && !( string_rules.equals( "anything" ) ) )
      accumulated_error_string.append( "evaluate_data_integrity improperly invoked, invalid string_rules entry" );
      if ( !( exactness.equals( "exactly" ) ) && !( exactness.equals( "less_than_equal" ) ) )
      accumulated_error_string.append( "evaluate_data_integrity improperly invoked, exactness argument must be either \"exactly\" or \"less_than_equal\"" );
      if ( !( accumulated_error_string.equals( "" ) ) )
      return 123023;
      break_flag = false;
      i = 0;
      while ( ( i<string_length ) && ( break_flag == false ) )
      {
        validate_character( string_rules, data_char_array[i], char_quality );
        if ( char_quality.equals( "false" ) )
        {
          if ( string_rules != "anything" )
          {
            accumulated_error_string.append( "not_" );
            accumulated_error_string.append( string_rules );
          }
          break_flag = true;
        }
        i++;
      }
      if ( exactness.equals( "exactly" ) )
      {
        if ( string_length != str_size )
        {
          accumulated_error_string.append( "String must be exactly " ) ;
          accumulated_error_string.append( str_size                  ) ;
          accumulated_error_string.append( " characters."            ) ;
        }
      }
      else if ( exactness.equals( "less_than_equal" ) )
      {
        if ( string_length > str_size )
        {
          accumulated_error_string.append( "String must be less than or equal to " ) ;
          accumulated_error_string.append( str_size                                ) ;
          accumulated_error_string.append( " characters."                          ) ;
        }
      }
      if ( accumulated_error_string.equals ( "" ) )
      accumulated_error_string.append( "STRING_VALID" );
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
      return  lResult;
    }
  }


    // Note on formal parameters of following method:
    //    Because the valid range of the StringBuffer char_validity is { "true", "false" }, it
    //    would thus be preferable to declare char_validity to be type boolean.
  //    However, since it's not possible to pass a modified boolean out of a method
  //    the StringBuffer type is used instead.  This type can modify its contents within a method
  //    with the .append function.          -timf 02-09-00

  public long validate_character( String char_constraints, char data_char, StringBuffer char_validity )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("validate_character()");

    GiveUp:try
    {
      //Begin method body
      if      ( ( char_constraints.equals( "strictly_numeric" ) ) && ( Character.isDigit( data_char ) ) )
      char_validity.append( "true" );
      else if ( ( char_constraints == "alphanumeric"     ) && ( Character.isLetterOrDigit( data_char ) ) )
      char_validity.append( "true" );
      else if ( ( char_constraints == "alphabetic"   ) && ( Character.isLetter( data_char ) ) )
      char_validity.append( "true" );
      else if ( char_constraints == "anything"         )
      char_validity.append( "true" );
      else
      char_validity.append( "false" );
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

  public long Obtain_hash_table_of_errors( StringBuffer hash_err_diagnostics, Hashtable hsh_table_data, Hashtable hsh_table_of_errors )
  {
    long lResult = JUtils.RESULT_FAILURE;
    dbg.Enter("Obtain_hash_table_of_errors()");

    GiveUp:try
    {
      //Begin method body
      // This function call should return an appended StringBuffer, hash_err_diagnositics
      //    that specifies whether the entire
      //    hash table is free from any data errors (by returning the
      //    string "Hash table data valid") or returning the string
      //    "Errors in hash table" along with a completed hsh_table_of_errors.
      //
      boolean errors_found;
      String temp_keyx, error_contents;
      StringBuffer error_rpt;
      errors_found = false;
      for ( Enumeration enum = hsh_table_data.keys(); enum.hasMoreElements(); )
      {
        temp_keyx = enum.nextElement().toString() ;
        Object tempstringx = hsh_table_data.get( temp_keyx );
        error_rpt = new StringBuffer();
        ValidateData(temp_keyx.toString(), tempstringx.toString(), error_rpt);
        error_contents = error_rpt.toString();
        if ( ! ( error_contents.equals( "STRING_VALID" ) ) )
        errors_found = true;
        Object val = hsh_table_of_errors.put( temp_keyx, error_contents );
      }
      if ( errors_found )
      hash_err_diagnostics.append( "Errors in hash table" );
      else
      hash_err_diagnostics.append( "Hash table data valid" );
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


  public long convert_long_name_to_iso_field_name( String long_name, StringBuffer iso_field_name )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("convert_long_name_to_iso_field_name()");

    GiveUp:try
    {
      //Begin method body
      if      ( long_name.equals ("MessageLength"                      ) ) iso_field_name.append( "len"  );
      else if ( long_name.equals ("IsoPrimaryBitMap"                   ) ) iso_field_name.append( "iso1" );
      else if ( long_name.equals ("IsoSecondaryBitMap"                 ) ) iso_field_name.append( "iso2" );
      else if ( long_name.equals ("IsoExtendedBitMap"                  ) ) iso_field_name.append( "iso3" );
      else if ( long_name.equals ("MessageType"                        ) ) iso_field_name.append( "tran" );
      else if ( long_name.equals ("PrimaryAccountNumber"               ) ) iso_field_name.append( "bit2"  );
      else if ( long_name.equals ("ProcessingCode"                     ) ) iso_field_name.append( "bit3"  );
      else if ( long_name.equals ("TransactionAmount"                  ) ) iso_field_name.append( "bit4"  );
      else if ( long_name.equals ("TransactionDate&Time"               ) ) iso_field_name.append( "bit7"  );
      else if ( long_name.equals ("SystemTraceAuditNumber"             ) ) iso_field_name.append( "bit11" );
      else if ( long_name.equals ("LocalTransactionTime"               ) ) iso_field_name.append( "bit12" );
      else if ( long_name.equals ("LocalTransactionDate"               ) ) iso_field_name.append( "bit13" );
      else if ( long_name.equals ("ExpirationDate"                     ) ) iso_field_name.append( "bit14" );
      else if ( long_name.equals ("SettlementDate"                     ) ) iso_field_name.append( "bit15" );
      else if ( long_name.equals ("MerchantType"                       ) ) iso_field_name.append( "bit18" );
      else if ( long_name.equals ("PointOfServiceEntryMode"            ) ) iso_field_name.append( "bit22" );
      else if ( long_name.equals ("PointOfServiceConditionCode"        ) ) iso_field_name.append( "bit25" );
      else if ( long_name.equals ("Track2Data"                         ) ) iso_field_name.append( "bit35" );
      else if ( long_name.equals ("RetrievalReferenceNumber"           ) ) iso_field_name.append( "bit37" );
      else if ( long_name.equals ("AuthorizationIdentificationResponse") ) iso_field_name.append( "bit38" );
      else if ( long_name.equals ("ResponseCode"                       ) ) iso_field_name.append( "bit39" );
      else if ( long_name.equals ("CardAcceptorTerminalIdentification" ) ) iso_field_name.append( "bit41" );
      else if ( long_name.equals ("CardAcceptorIdentificationCode"     ) ) iso_field_name.append( "bit42" );
      else if ( long_name.equals ("CardAcceptorNameLocation"           ) ) iso_field_name.append( "bit43" );
      else if ( long_name.equals ("Track1Data"                         ) ) iso_field_name.append( "bit45" );
      else if ( long_name.equals ("AdditionalDataPrivate"              ) ) iso_field_name.append( "bit48" );
      else if ( long_name.equals ("PersonalIdentificationNumberData"   ) ) iso_field_name.append( "bit52" );
      else if ( long_name.equals ("SecurityRelatedControlInformation"  ) ) iso_field_name.append( "bit53" );
      else if ( long_name.equals ("AdditionalAmount"                   ) ) iso_field_name.append( "bit54" );
      else if ( long_name.equals ("AuthorizationLifeCycle"             ) ) iso_field_name.append( "bit57" );
      else if ( long_name.equals ("PrivateDataForISP"                  ) ) iso_field_name.append( "bit63" );
      else if ( long_name.equals ("SettlementCode"                     ) ) iso_field_name.append( "bit66" );
      else if ( long_name.equals ("NetworkManagementInformationCode"   ) ) iso_field_name.append( "bit70" );
      else if ( long_name.equals ("TotalNumberCredits"                 ) ) iso_field_name.append( "bit74" );
      else if ( long_name.equals ("TotalNumberCreditRevesals"          ) ) iso_field_name.append( "bit75" );
      else if ( long_name.equals ("TotalNumberDebits"                  ) ) iso_field_name.append( "bit76" );
      else if ( long_name.equals ("TotalNumberDebitReversals"          ) ) iso_field_name.append( "bit77" );
      else if ( long_name.equals ("TotalNumberAuthorizations"          ) ) iso_field_name.append( "bit81" );
      else if ( long_name.equals ("TotalAmountCredits"                 ) ) iso_field_name.append( "bit86" );
      else if ( long_name.equals ("TotalAmountCreditReversals"         ) ) iso_field_name.append( "bit87" );
      else if ( long_name.equals ("TotalAmountDebits"                  ) ) iso_field_name.append( "bit88" );
      else if ( long_name.equals ("TotalAmountDebitReversals"          ) ) iso_field_name.append( "bit89" );
      else if ( long_name.equals ("OriginalDataElements"               ) ) iso_field_name.append( "bit90" );
      else if ( long_name.equals ("MessageSecurityCode"                ) ) iso_field_name.append( "bit96" );
      else if ( long_name.equals ("NetSettlementAmount"                ) ) iso_field_name.append( "bit97" );
      else if ( long_name.equals ("ReceivingInstitutionIDCode"         ) ) iso_field_name.append( "bit100");
      else if ( long_name.equals ("ANSIAdditionalTraceData"            ) ) iso_field_name.append( "bit116");
      else if ( long_name.equals ("TransStartTime"                     ) ) iso_field_name.append( "bit130");
      else if ( long_name.equals ("TransEndTime"                       ) ) iso_field_name.append( "bit131");
      else if ( long_name.equals ("AuthorizationStartTime"             ) ) iso_field_name.append( "bit132");
      else if ( long_name.equals ("AuthorizationEndTime"               ) ) iso_field_name.append( "bit133");
      else if ( long_name.equals ("PaymentTypeCode"                    ) ) iso_field_name.append( "bit134");
      else if ( long_name.equals ("TransactionType"                    ) ) iso_field_name.append( "bit135");
      else if ( long_name.equals ("EventNumber"                        ) ) iso_field_name.append( "bit136");
      else if ( long_name.equals ("ServerName"                         ) ) iso_field_name.append( "bit137");
      else if ( long_name.equals ("OriginalSTAN"                       ) ) iso_field_name.append( "bit138");
      else if ( long_name.equals ("StandInIndicator"                   ) ) iso_field_name.append( "bit139");
      else if ( long_name.equals ("LogToDiskTimer"                     ) ) iso_field_name.append( "bit140");
      else if ( long_name.equals ("BatchNumber"                        ) ) iso_field_name.append( "bit141");
      else if ( long_name.equals ("ActionCode"                         ) ) iso_field_name.append( "bit142");
      else if ( long_name.equals ("DemoModeIndicator"                  ) ) iso_field_name.append( "bit143");
      else if ( long_name.equals ("TransGTRID"                         ) ) iso_field_name.append( "bit144");
      else if ( long_name.equals ("OriginatingPID"                     ) ) iso_field_name.append( "bit145");
      else if ( long_name.equals ("OriginateNode"                      ) ) iso_field_name.append( "bit146");
      else if ( long_name.equals ("CashbackAmount"                     ) ) iso_field_name.append( "bit147");
      else if ( long_name.equals ("EmployeeNumber"                     ) ) iso_field_name.append( "bit148");
      else if ( long_name.equals ("ControllerType"                     ) ) iso_field_name.append( "bit149");
      else if ( long_name.equals ("CheckType"                          ) ) iso_field_name.append( "bit150");
      else if ( long_name.equals ("MessageMap"                         ) ) iso_field_name.append( "bit153");
      else if ( long_name.equals ("ReconcilliationType"                ) ) iso_field_name.append( "bit154");
      else if ( long_name.equals ("IndividualTotals"                   ) ) iso_field_name.append( "bit155");
      else if ( long_name.equals ("KeyManagementInformation"           ) ) iso_field_name.append( "bit156");
      else if ( long_name.equals ("DriversLicense"                     ) ) iso_field_name.append( "bit157");
      else if ( long_name.equals ("TimeOutIndicator"                   ) ) iso_field_name.append( "bit158");
      else if ( long_name.equals ("LateResponseIndicator"              ) ) iso_field_name.append( "bit159");
      else if ( long_name.equals ("AuthorizerServer"                   ) ) iso_field_name.append( "bit160");
      else if ( long_name.equals ("AuthorizerName"                     ) ) iso_field_name.append( "bit161");
      else if ( long_name.equals ("EmployeePIN"                        ) ) iso_field_name.append( "bit162");
      else if ( long_name.equals ("LogonTime"                          ) ) iso_field_name.append( "bit163");
      else if ( long_name.equals ("LogonDate"                          ) ) iso_field_name.append( "bit164");
      else if ( long_name.equals ("CheckRouting"                       ) ) iso_field_name.append( "bit165");
      else if ( long_name.equals ("CheckingAccountNumber"              ) ) iso_field_name.append( "bit166");
      else if ( long_name.equals ("CheckNumber"                        ) ) iso_field_name.append( "bit167");
      else if ( long_name.equals ("SocialSecurityNumber"               ) ) iso_field_name.append( "bit168");
      else if ( long_name.equals ("OtherCardNumber"                    ) ) iso_field_name.append( "bit169");
      else if ( long_name.equals ("IdentificationType"                 ) ) iso_field_name.append( "bit170");
      else if ( long_name.equals ("ResubmittalCount"                   ) ) iso_field_name.append( "bit171");
      else if ( long_name.equals ("HostSettlemnetDate"                 ) ) iso_field_name.append( "bit172");
      else if ( long_name.equals ("HostProcessingCode"                 ) ) iso_field_name.append( "bit173");
      else if ( long_name.equals ("SettleInstID"                       ) ) iso_field_name.append( "bit174");
      else if ( long_name.equals ("StateCode"                          ) ) iso_field_name.append( "bit175");
      else if ( long_name.equals ("ZipCode"                            ) ) iso_field_name.append( "bit176");
      else if ( long_name.equals ("HostResponseCode"                   ) ) iso_field_name.append( "bit177");
      else if ( long_name.equals ("MerchantID"                         ) ) iso_field_name.append( "bit178");
      else if ( long_name.equals ("StoreAuthData"                      ) ) iso_field_name.append( "bit179");
      else if ( long_name.equals ("SwitchDateTime"                     ) ) iso_field_name.append( "bit180");
      else if ( long_name.equals ("CourtesyCardNumber"                 ) ) iso_field_name.append( "bit181");
      else if ( long_name.equals ("StoreID"                            ) ) iso_field_name.append( "bit182");
      else if ( long_name.equals ("ManualVoucherNumber"                ) ) iso_field_name.append( "bit183");
      else                                   iso_field_name.append( "Unknown_long_name_string_input");
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


  public long convert_iso_field_name_to_long_name( String iso_field_name, StringBuffer long_name )
  {
    long lResult = JUtils.RESULT_FAILURE;

    dbg.Enter("convert_iso_field_name_to_long_name()");

    GiveUp:try
    {
      //Begin method body
      if      ( iso_field_name == "len"   ) long_name.append( "MessageLength"                      ) ;
      else if ( iso_field_name == "iso1"  ) long_name.append( "IsoPrimaryBitMap"                   ) ;
      else if ( iso_field_name == "iso2"  ) long_name.append( "IsoSecondaryBitMap"                 ) ;
      else if ( iso_field_name == "iso3"  ) long_name.append( "IsoExtendedBitMap"                  ) ;
      else if ( iso_field_name == "tran"  ) long_name.append( "TransactionType"                    ) ;
      else if ( iso_field_name == "bit2"  ) long_name.append( "PrimaryAccountNumber"               ) ;
      else if ( iso_field_name == "bit3"  ) long_name.append( "ProcessingCode"                     ) ;
      else if ( iso_field_name == "bit4"  ) long_name.append( "Transaction Amount"                 ) ;
      else if ( iso_field_name == "bit7"  ) long_name.append( "Trasmission Date & Time"            ) ;
      else if ( iso_field_name == "bit11" ) long_name.append( "SystemTraceAuditNumber"             ) ;
      else if ( iso_field_name == "bit12" ) long_name.append( "LocalTransaction Time"              ) ;
      else if ( iso_field_name == "bit13" ) long_name.append( "LocalTransaction Date"              ) ;
      else if ( iso_field_name == "bit14" ) long_name.append( "ExpirationDate"                     ) ;
      else if ( iso_field_name == "bit15" ) long_name.append( "SettlementDate"                     ) ;
      else if ( iso_field_name == "bit18" ) long_name.append( "MerchantType"                       ) ;
      else if ( iso_field_name == "bit22" ) long_name.append( "PointOfServiceEntryMode"            ) ;
      else if ( iso_field_name == "bit25" ) long_name.append( "PointOfServiceConditionCode"        ) ;
      else if ( iso_field_name == "bit35" ) long_name.append( "Track 2Data"                        ) ;
      else if ( iso_field_name == "bit37" ) long_name.append( "RetrievalReferenceNumber"           ) ;
      else if ( iso_field_name == "bit38" ) long_name.append( "AuthorizationIdentificationResponse") ;
      else if ( iso_field_name == "bit39" ) long_name.append( "ResponseCode"                       ) ;
      else if ( iso_field_name == "bit41" ) long_name.append( "Card AcceptorTerninalIdentification") ;
      else if ( iso_field_name == "bit42" ) long_name.append( "CardAcceptorIdentificationCode"     ) ;
      else if ( iso_field_name == "bit43" ) long_name.append( "CardAcceptor Name/Location"         ) ;
      else if ( iso_field_name == "bit45" ) long_name.append( "Track1Data"                         ) ;
      else if ( iso_field_name == "bit48" ) long_name.append( "Additional DataPrivate"             ) ;
      else if ( iso_field_name == "bit52" ) long_name.append( "PersonalIdentificationNumberData"   ) ;
      else if ( iso_field_name == "bit53" ) long_name.append( "SecurityRelatedControlInformation"  ) ;
      else if ( iso_field_name == "bit54" ) long_name.append( "AdditionalAmount"                   ) ;
      else if ( iso_field_name == "bit57" ) long_name.append( "AuthorizationLifeCycle"             ) ;
      else if ( iso_field_name == "bit63" ) long_name.append( "PrivateDataForISP"                  ) ;
      else if ( iso_field_name == "bit66" ) long_name.append( "Settlement Code"                    ) ;
      else if ( iso_field_name == "bit70" ) long_name.append( "NetworkManagementInformationCode"   ) ;
      else if ( iso_field_name == "bit74" ) long_name.append( "Total Number Credits"               ) ;
      else if ( iso_field_name == "bit75" ) long_name.append( "TotalNumberCreditRevesals"          ) ;
      else if ( iso_field_name == "bit76" ) long_name.append( "TotalNumberDebits"                  ) ;
      else if ( iso_field_name == "bit77" ) long_name.append( "TotalNumberDebitReversals"          ) ;
      else if ( iso_field_name == "bit81" ) long_name.append( "TotalNumberAuthorizations"          ) ;
      else if ( iso_field_name == "bit86" ) long_name.append( "TotalAmountCredits"                 ) ;
      else if ( iso_field_name == "bit87" ) long_name.append( "TotalAmountCreditReversals"         ) ;
      else if ( iso_field_name == "bit88" ) long_name.append( "TotalAmountDebits"                  ) ;
      else if ( iso_field_name == "bit89" ) long_name.append( "TotalAmountDebitReversals"          ) ;
      else if ( iso_field_name == "bit90" ) long_name.append( "OriginalData lements"               ) ;
      else if ( iso_field_name == "bit96" ) long_name.append( "Message ecurityCode"                ) ;
      else if ( iso_field_name == "bit97" ) long_name.append( "NetSettlementAmount"                ) ;
      else if ( iso_field_name == "bit100") long_name.append( "ReceivingInstitutionIDCode"         ) ;
      else if ( iso_field_name == "bit116") long_name.append( "ANSIAdditionalTraceData 2"          ) ;
      else if ( iso_field_name == "bit130") long_name.append( "TransStartTime"                     ) ;
      else if ( iso_field_name == "bit131") long_name.append( "TransEndTime"                       ) ;
      else if ( iso_field_name == "bit132") long_name.append( "Authorization StartTime"            ) ;
      else if ( iso_field_name == "bit133") long_name.append( "Authorization EndTime"              ) ;
      else if ( iso_field_name == "bit134") long_name.append( "PaymentTypeCode"                    ) ;
      else if ( iso_field_name == "bit135") long_name.append( "Transaction Type"                   ) ;
      else if ( iso_field_name == "bit136") long_name.append( "EventNumber"                        ) ;
      else if ( iso_field_name == "bit137") long_name.append( "ServerName"                         ) ;
      else if ( iso_field_name == "bit138") long_name.append( "Original STAN"                      ) ;
      else if ( iso_field_name == "bit139") long_name.append( "StandInIndicator"                   ) ;
      else if ( iso_field_name == "bit140") long_name.append( "LogToDiskTimer"                     ) ;
      else if ( iso_field_name == "bit141") long_name.append( "BatchNumber"                        ) ;
      else if ( iso_field_name == "bit142") long_name.append( "ActionCode"                         ) ;
      else if ( iso_field_name == "bit143") long_name.append( "DemoModeIndicator"                  ) ;
      else if ( iso_field_name == "bit144") long_name.append( "TransGTRID"                         ) ;
      else if ( iso_field_name == "bit145") long_name.append( "OriginatingPID"                     ) ;
      else if ( iso_field_name == "bit146") long_name.append( "OriginateNode"                      ) ;
      else if ( iso_field_name == "bit147") long_name.append( "CashbackAmount"                     ) ;
      else if ( iso_field_name == "bit148") long_name.append( "EmployeeNumber"                     ) ;
      else if ( iso_field_name == "bit149") long_name.append( "ControllerType"                     ) ;
      else if ( iso_field_name == "bit150") long_name.append( "CheckType"                          ) ;
      else if ( iso_field_name == "bit153") long_name.append( "MessageMap"                         ) ;
      else if ( iso_field_name == "bit154") long_name.append( "ReconcilliationType"                ) ;
      else if ( iso_field_name == "bit155") long_name.append( "IndividualTotals"                   ) ;
      else if ( iso_field_name == "bit156") long_name.append( "KeyManagementInformation"           ) ;
      else if ( iso_field_name == "bit157") long_name.append( "DriversLicense"                     ) ;
      else if ( iso_field_name == "bit158") long_name.append( "TimeOutIndicator"                   ) ;
      else if ( iso_field_name == "bit159") long_name.append( "LateResponseIndicator"              ) ;
      else if ( iso_field_name == "bit160") long_name.append( "AuthorizerServer"                   ) ;
      else if ( iso_field_name == "bit161") long_name.append( "AuthorizerName"                     ) ;
      else if ( iso_field_name == "bit162") long_name.append( "EmployeePIN"                        ) ;
      else if ( iso_field_name == "bit163") long_name.append( "LogonTime"                          ) ;
      else if ( iso_field_name == "bit164") long_name.append( "LogonDate"                          ) ;
      else if ( iso_field_name == "bit165") long_name.append( "CheckRouting"                       ) ;
      else if ( iso_field_name == "bit166") long_name.append( "CheckingAccountNumber"              ) ;
      else if ( iso_field_name == "bit167") long_name.append( "CheckNumber"                        ) ;
      else if ( iso_field_name == "bit168") long_name.append( "SocialSecurityNumber"               ) ;
      else if ( iso_field_name == "bit169") long_name.append( "OtherCardNumber"                    ) ;
      else if ( iso_field_name == "bit170") long_name.append( "IdentificationType"                 ) ;
      else if ( iso_field_name == "bit171") long_name.append( "ResubmittalCount"                   ) ;
      else if ( iso_field_name == "bit172") long_name.append( "HostSettlemnetDate"                 ) ;
      else if ( iso_field_name == "bit173") long_name.append( "HostProcessingCode"                 ) ;
      else if ( iso_field_name == "bit174") long_name.append( "SettleInstID"                       ) ;
      else if ( iso_field_name == "bit175") long_name.append( "StateCode"                          ) ;
      else if ( iso_field_name == "bit176") long_name.append( "ZipCode"                            ) ;
      else if ( iso_field_name == "bit177") long_name.append( "HostResponseCode"                   ) ;
      else if ( iso_field_name == "bit178") long_name.append( "MerchantID"                         ) ;
      else if ( iso_field_name == "bit179") long_name.append( "StoreAuthData"                      ) ;
      else if ( iso_field_name == "bit180") long_name.append( "SwitchDateTime"                     ) ;
      else if ( iso_field_name == "bit181") long_name.append( "CourtesyCardNumber"                 ) ;
      else if ( iso_field_name == "bit182") long_name.append( "StoreID"                            ) ;
      else if ( iso_field_name == "bit183") long_name.append( "ManualVoucherNumber"                ) ;
      else                                  long_name.append( "Unknown_iso_field_name_string_input") ;
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
      return   lResult ;
    }

  }
}
//$Id: CISOMessage.java,v 1.2 2000/03/30 13:48:27 anonymous Exp $
