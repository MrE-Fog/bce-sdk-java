/*
 * Copyright 2020 Baidu, Inc. All Rights Reserved
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
package com.baidubce.services.iam.model;

import com.baidubce.common.BaseBceResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPolicyResponse extends BaseBceResponse {
    /**
     * id
     */
    private String id;

    /**
     * name
     */
    private String name;

    /**
     * type
     */
    private String type;

    /**
     * createTime
     */
    private Date createTime;

    /**
     * description
     */
    private String description;

    /**
     * document
     */
    private String document;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocument() {
        return this.document;
    }

    @Override
    public String toString() {
        return "GetPolicyResponse{"
                + "id=" + id + "\n"
                + "name=" + name + "\n"
                + "type=" + type + "\n"
                + "createTime=" + createTime + "\n"
                + "description=" + description + "\n"
                + "document=" + document + "\n"
                + "}";
    }

}