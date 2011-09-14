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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openengsb.connector.facebook.internal.abstraction.FacebookAbstraction;
import org.openengsb.connector.facebook.internal.abstraction.FacebookAbstractionFactory;
import org.openengsb.connector.facebook.internal.abstraction.FacebookProperties;
import org.openengsb.core.api.AliveState;

public class FacebookNotifierFactoryTest {

    public static class FacebookAbstractionImp implements FacebookAbstraction {
        FacebookProperties props = mock(FacebookProperties.class);

        @Override
        public void send(FacebookProperties properties, String textContent) {
        }

        @Override
        public FacebookProperties createFacebookProperties() {
            return props;
        }

        @Override
        public AliveState getAliveState() {
            return null;
        }
    }

    private FacebookNotifierFactory factory;

    @Before
    public void setUp() throws Exception {
        this.factory = new FacebookNotifierFactory();
        this.factory.setFactory(new FacebookAbstractionFactory() {
            @Override
            public FacebookAbstraction newInstance() {
                return new FacebookAbstractionImp();
            }
        });
    }

    @Test
    public void testCreateFacebookNotifier_shouldWork() throws Exception {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("userID", "UserID");
        attributes.put("userToken", "UserToken");

        FacebookNotifier notifier = (FacebookNotifier) factory.createNewInstance("id");
        factory.applyAttributes(notifier, attributes);
        FacebookProperties propertiesMock = notifier.getProperties();

        assertNotNull(notifier);
        assertEquals("id", notifier.getInstanceId());

        verify(propertiesMock).setUserID("UserID");
        //verify(propertiesMock).setUserToken("UserToken");
    }

    @Test
    public void testUpdateFacebookNotifier_shouldWork() throws Exception {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("userID", "UserID");
        attributes.put("userToken", "UserToken");

        FacebookNotifier notifier = (FacebookNotifier) factory.createNewInstance("id");
        factory.applyAttributes(notifier, attributes);
        FacebookProperties propertiesMock = notifier.getProperties();

        attributes.put("userID", "otherValue");

        factory.applyAttributes(notifier, attributes);

        verify(propertiesMock).setUserID("otherValue");
    }
}
