package com.easyjava.entity.po;

import java.io.Serializable;

public class ProductInfo implements Serializable {
	private Integer id;
	private String companyId;
	private String code;
	private String productName;
	private BigDecimal price;
	private Integer skuType;
	private String colorType;
	private Date creareTime;
	private Date creareData;
	private Long stock;
	private Integer status;
}