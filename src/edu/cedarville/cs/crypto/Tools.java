package edu.cedarville.cs.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Tools {
	
	public static Integer[] convertFromBytesToInts(byte[] bs) {
        Integer[] ints = new Integer[bs.length/4 + (bs.length % 4 != 0 ? 1 : 0)];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            int a,b,c,d;
            
            a = (j   < bs.length ? bs[j]   : 0x00) << 24;
            b = (j+1 < bs.length ? bs[j+1] : 0x00) << 16 ;
            c = (j+2 < bs.length ? bs[j+2] : 0x00) << 8 ;
            d = (j+3 < bs.length ? bs[j+3] : 0x00);
            
            ints[i] = a | b | c | d;
            
            j += 4;
        }
		return ints;
	}
	
	public static Integer[] convertFromHexStringToInts(String s) {

        BigInteger uncipheredInt = new BigInteger(s, 8);

        int numOfBytes = uncipheredInt.bitLength()/8;
        System.out.println("Number of bytes in unciphered file: " + numOfBytes);
        Integer[] uncipheredInts = new Integer[numOfBytes];
        
		return null;
	}
	
	public static byte[] convertFromIntsToBytes(Integer[] ints) {
        byte[] bytes = new byte[ints.length * 4];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            
            bytes[j]   |= ints[i];
            bytes[j+1] |= ints[i] >>> 8;
            bytes[j+2] |= ints[i] >>> 16;
            bytes[j+3] |= ints[i] >>> 24;
            
            j += 4;
        }
		return bytes;
	}
	
	public static String convertFromIntsToHexString(Integer[] ints) {
		return null;
	}
    
    public static void main (String[] args) {
        String str = "Hello world!";
        try {
            BigInteger test = new BigInteger(str.getBytes("UTF-8"));
            Integer[] testInt = {20};
            
            System.out.println("Initial value: " + Integer.toBinaryString(testInt[0]));
            
            byte[] bytes = convertFromIntsToBytes(testInt);
            System.out.println("Converted to: " + bytes[0]);
           
            testInt = convertFromBytesToInts(bytes);
            System.out.println("End value: " + Integer.toBinaryString(testInt[0]));
        }
        
        catch (UnsupportedEncodingException e) {
            
        }
    }

}
