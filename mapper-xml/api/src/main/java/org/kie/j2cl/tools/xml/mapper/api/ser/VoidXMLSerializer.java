/*
 * Copyright 2013 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.ser;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Default {@link XMLSerializer} implementation for {@link Void}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class VoidXMLSerializer extends XMLSerializer<Void> {

  /**
   * getInstance
   *
   * @return an instance of {@link VoidXMLSerializer}
   */
  public static VoidXMLSerializer getInstance() {
    return new VoidXMLSerializer();
  }

  private VoidXMLSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected void serializeNullValue(
      XMLWriter writer, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (writer.getSerializeNulls()) {
      writer.setSerializeNulls(false);
      writer.nullValue();
      writer.setSerializeNulls(true);
    } else {
      writer.nullValue();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, Void value, XMLSerializationContext ctx, XMLSerializerParameters params) {
    // we should never be here, the null value is already handled and it's the only possible value
    // for Void
  }
}
