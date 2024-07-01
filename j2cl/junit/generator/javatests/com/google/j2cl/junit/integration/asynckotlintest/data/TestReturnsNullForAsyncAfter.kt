/*
 * Copyright 2022 Google Inc.
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
package com.google.j2cl.junit.integration.asynckotlintest.data

import com.google.j2cl.junit.async.AsyncTestRunner
import com.google.j2cl.junit.async.Timeout
import com.google.j2cl.junit.integration.testing.async.Thenable
import com.google.j2cl.junit.integration.testing.testlogger.TestCaseLogger
import kotlin.test.AfterTest
import kotlin.test.Test
import org.junit.runner.RunWith

/** Integration test used in J2clTestRunnerTest. */
@RunWith(AsyncTestRunner::class)
class TestReturnsNullForAsyncAfter {
  @Timeout(200L)
  @AfterTest
  fun after(): Thenable? {
    return null
  }

  @Test
  fun testMethod() {
    TestCaseLogger.log("test method ran!")
  }
}
