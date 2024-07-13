/*
 * Copyright 2021 Google Inc.
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

import com.google.j2cl.transpiler.ast.AstUtils;
import com.google.j2cl.transpiler.ast.CompilationUnit;
import com.google.j2cl.transpiler.ast.Expression;
import com.google.j2cl.transpiler.ast.NullLiteral;
import com.google.j2cl.transpiler.ast.TypeDescriptor;
import com.google.j2cl.transpiler.ast.TypeDescriptors;
import com.google.j2cl.transpiler.passes.ConversionContextVisitor.ContextRewriter;

/**
 * Replaces all null literals with null literals that have the exact type required in the construct
 * they appear.
 */
public class NormalizeNullLiterals extends NormalizationPass {

  @Override
  public void applyTo(CompilationUnit compilationUnit) {
    compilationUnit.accept(
        new ConversionContextVisitor(
            new ContextRewriter() {
              @Override
              public Expression rewriteTypeConversionContext(
                  TypeDescriptor inferredTypeDescriptor,
                  TypeDescriptor actualTypeDescriptor,
                  Expression expression) {
                if (expression instanceof NullLiteral) {
                  if (AstUtils.isNonNativeJsEnum(inferredTypeDescriptor)) {
                    // JsEnums types are removed, so this should be the boxed type. j.l.Object also
                    // works because boxed js enum types are never explicitly referred to in method
                    // signatures, and the only other types they can be passed as is Comparable and
                    // Serializable which are interfaces and modeled in the backend as
                    // java.lang.Object.
                    return TypeDescriptors.get().javaLangObject.getDefaultValue();
                  }
                  return inferredTypeDescriptor.getDefaultValue();
                }
                return expression;
              }
            }));
  }
}
