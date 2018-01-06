package test.yang.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yang.dao.StuGroupInfoDao;

public class StuGroupInfoDaoTest {
	private static StuGroupInfoDaoTest stu = new StuGroupInfoDaoTest();

	@Test
	public void test() {
		ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
		StuGroupInfoDao stuGroupInfoDao =
				app.getBean("stuGroupInfoDao", StuGroupInfoDao.class);
		stuGroupInfoDao.deleteGroupById("4132");
	}

	public static void main(String[] args) {
		stu.test();
	}

}
