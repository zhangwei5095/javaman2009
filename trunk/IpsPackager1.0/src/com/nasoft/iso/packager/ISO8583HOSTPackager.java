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
      /* 002 */new IF_CHAR(19, "主账号/卡号"),
      /* 003 */new IFA_NUMERIC(12, "not use"),
      /* 004 */new IFA_NUMERIC(12, "交易金额"),
      /* 005 */new IFA_NUMERIC(8, "主机流水号"),
      /* 006 */new IF_CHAR(10, "客户号"),
      /* 007 */new IFA_NUMERIC(10, "Date and time, transmission"),
      /* 008 */new IFA_NUMERIC(8, "Amount, Cardholder billing fee"),
      /* 009 */new IFA_NUMERIC(8, "文件入帐日期"),
      /* 010 */new IFA_NUMERIC(8, "卡有效期"),
      /* 011 */new IFA_NUMERIC(8, "流水号卡"),
      /* 012 */new IFA_NUMERIC(6, "本地时间"),//
      /* 013 */new IFA_NUMERIC(8, "本地日期"),
      /* 014 */new IFA_NUMERIC(8, "清算日期"),
      /* 015 */new IF_CHAR(16, "交易标志"),//
      /* 016 */new IFA_NUMERIC(2, "存期"),
      /* 017 */new IFA_NUMERIC(12, "起扣金额"),
      /* 018 */new IF_CHAR(4, "商户类型"),
      /* 019 */new IF_CHAR(2, "挂失事项"),
      /* 020 */new IF_CHAR(6, "摘要"),
      /* 021 */new IFA_NUMERIC(8, "起息日"),
      /* 022 */new IF_CHAR(3, "服务点进入方式"),
      /* 023 */new IFA_NUMERIC(3, "Card sequence number"),
      /* 024 */new IFA_NUMERIC(3, "Function code"),
      /* 025 */new IF_CHAR(2, "服务点条件代码"),//
      /* 026 */new IF_CHAR(12, "业务手续费收取方式"),
      /* 027 */new IFA_NUMERIC(3, "异地手续费收取比率"),
      /* 028 */new IFA_NUMERIC(8, "主机系统异地手续费"),//
      /* 029 */new IFA_NUMERIC(8, "业务手续费"),
      /* 030 */new IFA_NUMERIC(8, "业务预留手续费"),
      /* 031 */new IFA_LLCHAR(99, "Acquirer reference data"),
      /* 032 */new IF_CHAR(11, "交易系统代码"),
      /* 033 */new IF_CHAR(11, "发信系统代码"),
      /* 034 */new IFA_LLCHAR(28, "Primary account number, extended"),
      /* 035 */new IFA_LLCHAR(37, "第二磁道数据"),
      /* 036 */new IFA_LLLCHAR(104, "第三磁道数据"),
      /* 037 */new IF_CHAR(12, "系统检索号"),
      /* 038 */new IF_CHAR(16, "授权码/止付号"),
      /* 039 */new IF_CHAR(7, "响应码"),
      /* 040 */new IF_CHAR(5, "开户局代码"),
      /* 041 */new IF_CHAR(8, "终端标识号"),
      /* 042 */new IF_CHAR(15, "交易局代码/受卡方标识码"),
      /* 043 */new IF_CHAR(32, "响应码附加信息"),//
      /* 044 */new IFA_LLCHAR(40, "户名"),//
      /* 045 */new IFA_LLLCHAR(512, "附加数据"),//
      /* 046 */new IF_CHAR(10, "存折印刷号"),
      /* 047 */new IF_CHAR(1, "扣款精度"),
      /* 048 */new IF_CHAR(12, "最低留存金额/ATM存款钱箱金额"),//
      /* 049 */new IF_CHAR(3, "交易货币代码"),
      /* 050 */new IF_CHAR(3, "Currency code, Settlement"),
      /* 051 */new IF_CHAR(6, "柜员号"),
      /* 052 */new IF_CHAR(16, "个人识别号（PIN）"), ///
      /* 053 */new IFA_NUMERIC(16, "安全控制信息"),
      /* 054 */new IF_CHAR(62, "附加余额"),//
      /* 055 */new IFA_LLLBINARY(255, "IC card system related data"),
      /* 056 */new IFA_LLNUM(35, "Original data elements"),
      /* 057 */new IFA_LLLCHAR(100, "卡方私有数据"),//
      /* 058 */new IFA_LLLCHAR(100, "IC(PBOC) Data Reserved"), //
      /* 059 */new IFA_LLLCHAR(30, "扩展消息原因码"), //
      /* 060 */new IF_CHAR(4, "消息原因码"), //
      /* 061 */new IF_CHAR(22, "证件信息"), //
      /* 062 */new IF_CHAR(16, "附加个人标识号数据"), //
      /* 063 */new IFA_LLLCHAR(200, "Finacial Network Data "), //
      /* 064 */new IFB_BINARY(8, "Message authentication code field"),
      /* 065 */new IFB_BINARY(8, "Reserved for ISO use"),
      /* 066 */new IF_CHAR(1, "清算应答代码"), //
      /* 067 */new IFA_NUMERIC(22, "委托机构分户号"),
      /* 068 */new IFA_LLCHAR(40, "受理方名称和地址"),
      /* 069 */new IFA_NUMERIC(3, "Country code, settlement institution"),
      /* 070 */new IFA_NUMERIC(3, "Network Management Information Code"), //
      /* 071 */new IFA_NUMERIC(8, "Message number"),
      /* 072 */new IF_CHAR(24, "账户状态"),
      /* 073 */new IFA_NUMERIC(6, "Date, action"),
      /* 074 */new IF_CHAR(128, "支票详细信息"),
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
      /* 090 */new IF_CHAR(62, "原始交易信息"), //
      /* 091 */new IFA_NUMERIC(3, "Country code, transaction Dest. Inst."),
      /* 092 */new IFA_NUMERIC(3, "Country code, transaction Orig. Inst."),
      /* 093 */new IFA_LLNUM(11, "Transaction Dest. Inst. Id code"),
      /* 094 */new IFA_LLNUM(11, "Transaction Orig. Inst. Id code"),
      /* 095 */new IF_CHAR(42, "CReplacement Amounts"), //
      /* 096 */new IF_CHAR(16, "报文安全码"), //
      /* 097 */new IFA_AMOUNT(1 + 16, "Amount, Net reconciliation"),
      /* 098 */new IF_CHAR(25, "Payee"),
      /* 099 */new IFA_LLCHAR(11, "Settlement institution Id code"),
      /* 100 */new IFA_LLNUM(11, "Receiving institution Id code"),
      /* 101 */new IFA_LLCHAR(17, "File name"),
      /* 102 */new IF_CHAR(19, "附加账号"),
      /* 103 */new IF_CHAR(19, "转入账号"),
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
      /* 120 */new IFA_LLLCHAR(999, "通知信息"),
      /* 121 */new IFA_LLLCHAR(100, "CUPS Reserved"), //
      /* 122 */new IFA_LLLCHAR(100, "Acquiring Institution Reserved"), //
      /* 123 */new IFA_LLLCHAR(100, "Issuer Institution Reserved"), //
      /* 124 */new IFA_LLLCHAR(999, "差错处理内容"),
      /* 125 */new IFA_LLLCHAR(999, "Reserved for private use"),
      /* 126 */new IFA_LLLCHAR(999, "Reserved for private use"),
      /* 127 */new IFA_LLLCHAR(999, "Reserved for private use"),
      /* 128 */new IF_CHAR(16, "报文鉴别码") };

  public ISO8583HOSTPackager()
  {
    super();
    setFieldPackager(fld);
  }
}
