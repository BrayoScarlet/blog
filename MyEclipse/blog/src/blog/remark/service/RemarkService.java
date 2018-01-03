package blog.remark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blog.article.dao.ArticleDao;
import blog.article.domain.Article;
import blog.remark.dao.RemarkDao;
import blog.remark.domain.Remark;
import blog.user.dao.UserDao;
import blog.user.domain.User;
import blog.utils.commons.CommonUtils;

public class RemarkService {
	private RemarkDao remarkDao = new RemarkDao();
	private UserDao userDao = new UserDao();
	private ArticleDao articleDao = new ArticleDao();

	/**
	 * 添加回复业务
	 * @param remark
	 */
	public void addRemark(Remark remark) {
		remarkDao.addRemark(remark);
	}

	/**
	 * 按aid查询所有的回复信息, 即某一篇博客的所有回复信息
	 * @param articleAid
	 * @param article 
	 * @return
	 */
	public List<Remark> findRemarksByArticle(Article article) {
		/*
		 * 1, 按aid查询出所有的回复记录, 封装到List<Map>中
		 * 2, 创建一个remark对象
		 * 3, 根据Map中uid, 使用userDao查询出user对象,并赋给remark
		 * 4, 将参数中的article赋给remark对象
		 * 5, 将所有的remark添加到list中
		 */

		List<Remark> remarkList = new ArrayList<Remark>();
		List<Map<String, Object>> listMap =
				remarkDao.findRemarkRecordsByAid(article.getAid());
		for (Map<String, Object> map : listMap) {
			Remark remark = CommonUtils.toBean(map, Remark.class);
			User author = userDao.findByUid(map.get("uid") + "");
			remark.setAuthor(author);
			remark.setArticle(article);
			remarkList.add(remark);
		}
		return remarkList;
	}

	/**
	 * 按rid查询aid业务
	 * @param rid
	 * @return
	 */
	public String findAidByRid(String rid) {
		return remarkDao.findAidByRid(rid);
	}

	/**
	 * 根据rid删除对应的记录业务
	 * @param rid
	 */
	public void deleteRemarkByRid(String rid) {
		remarkDao.deleteRemarkByRid(rid);
	}

}
