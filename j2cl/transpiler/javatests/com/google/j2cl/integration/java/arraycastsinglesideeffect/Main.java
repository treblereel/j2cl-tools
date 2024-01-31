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
package arraycastsinglesideeffect;

import static com.google.j2cl.integration.testing.Asserts.assertTrue;

public class Main {
  @SuppressWarnings("unused")
  public static void main(String... args) {
    Main main = new Main();

    Object[] object = (Object[]) main.getWithSideEffect();
    assertTrue(main.sideEffectCount == 1);

    object = (Object[]) main.getWithSideEffect();
    assertTrue(main.sideEffectCount == 2);

    object = (Object[]) main.getWithSideEffect();
    assertTrue(main.sideEffectCount == 3);
  }

  private int sideEffectCount = 0;

  private Object getWithSideEffect() {
    sideEffectCount++;
    return new Object[1];
  }
}
