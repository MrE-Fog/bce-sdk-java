/*
 * Copyright  2019 Baidu, Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.baidubce.services.cfc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object for listing the CFC functions
 */
public class ListFunctionsResponse extends CfcResponse {
    /**
     * Functions
     */
    @JsonProperty(value = "Functions")
    private List<FunctionConfiguration> Functions = new ArrayList<FunctionConfiguration>();

    /**
     * Get the function list
     * @return
     */
    @JsonProperty(value = "Functions")
    public List<FunctionConfiguration> getFunctions() {
        return this.Functions;
    }

    /**
     * Set the function list
     * @param functions
     */
    @JsonProperty(value = "Functions")
    public void setFunctions(List<FunctionConfiguration> functions) {
        this.Functions = functions;
    }

}

