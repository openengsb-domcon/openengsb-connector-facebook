/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.connector.facebook.internal;

import java.util.Properties;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class FacebookProperties {
    private final Properties properties;
    public static final String USER_ID = "userID";
    public static final String USER_TOKEN = "userToken";

    public FacebookProperties() {
        properties = new Properties();
    }

    public void setUserID(String userID) {
        properties.setProperty(USER_ID, userID);
    }

    public void setUserToken(String userToken) {
        properties.setProperty(USER_TOKEN, userToken);
    }

    public String getUserID() {
        return properties.get(USER_ID).toString();
    }

    public String getUserToken() {
        return properties.getProperty(USER_TOKEN).toString();
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
