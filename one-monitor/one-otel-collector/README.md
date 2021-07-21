# Otel Collector(Official)
[Official Collector GitHub](https://opentelemetry.io/docs/collector/)

# Otel Collector(Custom)

## Components
### Receiver
* protocols: only support `gRPC`
* data source: only support `trace`, `metrics`

### Processor
* data filter
* data format

### DataQueue
* data queue: `MMAP`, `Page Cache`

### Exporter
* data export: only support `Kafka`

### Extension
* server register: `standalone`, `cluster`
* sever config: `local`, `remote`
* health check


