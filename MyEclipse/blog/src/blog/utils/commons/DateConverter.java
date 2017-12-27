package blog.utils.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 * 把String转换成java.util.Date的类型转换器
 * @author SolitaryEagle
 *
 */
public class DateConverter implements Converter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object convert(Class type, Object value) {
		if (value == null) {	//如果要转换成的值为null, 那么直接返回null
			return null;
		}
		//如果要转换的值不是String, 那么就不转了,直接返回
		if (!(value instanceof String)) {
			return value;
		}
		//把值转换成String
		String val = (String) value;
		//使用SimpleDateFormat进行转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(val);
		}
		catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
