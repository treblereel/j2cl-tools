/*
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

package org.kie.j2cl.tools.yaml.tests.annotations.subtype;

import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;

@YAMLMapper
public class PetShop {

  private List<Animal> animalList;

  public List<Animal> getAnimalList() {
    return animalList;
  }

  public void setAnimalList(List<Animal> animalList) {
    this.animalList = animalList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PetShop petShop = (PetShop) o;
    return Objects.equals(animalList, petShop.animalList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(animalList);
  }
}
