/**
 * Copyright 2016 aTool.org
 */
package com.ailk.im.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Auto-generated: 2016-11-26 1:17:41
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadResp {
    private String message;

    private String type;

    private String success;

    private String url;
    String mimeType;
    String size;
    String fileId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}