package com.ai.ecp.app.resp.gds;

import java.sql.Timestamp;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class GdsCollectBaseInfo extends AppBody{
	/**
	 * 收藏主键
	 */
	private Long id;

	/**
	 * 商品编码
	 */
    private Long gdsId;

    /**
     * 单品编码
     */
    private Long skuId;

    /**
     * 店铺编码
     */
    private Long shopId;

    /**
     * 商品名称
     */
    private String gdsName;
    
    /**
     * 单品属性串
     */
	private String skuProps;

	/**
	 * 收藏时价格
	 */
    private Long gdsPrice;
    /**
     * 指导价
     */
    private Long guidePrice;

    /**
     * 用户编码
     */
    private Long staffId;
    
    /**
     * 用户名
     */
    private String staffName;

    /**
     * 收藏时间
     */
    private Timestamp collectionTime;

    /**
     * 现价
     */
    private Long nowPrice;

    /**
     * 用户收藏统计数
     */
    private Long collectStaffCount;
    
    /**
     * 主图UUID
     */
    private String skuMainPicUrl;
    
    /**
     * 商品链接
     */
    private String gdsUrl;
    
    /**
     * 库存描述
     */
    private String stockInfo;
    
    /**
     * 库存数量
     */
    private Long stock;
    
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private String status;
    /**
     * 商品分类
     */
    private String catgCode;
	/**
	 * 是否虚拟商品
	 */
	private boolean virtual;
    
    public String getCatgCode() {
		return catgCode;
	}

	public void setCatgCode(String catgCode) {
		this.catgCode = catgCode;
	}

	private Timestamp createTime;

    private Long createStaff;

    private Timestamp updateTime;

    private Long updateStaff;
    
    private String gdsStatus;
    
    private Long reducePrice;
    
    /**
     * 商品类型 (用于判断虚拟商品不能重复加入购物车)
     * add by gxq
     */
    private Long gdsTypeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGdsId() {
		return gdsId;
	}

	public void setGdsId(Long gdsId) {
		this.gdsId = gdsId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getGdsName() {
		return gdsName;
	}

	public void setGdsName(String gdsName) {
		this.gdsName = gdsName;
	}

	public String getSkuProps() {
		return skuProps;
	}

	public void setSkuProps(String skuProps) {
		this.skuProps = skuProps;
	}

	public Long getGdsPrice() {
		return gdsPrice;
	}

	public void setGdsPrice(Long gdsPrice) {
		this.gdsPrice = gdsPrice;
	}

	public Long getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(Long guidePrice) {
		this.guidePrice = guidePrice;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Timestamp getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(Timestamp collectionTime) {
		this.collectionTime = collectionTime;
	}

	public Long getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(Long nowPrice) {
		this.nowPrice = nowPrice;
	}

	public Long getCollectStaffCount() {
		return collectStaffCount;
	}

	public void setCollectStaffCount(Long collectStaffCount) {
		this.collectStaffCount = collectStaffCount;
	}

	public String getSkuMainPicUrl() {
		return skuMainPicUrl;
	}

	public void setSkuMainPicUrl(String skuMainPicUrl) {
		this.skuMainPicUrl = skuMainPicUrl;
	}

	public String getGdsUrl() {
		return gdsUrl;
	}

	public void setGdsUrl(String gdsUrl) {
		this.gdsUrl = gdsUrl;
	}

	public String getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(String stockInfo) {
		this.stockInfo = stockInfo;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(Long createStaff) {
		this.createStaff = createStaff;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateStaff() {
		return updateStaff;
	}

	public void setUpdateStaff(Long updateStaff) {
		this.updateStaff = updateStaff;
	}

	public String getGdsStatus() {
		return gdsStatus;
	}

	public void setGdsStatus(String gdsStatus) {
		this.gdsStatus = gdsStatus;
	}

	public Long getReducePrice() {
		return reducePrice;
	}

	public void setReducePrice(Long reducePrice) {
		this.reducePrice = reducePrice;
	}

	public Long getGdsTypeId() {
		return gdsTypeId;
	}

	public void setGdsTypeId(Long gdsTypeId) {
		this.gdsTypeId = gdsTypeId;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
}

