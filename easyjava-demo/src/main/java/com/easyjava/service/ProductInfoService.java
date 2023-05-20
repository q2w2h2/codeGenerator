package com.easyjava.service;

import com.easyjava.entity.po.ProductInfo;
import com.easyjava.entity.query.ProductInfoQuery;
import com.easyjava.entity.vo.PaginationResultVO;

import java.util.List;
/**
 * @Description: 商品信息业务接口
 * @author: 小阙
 * @date: 2023/05/20
 */
public interface ProductInfoService {
	/**
	 * 根据条件查询列表
	 */
	List<ProductInfo> findListByParam(ProductInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Long findCountByParam(ProductInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ProductInfo> findListByPage(ProductInfoQuery query);

	/**
	 * 新增
	 */
	Long add(ProductInfo bean);

	/**
	 * 批量新增
	 */
	Long addBatch(List<ProductInfo> listBean);

	/**
	 * 批量新增或修改
	 */
	Long addOrUpdateBatch(List<ProductInfo> listBean);

	/**
	 * 根据Id查询
	 */
	ProductInfo getById(Integer id);

	/**
	 * 根据Id更新
	 */
	Long updateById(ProductInfo bean, Integer id);

	/**
	 * 根据Id删除
	 */
	Long deleteById(Integer id);

	/**
	 * 根据Code查询
	 */
	ProductInfo getByCode(String code);

	/**
	 * 根据Code更新
	 */
	Long updateByCode(ProductInfo bean, String code);

	/**
	 * 根据Code删除
	 */
	Long deleteByCode(String code);

	/**
	 * 根据SkuTypeAndColorType查询
	 */
	ProductInfo getBySkuTypeAndColorType(Integer skuType, String colorType);

	/**
	 * 根据SkuTypeAndColorType更新
	 */
	Long updateBySkuTypeAndColorType(ProductInfo bean, Integer skuType, String colorType);

	/**
	 * 根据SkuTypeAndColorType删除
	 */
	Long deleteBySkuTypeAndColorType(Integer skuType, String colorType);

}