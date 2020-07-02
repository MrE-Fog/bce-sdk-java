/*
 * Copyright (c) 2019-2020 Baidu.com, Inc. All Rights Reserved
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
package com.baidubce.services.bcc.model.volume;

import com.baidubce.auth.BceCredentials;
import com.baidubce.model.AbstractBceRequest;
import com.baidubce.services.bcc.model.Billing;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * the request for changing volume's billing method
 */
public class ModifyVolumeChargeTypeRequest extends AbstractBceRequest {

    /**
     * specify the volume to change billing method
     */
    @JsonIgnore
    private String volumeId;

    /**
     * Payment Information
     */
    private Billing billing;


    public ModifyVolumeChargeTypeRequest withVolumeId(String volumeId) {
        this.volumeId = volumeId;
        return this;
    }

    public ModifyVolumeChargeTypeRequest withBilling(Billing billing) {
        this.billing = billing;
        return this;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    @Override
    public String toString() {
        return "ModifyVolumeChargeRequest{" +
                "volumeId='" + volumeId + '\'' +
                ", billing=" + billing +
                '}';
    }

    @Override
    public AbstractBceRequest withRequestCredentials(BceCredentials credentials) {
        this.setRequestCredentials(credentials);
        return this;
    }


}
