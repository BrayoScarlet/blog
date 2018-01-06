package test;

import org.junit.Test;

import blog.utils.commons.CommonUtils;
import blog.utils.encryption.Encryption;

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

	@Test
	public void fun2() {
		System.out.println(CommonUtils.uuid());
		System.out.println(Encryption.getMD5("admin1"));
		System.out.println(Encryption.getMD5("admin2"));
	}

	int[] arr = new int[5];

}
