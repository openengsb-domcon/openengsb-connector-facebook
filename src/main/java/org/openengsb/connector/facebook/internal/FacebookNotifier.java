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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;
import org.openengsb.core.api.AliveState;
import org.openengsb.core.api.DomainMethodExecutionException;
import org.openengsb.core.common.AbstractOpenEngSBConnectorService;
import org.openengsb.domain.notification.NotificationDomain;
import org.openengsb.domain.notification.model.Notification;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookNotifier extends AbstractOpenEngSBConnectorService implements NotificationDomain {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookNotifier.class);
    private ServiceRegistration serviceRegistration;
    private FacebookProperties properties;
    private AliveState aliveState = AliveState.DISCONNECTED;

    public FacebookNotifier(String instanceId) {
        super(instanceId);
        properties = new FacebookProperties();
    }

    @Override
    public void notify(Notification notification) {
        LOGGER.info("Message: {}", StringUtils.abbreviate(notification.getMessage(), 200));
        send(notification.getMessage());
        LOGGER.info("facebook message has been sent");
    }

    @Override
    public AliveState getAliveState() {
        return aliveState;
    }

    public void send(String textContent) {
        try {
            aliveState = AliveState.CONNECTING;
            String httpsURL =
                "https://graph.facebook.com/" + properties.getUserID() + "/feed?access_token="
                        + properties.getUserToken();
            String params = "&message=" + textContent;
            String entryId = sendData(httpsURL, params);
            LOGGER.info("created wall entry with the id \"{}\"", entryId);
            aliveState = AliveState.ONLINE;
        } catch (Exception e) {
            aliveState = AliveState.OFFLINE;
            throw new DomainMethodExecutionException(e);
        }
    }

    private static String sendData(String httpsURL, String params) throws Exception {
        LOGGER.info("sending facebook-message");
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        if (params != null) {
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStreamWriter ow = new OutputStreamWriter(con.getOutputStream());
            ow.write(params);
            ow.flush();
            ow.close();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuffer output = new StringBuffer();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            output.append(inputLine);
            LOGGER.debug(inputLine);
        }
        in.close();
        LOGGER.info("facebook message has been sent");
        return output.toString();
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
}
