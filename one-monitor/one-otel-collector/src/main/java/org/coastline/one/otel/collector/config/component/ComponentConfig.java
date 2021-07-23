package org.coastline.one.otel.collector.config.component;

import org.coastline.one.otel.collector.config.component.ComponentID;

/**
 * @author Jay.H.Zou
 * @date 2021/7/23
 */
public abstract class ComponentConfig {

    private ComponentID componentID;

    /*boolean validate(ComponentID componentID) {
        return true;
    }*/

    public ComponentID getComponentID() {
        return componentID;
    }

    public void setComponentID(ComponentID componentID) {
        this.componentID = componentID;
    }
}
