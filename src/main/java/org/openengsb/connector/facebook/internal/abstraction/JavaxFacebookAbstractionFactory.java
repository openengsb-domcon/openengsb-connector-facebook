package org.openengsb.connector.facebook.internal.abstraction;

public class JavaxFacebookAbstractionFactory implements FacebookAbstractionFactory {

    @Override
    public FacebookAbstraction newInstance() {
        return new JavaxFacebookAbstraction();
    }

}
