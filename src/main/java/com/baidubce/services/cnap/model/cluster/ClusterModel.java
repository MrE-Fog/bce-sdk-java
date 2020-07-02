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

package com.baidubce.services.cnap.model.cluster;

import java.util.Date;

import com.baidubce.services.cnap.model.application.ResourceUsageModel;

/**
 * The model for cluster.
 */
public class ClusterModel {
    /**
     * The id of cluster.
     */
    private String resourceID;

    /**
     * The name of cluster.
     */
    private String name;

    /**
     * The description of cluster.
     */
    private String description;

    /**
     * The id of underlay cluster.
     */
    private String underlayClusterID;

    /**
     * The region of cluster.
     */
    private String region;

    /**
     * The type of cluster.
     */
    private String type = "cce";

    /**
     * The slave vm count of cluster.
     */
    private int slaveVmCount;

    /**
     * The status of cluster.
     */
    private String status;

    /**
     * The resource usage.
     */
    private ResourceUsageModel resourceUsage;

    /**
     * The create time.
     */
    private Date createdAt;

    /**
     * The update time.
     */
    private Date updatedAt;

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnderlayClusterID() {
        return underlayClusterID;
    }

    public void setUnderlayClusterID(String underlayClusterID) {
        this.underlayClusterID = underlayClusterID;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSlaveVmCount() {
        return slaveVmCount;
    }

    public void setSlaveVmCount(int slaveVmCount) {
        this.slaveVmCount = slaveVmCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResourceUsageModel getResourceUsage() {
        return resourceUsage;
    }

    public void setResourceUsage(ResourceUsageModel resourceUsage) {
        this.resourceUsage = resourceUsage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
