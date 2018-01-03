package test;

import org.junit.Test;

public class StringTest {

	@Test
	public void fun1() {
		String str = "       ";
		System.out.println("=========" + str.trim() + "==========");
		String[] strings = str.trim().split(" +");
		System.out.println(strings.length);
		for (String string : strings) {
			System.out.println("=========" + string + "============");
		}
	}
}
