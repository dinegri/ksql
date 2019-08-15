/*
 * Copyright 2019 Confluent Inc.
 *
 * Licensed under the Confluent Community License; you may not use this file
 * except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.execution.plan;

import io.confluent.ksql.GenericRow;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

@Immutable
public class StreamSink<K> implements ExecutionStep<KStream<K, GenericRow>> {
  private final ExecutionStepProperties properties;
  private final ExecutionStep<KStream<K, GenericRow>>  source;
  private final Formats formats;
  private final String topicName;

  public StreamSink(
      final ExecutionStepProperties properties,
      final ExecutionStep<KStream<K, GenericRow>> source,
      final Formats formats,
      final String topicName) {
    this.properties = Objects.requireNonNull(properties, "properties");
    this.formats = Objects.requireNonNull(formats, "formats");
    this.source = Objects.requireNonNull(source, "source");
    this.topicName = Objects.requireNonNull(topicName, "topicName");
  }

  public String getTopicName() {
    return topicName;
  }

  @Override
  public ExecutionStepProperties getProperties() {
    return properties;
  }

  @Override
  public List<ExecutionStep<?>> getSources() {
    return Collections.singletonList(source);
  }

  @Override
  public KStream<K, GenericRow> build(final StreamsBuilder streamsBuilder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final StreamSink<?> that = (StreamSink<?>) o;
    return Objects.equals(properties, that.properties)
        && Objects.equals(source, that.source)
        && Objects.equals(formats, that.formats)
        && Objects.equals(topicName, that.topicName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(properties, source, formats, topicName);
  }
}
