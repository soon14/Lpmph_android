package com.ai.ecp.app.resp;


import com.ailk.butterfly.app.model.AppBody;

public class KillGdsInfoRespVO extends AppBody{

    private Long gdsId;//商品编码  
    
    private String gdsName;//商品名称 
    
    private String gdsDesc;//商品描述

    private Long skuId;//单品编码 
    
    private Long basePrice;//参考价
    
    private Long buyPrice;//购买价格
    
    private Long killPrice;//秒杀价格
    
    private String mediaUuid;//商品主图url(用来缩放的)
    
    private String URL;//商品主图url
    
    private String DetailURL;//商品主图url
    
    private Long buyCnt;// 购买数量
    
    private Long promCnt;// 促销数量
    
    private double percent;// 以抢购占总数的百分比

    private String ifSell="0";//0抢购数量已空 1抢购数量还有剩余

	private Boolean gdsTypeFlag;//true 表示虚拟商品 false 表示实物

    public String getGdsName() {
        return gdsName;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
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

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

	public String getGdsDesc() {
		return gdsDesc;
	}

	public Long getKillPrice() {
		return killPrice;
	}


	public void setGdsDesc(String gdsDesc) {
		this.gdsDesc = gdsDesc;
	}

	public void setKillPrice(Long killPrice) {
		this.killPrice = killPrice;
	}

	public Long getBuyCnt() {
		return buyCnt;
	}

	public Long getPromCnt() {
		return promCnt;
	}

	public void setBuyCnt(Long buyCnt) {
		this.buyCnt = buyCnt;
	}

	public void setPromCnt(Long promCnt) {
		this.promCnt = promCnt;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getMediaUuid() {
		return mediaUuid;
	}

	public void setMediaUuid(String mediaUuid) {
		this.mediaUuid = mediaUuid;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getIfSell() {
		return ifSell;
	}

	public void setIfSell(String ifSell) {
		this.ifSell = ifSell;
	}

	public String getDetailURL() {
		return DetailURL;
	}

	public void setDetailURL(String detailURL) {
		DetailURL = detailURL;
	}

	public Boolean getGdsTypeFlag() {
		return gdsTypeFlag;
	}

	public void setGdsTypeFlag(Boolean gdsTypeFlag) {
		this.gdsTypeFlag = gdsTypeFlag;
	}
}
