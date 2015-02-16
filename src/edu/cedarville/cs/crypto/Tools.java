package edu.cedarville.cs.crypto;

import java.math.BigInteger;

public class Tools {

    public static Integer[] convertFromBytesToInts(byte[] bs) {
        Integer[] ints = new Integer[bs.length / 4 + (bs.length % 4 != 0 ? 1 : 0)];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            int a, b, c, d;

            a = (j < bs.length ? bs[j] : 0) << 24;
            b = (j + 1 < bs.length ? bs[j + 1] : 0) << 16;
            c = (j + 2 < bs.length ? bs[j + 2] : 0) << 8;
            d = (j + 3 < bs.length ? bs[j + 3] : 0);

            ints[i] = a | b | c | d;

            j += 4;
        }
        return ints;
    }

    public static Integer[] convertFromHexStringToInts(String s) {

        BigInteger uncipheredInt = new BigInteger(s, 8);

        int numOfBytes = uncipheredInt.bitLength() / 8 + (uncipheredInt.bitLength() % 8 == 0 ? 1 : 0);
        System.out.println("Number of bytes in unciphered file: " + numOfBytes);
        
        Integer[] uncipheredInts = new Integer[numOfBytes];

        return null;
    }

    public static byte[] convertFromIntsToBytes(Integer[] ints) {
        byte[] bytes = new byte[ints.length * 4];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {

            bytes[j] |= ints[i];
            bytes[j + 1] |= ints[i] >>> 8;
            bytes[j + 2] |= ints[i] >>> 16;
            bytes[j + 3] |= ints[i] >>> 24;

            j += 4;
        }
        return bytes;
    }

    public static String convertFromIntsToHexString(Integer[] ints) {
        return null;
    }
    
    private static void printByteArrayAsBinary(byte[] bytes) {
        System.out.print("Byte array: ");
        String[] strings = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            strings[i] = Integer.toBinaryString(bytes[i]);
            String s = "";
            for (int j = 0; j < 8 - strings[i].length(); j++) {
                s += "0";
            }
            strings[i] = s + strings[i];
        }
        for (int i = 0; i < bytes.length; i++) {
            if (i%4 == 0) {
                System.out.println("");
            }
            System.out.print(strings[i] + " ");
        }
        System.out.println("\n");
    }
    
    private static void printIntArrayAsBinary(Integer[] ints) {
        System.out.print("Integer array: ");
        String[] strings = new String[ints.length];
        for (int i = 0; i < ints.length; i++) {
            strings[i] = Integer.toBinaryString(ints[i]);
            String s = "";
            for (int j = 0; j < 8 - strings[i].length(); j++) {
                s += "0";
            }
            strings[i] = s + strings[i];
        }
        for (int i = 0; i < ints.length; i++) {
            if (i%4 == 0) {
                System.out.println("");
            }
            System.out.print(strings[i] + " ");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        String str = "Hello world!";

        Integer[] testInt = {0x11000011, 0x00111100};

        printIntArrayAsBinary(testInt);
        
        byte[] bytes = convertFromIntsToBytes(testInt);

        printByteArrayAsBinary(bytes);
       
        testInt = convertFromBytesToInts(bytes);

        printIntArrayAsBinary(testInt);
        
        int i = (((1 & 0xff) << 24)
                | ((-10 & 0xff) << 16)
                | ((-100 & 0xff) << 8)
                | ((-1 & 0xff)));
        System.out.println(i);
        System.out.println(i);
    }

}
