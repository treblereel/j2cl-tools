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

package org.kie.j2cl.tools.json.mapper.collections.set;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Test;
import org.kie.j2cl.tools.json.mapper.collections.User;

@J2clTestInput(SortedSetTest.class)
public class SortedSetTest {

  public static final UsersSortedSet_JsonMapperImpl INSTANCE1 = new UsersSortedSet_JsonMapperImpl();

  @Test
  public void test1() {
    UsersSortedSet users = new UsersSortedSet();
    assertEquals("{}", INSTANCE1.toJSON(users));
    assertEquals(users, INSTANCE1.fromJSON("{}"));
  }

  @Test
  public void test2() {
    UsersSortedSet users = new UsersSortedSet();

    User user = new User();
    user.setFirstName("John");
    user.setSecondName("Doe");
    user.setIsMarried(true);

    SortedSet<User> usersSet = new TreeSet<>();
    usersSet.add(user);

    users.setUsers(usersSet);

    String rez = INSTANCE1.toJSON(users);

    assertEquals(
        "{\"users\":[{\"firstName\":\"John\",\"secondName\":\"Doe\",\"isMarried\":true}]}", rez);
    assertEquals(users, INSTANCE1.fromJSON(rez));
  }

  @Test
  public void test3() {
    UsersSortedSet users = new UsersSortedSet();

    User user = new User();
    user.setFirstName("John");
    user.setSecondName("Doe");
    user.setIsMarried(true);

    User user1 = new User();
    user1.setFirstName("Bob");
    user1.setSecondName("Green");
    user1.setIsMarried(false);

    SortedSet<User> usersSet = new TreeSet<>();
    usersSet.add(user);
    usersSet.add(user1);

    users.setUsers(usersSet);

    String rez = INSTANCE1.toJSON(users);

    assertEquals(
        "{\"users\":[{\"firstName\":\"John\",\"secondName\":\"Doe\",\"isMarried\":true},{\"firstName\":\"Bob\",\"secondName\":\"Green\",\"isMarried\":false}]}",
        rez);
    assertEquals(users, INSTANCE1.fromJSON(rez));
  }
}
