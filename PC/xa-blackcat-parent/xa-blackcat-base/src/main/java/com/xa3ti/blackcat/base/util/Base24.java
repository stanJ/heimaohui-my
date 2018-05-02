package com.xa3ti.blackcat.base.util;

import java.nio.ByteBuffer;



/**
 * @author nijie
 *
 */
public class Base24 {
	private static String sel = "BCDFGHJKMPQRTVWXY2346789";

	public static String Encode(byte[] bytes) {
		String Text = new String(bytes);
		//System.out.println("Text->" + Text);
		int i = 0;
		int Pos = 0;
		char[] Buf = new char[Text.length() << 1];
		//System.out.println("Text.length()<<1->" + (Text.length() << 1));
		while ((i = Pos) < Text.length()) {
			Buf[i << 1] = sel.charAt((Text.charAt(Pos) >> 4));
			Buf[(i << 1) + 1] = sel.charAt(23 - (Text.charAt(Pos) & 0x0F));
			Pos++;
		}

		return new String(Buf);
	}

	public static String Decode(String Text) {
		if (Text.length() % 2 != 0)
			return null;

		int[] NPos = new int[2];
		char[] N = new char[2];
		char[] Buf = new char[Text.length() >> 1];

		for (int i = 0; i < (Text.length() >> 1); i++) {
			NPos[0] = sel.indexOf(Text.charAt(i << 1));
			NPos[1] = 23 - sel.indexOf(Text.charAt((i << 1) + 1));
			if (NPos[0] < 0 || NPos[1] < 0) {
				return null;
			}

			Buf[i] = ((char) ((NPos[0] << 4) | NPos[1]));
		}
		return new String(Buf);
	}

	public static int getUnsignedIntt(int data) { // 将int数据转换为0~4294967295
													// (0xFFFFFFFF即DWORD)。
		return data & 0x0FFFFFFFF;
	}

	public void printBytes(byte[] b) {
		System.out.println("bytes=========>" + b.length);
		for (int i = 0; i < b.length; i++) {
			System.out.printf("%02X", b[i]);
		}
	}

	public static void main(String[] args) {
		String encoded = Base24.Encode("CSfX7ACGoQk=".getBytes());
		System.out.println("encoded->" + encoded);

		String decoded = Base24.Decode(encoded);
		System.out.println("decoded->" + decoded);
	}

}
