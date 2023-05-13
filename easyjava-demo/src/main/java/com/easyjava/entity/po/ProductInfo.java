package com.easyjava.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @Description: 商品信息

 * @author: 小阙
 * @date: 2023/24/13
 */
public class ProductInfo implements Serializable {
	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 公司id
	 */
	@JsonIgnore
	private String companyId;
	/**
	 * 商品编号
	 */
	private String code;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * sku模型
	 */
	private Integer skuType;
	/**
	 * 颜色类型
	 */
	private String colorType;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createTime;
	/**
	 * 创建日期
	 */
	@JsonFormat(pattern = "yyyy-mm-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createData;
	/**
	 * 库存
	 */
	private Long stock;
	/**
	 * 状态·
	 */
	@JsonIgnore
	private Integer status;
}