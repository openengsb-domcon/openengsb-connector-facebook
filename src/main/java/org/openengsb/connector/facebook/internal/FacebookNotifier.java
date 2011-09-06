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

import org.apache.commons.lang.StringUtils;
import org.openengsb.connector.facebook.internal.abstraction.FacebookAbstraction;
import org.openengsb.connector.facebook.internal.abstraction.FacebookProperties;
import org.openengsb.core.api.AliveState;
import org.openengsb.core.common.AbstractOpenEngSBConnectorService;
import org.openengsb.domain.notification.NotificationDomain;
import org.openengsb.domain.notification.model.Notification;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookNotifier extends AbstractOpenEngSBConnectorService implements NotificationDomain {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookNotifier.class);

    private final FacebookAbstraction facebookAbstraction;
    private ServiceRegistration serviceRegistration;
    private FacebookProperties properties;

    public FacebookNotifier(String instanceId, FacebookAbstraction facebookAbstraction) {
        super(instanceId);
        this.facebookAbstraction = facebookAbstraction;
    }

    @Override
    public void notify(Notification notification) {
        LOGGER.info("notifying {} via facebook...", notification.getRecipient());
        LOGGER.info("Message: {}", StringUtils.abbreviate(notification.getMessage(), 200));
        facebookAbstraction.send(properties, notification.getMessage());
        LOGGER.info("facebook has been sent");
    }

    @Override
    public AliveState getAliveState() {
        AliveState aliveState = facebookAbstraction.getAliveState();
        if (aliveState == null) {
            return AliveState.OFFLINE;
        }
        return aliveState;
    }

    public ServiceRegistration getServiceRegistration() {
        return serviceRegistration;
    }

    public void setServiceRegistration(ServiceRegistration serviceRegistration) {
        this.serviceRegistration = serviceRegistration;
    }
    
    public FacebookProperties getProperties() {
        return properties;
    }

    public void createProperties() {
        properties = facebookAbstraction.createFacebookProperties();
    }
}
