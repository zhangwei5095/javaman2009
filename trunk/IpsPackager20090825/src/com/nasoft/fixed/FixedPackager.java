package com.nasoft.fixed;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Vector;

import com.nasoft.iso.ISOBasePackager;
import com.nasoft.iso.ISOBitMap;
import com.nasoft.iso.ISOBitMapPackager;
import com.nasoft.iso.ISOComponent;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOField;
import com.nasoft.iso.ISOFieldPackager;
import com.nasoft.iso.ISOMsgFieldPackager;

public class FixedPackager extends ISOBasePackager {

	public byte[] pack(ISOComponent m) throws ISOException {
		// LogEvent evt = new LogEvent(this, "pack");
		try {
			// TestLog log=TestLog.getNewInstance();
			if (m.getComposite() != m)
				throw new ISOException("Can't call packager on non Composite");

			ISOComponent c;
			Vector v = new Vector();
			Hashtable fields = m.getChildren();
			int len = 0;

			c = (ISOComponent) fields.get(new Integer(0));
			byte[] b;

			String bitMapStr = m.getBitMapStr();
			if (bitMapStr.charAt(bitMapStr.length() - 1) == ',') {
				bitMapStr = bitMapStr.substring(0, bitMapStr.length() - 1);
			}

			String[] bitMapArray = bitMapStr.split(",");
			int fldLen = bitMapArray.length;

			for (int i = 0; i < fldLen; i++) {
				if (!"999".equals(bitMapArray[i])) {
					if ((c = (ISOComponent) fields.get(new Integer(Integer
							.parseInt(bitMapArray[i])))) != null) {
						try {
							ISOFieldPackager fp = fld[Integer
									.parseInt(bitMapArray[i])];
							if (fp == null)
								throw new ISOException("null field packager");
							b = fp.pack(c);
							// log.writeLog("filed["+bitMapArray[i]+"]="+c.getValue());
							len += b.length;
							v.addElement(b);
						} catch (ISOException e) {
//							log.writeLog("error packing field " + i);
							// evt.addMessage("error packing field " + i);
							// evt.addMessage(c);
							// evt.addMessage(e);
							e.printStackTrace();
							throw e;
						}
					} else {
						try {
							c = new ISOField(Integer.parseInt(bitMapArray[i]),
									"");
							ISOFieldPackager fp = fld[Integer
									.parseInt(bitMapArray[i])];
							if (fp == null)
								throw new ISOException("null field packager");
							b = fp.pack(c);
							// log.writeLog("filed["+bitMapArray[i]+"]="+c.getValue());
							len += b.length;
							v.addElement(b);
						} catch (ISOException e) {
							// log.writeLog("error packing field " + i);
							// evt.addMessage("error packing field " + i);
							// evt.addMessage(c);
							// evt.addMessage(e);
							throw e;
						}
					}
				}

			}

			int k = 0;
			byte[] d = new byte[len];
			for (int i = 0; i < v.size(); i++) {
				b = (byte[]) v.elementAt(i);
				for (int j = 0; j < b.length; j++)
					d[k++] = b[j];
			}
			// if (logger != null) // save a few CPU cycle if no logger
			// available
			// evt.addMessage(ISOUtil.hexString(d));
			return d;
		} catch (ISOException e) {
			// evt.addMessage(e);
			throw e;
		} finally {
			// Logger.log(evt);
		}
	}

	/**
	 * @param m
	 *            the Container of this message
	 * @param b
	 *            ISO message image
	 * @return consumed bytes
	 * @exception ISOException
	 */
	public int unpack(ISOComponent m, byte[] b) throws ISOException {
		// LogEvent evt = new LogEvent(this, "unpack");
		// TestLog log=TestLog.getNewInstance();
		System.out.println(new String(b));
		int consumed = 0;
		try {
			if (m.getComposite() != m)
				throw new ISOException("Can't call packager on non Composite");
			// if (logger != null) // save a few CPU cycle if no logger
			// available
			// evt.addMessage(ISOUtil.hexString(b));

			String bitMapStr = m.getBitMapStr();
			if (bitMapStr.charAt(bitMapStr.length() - 1) == ',') {
				bitMapStr = bitMapStr.substring(0, bitMapStr.length() - 1);
			}
			// log.writeLog("bitMapStr="+bitMapStr);
			String[] bitMapArray = bitMapStr.split(",");
			int fldLen = bitMapArray.length;

			for (int i = 0; i < fldLen; i++) {
				try {
					if (!"999".equals(bitMapArray[i])) {
						if (fld[Integer.parseInt(bitMapArray[i])] != null) {
							int fileNo = Integer.parseInt(bitMapArray[i]);
							ISOFieldPackager fp = fld[fileNo];
							ISOComponent c = fld[fileNo]
									.createComponent(fileNo);
							consumed += fld[fileNo].unpack(c, b, consumed);
							// log.writeLog("filed[" +
							// Integer.parseInt(bitMapArray[i]) + "]=" +
							// c.getValue());
							// if (logger != null) {
							// evt.addMessage("<unpack fld=\"" + i
							// + "\" packager=\""
							// +
							// fld[Integer.parseInt(bitMapArray[i])].getClass().getName()
							// + "\">");
							// if (c.getValue() instanceof ISOMsg) {
							// evt.addMessage(c.getValue());
							// //
							// System.out.println("filed["+i+"]="+c.getValue());
							// }
							//
							// else
							// evt.addMessage(" <value>"
							// + c.getValue().toString() + "</value>");
							// evt.addMessage("</unpack>");
							// }
							m.set(c);
						}
					}

				} catch (ISOException e) {
					// log.writeLog("error unpacking field " +
					// Integer.parseInt(bitMapArray[i]));
					e.printStackTrace();
					// evt.addMessage(e);
					throw e;// NDot C029拆包出错返回前面内容
				}
			}

			// if (b.length != consumed) {
			// evt.addMessage("WARNING: unpack len=" + b.length + " consumed="
			// + consumed);
			// }
			return consumed;
		} catch (ISOException e) {
			// evt.addMessage(e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			// evt.addMessage(e);
			throw new ISOException(e);
		} finally {
			// Logger.log(evt);
			return consumed; // NDot C029拆包出错返回前面内容
		}
	}

	public void unpack(ISOComponent m, InputStream in) throws IOException,
			ISOException {
		// LogEvent evt = new LogEvent(this, "unpack");
		try {
			if (m.getComposite() != m)
				throw new ISOException("Can't call packager on non Composite");

			if (!(fld[0] instanceof ISOMsgFieldPackager)
					&& !(fld[0] instanceof ISOBitMapPackager)) {
				ISOComponent mti = fld[0].createComponent(0);
				fld[0].unpack(mti, in);
				m.set(mti);
			}
			BitSet bmap = null;

			int maxField = fld.length;
			if (emitBitMap()) {
				ISOBitMap bitmap = new ISOBitMap(-1);
				getBitMapfieldPackager().unpack(bitmap, in);
				bmap = (BitSet) bitmap.getValue();
				// if (logger != null)
				// evt.addMessage("<bitmap>" + bmap.toString() + "</bitmap>");
				m.set(bitmap);
				maxField = Math.min(maxField, bmap.size());
			}

			for (int i = getFirstField(); i < maxField; i++) {
				if ((bmap == null || bmap.get(i)) && fld[i] != null) {
					ISOComponent c = fld[i].createComponent(i);
					fld[i].unpack(c, in);
					// if (logger != null) {
					// evt.addMessage("<unpack fld=\"" + i + "\" packager=\""
					// + fld[i].getClass().getName() + "\">");
					// if (c.getValue() instanceof ISOMsg)
					// evt.addMessage(c.getValue());
					// else
					// evt.addMessage(" <value>"
					// + c.getValue().toString() + "</value>");
					// evt.addMessage("</unpack>");
					// }
					m.set(c);
				}
			}
			if (bmap != null && bmap.get(65) && fld.length > 128
					&& fld[65] instanceof ISOBitMapPackager) {
				bmap = (BitSet) ((ISOComponent) m.getChildren().get(
						new Integer(65))).getValue();
				for (int i = 1; i < 64; i++) {
					if (bmap == null || bmap.get(i)) {
						ISOComponent c = fld[i + 128].createComponent(i);
						fld[i + 128].unpack(c, in);
						// if (logger != null) {
						// evt
						// .addMessage("<unpack fld=\"" + i + 128
						// + "\" packager=\""
						// + fld[i + 128].getClass().getName()
						// + "\">");
						// evt.addMessage(" <value>"
						// + c.getValue().toString() + "</value>");
						// evt.addMessage("</unpack>");
						// }
						m.set(c);
					}
				}
			}
		} catch (ISOException e) {
			// evt.addMessage(e);
			throw e;
		} catch (EOFException e) {
			throw e;
		} catch (Exception e) {
			// evt.addMessage(e);
			throw new ISOException(e);
		} finally {
			// Logger.log(evt);

		}
	}

}
