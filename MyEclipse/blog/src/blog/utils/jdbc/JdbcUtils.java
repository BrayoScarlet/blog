package blog.utils.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 使用本方法,必须提供c3p0-config.xml文件
 * @author SolitaryEagle
 *
 */
public class JdbcUtils {
	//饿汉式
	private static DataSource ds = new ComboPooledDataSource();

	/**
	 * 它为null表示没有事务
	 * 它不为null表示有事务
	 * 当开启事务时,需要给它赋值
	 * 当结束事务时需要给它赋值为null
	 * 并且在开启事务时, 让dao的多个方法共享这个Connection
	 */
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	/**
	 * 获取连接池
	 * @return
	 */
	public static DataSource getDataSource() {
		return ds;
	}

	/**
	 * dao使用本方法来获取连接
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		/*
		 * 如果有事务,返回当前事务的con
		 * 如果没有事务,通过连接池返回新的con
		 */
		Connection con = tl.get();	//获取当前线程的事务连接
		if (con != null) {
			return con;
		}
		return ds.getConnection();
	}

	/**
	 * 开启事务
	 * @throws SQLException 
	 */
	public static void beginTransaction() throws SQLException {
		Connection con = tl.get();	//获取当前线程的事务连接
		if (con != null) {
			throw new SQLException("事务已经开启");
		}
		con = ds.getConnection();	//给con赋值
		con.setAutoCommit(false); 	//设置为手动提交
		tl.set(con);	//把当前事务连接放到tl中
	}

	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public static void CommitTransaction() throws SQLException {
		Connection con = tl.get();	//获取当前线程的事务连接
		if (con == null) {
			throw new SQLException("没有事务,不能提交!");
		}
		con.commit(); //提交事务
		con.close();  //关闭连接
		con = null;	 //表示事务结束!
		tl.remove();
	}

	/**
	 * 回滚事务
	 * @throws SQLException
	 */
	public static void rollbackTransaction() throws SQLException {
		Connection con = tl.get(); 	//获取当前线程的事务连接
		if (con == null) {
			throw new SQLException("没有事务,不能回滚!");
		}
		con.rollback(); //回滚事务
		con.close(); 	//关闭事务
		con = null;		//表示事务结束
		tl.remove();
	}

	/**
	 * 释放连接
	 * @param connection
	 * @throws SQLException
	 */
	public static void releaseConnection(Connection connection) throws SQLException {
		Connection con = tl.get(); 	//获取当前线程的事务连接
		//如果参数连接,与当前事务连接不同,说明这个连接不是当前事务,可以关闭
		if (connection != con) {
			//如果参数连接没有关闭, 关闭之
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

}
