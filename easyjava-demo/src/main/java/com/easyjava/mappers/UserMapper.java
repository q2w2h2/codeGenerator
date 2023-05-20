package com.easyjava.mappers;

import org.springframework.data.repository.query.Param;

/**
 * @Description: 用户表Mapper对象
 * @author: 小阙
 * @date: 2023/05/20
 */
public interface UserMapper<T, P> extends BaseMapper {
	/**
	 * 根据Id查询
	 */
	T selectById(@Param("id") Integer id);

	/**
	 * 根据Id更新
	 */
	Long updateById(@Param("bean") T t, @Param("id") Integer id);

	/**
	 * 根据Id删除
	 */
	Long deleteById(@Param("id") Integer id);


}