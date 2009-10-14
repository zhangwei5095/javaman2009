package com.nasoft.iso.packager;

import com.nasoft.iso.IFA_AMOUNT;
import com.nasoft.iso.IFA_BINARY;
import com.nasoft.iso.IFA_BITMAP;
import com.nasoft.iso.IFA_LLLCHAR;
import com.nasoft.iso.IFA_LLNUM;
import com.nasoft.iso.IFA_NUMERIC;
import com.nasoft.iso.IF_CHAR;
import com.nasoft.iso.ISOBasePackager;
import com.nasoft.iso.ISOComponent;
import com.nasoft.iso.ISOFieldPackager;
import com.nasoft.iso.ISOPackager;

/**
 * ISO 8583 v1987 ASCII Packager
 * 
 * @author apr@cs.com.uy
 * @version $Id: ISO8583BobjPackager.java,v 1.5 2003/05/16 04:15:15 alwyns Exp $
 * @see ISOPackager
 * @see ISOBasePackager
 * @see ISOComponent
 */
public class ISO8583BobjPackager extends ISOBasePackager
{

  protected ISOFieldPackager fld[] = { new IFA_NUMERIC(5, "功能数据区"),
      new IFA_BITMAP(16, "BITMAP图数据区"),
      /* 001 */new IF_CHAR(0, "批量笔数"),
      /* 002 */new IF_CHAR(24, "主帐号"),
      /* 003 */new IF_CHAR(3, "交易码"),
      /* 004 */new IFA_LLNUM(12, "金额2(发生额)"),
      /* 005 */new IFA_LLNUM(12, "金额2(发生额)"),
      /* 006 */new IFA_LLNUM(12, "金额3(发生额)"),
      /* 007 */new IFA_NUMERIC(10, "传输日期和时间"),
      /* 008 */new IF_CHAR(13, ""),
      /* 009 */new IF_CHAR(3, ""),
      /* 010 */new IF_CHAR(16, ""),
      /* 011 */new IFA_NUMERIC(6, "系统跟踪审计号"),
      /* 012 */new IFA_NUMERIC(6, "发生时间"),
      /* 013 */new IFA_NUMERIC(4, "发生日期"),
      /* 014 */new IFA_NUMERIC(8, "网银校验"),
      /* 015 */new IFA_NUMERIC(8, ""),
      /* 016 */new IF_CHAR(4, ""),
      /* 017 */new IF_CHAR(6, ""),
      /* 018 */new IF_CHAR(4, ""),
      /* 019 */new IF_CHAR(6, ""),
      /* 020 */new IF_CHAR(7, ""),
      /* 021 */new IF_CHAR(7, ""),
      /* 022 */new IF_CHAR(3, "服务进入方式"),
      /* 023 */new IF_CHAR(3, "项目报号"),
      /* 024 */new IFA_NUMERIC(8, ""),
      /* 025 */new IFA_NUMERIC(5, "网点流水号"),
      /* 026 */new IFA_NUMERIC(8, ""),
      /* 027 */new IF_CHAR(1, "借/贷标志"),
      /* 028 */new IFA_LLNUM(12, "金额"),
      /* 029 */new IFA_LLNUM(12, "金额"),
      /* 030 */new IFA_LLNUM(12, "金额"),
      /* 031 */new IFA_LLNUM(12, "金额"),
      /* 032 */new IFA_AMOUNT(5, ""),
      /* 033 */new IFA_LLNUM(5, ""),
      /* 034 */new IF_CHAR(28, "帐号4"),
      /* 035 */new IF_CHAR(37, "第二磁道数据"),
      /* 036 */new IF_CHAR(104, "第三磁道数据"),
      /* 037 */new IF_CHAR(10, "控制标志"),
      /* 038 */new IF_CHAR(3, "摘要码"),
      /* 039 */new IF_CHAR(4, "响应代码"),
      /* 040 */new IF_CHAR(2, "储种号"),
      /* 041 */new IFA_NUMERIC(3, "短整数1"),
      /* 042 */new IF_CHAR(16, "帐户密码"),
      /* 043 */new IFA_NUMERIC(10, "凭证号"),
      /* 044 */new IF_CHAR(9, "客户号"),
      /* 045 */new IF_CHAR(76, "第一磁道数据"),
      /* 046 */new IF_CHAR(13, "主帐号2"),
      /* 047 */new IF_CHAR(3, "分帐号2"),
      /* 048 */new IF_CHAR(16, "卡号2"),
      /* 049 */new IF_CHAR(2, "币种"),
      /* 050 */new IF_CHAR(118, ""),
      /* 051 */new IF_CHAR(123, ""),
      /* 052 */new IF_CHAR(16, "密码"),
      /* 053 */new IF_CHAR(12, ""),
      /* 054 */new IF_CHAR(139, ""),
      /* 055 */new IF_CHAR(70, ""),
      /* 056 */new IF_CHAR(57, ""),
      /* 057 */new IF_CHAR(232, ""),
      /* 058 */new IF_CHAR(172, ""),
      /* 059 */new IF_CHAR(64, ""),
      /* 060 */new IF_CHAR(1, ""),
      /* 061 */new IFA_LLLCHAR(6, "利率"),
      /* 062 */new IFA_NUMERIC(4, "短整数2"),
      /* 063 */new IF_CHAR(1, "状态标志"),
      /* 064 */new IF_CHAR(16, "客户密码"),
      /* 065 */new IFA_NUMERIC(2, ""),
      /* 066 */new IF_CHAR(1, "状态2"),
      /* 067 */new IF_CHAR(2, "状态4"),
      /* 068 */new IFA_LLNUM(16, "利息/积数1"),
      /* 069 */new IFA_LLNUM(16, "利息/积数2"),
      /* 070 */new IFA_NUMERIC(3, "约期次数"),
      /* 071 */new IFA_NUMERIC(4, "序号"),
      /* 072 */new IFA_NUMERIC(2, "打折行数"),
      /* 073 */new IFA_NUMERIC(8, "日期"),
      /* 074 */new IFA_NUMERIC(10, "整数1"),
      /* 075 */new IFA_NUMERIC(10, "整数2"),
      /* 076 */new IFA_NUMERIC(10, "整数3"),
      /* 077 */new IFA_NUMERIC(10, "整数4"),
      /* 078 */new IFA_NUMERIC(10, "整数5"),
      /* 079 */new IFA_NUMERIC(10, "整数6"),
      /* 080 */new IFA_NUMERIC(10, "整数7"),
      /* 081 */new IFA_NUMERIC(10, "客户号"),
      /* 082 */new IF_CHAR(4, "前台终端号"),
      /* 083 */new IFA_NUMERIC(4, "文件记录宽度"),
      /* 084 */new IF_CHAR(12, "凭证号1"),
      /* 085 */new IF_CHAR(12, "凭证号2"),
      /* 086 */new IFA_LLNUM(16, "金额8"),
      /* 087 */new IFA_LLNUM(16, "金额9"),
      /* 088 */new IFA_LLNUM(16, "金额10"),
      /* 089 */new IFA_LLNUM(16, "金额11"),
      /* 090 */new IFA_NUMERIC(6, ""),
      /* 091 */new IFA_NUMERIC(1, "场次"),
      /* 092 */new IF_CHAR(2, "状态3"),
      /* 093 */new IF_CHAR(5, "联行号"),
      /* 094 */new IF_CHAR(16, "支票密码"),
      /* 095 */new IF_CHAR(42, "备注2"),
      /* 096 */new IFA_BINARY(8, "中间价"),
      /* 097 */new IF_CHAR(17, ""),
      /* 098 */new IF_CHAR(25, ""),
      /* 099 */new IF_CHAR(9, "交换号"),
      /* 100 */new IFA_NUMERIC(5, "接受机构"),
      /* 101 */new IF_CHAR(20, "有效证件"),
      /* 102 */new IF_CHAR(24, "帐号2"),
      /* 103 */new IF_CHAR(24, "帐号3"),
      /* 104 */new IF_CHAR(52, "备注，原因"),
      /* 105 */new IF_CHAR(16, " 姓名"),
      /* 106 */new IFA_LLLCHAR(5, ""),
      /* 107 */new IF_CHAR(1, ""),
      /* 108 */new IF_CHAR(1, ""),
      /* 109 */new IF_CHAR(1, ""),
      /* 110 */new IF_CHAR(1, ""),
      /* 111 */new IF_CHAR(1, ""),
      /* 112 */new IF_CHAR(16, ""),
      /* 113 */new IFA_LLLCHAR(64, ""),
      /* 114 */new IFA_LLLCHAR(180, ""),
      /* 115 */new IFA_LLLCHAR(16, ""),
      /* 116 */new IFA_LLLCHAR(122, ""),
      /* 117 */new IFA_LLLCHAR(1, ""),
      /* 118 */new IFA_LLLCHAR(59, ""),
      /* 119 */new IFA_LLLCHAR(270, ""),
      /* 120 */new IFA_LLLCHAR(76, ""),
      /* 121 */new IF_CHAR(1, ""),
      /* 122 */new IF_CHAR(1, ""),
      /* 123 */new IF_CHAR(1, ""),
      /* 124 */new IF_CHAR(1, ""),
      /* 125 */new IF_CHAR(1, ""),
      /* 126 */new IF_CHAR(1, ""),
      /* 127 */new IF_CHAR(1, ""),
      /* 128 */new IF_CHAR(8, ""), };

  public ISO8583BobjPackager()
  {
    super();
    setFieldPackager(fld);
  }
}
