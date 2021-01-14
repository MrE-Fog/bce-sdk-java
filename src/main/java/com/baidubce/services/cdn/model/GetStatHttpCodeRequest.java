/*
 * Copyright 2016 Baidu, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.baidubce.services.cdn.model;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;

import java.util.Date;

/**
 * @author yixing
 *
 */
public class GetStatHttpCodeRequest extends AbstractBceRequest {
    private String domain;
    private Date endTime;  
    private Date startTime;
    private Integer period;
    private boolean withRegion;

    /**
     * @return domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain name
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    /**
     * @param domain the domain name
     * @return returns this object
     */
    public GetStatHttpCodeRequest withDomain(String domain) {
        setDomain(domain);
        return this;
    }

    /**
     * @return endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * @param endTime
     * @return returns this object
     */
    public GetStatHttpCodeRequest withEndTime(Date endTime) {
        setEndTime(endTime);
        return this;
    }

    /**
     * @return startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * @param startTime
     * @return returns this object
     */
    public GetStatHttpCodeRequest withStartTime(Date startTime) {
        setStartTime(startTime);
        return this;
    }
    
    /**
     * @return period
     */
    public Integer getPeriod() {
        return period;
    }
    
    /**
     * @param period
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * @param period
     * @return returns this object
     */
    public GetStatHttpCodeRequest withPeriod(Integer period) {
        setPeriod(period);
        return this;
    }
    
    /**
     * @return withRegion
     */
    public boolean isWithRegion() {
        return withRegion;
    }

    /**
     * @param withRegion
     */
    public void setWithRegion(boolean withRegion) {
        this.withRegion = withRegion;
    }

    /**
     * @param withRegion
     * @return returns this object
     */
    public GetStatHttpCodeRequest withRegion(boolean withRegion) {
        setWithRegion(withRegion);
        return this;
    }

    /**
     * (non-Javadoc)
     * @see com.baidubce.model.AbstractBceRequest#withRequestCredentials(com.baidubce.auth.BceCredentials)
     */
    @Override
    public GetStatHttpCodeRequest withRequestCredentials(BceCredentials credentials) {
        this.setRequestCredentials(credentials);
        return this;
    }
}
