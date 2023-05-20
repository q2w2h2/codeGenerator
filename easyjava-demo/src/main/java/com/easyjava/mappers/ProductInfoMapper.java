package com.easyjava.mappers;

import org.springframework.data.repository.query.Param;

/**
 * @Description: 商品信息Mapper对象
 * @author: 小阙
 * @date: 2023/05/20
 */
public interface ProductInfoMapper<T, P> extends BaseMapper {
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

	/**
	 * 根据Code查询
	 */
	T selectByCode(@Param("code") String code);

	/**
	 * 根据Code更新
	 */
	Long updateByCode(@Param("bean") T t, @Param("code") String code);

	/**
	 * 根据Code删除
	 */
	Long deleteByCode(@Param("code") String code);

	/**
	 * 根据SkuTypeAndColorType查询
	 */
	T selectBySkuTypeAndColorType(@Param("skuType") Integer skuType, @Param("colorType") String colorType);

	/**
	 * 根据SkuTypeAndColorType更新
	 */
	Long updateBySkuTypeAndColorType(@Param("bean") T t, @Param("skuType") Integer skuType, @Param("colorType") String colorType);

	/**
	 * 根据SkuTypeAndColorType删除
	 */
	Long deleteBySkuTypeAndColorType(@Param("skuType") Integer skuType, @Param("colorType") String colorType);


}