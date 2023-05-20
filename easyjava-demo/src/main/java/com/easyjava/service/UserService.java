package com.easyjava.service;

import com.easyjava.entity.po.User;
import com.easyjava.entity.query.UserQuery;
import com.easyjava.entity.vo.PaginationResultVO;

import java.util.List;
/**
 * @Description: 用户表业务接口
 * @author: 小阙
 * @date: 2023/05/20
 */
public interface UserService {
	/**
	 * 根据条件查询列表
	 */
	List<User> findListByParam(UserQuery query);

	/**
	 * 根据条件查询数量
	 */
	Long findCountByParam(UserQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<User> findListByPage(UserQuery query);

	/**
	 * 新增
	 */
	Long add(User bean);

	/**
	 * 批量新增
	 */
	Long addBatch(List<User> listBean);

	/**
	 * 批量新增或修改
	 */
	Long addOrUpdateBatch(List<User> listBean);

	/**
	 * 根据Id查询
	 */
	User getById(Integer id);

	/**
	 * 根据Id更新
	 */
	Long updateById(User bean, Integer id);

	/**
	 * 根据Id删除
	 */
	Long deleteById(Integer id);

}