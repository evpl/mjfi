/*
 * Copyright (c) 2021 Evgenii Plugatar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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
package com.plugatar.mjfi;

import java.util.function.BiFunction;

/**
 * Represents a function that accepts an object-valued and a
 * {@code boolean}-valued argument and produces a result.
 * This is the {@code (reference, boolean)} specialization of {@link BiFunction}.
 *
 * @param <T> the type of the object argument to the function
 * @param <R> the type of the result of the function
 * @see BiFunction
 */
@FunctionalInterface
public interface ObjBooleanFunction<T, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t     the first function argument
     * @param value the second function argument
     * @return the function result
     */
    R apply(T t, boolean value);
}
