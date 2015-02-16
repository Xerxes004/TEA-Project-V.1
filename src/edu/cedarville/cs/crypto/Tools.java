package edu.cedarville.cs.crypto;

import java.math.BigInteger;

public class Tools {

    public static Integer[] convertFromBytesToInts(byte[] bs) {
        Integer[] ints = new Integer[bs.length / 4 + (bs.length % 4 != 0 ? 1 : 0)];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            int a, b, c, d;

            a = (j + 3 < bs.length ? bs[j + 3] : 0) << 24;
            b = (j + 2 < bs.length ? bs[j + 2] : 0) << 16;
            c = (j + 1 < bs.length ? bs[j + 1] : 0) << 8;
            d = (j < bs.length ? bs[j] : 0);

            ints[i] = a | b | c | d;

            j += 4;
        }
        return ints;
    }

    public static Integer[] convertFromHexStringToInts(String s) {
        int numOfHexStrings = s.length()/4 + (s.length() % 4 != 0 ? 1 : 0);
        String[] strings = new String[numOfHexStrings];
        int j = 0;
        for (int i = 0; i < numOfHexStrings; i++) {
            strings[i] = s.substring(j, j+4);
            j += 4;
        }
        Integer[] ints = new Integer[numOfHexStrings];
        for (int i = 0; i < numOfHexStrings; i++) {
            BigInteger uncipheredInt = new BigInteger(strings[i], 8);
            ints[i] = uncipheredInt.intValue();
        }
        
        return ints;
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
        System.out.print("Printing ");
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
            if (i % 4 == 0) {
                System.out.println("");
            }
            System.out.print(strings[i] + " ");
        }
        System.out.println("\n");
    }

    private static void printIntArrayAsBinary(Integer[] ints) {
        printByteArrayAsBinary(convertFromIntsToBytes(ints));
    }

    public static void main(String[] args) {
        String str = "Hello world!";

        Integer[] testInt = {100, 0x00111100};

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
