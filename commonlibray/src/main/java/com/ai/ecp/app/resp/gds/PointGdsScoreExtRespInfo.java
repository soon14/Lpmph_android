package com.ai.ecp.app.resp.gds;

import java.sql.Timestamp;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class PointGdsScoreExtRespInfo  extends AppBody {
	   private Long id;

	    private Long skuId;

	    private Long shopId;

	    private Long gdsId;

	    private Long score;

	    private Long price;
	    
	    private String ifDefault;

	    private String scoreType;
	    
	    private String status;

	    private Timestamp createTime;

	    private Long createStaff;

	    private Timestamp updateTime;

	    private Long updateStaff;

		public Long getId() {
			return id;
		}

		public Long getSkuId() {
			return skuId;
		}

		public Long getShopId() {
			return shopId;
		}

		public Long getGdsId() {
			return gdsId;
		}

		public Long getScore() {
			return score;
		}

		public Long getPrice() {
			return price;
		}

		public String getIfDefault() {
			return ifDefault;
		}

		public String getScoreType() {
			return scoreType;
		}

		public String getStatus() {
			return status;
		}

		public Timestamp getCreateTime() {
			return createTime;
		}

		public Long getCreateStaff() {
			return createStaff;
		}

		public Timestamp getUpdateTime() {
			return updateTime;
		}

		public Long getUpdateStaff() {
			return updateStaff;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setSkuId(Long skuId) {
			this.skuId = skuId;
		}

		public void setShopId(Long shopId) {
			this.shopId = shopId;
		}

		public void setGdsId(Long gdsId) {
			this.gdsId = gdsId;
		}

		public void setScore(Long score) {
			this.score = score;
		}

		public void setPrice(Long price) {
			this.price = price;
		}

		public void setIfDefault(String ifDefault) {
			this.ifDefault = ifDefault;
		}

		public void setScoreType(String scoreType) {
			this.scoreType = scoreType;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}

		public void setCreateStaff(Long createStaff) {
			this.createStaff = createStaff;
		}

		public void setUpdateTime(Timestamp updateTime) {
			this.updateTime = updateTime;
		}

		public void setUpdateStaff(Long updateStaff) {
			this.updateStaff = updateStaff;
		}
}
