/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml;

import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.NodeType;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;
import org.kie.j2cl.tools.yaml.tests.swf.definition.ValueHolder;

public class ValueHolderYamlTypeSerializer
    implements YAMLDeserializer<ValueHolder>, YAMLSerializer<ValueHolder> {

  @Override
  public ValueHolder deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx)
      throws YAMLDeserializationException {
    YamlNode value = yaml.getNode(key);
    if (value == null) {
      return null;
    }
    return deserialize(value, ctx);
  }

  @Override
  public ValueHolder deserialize(YamlNode node, YAMLDeserializationContext ctx) {
    if (node != null && node.type() == NodeType.MAPPING && !node.asMapping().isEmpty()) {
      return new ValueHolder(node.asMapping());
    }
    return null;
  }

  @Override
  public void serialize(
      YamlMapping writer, String propertyName, ValueHolder value, YAMLSerializationContext ctx) {
    if (value == null) {
      return;
    }
    writer.addNode(propertyName, value.getHolder());
  }

  @Override
  public void serialize(YamlSequence writer, ValueHolder value, YAMLSerializationContext ctx) {
    writer.addNode(value.getHolder());
  }
}
