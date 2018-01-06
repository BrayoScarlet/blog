package yang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yang.dao.FeedBackInfoDao;
import yang.model.FeedBackInfo;

@Service
public class FeedBackService {
	@Autowired
	private FeedBackInfoDao feedbackInfoDao = null;
	
	//查看所有的反馈信息
	public FeedBackInfo[] findAllFeedBackInfos() {
		FeedBackInfo[] feeds = feedbackInfoDao.findAllFeedBackInfos();
		return feeds;
	} 
	
	//新增一条反馈信息
	public boolean insertFeedBackInfo(FeedBackInfo feed) {
		return feedbackInfoDao.insertFeedBackInfo(feed);
	}
	
	//删除指定id的反馈信息
	public boolean deletefeedById(String fbId) {
		return feedbackInfoDao.deletefeedById(fbId);
	}
}
