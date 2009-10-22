package org.ndot.code;
/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� AscBcd2.java
 * 
 *<P>
 * 
 * �� ��:
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-10-22
 * 
 */
public class AscBcd2 {
	/**
     * @����: ������
     */
    public AscBcd2() {
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String sTestData = "1  4   11   ";
        byte bBCD[] = new byte[sTestData.length()];

        bBCD = str2Bcd(sTestData);
        String str = bcd2Str(bBCD);
        System.out.println("ԭ����:" + sTestData);
        System.out.println("ԭ����:" + sTestData.getBytes());
        System.out.println("Bcd:" + bBCD);
        System.out.println("ASC:" + str);

                System.out.println("========================================");

        sTestData = "= ABCDEF0123     456789abcdef";
        bBCD = str2Bcd(sTestData);
        str = bcd2Str(bBCD);
        System.out.println("ԭ����:" + sTestData);
        System.out.println("Bcd:" + bBCD);
        System.out.println("ASC:" + str);        
    }

    /**
     * @��������: 10���ƴ�תΪBCD��
     * @�������: 10���ƴ�
     * @������: BCD��
     */
    public static byte[] str2Bcd(String asc) {

        // ԭ���ݵĳ���
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = asc + "0";
            len = asc.length();
        }

        // ԭ����
        byte bOriginalData[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        // ���ַ�������ת�����ֽ�����
        bOriginalData = asc.getBytes();

        // ת�����BCD��
        byte bBCD[] = new byte[len];

        int sH, sL;

        for (int p = 0; p < asc.length()/2; p++) {

            if ( (bOriginalData[2 * p] >= 'a') && (bOriginalData[2 * p] <= 'f')) {
                sH = bOriginalData[2 * p] - 'a' + 10;
            } else if ((bOriginalData[2 * p] >= 'A') && (bOriginalData[2 * p] <= 'F')) {
                sH = bOriginalData[2 * p] - 'A' + 10;
            } else {
                sH = bOriginalData[2 * p] & 0x0f;
            }

            if ( (bOriginalData[2 * p + 1] >= 'a') && (bOriginalData[2 * p + 1] <= 'f')) {
                sL = bOriginalData[2 * p + 1] - 'a' + 10;
            } else if ((bOriginalData[2 * p + 1] >= 'A') && (bOriginalData[2 * p + 1] <= 'F')) {
                sL = bOriginalData[2 * p + 1] - 'A' + 10;
            } else {
                sL = bOriginalData[2 * p + 1] & 0x0f;
            }

            bBCD[p] = (byte)((sH << 4) + sL);
        }
        return bBCD;
    }

    /**
     * @��������: BCD�봮ת��Ϊ�ַ���
     * @�������: BCD��
     * @������: 10���ƴ�
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length*2], val;

        for(int i = 0; i < bytes.length; i++){
            val = (char)(((bytes[i]& 0xf0) >> 4)&0x0f);
            temp[i * 2] = (char)(val > 9 ? val + 'A' - 10 : val + '0');

            val = (char)(bytes[i]& 0x0f);
            temp[i * 2 + 1] = (char)(val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

}
