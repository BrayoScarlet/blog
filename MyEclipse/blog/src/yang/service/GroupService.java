package yang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.user.domain.User;
import yang.dao.MemberInfoDao;
import yang.dao.StuGroupInfoDao;
import yang.model.StuGroupInfo;

@Service
public class GroupService {
	@Autowired
	private StuGroupInfoDao stugroupInfoDao = null;
	@Autowired
	private MemberInfoDao memberInfoDao = null;

	//查询所有学习小组
	public StuGroupInfo[] findAllGroups() {
		StuGroupInfo[] groups = stugroupInfoDao.findAllGroups();
		return groups;
	}

	//根据Id查找学习小组
	public StuGroupInfo findGroupById(String stugroupId) {
		StuGroupInfo group = stugroupInfoDao.findGroupById(stugroupId);
		return group;
	}

	//新增一个学习小组
	public boolean insertGroup(StuGroupInfo group) {
		return stugroupInfoDao.insertGroup(group);
	}

	//根据名字模糊查询
	/*
	 * 	public StuGroupInfo[] searchGroupByName(String stugroupname) {
		StuGroupInfo[] groups = stugroupInfoDao.searchGroupByName(stugroupname);
		return groups;
	}*/

	//根据关键词模糊查询小组名字和简介
	public StuGroupInfo[] searchGroupByKey(String keywords) {
		StuGroupInfo[] groups = stugroupInfoDao.searchGroupByKey(keywords);
		return groups;
	}

	//当有用户加入小组时，人数加1
	public boolean plusMemberNum(String stugroupId) {
		return stugroupInfoDao.plusMemberNum(stugroupId);
	}

	//////////////////////////////////////////
	//新增一个小组的成员
	public boolean insertMemberInfo(StuGroupInfo group, User user) {
		if (user == null) {
			return false;
		}
		return memberInfoDao.insertMemberInfo(group, user);
	}

	//根据用户是否属于某个学习小组
	public boolean isofMember(String stugroupId, User user) {
		return memberInfoDao.isofMember(stugroupId, user);
	}

}
