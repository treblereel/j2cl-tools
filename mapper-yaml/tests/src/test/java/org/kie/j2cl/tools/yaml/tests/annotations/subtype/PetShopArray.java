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

import java.util.Arrays;
import java.util.Objects;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;

@YAMLMapper
public class PetShopArray {

  private Animal[] animals;

  private Animal animal;

  public Animal[] getAnimals() {
    return animals;
  }

  public void setAnimals(Animal[] animals) {
    this.animals = animals;
  }

  public Animal getAnimal() {
    return animal;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PetShopArray that = (PetShopArray) o;
    return Arrays.equals(animals, that.animals) && Objects.equals(animal, that.animal);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(animal);
    result = 31 * result + Arrays.hashCode(animals);
    return result;
  }
}
