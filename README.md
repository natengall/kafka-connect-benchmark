## kafka-connect-benchmark

This repository contains a collection of utility tools to benchmark any Kafka data pipeline via the use of connectors that are pluggable into the Kafka Connect framework.

The BenchmarkSinkConnector is a sink connector that binds to a Kafka topic and computes the difference between the current clock (as reported by the system hosting the Kafka Connect worker) and a user-defined timestamp within each Kafka record. This diff is added to a collection which is passed to the HdrHistogram library to compute summary statistics that characterize the end-to-end latency of the specified data pipeline.