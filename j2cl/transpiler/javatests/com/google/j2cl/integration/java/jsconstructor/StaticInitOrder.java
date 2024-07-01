/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jsconstructor;

import static com.google.j2cl.integration.testing.Asserts.assertTrue;

import jsinterop.annotations.JsType;

@JsType
public class StaticInitOrder {
  public static int counter = 1;

  public static void test() {
    assertTrue(StaticInitOrder.counter == 5);
    assertTrue(StaticInitOrder.field1 == 2);
    assertTrue(StaticInitOrder.field2 == 3);
  }

  public static int field1 = initializeField1();
  public static int field2 = initializeField2();

  static {
    assertTrue(counter++ == 3); // #3
  }

  static {
    assertTrue(counter++ == 4); // #4
  }

  public static int initializeField1() {
    assertTrue(counter++ == 1); // #1
    return counter;
  }

  public static int initializeField2() {
    assertTrue(counter++ == 2); // #2
    return counter;
  }
}
