package org.coastline.one.otel.collector.config.component;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.coastline.one.otel.collector.exception.ConfigException;

import java.util.List;


/**
 * ComponentID represents the identity for a component. It combines two values:
 * type - the Type of the component.
 * name - the name of that component.
 * The component ComponentID (combination type + name) is unique for a given component.Kind.
 *
 * @author Jay.H.Zou
 * @date 2021/7/23
 */
public class ComponentID {

    // typeAndNameSeparator is the separator that is used between type and name in type/name composite keys.
    public static String typeAndNameSeparator = "/";

    private String type;

    private String name;

    public static ComponentID createID(String type) {
        return new ComponentID(type, type);
    }

    // NewIDFromString decodes a string in type[/name] format into ComponentID.
    // The type and name components will have spaces trimmed, the "type" part must be present,
    // the forward slash and "name" are optional.
    // The returned ComponentID will be invalid if err is not-nil.
    public static ComponentID createIDFromString(String idStr) {
        Iterable<String> split = Splitter.on(typeAndNameSeparator)
                .trimResults()
                .omitEmptyStrings()
                .split(idStr);
        List<String> values = Lists.newArrayList(split);

        if (values.isEmpty()) {
            throw new ConfigException("idStr must have non empty type");
        }
        if (values.size() < 2) {
            throw new ConfigException("name part must be specified after \"/\" in type/name key");
        }
        return new ComponentID(values.get(0), values.get(1));
    }

    public ComponentID(String type) {
        this(type, null);
    }

    public ComponentID(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return type + typeAndNameSeparator + name;
    }

}
