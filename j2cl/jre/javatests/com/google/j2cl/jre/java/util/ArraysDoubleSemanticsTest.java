/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.j2cl.jre.java.util;

import java.util.Arrays;

/** Tests {@link Arrays} Double semantics. */
public class ArraysDoubleSemanticsTest extends EmulTestBase {

  public void testEquals() throws Exception {
    assertFalse(Arrays.equals(new Double[] {-0.0d}, new Double[] {0.0d}));
    assertTrue(Arrays.equals(new Double[] {Double.NaN}, new Double[] {Double.NaN}));

    assertFalse(Arrays.equals(new double[] {-0.0d}, new double[] {0.0d}));
    assertTrue(Arrays.equals(new double[] {Double.NaN}, new double[] {Double.NaN}));
  }
}
