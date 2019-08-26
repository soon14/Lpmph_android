package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 16/3/14.
 */
public class Ord00201Resp implements Serializable{
    private boolean isChoosed;

    private List<Long> coupIdList;
    private List<Long> coupValueList;
    private List<Long> detailCoupIdList;

    private String billType;
    private String billTypeText;
    private String detailFlag;
    private String billTitle;
    private String billContent;
    /**
     * 纳税人识别号
     */
    private String taxpayerNo;



    private AcctInfoResDTO acctInfoResDTO = new AcctInfoResDTO();
    private List<CoupDetailRespDTO> coupDetails = new ArrayList<>();
    private List<CoupCheckBeanRespDTO> coupCheckBeans = new ArrayList<>();

    //配送方式:0为邮局,1为快递,2为自提
    private String deliverTypes;
    private String checkDeliverType;
    private Long expressMoney;
    private String noExpress;//是否免邮优惠券，1为免邮

    private Long promotionCodeValue;//优惠码金额
    private String promotionCode;//优惠码
    private String promotionHashKey;//优惠码hashkey

    /**
     * 购物车ID
     */
    private Long cartId;
    /**
     * 登录用户ID
     */
    private Long staffId;
    /**
     * 店铺ID
     */
    private Long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 促销ID
     */
    private Long promId;
    /**
     * 购物车明细
     */
    private List<Ord00202Resp> ordCartItemList;
    /**
     * 组合商品明细
     */
    private Ord00204Resp groupLists;
    /**
     * 赠品信息
     */
    private String giftInfo;
    /**
     * 各种促销信息（列表展示）
     */
    private List<Ord00203Resp> promInfoDTOList;
    /**
     * 优惠条件是否满足
     */
    private boolean ifFulfillProm;
    /**
     * 满足优惠条件展示信息
     */
    private String fulfilMsg;
    /**
     * 不满足优惠条件展示信息
     */
    private String noFulfilMsg;
    /**
     * 店铺打折后价格
     */
    private Long discountPrice;
    /**
     * 订单级别数量
     */
    private Long discountAmount;
    /**
     * 店铺打折金额
     */
    private Long discountMoney;

    public List<Long> getDetailCoupIdList() {
        return detailCoupIdList;
    }

    public void setDetailCoupIdList(List<Long> detailCoupIdList) {
        this.detailCoupIdList = detailCoupIdList;
    }

    public AcctInfoResDTO getAcctInfoResDTO() {
        return acctInfoResDTO;
    }

    public void setAcctInfoResDTO(AcctInfoResDTO acctInfoResDTO) {
        this.acctInfoResDTO = acctInfoResDTO;
    }

    public List<CoupDetailRespDTO> getCoupDetails() {
        return coupDetails;
    }

    public void setCoupDetails(List<CoupDetailRespDTO> coupDetails) {
        this.coupDetails = coupDetails;
    }

    public List<CoupCheckBeanRespDTO> getCoupCheckBeans() {
        return coupCheckBeans;
    }

    public void setCoupCheckBeans(List<CoupCheckBeanRespDTO> coupCheckBeans) {
        this.coupCheckBeans = coupCheckBeans;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillTypeText() {
        return billTypeText;
    }

    public void setBillTypeText(String billTypeText) {
        this.billTypeText = billTypeText;
    }

    public String getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(String detailFlag) {
        this.detailFlag = detailFlag;
    }

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public List<Long> getCoupIdList() {
        return coupIdList;
    }

    public void setCoupIdList(List<Long> coupIdList) {
        this.coupIdList = coupIdList;
    }

    public List<Long> getCoupValueList() {
        return coupValueList;
    }

    public void setCoupValueList(List<Long> coupValueList) {
        this.coupValueList = coupValueList;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public List<Ord00202Resp> getOrdCartItemList() {
        return ordCartItemList;
    }

    public void setOrdCartItemList(List<Ord00202Resp> ordCartItemList) {
        this.ordCartItemList = ordCartItemList;
    }

    public Ord00204Resp getGroupLists() {
        return groupLists;
    }

    public void setGroupLists(Ord00204Resp groupLists) {
        this.groupLists = groupLists;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(String giftInfo) {
        this.giftInfo = giftInfo;
    }

    public List<Ord00203Resp> getPromInfoDTOList() {
        return promInfoDTOList;
    }

    public void setPromInfoDTOList(List<Ord00203Resp> promInfoDTOList) {
        this.promInfoDTOList = promInfoDTOList;
    }

    public boolean isIfFulfillProm() {
        return ifFulfillProm;
    }

    public void setIfFulfillProm(boolean ifFulfillProm) {
        this.ifFulfillProm = ifFulfillProm;
    }

    public String getFulfilMsg() {
        return fulfilMsg;
    }

    public void setFulfilMsg(String fulfilMsg) {
        this.fulfilMsg = fulfilMsg;
    }

    public String getNoFulfilMsg() {
        return noFulfilMsg;
    }

    public void setNoFulfilMsg(String noFulfilMsg) {
        this.noFulfilMsg = noFulfilMsg;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Long discountMoney) {
        this.discountMoney = discountMoney;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public String getDeliverTypes() {
        return deliverTypes;
    }

    public void setDeliverTypes(String deliverTypes) {
        this.deliverTypes = deliverTypes;
    }

    public String getCheckDeliverType() {
        return checkDeliverType;
    }

    public void setCheckDeliverType(String checkDeliverType) {
        this.checkDeliverType = checkDeliverType;
    }

    public Long getExpressMoney() {
        return expressMoney;
    }

    public void setExpressMoney(Long expressMoney) {
        this.expressMoney = expressMoney;
    }

    public String getNoExpress() {
        return noExpress;
    }

    public void setNoExpress(String noExpress) {
        this.noExpress = noExpress;
    }

    public Long getPromotionCodeValue() {
        return promotionCodeValue;
    }

    public void setPromotionCodeValue(Long promotionCodeValue) {
        this.promotionCodeValue = promotionCodeValue;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionHashKey() {
        return promotionHashKey;
    }

    public void setPromotionHashKey(String promotionHashKey) {
        this.promotionHashKey = promotionHashKey;
    }

    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }
}
