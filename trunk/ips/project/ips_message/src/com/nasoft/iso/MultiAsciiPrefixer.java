package com.nasoft.iso;

/**
 * Ascill码明细域
 * @author dbg
 *
 */
public class MultiAsciiPrefixer extends AsciiPrefixer {
	private int detailLen = 0;//单条明细的长度

	private int mDigits = 0;//明细长度位数
	
	private int detailPreLen=0;//明细前缀位数
	

	/**
	 * 
	 * @param nDigits 单条明细长度的位数
	 * @param mDigits 明细条数位数
	 * @param detailLen 单条明细的长度
	 */
	public MultiAsciiPrefixer(int nDigits, int mDigits, int detailLen,int detailPreLen) {
		this.nDigits = nDigits;
		this.mDigits = mDigits;
		this.detailLen = detailLen;
		this.detailPreLen=detailPreLen;
	}

	public void encodeLength(int length, byte[] b) throws ISOException {
		int detailLength = this.detailLen;
//		length=length-(detailPreLen-mDigits);
//		(detailPreLen-mDigits);
		if (length % detailLength != 0) {
			throw new ISOException("invalid len " + length
					+ ". Prefixing digits = " + nDigits + " detailLen = "
					+ this.detailLen);
		}
		int detailNum = length / detailLength;
		// Write the string backwards - I don't know why I didn't see this at first.
		for (int i = mDigits - 1; i >= 0; i--) {
			b[i] = (byte) (detailNum % 10 + '0');
			detailNum /= 10;
		}
		if (detailNum != 0) {
			throw new ISOException("invalid len " + length
					+ ". Prefixing digits = " + nDigits + " detailLen = "
					+ this.detailLen);
		}

		if(nDigits!=0){
			for (int i = nDigits - 1; i >= 0; i--) {
				b[i + this.mDigits] = (byte) (detailLength % 10 + '0');
				detailLength /= 10;
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
		for (int i = 0; i < mDigits; i++) {
			
			if(b[offset+i]!=0x20){
//				System.out.println("ddd");
				detailNum = detailNum * 10 + b[offset + i] - (byte) '0';
			}
			
		}
		int detailLength = 0;
		
		for (int i = 0; i < nDigits; i++) {
			detailLength = detailLength * 10 + b[offset + i + this.mDigits]
					- (byte) '0';
		}
		
		if(nDigits==0){
			detailLength=this.detailLen;
		}
		return detailNum * detailLength;
//		return detailNum * detailLength+this.detailPreLen-this.mDigits;
	}

	public int getPackedLength() {
		return nDigits + this.mDigits;
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
