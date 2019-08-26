package com.ai.ecp.app.req;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/30 09:17
 */
public class ROrdInvoiceCommRequest {

    private String detailFlag;
    private String invoiceTitle;
    private String invoiceContent;
    private String invoiceType;

    /**
     * 纳税人识别号
     */
    private String taxpayerNo;

    public String getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(String detailFlag) {
        this.detailFlag = detailFlag;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }
}
