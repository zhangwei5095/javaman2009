package com.nasoft.util;




import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;




public class EbcdicUtil
{
    final static Charset gbk = Charset.forName("GBK"); 
    
//    private static final int GBK_1_2_LOW = 0x48A0;
//    private static final int GBK_1_2_HIGH = 0x6FFD;
    
    final static byte[][] IBMTCHN1 = convertToByteArray(new int[][] { //[6][190]
        {166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,
        166,166,166,166,166,166,161,161,161,161,161,161,161,161,166,166,166,166,
        166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,166,
        166,166,161,161,161,161,161,161,161,167,167,167,167,167,167,167,167,167,
        167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,
        167,167,167,167,167,167,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,
        167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,167,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,162,162,162,162,
        162,162,162,162,162,162,162,162,161,161},
        {161,161,161,161,161,161,161,161,161,161,163,163,163,163,163,163,161,161,
        161,161,161,161,161,161,161,163,163,163,163,163,161,163,163,161,161,161,
        161,161,161,161,161,161,163,163,163,163,163,161,161,161,161,161,161,161,
        161,161,163,163,163,163,161,163,163,161,163,163,163,163,163,163,163,163,
        163,161,161,161,161,161,161,161,163,163,163,163,163,163,163,163,163,161,
        161,161,161,161,161,161,161,163,163,163,163,163,163,163,163,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,163,163,163,163,163,163,163,163,163,163,161,161,161,161,161,161,163,
        163,163,163,163,163,163,163,163,163,161,161,161,161,161,161,161,161,163,
        163,163,163,163,163,163,163,161,161,161,161,161,161,163,163,163,163,163,
        163,163,163,163,163,161,161,161,161,161},
        {161,161,161,161,161,165,165,165,165,161,161,161,161,161,161,161,165,165,
        165,165,165,165,165,161,165,165,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,165,165,165,165,165,165,165,165,
        165,165,161,165,165,165,165,165,165,165,165,165,165,165,165,165,165,165,
        161,161,165,165,165,161,161,165,165,165,165,165,165,165,165,165,161,165,
        165,165,165,161,161,161,161,161,161,161,161,161,161,165,165,165,165,161,
        161,165,165,165,165,165,165,165,165,165,165,165,165,165,165,165,165,165,
        165,165,165,165,165,165,165,165,165,165,165,161,161,161,161,163,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161},
        {161,161,161,163,163,164,164,164,164,161,161,161,161,161,161,163,164,164,
        164,164,164,164,164,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,163,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,164,164,164,164,164,164,164,164,
        164,164,161,164,164,164,164,164,164,164,164,164,164,164,164,164,164,164,
        161,161,164,164,164,161,161,164,164,164,164,164,164,164,164,164,161,164,
        164,164,164,161,161,161,161,161,161,161,161,161,161,164,164,164,164,161,
        161,164,164,164,164,164,164,164,164,164,164,164,164,164,164,164,164,164,
        164,164,164,161,164,164,164,164,164,164,164,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161},
        { 161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,162,162,162,162,162,162,162,162,162,162,162,162,162,162,
        162,162,162,162,162,162,162,162,162,162,162,162,162,162,162,162,162,162,
        162,162,162,162,162,162,162,162,161,161,161,161,161,161,161,161,162,162,
        162,162,162,162,162,162,162,162,161,161,161,161,161,161,162,162,162,162,
        162,162,162,162,162,162,161,161,161,161},
        { 161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,
        168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,168,
        168,161,161,161,161,161,161,161,161,161,161,161,161,161,161,169,169,169,
        169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,
        169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,
        169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,
        169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,169,
        169,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
        161,161,161,161,161,161,161,161,161,161}
    });
    

    final static byte[][] IBMTCHN2 = convertToByteArray(new int[][] {
            {193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,
            211,212,213,214,215,216,161,161,161,161,161,161,161,161,161,162,163,164,
            165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,
            183,184,161,161,161,161,161,161,161,209,210,211,212,213,214,215,216,217,
            218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,
            236,237,238,239,240,241,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,
            178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,241,242,243,244,
            245,246,247,248,249,250,251,252,161,161},
            {161,161,161,161,161,161,161,161,161,234,174,188,168,171,252,166,161,161,
            161,161,161,161,161,161,161,161,164,170,169,187,161,173,175,161,161,161,
            161,161,161,161,161,161,172,165,223,190,191,161,161,161,161,161,161,161,
            161,161,224,186,163,192,161,189,162,161,225,226,227,228,229,230,231,232,
            233,161,161,161,161,161,161,161,234,235,236,237,238,239,240,241,242,161,
            161,161,161,161,161,161,161,243,244,245,246,247,248,249,250,161,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,251,193,194,195,196,197,198,199,200,201,161,161,161,161,161,161,253,
            202,203,204,205,206,207,208,209,210,161,161,161,161,161,161,231,161,211,
            212,213,214,215,216,217,218,161,161,161,161,161,161,176,177,178,179,180,
            181,182,183,184,185,161,161,161,161,161},
            {163,184,185,162,164,242,161,163,165,233,161,161,161,161,161,161,167,169,
            227,229,231,195,238,161,245,246,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161,162,164,166,168,170,171,173,175,
            177,179,161,181,183,185,187,189,191,193,196,198,200,202,203,204,205,206,
            161,161,207,210,213,161,171,216,219,222,223,224,225,226,228,230,161,232,
            233,234,235,161,161,161,161,161,161,161,161,161,161,236,237,239,243,161,
            161,172,174,176,178,180,182,184,186,188,190,192,194,197,199,201,208,211,
            214,217,220,244,209,212,215,218,221,240,241,161,161,161,161,220,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161},
            {161,186,187,219,221,242,161,163,165,170,192,217,222,230,161,167,167,169,
            227,229,231,195,238,161,161,161,168,161,161,161,161,167,174,176,178,180,
            182,190,220,224,225,236,249,161,161,237,161,222,175,177,179,181,183,191,
            221,223,226,193,194,172,161,161,173,161,162,164,166,168,170,171,173,175,
            177,179,161,181,183,185,187,189,191,193,196,198,200,202,203,204,205,206,
            161,161,207,210,213,161,161,216,219,222,223,224,225,226,228,230,161,232,
            233,234,235,161,161,161,161,161,161,161,161,161,161,236,237,239,243,161,
            161,172,174,176,178,180,182,184,186,188,190,192,194,197,199,201,208,211,
            214,217,220,161,209,212,215,218,221,240,241,161,161,161,161,240,241,247,
            248,242,238,239,243,244,245,246,161,161,227,228,229,250,251,252,253,161,
            161,161,161,161,161,161,161,161,161,161},
            {161,161,161,161,165,166,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,188,189,161,161,161,161,161,195,196,197,
            198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,
            216,161,218,219,161,161,161,161,161,161,161,161,161,161,161,161,161,232,
            161,161,235,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,177,178,179,180,181,182,183,184,185,186,187,188,189,190,
            191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,
            209,210,211,212,213,214,215,216,161,161,161,161,161,161,161,161,217,218,
            219,220,221,222,223,224,225,226,161,161,161,161,161,161,229,230,231,232,
            233,234,235,236,237,238,161,161,161,161},
            {161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,
            215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,
            233,161,161,161,161,161,161,161,161,161,161,161,161,161,161,164,165,166,
            167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,
            185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,
            203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,
            221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,
            239,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,161,
            161,161,161,161,161,161,161,161,161,161}
            });

    final static byte[][] CHNTIBM1 = convertToByteArray(new int[][] { //[9][93]    
                    { 64, 67, 67, 67, 69, 69, 68, 68, 64, 68, 67, 68, 68, 68, 68, 68, 68, 68,
                     68, 68, 68, 68, 68, 67, 67, 68, 68, 69, 69, 68, 68, 68, 68, 68, 69, 69,
                     69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
                     69, 69, 68, 69, 69, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 66, 69,
                     67, 66, 69, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68,
                     68, 68, 68},
                    { 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 69, 69,
                     69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
                     69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69,
                     69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 64, 64, 69, 69, 69, 69,
                     69, 69, 69, 69, 69, 69, 64, 64, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,
                     65, 65, 64},
                    { 66, 66, 66, 66, 66, 66, 68, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66,
                     66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66,
                     66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66,
                     66, 66, 66, 66, 68, 67, 68, 68, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66,
                     66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66,
                     66, 66, 66},
                    { 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68,
                     68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68,
                     68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68,
                     68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68,
                     68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 68, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67,
                     67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67,
                     67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67,
                     67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67,
                     67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 64, 64, 64, 64,
                     64, 64, 64},
                    { 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,
                     65, 65, 65, 65, 65, 65, 64, 64, 64, 64, 64, 64, 64, 64, 65, 65, 65, 65,
                     65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,
                     65, 65, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,
                     65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 64, 64, 64,
                     64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 65, 65, 65, 65, 65, 65,
                     65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,
                     65, 65, 65, 65, 65, 65, 65, 65, 65, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
                     70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
                     70, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 64, 64, 64, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
                     70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
                     70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
                     70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
                     70, 70, 70, 70, 70, 70, 70, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64}
                    });

    final static byte[][] CHNTIBM2 = convertToByteArray(new int[][] { //[9][93]    
                    { 64, 68, 65, 69, 69, 70, 96, 91, 64, 74,161,124,127, 97,113, 98,114, 99,
                    115,100,116,101,117, 66, 67, 66, 67, 91, 92,102,118, 75,122,123, 98, 99,
                    100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,
                    118,119, 76,121,122,103,119, 77,120,104,105,121,237,238,239, 78,224,136,
                     74, 74,139,106,110,229,230,224,225,228,231,232,233,234,226,227,107,240,
                    241,242,243},
                    { 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,177,178,
                    179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,
                    197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,
                    215,216,225,226,227,228,229,230,231,232,233,234, 64, 64,241,242,243,244,
                    245,246,247,248,249,250, 64, 64,241,242,243,244,245,246,247,248,249,250,
                    251,252, 64},
                    { 90,127,123, 91,108, 80, 80, 77, 93, 92, 78,107, 96, 75, 97,240,241,242,
                    243,244,245,246,247,248,249,122, 94, 76,126,110,111,124,193,194,195,196,
                    197,198,199,200,201,209,210,211,212,213,214,215,216,217,226,227,228,229,
                    230,231,232,233, 68,224, 69,112,109,121,129,130,131,132,133,134,135,136,
                    137,145,146,147,148,149,150,151,152,153,162,163,164,165,166,167,168,169,
                    192, 79,208},
                    { 71,129, 72,130, 73,131, 81,132, 82,133,134,192,135,193,136,194,137,195,
                    138,196,140,197,141,198,142,199,143,200,144,201,145,202,146,203, 86,147,
                    204,148,205,149,206,150,151,152,153,154,157,207,213,158,208,214,159,209,
                    215,162,210,216,163,211,217,164,165,166,167,168, 83,169, 84,170, 85,172,
                    173,174,175,186,187, 87,188,218,219, 70,189, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 71,129, 72,130, 73,131, 81,132, 82,133,134,192,135,193,136,194,137,195,
                    138,196,140,197,141,198,142,199,143,200,144,201,145,202,146,203, 86,147,
                    204,148,205,149,206,150,151,152,153,154,157,207,213,158,208,214,159,209,
                    215,162,210,216,163,211,217,164,165,166,167,168, 83,169, 84,170, 85,172,
                    173,174,175,186,187, 87,188,218,219, 70,189,212, 89, 90, 64, 64, 64, 64,
                     64, 64, 64},
                    { 97, 98, 99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,
                    115,116,117,118,119,120, 64, 64, 64, 64, 64, 64, 64, 64, 65, 66, 67, 68,
                     69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
                     87, 88, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    {192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,
                    210,211,212,213,214,215,216,217,218,219,220,221,222,223,224, 64, 64, 64,
                     64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,128,129,130,131,132,133,
                    134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,
                    152,153,154,155,156,157,158,159,160, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                    101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,
                    119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,
                    137, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64},
                    { 64, 64, 64,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,
                    167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,
                    185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,
                    203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,
                    221,222,223,224,225,226,227, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
                     64, 64, 64}
                    });

    private static byte[] ASC_EBC = convertToByteArray(new int[]{ 
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x40,0x5a,0x7f,0x7b,0xe0,0x6c,0x50,0x7d,
            0x4d,0x5d,0x5c,0x4e,0x6b,0x60,0x4b,0x61,
            0xf0,0xf1,0xf2,0xf3,0xf4,0xf5,0xf6,0xf7,
            0xf8,0xf9,0x7a,0x5e,0x4c,0x7e,0x6e,0x6f,
            0x7c,0xc1,0xc2,0xc3,0xc4,0xc5,0xc6,0xc7,
            0xc8,0xc9,0xd1,0xd2,0xd3,0xd4,0xd5,0xd6,
            0xd7,0xd8,0xd9,0xe2,0xe3,0xe4,0xe5,0xe6,
            0xe7,0xe8,0xe9,0xa0,0x3f,0xb0,0x5f,0x6d,
            0x79,0x81,0x82,0x83,0x84,0x85,0x86,0x87,
            0x88,0x89,0x91,0x92,0x93,0x94,0x95,0x96,
            0x97,0x98,0x99,0xa2,0xa3,0xa4,0xa5,0xa6,
            0xa7,0xa8,0xa9,0xc0,0x4f,0xd0,0xa1,0x00,
            0x4a,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00});
    

    private static byte[] EBC_ASC = convertToByteArray(new int[]{ 
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x20,
            0x20,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x80,0x2e,0x3C,0x28,0x2B,0x7C,
            0x26,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x21,0x5C,0x2A,0x29,0x3B,0x5E,
            0x2D,0x2F,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x7C,0x2C,0x25,0x5F,0x3E,0x3F,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x60,0x3A,0x23,0x40,0x27,0x3D,0x22,
            0x00,0x61,0x62,0x63,0x64,0x65,0x66,0x67,
            0x68,0x69,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x6A,0x6B,0x6C,0x6D,0x6E,0x6F,0x70,
            0x71,0x72,0x00,0x00,0x00,0x00,0x00,0x00,
            0x5B,0x7E,0x73,0x74,0x75,0x76,0x77,0x78,
            0x79,0x7A,0x00,0x00,0x00,0x00,0x00,0x00,
            0x5D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
            0x7B,0x41,0x42,0x43,0x44,0x45,0x46,0x47,
            0x48,0x49,0x00,0x00,0x00,0x00,0x00,0x00,
            0x7D,0x4A,0x4B,0x4C,0x4D,0x4E,0x4F,0x50,
            0x51,0x52,0x00,0x00,0x00,0x00,0x00,0x00,
            0x24,0x00,0x53,0x54,0x55,0x56,0x57,0x58,
            0x59,0x5A,0x00,0x00,0x00,0x00,0x00,0x00,
            0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,
            0x38,0x39,0x00,0x00,0x00,0x00,0x00,0x00
          });
    
    public static final byte AtoE(char asciiByte)
    {
       return ASC_EBC[asciiByte&0xFF];
    }

    public static final char EtoA(byte ebcdicByte)
    {
       return (char) (EBC_ASC[ebcdicByte&0xFF] & 0xFF);
    }
    
    private static byte[][] convertToByteArray(int[][] intArray)
    {
        byte[][] result = new byte[intArray.length][intArray[0].length];

        for (int i = 0; i < intArray.length; i++)
        {
            for (int j = 0; j < intArray[i].length; j++)
            {
                result[i][j] =   (byte) intArray[i][j];
            }
            
        }
        return result; 
    }

    private static byte[] convertToByteArray(int[] intArray)
    {
        byte[] result = new byte[intArray.length];

        for (int i = 0; i < intArray.length; i++)
        {
                result[i] =   (byte) intArray[i];
        }
        return result; 
    }
    

    private static final void IBMtoGB(byte _s1, byte _s2, ByteBuffer buffer)
    {
          int s1 = _s1 & 0xFF ;
          int s2 = _s2 & 0xFF ;
        
          int q = s1 * 2 + 31;
          int w = s2;
          
          if ((s1<=0x40) || (s2<=0x40)) 
          {
            q = 161;
            w = 161;
            
          }
          else if (s1<0x47) 
          {
            int i = s1 - 0x41;
            int j = s2 -0x41;
            q = IBMTCHN1[i][j];
            w = IBMTCHN2[i][j];
          }
          else if ((s1<0x48) || ((s1==0x48) && (s2<0x9F))) 
          {
            q = 161;
            w = 161;
          }
          else
          {
              if (w>0x9F) 
              {
                 q = q + 1;
                 w = w - 0x9F + 0xA0 -96;
              }

              w +=  96;
              int q2 = q / 2;
              if ((q2*2!=q) && (w>0xA1+63))  w --;
          }
          
          buffer.put((byte)q);
          buffer.put((byte)w);
    }    
    
    private static final byte[] GBtoIBM(byte _s1, byte _s2 )
    {
      int s1 = _s1 & 0xFF ;  
      int s2 = _s2 & 0xFF ;  
      
      int q = s1;
      int w0 = s2 - 0xA0;
      int q2 = q / 2;
      byte[] t = new byte[2];
      if (q<170) 
      {
        int i = s1 - 161;
        int j = s2 - 161;
        t[0] = CHNTIBM1[i][j];
        t[1] = CHNTIBM2[i][j];
        
      }
      else if (q2*2==q) 
      {
      
        t[0] = (byte) (q2 - 16);
        t[1] = (byte) (0xA0 + w0 - 1);
      } else {
        t[0] = (byte) (q2 - 15);
        t[1] = (byte) (0x40 + w0);
        if (w0>63) t[1] += 1;
      }
      return t ;
    }
    
    
    
    
    public static final byte[] PCHZToIBM(String buffer)
    {
        //boolean ChFlag = false; //汉字处理标志, true--汉字处理中

        int j = 0; //增加报文长度

        //ByteArrayOutputStream m = new ByteArrayOutputStream( (int)(buffer.length * 1.2));
        ByteBuffer buffer2 = ByteBuffer.allocate(buffer.length() * 5);
        
        for (int i = 0; i < buffer.length();)
        {
            
            char srByte = buffer.charAt(i); 
            //srByte>=$A1的处理---------------------------------------------------------
            if (srByte<0xA1) // 是英文字符
            {
                 if ((j>0) && (srByte==0x20)) 
                 {
                     j -= 1;
                     continue;
                 }
                 buffer2.put(AtoE(srByte));
                 //buffer2.put((byte) srByte);
                 i++; 
            }
            else // 中文字符
            {   

                int p1 = i ;
                int p2 = i ++ ;
                while (p2 < buffer.length()) // 汉字
                {
                    if (buffer.charAt(p2) < 0xA1) 
                    {
                        break ;
                    }
                    p2 ++ ;
                }
                buffer2.put((byte)0x0E);
                buffer2.put(GBtoIBM(buffer.substring(p1, p2)));
                buffer2.put((byte)0x0F);
                j += 2;
                i = p2;
            }
        }
        
        byte[] result = new byte[buffer2.position()] ;
        buffer2.rewind();
        buffer2.get(result);
        return result ;
        
    }   
    
    public static String IBMTOPCHZ(byte[] bytes)
    {
        StringBuffer buffer = new StringBuffer(bytes.length);
        for (int i = 0; i < bytes.length; i++)
        {
            byte srByte = bytes[i] ; 
            //srByte>=$A1的处理---------------------------------------------------------
            if (srByte==0x0e) // 是中文分割符号
            {
                int p = i + 1;
                int p2 = bytes.length ;
                while (p < bytes.length )
                {
                    if (bytes[p++]==0x0f) 
                    {
                        p2 = p -1 ;
                        break ;
                    }
                }
                buffer.append(outHZ(bytes, i+1 , p2));
               
                i = p2 ;
            }
            else // 中文字符
            {   
                buffer.append(EtoA(srByte));
            }
        }
        return buffer.toString();
    }
    
    private static String outHZ( byte[] bytes, int from, int to)
    {
        
        ByteBuffer buffer2 = ByteBuffer.allocate(to-from);
        for (int i = from; i < to; i++)
        {
            byte hi = bytes[i++];
            if (i == to ) 
            {
                // 解码少一位 ;
                buffer2.put((byte) '?');
                break ;
            }
            byte low = bytes[i];
            IBMtoGB(hi,low, buffer2);
        }
        buffer2.rewind() ;
        return gbk.decode(buffer2).toString();
        
    }

    private static byte[] GBtoIBM(String string)
    {
        
        final ByteBuffer en = gbk.encode(string);
        int len = en.limit();
        byte[] r = new byte[len];
        en.rewind() ;
        en.get(r);
        for (int i = 0; i < r.length; i++)
        {
            byte[] rr = GBtoIBM(r[i++], r[i]);
            r[i-1] = rr[0]; //$SUP-MDL$
            r[i] = rr[1]; //$SUP-MDL$
        }
        return r ;
    }

    //    
//    public static final int PCtoAS(InputStream in, OutputStream out)
////    var
////      srByte,taByte: Byte;
////      s1,s2,t1,t2: Byte;     //用于汉字的码值转换
////
////      ChFlag: Boolean;       
////      j : Integer;
//    {
//      boolean ChFlag = false; //汉字处理标志, true--汉字处理中
//
//      int j = 0;
//      byte[] buffer = new byte[1024];
//      int position = 0;
//      int bufferlen = 0 ; 
//      
//      
//      while ((bufferlen = in.read(buffer,position,1024-position)) > 0) do
//      {
//         for (int i = 0; i < bufferlen + position; i++)
//        {
//             //srByte>=$A1的处理---------------------------------------------------------
//             if (srByte<0xA1) 
//             {
//               if (ChFlag)     //前面一个字符是汉字,则先在目标流中加上结束符$0F
//               {
//                 ChFlag = false;
//                 taByte = $0F;
//                 Ta_Stream.WriteBuffer(taByte, 1);
//                 j = j+2; //由于添加了0E和OF，报文长度增加了两位，需要截去这个字段后面的两个空格
//               }
//
//               if (j>0) and (SrByte=$20) then
//                 {
//                    j = j-1;
//                    Continue;
//                 }
//
//               taByte = AtoE(srByte);
//               Ta_Stream.WriteBuffer(taByte, 1);
//
//               continue;
//             }
//             //srByte>=$A1的处理---------------------------------------------------------
//             if not ChFlag then   //前面一个字符不是汉字,则先在目标流中加上开始符$0E
//             {
//               ChFlag = true;
//               taByte = $0E;
//               Ta_Stream.WriteBuffer(taByte, 1);
//             }
//
//             s1 = srByte;
//             Sr_Stream.ReadBuffer(s2, 1);   //s1,s2组成一个汉字
//             GBtoIBM(s1,s2,t1,t2);
//             Ta_Stream.WriteBuffer(t1, 1);
//             Ta_Stream.WriteBuffer(t2, 1);
//        }
//
//
//
//      }
//
//      //最后一个字符是汉字,则补上一个结束符 $0F-------------------------------------
//      if ChFlag then
//      {
//        taByte = $0F;
//        Ta_Stream.WriteBuffer(taByte, 1);
//      }
//
//      result = Ta_Stream.Size;
//    }    
    public static void main(String[] args) 
    {
		Socket sock = null;
		try {
			// 连接银行服务器端口
			sock = new Socket("192.12.1.72", 16602);

			// 设置交易超时时间
			sock.setSoTimeout(6000);

			// 得到输出数据流
			BufferedOutputStream out = new BufferedOutputStream(sock
					.getOutputStream());
	
			String message= new String("RQ9105202007-12-312008-01-0112.10.301106  111021     077777770000          9010213010010907027766          福州创业园                                        0000000000090100050100051229");
//			byte[] aa = PCHZToIBM(message);
//			System.out.println(aa.length);
			String len = String.valueOf(message.getBytes().length);
			while (len.length() < 4) {
				len = "0" + len;
//				System.out.println(len);
			}
			String finalMessage = len + message;
			
//			byte[] ed = PCHZToIBM(finalMessage);
//			
//			System.out.println(new String(ed));
//			
//	        for (int i = 0; i < ed.length; i++)
//	        {
//	            System.out.println( Integer.toHexString((ed[i] & 0xFF)));
//	        }
//	        
//	        for (int i = 0; i < ed.length; i++)
//	        {
//	            byte[] es = new byte[ed.length-i];
//	            System.arraycopy(ed, 0, es, 0, es.length);
//	            
//	            System.out.println(IBMTOPCHZ(es));
//	        }
//			
//			// 发送请求数据
//
//
//
//			//
//			 out.write(ed);
			 out.write(finalMessage.getBytes("Cp935"));
			 out.flush();
//            System.out.println(new String(finalMessage.getBytes("Cp935")));
			BufferedInputStream in = new BufferedInputStream(sock
					.getInputStream());
			// 接受返回数据流
			StringBuffer strret = new StringBuffer(1024);
			int c = 0;
			while ((c = in.read()) > 0) {
				strret.append((char) c);
			}

			out.close();
			in.close();
			sock.close();
			// System.out.println("bankresult is"
			// + ASCIIToEBCDIC(strret.toString()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
        

//        System.out.println(IBMTOPCHZ(es));
        
//        byte[] c1 = GBtoIBM(ed[0], ed[1]);
//        
//        for (int i = 0; i < c1.length; i++)
//        {
//            System.out.println(  (c1[i] & 0xFF));
//        }
//        
//        byte[] c2 = IBMtoGB(c1[0], c1[1]);
//        System.out.println(new String(c2,"GB2312"));
        
        
    }

}
