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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class FacebookNotifierFactoryTest {
    private FacebookNotifierFactory factory;

    @Before
    public void setUp() throws Exception {
        this.factory = new FacebookNotifierFactory();
    }

    @Test
    public void testCreateFacebookNotifier_shouldWork() throws Exception {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("userID", "UserID");
        attributes.put("oAuthToken", "UserToken");

        FacebookNotifier notifier = (FacebookNotifier) factory.createNewInstance("id");
        factory.applyAttributes(notifier, attributes);
        FacebookProperties properties = notifier.getProperties();

        assertThat(notifier, notNullValue());
        assertThat(notifier.getInstanceId(), is("id"));
        assertThat(properties.getUserID(), is("UserID"));
        assertThat(properties.getUserToken(), is("UserToken"));
    }

    @Test
    public void testUpdateFacebookNotifier_shouldWork() throws Exception {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("userID", "UserID");
        attributes.put("oAuthToken", "UserToken");

        FacebookNotifier notifier = (FacebookNotifier) factory.createNewInstance("id");
        factory.applyAttributes(notifier, attributes);
        attributes.put("userID", "otherValue");
        factory.applyAttributes(notifier, attributes);
        FacebookProperties properties = notifier.getProperties();
        
        assertThat(properties.getUserID(), is("otherValue"));
    }
}
