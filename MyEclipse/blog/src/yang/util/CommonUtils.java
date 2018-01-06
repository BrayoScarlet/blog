package yang.util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.beanutils.ConvertUtils;

/**
 * 小工具
 * @author SolitaryEagle
 *
 */
public class CommonUtils {

	/**
	 * 把map转换成bean对象
	 * @param map
	 * @param clazz
	 * @return
	 */
	/*
	 * 	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			ConvertUtils.register(new DateConverter(), Date.class);
			BeanUtils.populate(bean, map);
			return bean;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}*/


	/**
	 * 生成一个不重复的32为字符串
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}
