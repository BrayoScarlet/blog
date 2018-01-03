package blog.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import blog.user.domain.User;
import blog.utils.jdbc.JdbcUtils;
import blog.utils.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner(JdbcUtils.getDataSource(), true);

	/**
	 * 按用户名查询
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		//使用别名处理数据库字段名与JavaBean属性名不一致的情况
		String sql = "select uid, email, username, `password`, school, qualification, "
				+ "graduation_year as graduationYear, specialisations, sex, ubrief, "
				+ "domicile, token, state, admin from `user` where username=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按邮箱查询
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		String sql = "select uid, email, username, `password`, school, qualification, "
				+ "graduation_year as graduationYear, specialisations, sex, ubrief, "
				+ "domicile, token, state, admin from `user` where email=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入用户到数据库
	 * @param form
	 */
	public void add(User form) {
		String sql = "insert into `user` values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { form.getUid(), form.getEmail(), form.getUsername(),
				form.getPassword(), form.getSchool(), form.getQualification(),
				form.getGraduationYear(), form.getSpecialisations(), form.getSex(),
				form.getUbrief(), form.getDomicile(), form.getToken(), form.isState(),
				form.getAdmin() };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按激活啊查询
	 * @param code
	 * @return
	 */
	public User findByToken(String token) {
		String sql = "select uid, email, username, `password`, school, qualification, "
				+ "graduation_year as graduationYear, specialisations, sex, ubrief, "
				+ "domicile, token, state, admin from `user` where token=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), token);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更新激活状态
	 * @param uid
	 * @param b
	 */
	public void updateState(String uid, boolean state) {
		String sql = "update `user` set state=? where uid=?";
		try {
			qr.update(sql, state, uid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按uid更新密码
	 * @param uid
	 * @param password
	 */
	public void updatePasswordByUid(String uid, String password) {
		String sql = "update `user` set `password`=? where uid=?";
		try {
			qr.update(sql, password, uid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按uid更新user的资料
	 * @param user
	 */
	public void updateUser(User user) {
		String sql = "update `user` set school=?, qualification=?, "
				+ "graduation_year=?, specialisations=?, sex=?, "
				+ "ubrief=?, domicile=? where uid=? ";
		Object[] params = { user.getSchool(), user.getQualification(),
				user.getGraduationYear(), user.getSpecialisations(), user.getSex(),
				user.getUbrief(), user.getDomicile(), user.getUid() };
		try {
			qr.update(sql, params);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按uid查询user
	 * @param uid
	 * @return
	 */
	public User findByUid(String uid) {
		String sql = "select uid, email, username, `password`, school, qualification, "
				+ "graduation_year as graduationYear, specialisations, sex, ubrief, "
				+ "domicile, token, state, admin from `user` where uid=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), uid);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
