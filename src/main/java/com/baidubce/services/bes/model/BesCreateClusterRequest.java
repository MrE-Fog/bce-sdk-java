/**
 * Copyright 2020 Baidu, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.baidubce.services.bes.model;

import com.baidubce.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 *  @Description:  Request to create a cluster
 */
public class BesCreateClusterRequest extends AbstractBesRequest {
    @JsonProperty
    private String name;

    @JsonProperty
    private String password;

    @JsonProperty
    private List<ModuleInfo> modules;

    @JsonProperty
    private String version;

    @JsonProperty
    private String availableZone;

    @JsonProperty
    private String securityGroupId;

    @JsonProperty
    private String subnetUuid;

    @JsonProperty
    private String vpcId;
    @JsonProperty
    private ClusterBilling billing;
    @JsonProperty
    private boolean isOldPackage;

    public boolean isOldPackage() {
        return isOldPackage;
    }

    public void setOldPackage(boolean oldPackage) {
        isOldPackage = oldPackage;
    }

    public ClusterBilling getBilling() {
        return billing;
    }

    public void setBilling(ClusterBilling billing) {
        this.billing = billing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ModuleInfo> getModules() {
        return modules;
    }

    public void setModules(List<ModuleInfo> modules) {
        this.modules = modules;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAvailableZone() {
        return availableZone;
    }

    public void setAvailableZone(String availableZone) {
        this.availableZone = availableZone;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getSubnetUuid() {
        return subnetUuid;
    }

    public void setSubnetUuid(String subnetUuid) {
        this.subnetUuid = subnetUuid;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    /**
     *  Configuration information for the cluster
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ModuleInfo {
        @JsonProperty
        private String type;
        @JsonProperty
        private int instanceNum;

        @JsonProperty
        private String slotType;

        @JsonProperty
        private DiskSlotInfo diskSlotInfo;

        public String getSlotType() {
            return slotType;
        }

        public void setSlotType(SlotType slotType) {
            setSlotType(slotType.getSlotType());
        }

        public void setSlotType(String slotType) {
            this.slotType = slotType;
        }

        public DiskSlotInfo getDiskSlotInfo() {
            return diskSlotInfo;
        }

        public void setDiskSlotInfo(DiskSlotInfo diskSlotInfo) {
            this.diskSlotInfo = diskSlotInfo;
        }

        public int getInstanceNum() {
            return instanceNum;
        }

        public void setInstanceNum(int instanceNum) {
            this.instanceNum = instanceNum;
        }

        public String getType() {
            return type;
        }

        public void setType(ModuleType type) {
            setType(type.getModuleType());
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * Cluster payment method
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClusterBilling {
        @JsonProperty
        private String paymentType;
        @JsonProperty
        private int time;

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(PaymentType paymentType) {
            setPaymentType(paymentType.getPaymentType());
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public String toJson() throws IOException {
        StringWriter stringWriter = new StringWriter();

        JsonGenerator jsonGenerator = JsonUtils.jsonGeneratorOf(stringWriter);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", name);
        jsonGenerator.writeStringField("password", password);
        jsonGenerator.writeArrayFieldStart("modules");
        for (BesCreateClusterRequest.ModuleInfo module : modules) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("type", module.getType());
            jsonGenerator.writeNumberField("instanceNum", module.getInstanceNum());
            jsonGenerator.writeStringField("slotType", module.getSlotType());
            if (module.getDiskSlotInfo() != null) {
                jsonGenerator.writeObjectFieldStart("diskSlotInfo");
                jsonGenerator.writeStringField("type", module.getDiskSlotInfo().getType());
                jsonGenerator.writeNumberField("size", module.getDiskSlotInfo().getSize());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeObjectFieldStart("billing");
        jsonGenerator.writeStringField("paymentType", billing.getPaymentType());
        jsonGenerator.writeNumberField("time", billing.getTime());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField("version", version);
        jsonGenerator.writeBooleanField("isOpenService", false);
        jsonGenerator.writeBooleanField("isOldPackage", isOldPackage);
        jsonGenerator.writeStringField("availableZone", availableZone);
        jsonGenerator.writeStringField("securityGroupId", securityGroupId);
        jsonGenerator.writeStringField("subnetUuid", subnetUuid);
        jsonGenerator.writeStringField("vpcId", vpcId);
        jsonGenerator.writeStringField("serviceType", "BES");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        return stringWriter.toString();
    }

}