package org.coastline.one.otel.collector.component;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public interface DataComponent {


    default void start() throws Exception {
        initialize();
        addShutdownHook();
    }

    default void initialize() throws Exception {
    }

    /**
     * destroy resource, don't throw exception
     */
    default void close() {
    }

    default void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }
}
