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
 * The request for appBlb serverGroup.
 */
public class AppSgRequest extends AbstractBceRequest {

    /**
     * the short id of the blb.
     */
    private String blbId;
    /**
     * the short id of the AppServerGroup.
     */
    private String sgId;
    /**
     * the name of AppServerGroup.
     */
    private String name;
    /**
     * the description of AppServerGroup.
     */
    private String desc;
    /**
     * the backendServerList of AppServerGroup.
     */
    private List<AppBackendServer> backendServerList;


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


    public AppSgRequest withName(String name) {
        this.name = name;
        return this;
    }

    public AppSgRequest withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public AppSgRequest withBlbId(String blbId) {
        this.blbId = blbId;
        return this;
    }

    public AppSgRequest withSgId(String sgId) {
        this.sgId = sgId;
        return this;
    }

    public String getSgId() {
        return sgId;
    }

    public void setSgId(String sgId) {
        this.sgId = sgId;
    }

    public AppSgRequest withBackendServerList(List<AppBackendServer> backendServerList) {
        this.setBackendServerList(backendServerList);
        return this;
    }

    public String getBlbId() {
        return blbId;
    }

    public void setBlbId(String blbId) {
        this.blbId = blbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<AppBackendServer> getBackendServerList() {
        return backendServerList;
    }

    public void setBackendServerList(List<AppBackendServer> backendServerList) {
        this.backendServerList = backendServerList;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    @Override
    public AppSgRequest withRequestCredentials(BceCredentials credentials) {
        this.setRequestCredentials(credentials);
        return this;
    }
}
