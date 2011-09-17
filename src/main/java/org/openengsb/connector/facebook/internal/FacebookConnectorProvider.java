package org.openengsb.connector.facebook.internal;

import java.util.HashMap;
import java.util.Map;

import org.openengsb.core.api.descriptor.AttributeDefinition;
import org.openengsb.core.api.descriptor.ServiceDescriptor;
import org.openengsb.core.api.descriptor.ServiceDescriptor.Builder;
import org.openengsb.core.common.AbstractConnectorProvider;

public class FacebookConnectorProvider extends AbstractConnectorProvider {
    private String facebookApplicationId;
    private String facebookApplicationSecret;

    @Override
    public ServiceDescriptor getDescriptor() {
        Map<String, String> params1 = new HashMap<String, String>();
        params1.put("client_id", facebookApplicationId);
        params1.put("scope", "publish_stream");

        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("client_id", facebookApplicationId);
        params2.put("client_secret", facebookApplicationSecret);

        String baseLink1 = "https://www.facebook.com/dialog/oauth";
        String baseLink2 = "https://graph.facebook.com/oauth/access_token";

        Builder builder = ServiceDescriptor.builder(strings);

        builder.id(this.id);
        builder.name("facebook.name").description("facebook.description");
        builder.attribute(buildAttribute(builder, FacebookProperties.USER_ID, "facebook.userID.name",
            "facebook.userID.description")).attribute(builder.newAttribute().id(FacebookProperties.USER_TOKEN)
            .description("facebook.userToken.description").name("facebook.userToken.name")
            .asOAuth().required().oAuthConfiguration(params1, params2, baseLink1, baseLink2,
                "redirect_uri", "code").build());

        return builder.build();
    }

    private AttributeDefinition buildAttribute(ServiceDescriptor.Builder builder, String id, String nameId,
            String descriptionId) {
        return builder.newAttribute().id(id).name(nameId).description(descriptionId).defaultValue("").required()
            .build();
    }

    public void setFacebookApplicationId(String facebookApplicationId) {
        this.facebookApplicationId = facebookApplicationId;
    }

    public void setFacebookApplicationSecret(String facebookApplicationSecret) {
        this.facebookApplicationSecret = facebookApplicationSecret;
    }
}
