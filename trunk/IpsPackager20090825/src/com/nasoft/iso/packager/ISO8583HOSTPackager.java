package com.nasoft.iso.packager;

import com.nasoft.iso.IFA_AMOUNT;
import com.nasoft.iso.IFA_LLCHAR;
import com.nasoft.iso.IFA_LLLBINARY;
import com.nasoft.iso.IFA_LLLCHAR;
import com.nasoft.iso.IFA_LLNUM;
import com.nasoft.iso.IFA_NUMERIC;
import com.nasoft.iso.IFB_BINARY;
import com.nasoft.iso.IFB_BITMAP;
import com.nasoft.iso.IF_CHAR;
import com.nasoft.iso.ISOBasePackager;
import com.nasoft.iso.ISOComponent;
import com.nasoft.iso.ISOFieldPackager;
import com.nasoft.iso.ISOPackager;

/**
 * ISO 8583 v1993 Binary Packager<br>
 * <b>WARNING UNTESTED</b>
 * 
 * @author <a href="mailto:u_arunkumar@yahoo.com">Arun Kumar U</a>
 * @author <a href="mailto:apr@cs.com.uy">Alejandro P. Revilla</a>
 * @version $Id: ISO93BPackager.java,v 1.4 2003/05/16 04:15:15 alwyns Exp $
 * @see ISOPackager
 * @see ISOBasePackager
 * @see ISOComponent
 */
public class ISO8583HOSTPackager extends ISOBasePackager
{
  // private static final boolean pad = false;
  protected ISOFieldPackager fld[] = {
      /* 000 */new IFA_NUMERIC(0, "Message Type Indicator"),
      /* 001 */new IFB_BITMAP(16, "Bitmap"),
      /* 002 */new IF_CHAR(19, "���˺�/����"),
      /* 003 */new IFA_NUMERIC(12, "not use"),
      /* 004 */new IFA_NUMERIC(12, "���׽��"),
      /* 005 */new IFA_NUMERIC(8, "������ˮ��"),
      /* 006 */new IF_CHAR(10, "�ͻ���"),
      /* 007 */new IFA_NUMERIC(10, "Date and time, transmission"),
      /* 008 */new IFA_NUMERIC(8, "Amount, Cardholder billing fee"),
      /* 009 */new IFA_NUMERIC(8, "�ļ���������"),
      /* 010 */new IFA_NUMERIC(8, "����Ч��"),
      /* 011 */new IFA_NUMERIC(8, "��ˮ�ſ�"),
      /* 012 */new IFA_NUMERIC(6, "����ʱ��"),//
      /* 013 */new IFA_NUMERIC(8, "��������"),
      /* 014 */new IFA_NUMERIC(8, "��������"),
      /* 015 */new IF_CHAR(16, "���ױ�־"),//
      /* 016 */new IFA_NUMERIC(2, "����"),
      /* 017 */new IFA_NUMERIC(12, "��۽��"),
      /* 018 */new IF_CHAR(4, "�̻�����"),
      /* 019 */new IF_CHAR(2, "��ʧ����"),
      /* 020 */new IF_CHAR(6, "ժҪ"),
      /* 021 */new IFA_NUMERIC(8, "��Ϣ��"),
      /* 022 */new IF_CHAR(3, "�������뷽ʽ"),
      /* 023 */new IFA_NUMERIC(3, "Card sequence number"),
      /* 024 */new IFA_NUMERIC(3, "Function code"),
      /* 025 */new IF_CHAR(2, "�������������"),//
      /* 026 */new IF_CHAR(12, "ҵ����������ȡ��ʽ"),
      /* 027 */new IFA_NUMERIC(3, "�����������ȡ����"),
      /* 028 */new IFA_NUMERIC(8, "����ϵͳ���������"),//
      /* 029 */new IFA_NUMERIC(8, "ҵ��������"),
      /* 030 */new IFA_NUMERIC(8, "ҵ��Ԥ��������"),
      /* 031 */new IFA_LLCHAR(99, "Acquirer reference data"),
      /* 032 */new IF_CHAR(11, "����ϵͳ����"),
      /* 033 */new IF_CHAR(11, "����ϵͳ����"),
      /* 034 */new IFA_LLCHAR(28, "Primary account number, extended"),
      /* 035 */new IFA_LLCHAR(37, "�ڶ��ŵ�����"),
      /* 036 */new IFA_LLLCHAR(104, "�����ŵ�����"),
      /* 037 */new IF_CHAR(12, "ϵͳ������"),
      /* 038 */new IF_CHAR(16, "��Ȩ��/ֹ����"),
      /* 039 */new IF_CHAR(7, "��Ӧ��"),
      /* 040 */new IF_CHAR(5, "�����ִ���"),
      /* 041 */new IF_CHAR(8, "�ն˱�ʶ��"),
      /* 042 */new IF_CHAR(15, "���׾ִ���/�ܿ�����ʶ��"),
      /* 043 */new IF_CHAR(32, "��Ӧ�븽����Ϣ"),//
      /* 044 */new IFA_LLCHAR(40, "����"),//
      /* 045 */new IFA_LLLCHAR(512, "��������"),//
      /* 046 */new IF_CHAR(10, "����ӡˢ��"),
      /* 047 */new IF_CHAR(1, "�ۿ��"),
      /* 048 */new IF_CHAR(12, "���������/ATM���Ǯ����"),//
      /* 049 */new IF_CHAR(3, "���׻��Ҵ���"),
      /* 050 */new IF_CHAR(3, "Currency code, Settlement"),
      /* 051 */new IF_CHAR(6, "��Ա��"),
      /* 052 */new IF_CHAR(16, "����ʶ��ţ�PIN��"), ///
      /* 053 */new IFA_NUMERIC(16, "��ȫ������Ϣ"),
      /* 054 */new IF_CHAR(62, "�������"),//
      /* 055 */new IFA_LLLBINARY(255, "IC card system related data"),
      /* 056 */new IFA_LLNUM(35, "Original data elements"),
      /* 057 */new IFA_LLLCHAR(100, "����˽������"),//
      /* 058 */new IFA_LLLCHAR(100, "IC(PBOC) Data Reserved"), //
      /* 059 */new IFA_LLLCHAR(30, "��չ��Ϣԭ����"), //
      /* 060 */new IF_CHAR(4, "��Ϣԭ����"), //
      /* 061 */new IF_CHAR(22, "֤����Ϣ"), //
      /* 062 */new IF_CHAR(16, "���Ӹ��˱�ʶ������"), //
      /* 063 */new IFA_LLLCHAR(200, "Finacial Network Data "), //
      /* 064 */new IFB_BINARY(8, "Message authentication code field"),
      /* 065 */new IFB_BINARY(8, "Reserved for ISO use"),
      /* 066 */new IF_CHAR(1, "����Ӧ�����"), //
      /* 067 */new IFA_NUMERIC(22, "ί�л����ֻ���"),
      /* 068 */new IFA_LLCHAR(40, "�������ƺ͵�ַ"),
      /* 069 */new IFA_NUMERIC(3, "Country code, settlement institution"),
      /* 070 */new IFA_NUMERIC(3, "Network Management Information Code"), //
      /* 071 */new IFA_NUMERIC(8, "Message number"),
      /* 072 */new IF_CHAR(24, "�˻�״̬"),
      /* 073 */new IFA_NUMERIC(6, "Date, action"),
      /* 074 */new IF_CHAR(128, "֧Ʊ��ϸ��Ϣ"),
      /* 075 */new IFA_NUMERIC(10, "Credits, reversal number"),
      /* 076 */new IFA_NUMERIC(10, "Debits, number"),
      /* 077 */new IFA_NUMERIC(10, "Debits, reversal number"),
      /* 078 */new IFA_NUMERIC(10, "Transfer, number"),
      /* 079 */new IFA_NUMERIC(10, "Transfer, reversal number"),
      /* 080 */new IFA_NUMERIC(10, "Inquiries, number"),
      /* 081 */new IFA_NUMERIC(10, "Authorizations, number"),
      /* 082 */new IFA_NUMERIC(12, "Processing Fee Amount Of Credits"), //
      /* 083 */new IFA_NUMERIC(10, "Payments, number"),
      /* 084 */new IFA_NUMERIC(12, "Processing Fee Amount Of Debits"), //
      /* 085 */new IFA_NUMERIC(10, "Fee collections, number"),
      /* 086 */new IFA_NUMERIC(16, "Credits, amount"),
      /* 087 */new IFA_NUMERIC(16, "Credits, reversal amount"),
      /* 088 */new IFA_NUMERIC(16, "Debits, amount"),
      /* 089 */new IFA_NUMERIC(16, "Debits, reversal amount"),
      /* 090 */new IF_CHAR(62, "ԭʼ������Ϣ"), //
      /* 091 */new IFA_NUMERIC(3, "Country code, transaction Dest. Inst."),
      /* 092 */new IFA_NUMERIC(3, "Country code, transaction Orig. Inst."),
      /* 093 */new IFA_LLNUM(11, "Transaction Dest. Inst. Id code"),
      /* 094 */new IFA_LLNUM(11, "Transaction Orig. Inst. Id code"),
      /* 095 */new IF_CHAR(42, "CReplacement Amounts"), //
      /* 096 */new IF_CHAR(16, "���İ�ȫ��"), //
      /* 097 */new IFA_AMOUNT(1 + 16, "Amount, Net reconciliation"),
      /* 098 */new IF_CHAR(25, "Payee"),
      /* 099 */new IFA_LLCHAR(11, "Settlement institution Id code"),
      /* 100 */new IFA_LLNUM(11, "Receiving institution Id code"),
      /* 101 */new IFA_LLCHAR(17, "File name"),
      /* 102 */new IF_CHAR(19, "�����˺�"),
      /* 103 */new IF_CHAR(19, "ת���˺�"),
      /* 104 */new IFA_LLLCHAR(100, "Transaction description"),
      /* 105 */new IFA_NUMERIC(16, "Credits, Chargeback amount"),
      /* 106 */new IFA_NUMERIC(16, "Debits, Chargeback amount"),
      /* 107 */new IFA_NUMERIC(10, "Credits, Chargeback number"),
      /* 108 */new IFA_NUMERIC(10, "Debits, Chargeback number"),
      /* 109 */new IFA_LLCHAR(84, "Credits, Fee amounts"),
      /* 110 */new IFA_LLCHAR(84, "Debits, Fee amounts"),
      /* 111 */new IFA_LLLCHAR(999, "Reserved for ISO use"),
      /* 112 */new IFA_LLLCHAR(999, "Reserved for ISO use"),
      /* 113 */new IFA_LLLCHAR(999, "Reserved for ISO use"),
      /* 114 */new IFA_LLLCHAR(999, "Reserved for ISO use"),
      /* 115 */new IFA_LLLCHAR(999, "Reserved for ISO use"),
      /* 116 */new IFA_LLLCHAR(999, "Reserved for national use"),
      /* 117 */new IFA_LLLCHAR(999, "Reserved for national use"),
      /* 118 */new IFA_LLLCHAR(999, "Reserved for national use"),
      /* 119 */new IFA_LLLCHAR(999, "Reserved for national use"),
      /* 120 */new IFA_LLLCHAR(999, "֪ͨ��Ϣ"),
      /* 121 */new IFA_LLLCHAR(100, "CUPS Reserved"), //
      /* 122 */new IFA_LLLCHAR(100, "Acquiring Institution Reserved"), //
      /* 123 */new IFA_LLLCHAR(100, "Issuer Institution Reserved"), //
      /* 124 */new IFA_LLLCHAR(999, "���������"),
      /* 125 */new IFA_LLLCHAR(999, "Reserved for private use"),
      /* 126 */new IFA_LLLCHAR(999, "Reserved for private use"),
      /* 127 */new IFA_LLLCHAR(999, "Reserved for private use"),
      /* 128 */new IF_CHAR(16, "���ļ�����") };

  public ISO8583HOSTPackager()
  {
    super();
    setFieldPackager(fld);
  }
}
