/*
 * Copyright 2015 Baidu, Inc.
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

package com.baidubce.services.lss.model;

/**
 * Represents the authentication settings.
 */
public class Auth {
    private Boolean push = null;

    private Boolean play = null;

    /**
     * Returns true if authentication is required before push.
     *
     * @return true if authentication is required before push
     */
    public Boolean getPush() {
        return push;
    }

    /**
     * Sets to true if authentication is required before push
     *
     * @param push true if authentication is required before push
     */
    public void setPush(Boolean push) {
        this.push = push;
    }

    /**
     * Sets to true if authentication is required before push
     *
     * @param push true if authentication is required before push
     * @return this object
     */
    public Auth withPush(Boolean push) {
        this.push = push;
        return this;
    }

    /**
     * Returns true if authentication is required before play
     *
     * @return true if authentication is required before play
     */
    public Boolean getPlay() {
        return play;
    }

    /**
     * Sets to true if authentication is required before play
     *
     * @param play true if authentication is required before play
     */
    public void setPlay(Boolean play) {
        this.play = play;
    }

    /**
     * Sets to true if authentication is required before play
     *
     * @param play true if authentication is required before play
     * @return this object
     */
    public Auth withPlay(Boolean play) {
        this.play = play;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class Auth {\n");
        sb.append("    push: ").append(push).append("\n");
        sb.append("    play: ").append(play).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
