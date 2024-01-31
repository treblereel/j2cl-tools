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
package com.google.j2cl.transpiler.passes;

import com.google.j2cl.common.SourcePosition;
import com.google.j2cl.transpiler.ast.AstUtils;
import com.google.j2cl.transpiler.ast.Method;
import com.google.j2cl.transpiler.ast.MethodDescriptor;
import com.google.j2cl.transpiler.ast.Type;

/**
 * Adds abstract method stubs for all methods that are abstract at a particular type.
 *
 * <p>In TypeScript an abstract class that implements interfaces need to redeclare all the abstract
 * methods (see https://github.com/Microsoft/TypeScript/issues/22815). The JavaScript output is
 * often used in TypeScript (e.g. via clutz) and needs these abstract method declarations to compile
 * with no errors.
 */
public class AddAbstractMethodStubs extends NormalizationPass {

  @Override
  public void applyTo(Type type) {
    if (!type.isClass() || !type.isAbstract() || type.isNative()) {
      return;
    }
    if (type.getSuperTypeDescriptor() != type.getTypeDescriptor().getSuperTypeDescriptor()) {
      // When the type hierarchy is modified for a type in the AST, the type model will have an
      // inconsistent view of the methods that need to be stubbed.
      // The main souce of such types are those that are changed by OptimizeAutoValue. In normal
      // circumstances, types that are affected by the optimization are not abstract, so they would
      // not need abstract stubs.
      // However in very specific scenarios, there might be classes that explicilty reference the
      // generated AutoValue class; and those that would be affected. Those classes might be user
      // written or the generated by an AutoValue plugin.
      //
      // For example, the user written class
      //
      //   abstract class SomeList extends List<AutoValue_SomeAutoValueClass> { }
      //
      // would result in an abstract stub referencing the AutoValue_SomeAutoValueClass.
      //
      //     abstract AutoValue_SomeAutoValueClass get(int i);
      //
      // Since AutoValue_SomeAutoValueClass was removed by the optimization this would result in
      // a compilation error in JavaScript.
      //
      // There are still scenarios that this back-off does not cover, but those are even more
      // unlikely in practice.

      return;
    }
    type.getTypeDescriptor().getPolymorphicMethods().stream()
        .filter(MethodDescriptor::isAbstract)
        .filter(m -> !m.isMemberOf(type.getDeclaration()))
        .filter(m -> m.getEnclosingTypeDescriptor().isInterface())
        .forEach(
            m -> {
              if (type.containsMethod(m.getDeclarationDescriptor().getMangledName())) {
                // The method is alread defined in the type; nothing to do.
                return;
              }
              type.addMember(
                  Method.newBuilder()
                      .setMethodDescriptor(
                          MethodDescriptor.Builder.from(m)
                              .setEnclosingTypeDescriptor(type.getTypeDescriptor())
                              .makeAbstractStub(m)
                              .build())
                      .setParameters(
                          AstUtils.createParameterVariables(m.getParameterTypeDescriptors()))
                      .setSourcePosition(SourcePosition.NONE)
                      .build());
            });
  }
}
