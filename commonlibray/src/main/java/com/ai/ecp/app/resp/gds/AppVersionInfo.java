package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/26 10:16
 */
public class AppVersionInfo extends AppBody {

    private Long id;
    // 版本归属项目
    private String verProgram;
    // 版本发布号
    private String verPublishNo;
    // 版本内部号
    private Long verNo;
    private String ifForce;
    // 版本描述
    private String verDetail;
    // 版本系统
    private String verOs;
    // 版本适配系统
    private String verAdaptOs;
    private Timestamp publishTime;
    // 版本地址
    private String verUrl;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long createStaff;
    private Long updateStaff;
    private Long publishStaff;
    private String verDetailUrl;
    //是否强制更新
    private Boolean ifFore;
    //是否有更新
    private Boolean ifUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerProgram() {
        return verProgram;
    }

    public void setVerProgram(String verProgram) {
        this.verProgram = verProgram;
    }

    public String getVerPublishNo() {
        return verPublishNo;
    }

    public void setVerPublishNo(String verPublishNo) {
        this.verPublishNo = verPublishNo;
    }

    public Long getVerNo() {
        return verNo;
    }

    public void setVerNo(Long verNo) {
        this.verNo = verNo;
    }

    public String getIfForce() {
        return ifForce;
    }

    public void setIfForce(String ifForce) {
        this.ifForce = ifForce;
    }

    public String getVerDetail() {
        return verDetail;
    }

    public void setVerDetail(String verDetail) {
        this.verDetail = verDetail;
    }

    public String getVerOs() {
        return verOs;
    }

    public void setVerOs(String verOs) {
        this.verOs = verOs;
    }

    public String getVerAdaptOs() {
        return verAdaptOs;
    }

    public void setVerAdaptOs(String verAdaptOs) {
        this.verAdaptOs = verAdaptOs;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public String getVerUrl() {
        return verUrl;
    }

    public void setVerUrl(String verUrl) {
        this.verUrl = verUrl;
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    public Long getPublishStaff() {
        return publishStaff;
    }

    public void setPublishStaff(Long publishStaff) {
        this.publishStaff = publishStaff;
    }

    public String getVerDetailUrl() {
        return verDetailUrl;
    }

    public void setVerDetailUrl(String verDetailUrl) {
        this.verDetailUrl = verDetailUrl;
    }

    public Boolean getIfFore() {
        return ifFore;
    }

    public void setIfFore(Boolean ifFore) {
        this.ifFore = ifFore;
    }

    public Boolean getIfUpdate() {
        return ifUpdate;
    }

    public void setIfUpdate(Boolean ifUpdate) {
        this.ifUpdate = ifUpdate;
    }
}
