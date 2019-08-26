package com.ai.ecp.search.dubbo.search.result;

import java.io.Serializable;

public class CollationReuslt implements Serializable{
    
    /** 
     * @since JDK 1.6 
     */ 
    private static final long serialVersionUID = 1L;

    private String collationQueryString;

    private long numberOfHits;

    public CollationReuslt() {
    }

    public CollationReuslt(long numberOfHits) {

        this.numberOfHits = numberOfHits;
    }

    public CollationReuslt(String collationQueryString) {

        this.collationQueryString = collationQueryString;
    }

    public CollationReuslt(String collationQueryString, long numberOfHits) {
        this.collationQueryString = collationQueryString;
        this.numberOfHits = numberOfHits;
    }

    public String getCollationQueryString() {
        return collationQueryString;
    }

    public long getNumberOfHits() {
        return numberOfHits;
    }

}

