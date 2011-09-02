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

package org.openengsb.connector.facebook.integration;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.openengsb.connector.facebook.internal.FacebookNotifier;
import org.openengsb.connector.facebook.internal.abstraction.JavaxFacebookAbstraction;
import org.openengsb.connector.facebook.model.TestNotification;
import org.openengsb.domain.notification.model.Attachment;
import org.openengsb.domain.notification.model.Notification;

public class FacebookNotifierUT {

    @Test
    public void testToSendAnEmailOverSSL() throws Exception {
        FacebookNotifier notifier = createSSLNotifier();
        Notification notification = createNotification();
        notifier.notify(notification);
    }
    
    @Test
    public void testToSendAFacebookMsgWithStartTls() throws Exception {
        FacebookNotifier notifier = createStartTlsNotifier();
        Notification notification = createNotification();
        notifier.notify(notification);
    }
    
    @Test
    public void testChangeFacebookProperties() {
        FacebookNotifier notifier = createStartTlsNotifier();
        Notification notification = createNotification();
        notifier.notify(notification);
        
        notifier = toSSLNotifier(notifier);
        notifier.notify(notification);
    }

    
    private FacebookNotifier createSSLNotifier() {
        FacebookNotifier notifier = createNotifier("notifier1"); 
        toSSLNotifier(notifier);        
        return notifier;
    }
    
    private FacebookNotifier toSSLNotifier(FacebookNotifier notifier) {
        updateProperties(notifier, "msg1", "token1");
        //notifier.getProperties().setSecureMode(SecureMode.SSL.toString());
        return notifier;
    }
    
    private FacebookNotifier createStartTlsNotifier() {
        FacebookNotifier notifier = createNotifier("notifier2", "msg2", "token2"); 
        //notifier.getProperties().setSecureMode(SecureMode.STARTTLS.toString());
        return notifier;
    }

    private FacebookNotifier createNotifier(String id, String userID, String userToken) {
        JavaxFacebookAbstraction facebookAbstraction = new JavaxFacebookAbstraction();
        FacebookNotifier notifier = new FacebookNotifier(id, facebookAbstraction);
        return updateProperties(notifier, userID, userToken);
    }
    
    private FacebookNotifier createNotifier(String id) {
        JavaxFacebookAbstraction facebookAbstraction = new JavaxFacebookAbstraction();
        return new FacebookNotifier(id, facebookAbstraction);
    }

    private FacebookNotifier updateProperties(FacebookNotifier notifier, String userID, String userToken) {
        notifier.createProperties();
        notifier.getProperties().setUserID(userID);
        notifier.getProperties().setUserToken(userToken);
        return notifier;
    }

    private Notification createNotification() {
        Notification notification = new TestNotification();
        notification.setRecipient("openengsb.notification.test@gmail.com");
        notification.setSubject("TestMail send on " + new Date());
        notification.setMessage("This is a test mail");
        notification.setAttachments(new ArrayList<Attachment>());
        return notification;
    }
}
