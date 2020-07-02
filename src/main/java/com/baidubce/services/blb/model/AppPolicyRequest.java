/*
 * Copyright (c) 2019 Baidu.com, Inc. All Rights Reserved
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
package com.baidubce.services.blb.model;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * The request for appBlb policy.
 */
public class AppPolicyRequest extends AbstractBceRequest {

    /**
     * the short id of the blb.
     */
    @JsonIgnore
    private String blbId;
    /**
     * the listenerPort of the policy.
     */
    private Integer listenerPort;
    /**
     * the appPolicyVos of the policy.
     */
    private List<AppPolicy> appPolicyVos;

    /**
     * An ASCII string whose length is less than 64.
     * <p>
     * The request will be idempotent if clientToken is provided.
     * If the clientToken is not specified by the user, a random String generated by default algorithm will be used.
     * See more detail at
     * <a href = "https://bce.baidu.com/doc/BCC/API.html#.E5.B9.82.E7.AD.89.E6.80.A7">
     * BCE API doc</a>
     */
    @JsonIgnore
    private String clientToken;

    public String getBlbId() {
        return blbId;
    }

    public void setBlbId(String blbId) {
        this.blbId = blbId;
    }

    public Integer getListenerPort() {
        return listenerPort;
    }

    public void setListenerPort(Integer listenerPort) {
        this.listenerPort = listenerPort;
    }

    public List<AppPolicy> getAppPolicyVos() {
        return appPolicyVos;
    }

    public void setAppPolicyVos(List<AppPolicy> appPolicyVos) {
        this.appPolicyVos = appPolicyVos;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public AppPolicyRequest withBlbId(String blbId) {
        this.blbId = blbId;
        return this;
    }

    public AppPolicyRequest withListenerPort(Integer listenerPort) {
        this.listenerPort = listenerPort;
        return this;
    }

    public AppPolicyRequest withAppPolicyVos(List<AppPolicy> appPolicyVos) {
        this.appPolicyVos = appPolicyVos;
        return this;
    }


    @Override
    public AppPolicyRequest withRequestCredentials(BceCredentials credentials) {
        this.setRequestCredentials(credentials);
        return this;
    }
}
