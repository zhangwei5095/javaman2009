package org.ndot.ips.exception;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSException.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-21
 * 
 */
public class IPSException extends Exception {

	private static final long serialVersionUID = -1328383839915898984L;

	private Throwable throwable = null;

	/**
	 * Default constructor.
	 */
	public IPSException() {
		super();
	}

	/**
	 * Constructs a <code>IPSException</code> object with a message.
	 * 
	 * @param msg
	 *            a description of the exception
	 */
	public IPSException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>IPSException</code> object with a
	 * <code>Throwable</code> cause.
	 * 
	 * @param th
	 *            the original cause
	 */
	public IPSException(Throwable th) {
		super(th.getMessage());
		throwable = th;
	}

	/**
	 * Constructs a <code>BaseException</code> object with a
	 * <code>Throwable</code> cause.
	 * 
	 * @param th
	 *            the original cause
	 */
	public IPSException(String msg, Throwable th) {
		super(msg);
		throwable = th;
	}

	/**
	 * Get the root cause.
	 */
	public Throwable getRootCause() {
		return throwable;
	}

	/**
	 * Print stack trace.
	 */
	public void printStackTrace(PrintWriter pw) {
		if (throwable == null) {
			super.printStackTrace(pw);
		} else {
			throwable.printStackTrace(pw);
		}
	}

	/**
	 * Print stack trace.
	 */
	public void printStackTrace(PrintStream ps) {
		if (throwable == null) {
			super.printStackTrace(ps);
		} else {
			throwable.printStackTrace(ps);
		}
	}

	/**
	 * Print stack trace.
	 */
	public void printStackTrace() {
		if (throwable == null) {
			super.printStackTrace();
		} else {
			throwable.printStackTrace();
		}
	}

}
