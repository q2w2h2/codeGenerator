package com.easyjava.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;

import com.easyjava.enums.DateTimePatternEnum;
import com.easyjava.utils.DateUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description: 商品信息
 * @author: 小阙
 * @date: 2023/05/20
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
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private Date createTime;
	/**
	 * 创建日期
	 */
	@JsonFormat(pattern = "YYYY-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private Date createData;
	/**
	 * 库存
	 */
	private Long stock;
	/**
	 * 状态
	 */
	@JsonIgnore
	private Integer status;

	public Integer getId() {
		return this.id;
	}

	public Integer setId(Integer id) {
		return this.id = id;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public String setCompanyId(String companyId) {
		return this.companyId = companyId;
	}

	public String getCode() {
		return this.code;
	}

	public String setCode(String code) {
		return this.code = code;
	}

	public String getProductName() {
		return this.productName;
	}

	public String setProductName(String productName) {
		return this.productName = productName;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public BigDecimal setPrice(BigDecimal price) {
		return this.price = price;
	}

	public Integer getSkuType() {
		return this.skuType;
	}

	public Integer setSkuType(Integer skuType) {
		return this.skuType = skuType;
	}

	public String getColorType() {
		return this.colorType;
	}

	public String setColorType(String colorType) {
		return this.colorType = colorType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date setCreateTime(Date createTime) {
		return this.createTime = createTime;
	}

	public Date getCreateData() {
		return this.createData;
	}

	public Date setCreateData(Date createData) {
		return this.createData = createData;
	}

	public Long getStock() {
		return this.stock;
	}

	public Long setStock(Long stock) {
		return this.stock = stock;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer setStatus(Integer status) {
		return this.status = status;
	}

	@Override
	public String toString() {
		return " 自增id:" + (id == null ? "空 " : id) +  " 公司id:" + (companyId == null ? "空 " : companyId) +  " 商品编号:" + (code == null ? "空 " : code) +  " 商品名称:" + (productName == null ? "空 " : productName) +  " 价格:" + (price == null ? "空 " : price) +  " sku模型:" + (skuType == null ? "空 " : skuType) +  " 颜色类型:" + (colorType == null ? "空 " : colorType) +  " 创建时间:" + (createTime == null ? "空 " : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) +  " 创建日期:" + (createData == null ? "空 " : DateUtils.format(createData, DateTimePatternEnum.YYYY_MM_DD.getPattern())) +  " 库存:" + (stock == null ? "空 " : stock) +  " 状态:" + (status == null ? "空 " : status) ;
	}
}