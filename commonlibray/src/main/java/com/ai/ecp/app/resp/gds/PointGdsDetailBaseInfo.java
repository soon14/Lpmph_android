package com.ai.ecp.app.resp.gds;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class PointGdsDetailBaseInfo extends AppBody{


	private String shopName;

	private String stockStatus;

	private String stockStatusDesc;
	
	private Long stockLackNum;

	private Boolean ifBrowse;

	private Long saleCount;

	private String digitsPrinPrice;

	private String mainPicUrl;
	
	
	private String gdsName;
	//收藏id，如果被当前用户收藏，则返回
	private Long collectId;
	
    /**
     * 商品积分
     */
    private List<PointGdsScoreExtRespInfo> scores;

	public Long getCollectId() {
		return collectId;
	}

	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}

	/**
	 * 商品副标题
	 */
	private String gdsSubHead;

	/**
	 * 商品知道价
	 */
	private Long guidePrice;

	private List<String> imageUrlList;

	/**
	 * 商品类型
	 */
	private Long gdsTypeId;

	/**
	 * 商品状态
	 */
	private String gdsStatus;

	/**
	 * 店铺编码
	 */
	private Long shopId;

	private String isbn;
	/**
	 * 分类编码
	 */
	private String mainCatgs;
	/**
	 * 单品信息
	 */
	private PointGdsSkuBaseInfo gdsSkuBaseInfo;
	
    /**
     * 商品描述
     */
    private String gdsDesc;

    /**
     * 包装清单
     */
    private String gdsPartlist;
    

	/**
	 * 参数属性
	 */
	private List<PointGdsPropBaseInfo> params;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public String getStockStatusDesc() {
		return stockStatusDesc;
	}

	public void setStockStatusDesc(String stockStatusDesc) {
		this.stockStatusDesc = stockStatusDesc;
	}

	public Boolean getIfBrowse() {
		return ifBrowse;
	}

	public void setIfBrowse(Boolean ifBrowse) {
		this.ifBrowse = ifBrowse;
	}

	public Long getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Long saleCount) {
		this.saleCount = saleCount;
	}

	public String getDigitsPrinPrice() {
		return digitsPrinPrice;
	}

	public void setDigitsPrinPrice(String digitsPrinPrice) {
		this.digitsPrinPrice = digitsPrinPrice;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public String getGdsSubHead() {
		return gdsSubHead;
	}

	public void setGdsSubHead(String gdsSubHead) {
		this.gdsSubHead = gdsSubHead;
	}

	public Long getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(Long guidePrice) {
		this.guidePrice = guidePrice;
	}

	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public Long getGdsTypeId() {
		return gdsTypeId;
	}

	public void setGdsTypeId(Long gdsTypeId) {
		this.gdsTypeId = gdsTypeId;
	}

	public String getGdsStatus() {
		return gdsStatus;
	}

	public void setGdsStatus(String gdsStatus) {
		this.gdsStatus = gdsStatus;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public PointGdsSkuBaseInfo getGdsSkuBaseInfo() {
		return gdsSkuBaseInfo;
	}

	public void setGdsSkuBaseInfo(PointGdsSkuBaseInfo gdsSkuBaseInfo) {
		this.gdsSkuBaseInfo = gdsSkuBaseInfo;
	}

	public List<PointGdsPropBaseInfo> getParams() {
		return params;
	}

	public void setParams(List<PointGdsPropBaseInfo> params) {
		this.params = params;
	}

	public String getGdsName() {
		return gdsName;
	}

	public void setGdsName(String gdsName) {
		this.gdsName = gdsName;
	}

	public String getMainCatgs() {
		return mainCatgs;
	}

	public void setMainCatgs(String mainCatgs) {
		this.mainCatgs = mainCatgs;
	}

	public String getGdsDesc() {
		return gdsDesc;
	}

	public void setGdsDesc(String gdsDesc) {
		this.gdsDesc = gdsDesc;
	}

	public String getGdsPartlist() {
		return gdsPartlist;
	}

	public void setGdsPartlist(String gdsPartlist) {
		this.gdsPartlist = gdsPartlist;
	}

    public Long getStockLackNum() {
        return stockLackNum;
    }

    public void setStockLackNum(Long stockLackNum) {
        this.stockLackNum = stockLackNum;
    }

	public List<PointGdsScoreExtRespInfo> getScores() {
		return scores;
	}

	public void setScores(List<PointGdsScoreExtRespInfo> scores) {
		this.scores = scores;
	}


}

