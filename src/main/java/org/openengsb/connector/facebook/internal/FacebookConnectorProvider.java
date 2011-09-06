package org.openengsb.connector.facebook.internal;

import java.util.HashMap;
import java.util.Map;

import org.openengsb.core.api.OAuthData;
import org.openengsb.core.api.descriptor.AttributeDefinition;
import org.openengsb.core.api.descriptor.ServiceDescriptor;
import org.openengsb.core.api.descriptor.ServiceDescriptor.Builder;
import org.openengsb.core.common.AbstractConnectorProvider;

public class FacebookConnectorProvider extends AbstractConnectorProvider {
    private final String applicationID = "102268793189836";
    private final String applicationSecret = "0a5d734743d6e15594c26247c44e7dd9";

    @Override
    public ServiceDescriptor getDescriptor() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appID", applicationID);
        params.put("appSecret", applicationSecret);
        params.put("scope", "offline_access");

        // nur die PostParameter mitgeben
        String baseLink1 = "https://www.facebook.com/dialog/oauth?client_id=" + applicationID + "&scope=offline_access";
        String baseLink2 = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=" + applicationID + "&scope=offline_access&type=client_cred&"
                + "client_secret=" + applicationSecret;
        OAuthData oAuthPageData = new OAuthData(params, params, baseLink1, baseLink2, "redirect_uri");

        Builder builder = ServiceDescriptor.builder(strings);

        builder.id(this.id);
        builder.name("facebook.name").description("facebook.description");
        builder.attribute(buildAttribute(builder, "userID", "facebook.userID.name",
            "facebook.userID.description")).attribute(builder.newAttribute().id("oAuthWidget")
            .description("facebook.userToken.description").name("facebook.userToken.name")
            .asOAuth().required().oAuthConfiguration(oAuthPageData).build());

        return builder.build();
    }

    private AttributeDefinition buildAttribute(ServiceDescriptor.Builder builder, String id, String nameId,
            String descriptionId) {
        return builder.newAttribute().id(id).name(nameId).description(descriptionId).defaultValue("").required()
            .build();
    }
}
