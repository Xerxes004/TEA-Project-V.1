package edu.cedarville.cs.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Tools {
    /**
     * Converts a byte[] array into an Integer[] array
     *
     * @param bs byte[] array to be converted
     * @return Integer[] array created from byte[] array
     */
    public static Integer[] convertFromBytesToInts(byte[] bs) {
        Integer[] ints = new Integer[bs.length / 4 + (bs.length % 4 != 0 ? 1 : 0)];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            int a, b, c, d;

            a = (j     < bs.length ? bs[j] : 0) << 24;
            b = (j + 1 < bs.length ? bs[j + 1] : 0) << 16;
            c = (j + 2 < bs.length ? bs[j + 2] : 0) << 8;
            d = (j + 3 < bs.length ? bs[j + 3] : 0);

            ints[i] = a | b | c | d;

            j += 4;
        }
        return ints;
    }

    /**
     * Converts a string of hex characters into an Integer[] array using
     * BigInteger.
     *
     * @param s string of hex characters
     * @return Integer[] array created from input hex string
     */
    public static Integer[] convertFromHexStringToInts(String s) {
        int numOfHexStrings = s.length() / 8 + (s.length() % 8 != 0 ? 1 : 0);

        String[] strings = new String[numOfHexStrings];

        int j = 0;
        System.out.println("String is " + s);

        for (int i = 0; i < numOfHexStrings; i++) {

            strings[i] = s.substring(j, j + (s.length() - j < 8 ? s.length() - j : 8));
            
            if (strings[i].length() < 8) {
                String str = "";
                for (int k = 0; k < (8 - strings[i].length()); k++) {
                    str += "0";
                }
                strings[i] = str + strings[i];
            }
            System.out.println("Made: " + strings[i]);
            j += 8;
        }

        Integer[] ints = new Integer[numOfHexStrings];
        for (int i = 0; i < strings.length; i++) {
            BigInteger uncipheredInt = new BigInteger(strings[i], 16);
            ints[i] = uncipheredInt.intValue();
            System.out.println("::" + Integer.toHexString(ints[i]));
        }

        return ints;
    }
    /**
     * Converts from an Integer[] array to a byte[] array
     *
     * @param ints
     * @return
     */
    public static byte[] convertFromIntsToBytes(Integer[] ints) {
        byte[] bytes = new byte[ints.length * 4];
        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            
            bytes[j]     |= ints[i] >>> 24;
            bytes[j + 1] |= ints[i] >>> 16;
            bytes[j + 2] |= ints[i] >>> 8;
            bytes[j + 3] |= ints[i];

            j += 4;
        }
        return bytes;
    }

    public static String convertFromIntsToHexString(Integer[] ints) {
        String hexStr = "";
        for (int i = 0; i < ints.length; i++) {
            hexStr += Integer.toHexString(ints[i]);
            System.out.println("Converted " + ints[i] + " to " + Integer.toHexString(ints[i]));
        }
        System.out.println("Converted to: " + hexStr);
        
        return hexStr;
    }

    private static void printByteArrayAsBinary(byte[] bytes) {
        System.out.print("Printing");
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
        Integer[] testInt = {0xffff, 0x10111100, 0x01020304};

        printIntArrayAsBinary(testInt);

        byte[] bytes = convertFromIntsToBytes(testInt);

        printByteArrayAsBinary(bytes);

        testInt = convertFromBytesToInts(bytes);

        printIntArrayAsBinary(testInt);

        String s = "a56babcd00000000ffffffffabcdef01";
        printIntArrayAsBinary(convertFromHexStringToInts(s));
        //System.out.println("Hex string: " + convertFromIntsToHexString(convertFromHexStringToInts(s)));
    }
}
