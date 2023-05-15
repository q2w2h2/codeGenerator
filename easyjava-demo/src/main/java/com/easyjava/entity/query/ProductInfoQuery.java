package com.easyjava.entity.query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 商品信息查询对象
 * @author: 小阙
 * @date: 2023/09/15
 */
public class ProductInfoQuery {
	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 公司id
	 */
	private String companyId;
	private String companyIdFuzzy;
	/**
	 * 商品编号
	 */
	private String code;
	private String codeFuzzy;
	/**
	 * 商品名称
	 */
	private String productName;
	private String productNameFuzzy;
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
	private String colorTypeFuzzy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	private String createTimeStart;
	private String createTimeEnd;
	/**
	 * 创建日期
	 */
	private Date createData;
	private String createDataStart;
	private String createDataEnd;
	/**
	 * 库存
	 */
	private Long stock;
	/**
	 * 状态
	 */
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

	public String getCompanyIdFuzzy() {
		return this.companyIdFuzzy;
	}

	public String setCompanyIdFuzzy(String companyIdFuzzy) {
		return this.companyIdFuzzy = companyIdFuzzy;
	}

	public String getCodeFuzzy() {
		return this.codeFuzzy;
	}

	public String setCodeFuzzy(String codeFuzzy) {
		return this.codeFuzzy = codeFuzzy;
	}

	public String getProductNameFuzzy() {
		return this.productNameFuzzy;
	}

	public String setProductNameFuzzy(String productNameFuzzy) {
		return this.productNameFuzzy = productNameFuzzy;
	}

	public String getColorTypeFuzzy() {
		return this.colorTypeFuzzy;
	}

	public String setColorTypeFuzzy(String colorTypeFuzzy) {
		return this.colorTypeFuzzy = colorTypeFuzzy;
	}

	public String getCreateTimeStart() {
		return this.createTimeStart;
	}

	public String setCreateTimeStart(String createTimeStart) {
		return this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return this.createTimeEnd;
	}

	public String setCreateTimeEnd(String createTimeEnd) {
		return this.createTimeEnd = createTimeEnd;
	}

	public String getCreateDataStart() {
		return this.createDataStart;
	}

	public String setCreateDataStart(String createDataStart) {
		return this.createDataStart = createDataStart;
	}

	public String getCreateDataEnd() {
		return this.createDataEnd;
	}

	public String setCreateDataEnd(String createDataEnd) {
		return this.createDataEnd = createDataEnd;
	}

}