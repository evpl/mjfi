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

import java.util.function.BiPredicate;

/**
 * Represents a predicate (boolean-valued function) of an
 * object-valued and a {@code long}-valued argument.
 * This is the {@code (reference, long)} specialization of {@link BiPredicate}.
 *
 * @param <T> the type of the object argument to the predicate
 * @see BiPredicate
 */
@FunctionalInterface
public interface ObjLongPredicate<T> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(T t, long value);
}
