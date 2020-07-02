/*
 * Copyright 2020 Baidu, Inc.
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

package com.baidubce.services.media.model;

import com.baidubce.model.AbstractBceResponse;
import lombok.Data;

/**
 * The model which will be used in listing notifications
 */
@Data
public class Notification extends AbstractBceResponse {
    private String name   = null;
    private String endpoint    = null;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Notification {\n");

        sb.append("name: ").append(name).append("\n");
        sb.append("endpoint: ").append(endpoint).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
