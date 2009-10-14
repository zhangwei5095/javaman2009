package com.nasoft.iso;
/**
 * Bcd码明细域
 * @author dbg
 *
 */
public class MultiBcdPrefixer extends BcdPrefixer {

	private int detailLen = 0;//单条明细的长度

	private int mDigits = 0;//明细长度位数

	private int detailPreLen=0;//明细前缀位数
	/**
	 * 
	 * @param nDigits 单条明细长度的位数
	 * @param mDigits 明细条数位数
	 * @param fieldLen 单条明细的长度
	 */
	public MultiBcdPrefixer(int nDigits, int mDigits, int detailLen,int detailPreLen) {
		this.nDigits = nDigits;
		this.mDigits = mDigits;
		this.detailLen = detailLen;
		this.detailPreLen=detailPreLen;
	}

	public void encodeLength(int length, byte[] b) throws ISOException {
		int detailLength = this.detailLen;
		length=length-(detailPreLen-mDigits);
		if (length % detailLength != 0) {
			throw new ISOException("invalid len " + length
					+ ". Prefixing digits = " + nDigits + " detailLen = "
					+ this.detailLen);
		}

		int nLen = (this.nDigits + 1) / 2;
		int mLen = (this.mDigits + 1) / 2;
		int detailNum = length / detailLength;
		for (int i = mLen - 1; i >= 0; i--) {
			int twoDigits = detailNum % 100;
			detailNum /= 100;
			b[i] = (byte) (((twoDigits / 10) << 4) + twoDigits % 10);
		}

		if (detailNum != 0) {
			throw new ISOException("invalid len " + length
					+ ". Prefixing digits = " + nDigits + " detailLen = "
					+ this.detailLen);
		}
		if(nLen!=0){
			for (int i = nLen - 1; i >= 0; i--) {
				int twoDigits = detailLength % 100;
				detailLength /= 100;
				b[i + mLen] = (byte) (((twoDigits / 10) << 4) + twoDigits % 10);
			}
			if (detailLength != 0) {
				throw new ISOException("invalid len " + length
						+ ". Prefixing digits = " + nDigits + " detailLen = "
						+ this.detailLen);
			}
		}
		

	}

	public int decodeLength(byte[] b, int offset) {
		int detailNum = 0;
		int detailLength = 0;
		int len = 0;

		for (int i = 0; i < (mDigits + 1) / 2; i++) {
			detailNum = 100 * detailNum + ((b[offset + i] & 0xF0) >> 4) * 10
					+ ((b[offset + i] & 0x0F));
		}
		
		for (int i = 0; i < (nDigits + 1) / 2; i++) {
			detailLength = 100 * detailLength + ((b[offset + i] & 0xF0) >> 4)
					* 10 + ((b[offset + i + mDigits] & 0x0F));
		}
		if(nDigits==0){
			detailLength=this.detailLen;
		}
		
		len = detailNum * detailLength+this.detailPreLen-this.mDigits;
		return len;
	}

	public int getPackedLength() {
		return (nDigits + mDigits + 1) / 2;
	}

	/**
	 * @return the detailLen
	 */
	public int getDetailLen() {
		return detailLen;
	}

	/**
	 * @param detailLen the detailLen to set
	 */
	public void setDetailLen(int detailLen) {
		this.detailLen = detailLen;
	}

	/**
	 * @return the detailPreLen
	 */
	public int getDetailPreLen() {
		return detailPreLen;
	}

	/**
	 * @param detailPreLen the detailPreLen to set
	 */
	public void setDetailPreLen(int detailPreLen) {
		this.detailPreLen = detailPreLen;
	}

	/**
	 * @return the mDigits
	 */
	public int getMDigits() {
		return mDigits;
	}

	/**
	 * @param digits the mDigits to set
	 */
	public void setMDigits(int digits) {
		mDigits = digits;
	}
}
