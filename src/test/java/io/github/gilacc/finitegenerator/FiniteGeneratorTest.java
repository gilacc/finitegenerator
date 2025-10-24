/*
 * io.github.gilacc.finitegenerator.FiniteGeneratorTest - Test suite for the FiniteGenerator class
 * Copyright (C) 2025 Antonio Gil
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.gilacc.finitegenerator;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FiniteGeneratorTest {

    /*
     * Generates an infinite sequence that yields all non-negative numbers, in ascending order.
     */
    private IntStream nonNegativeNumbersSequence() {
        return IntStream.iterate(0, i -> i + 1);
    }

    @Test
    void givenInfiniteIterator_whenExplicitLimitGeneratorToList_thenListSizeIsCorrect() {
        final Iterator<Integer> infinite = nonNegativeNumbersSequence().iterator();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 32767);
        assertThat(generator.toList().size(), is(32767));
    }

    @Test
    void givenInfiniteIterable_whenExplicitLimitGeneratorToList_thenListSizeIsCorrect() {
        final Iterable<Integer> infinite = () -> nonNegativeNumbersSequence().iterator();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 32767);
        assertThat(generator.toList().size(), is(32767));
    }

    @Test
    void givenInfiniteIterator_whenExplicitLimitGeneratorToSet_thenSetSizeIsCorrect() {
        final Iterator<Integer> infinite = nonNegativeNumbersSequence().iterator();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 32767);
        assertThat(generator.toSet().size(), is(32767));
    }

    @Test
    void givenInfiniteIterable_whenExplicitLimitGeneratorToSet_thenSetSizeIsCorrect() {
        final Iterable<Integer> infinite = () -> nonNegativeNumbersSequence().iterator();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 32767);
        assertThat(generator.toSet().size(), is(32767));
    }

    @Test
    void givenInfiniteIterator_whenGeneratorIterator_thenResultIsCorrect() {
        final Iterator<Integer> infinite = nonNegativeNumbersSequence().iterator();
        final List<Integer> expectedResult = nonNegativeNumbersSequence()
                .limit(1000)
                .boxed()
                .toList();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 1000);
        final List<Integer> result = StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(generator.iterator(), 0), false)
                .toList();
        assertThat(result, is(expectedResult));
    }

    @Test
    void givenInfiniteIterable_whenGeneratorSpliterator_thenResultIsCorrect() {
        final Iterable<Integer> infinite = () -> nonNegativeNumbersSequence().iterator();
        final List<Integer> expectedResult = nonNegativeNumbersSequence()
                .limit(1000)
                .boxed()
                .toList();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 1000);
        final List<Integer> result = StreamSupport
                .stream(generator.spliterator(), false)
                .toList();
        assertThat(result, is(expectedResult));
    }

    @Test
    void givenInfiniteIterable_whenGeneratorForEach_thenActionIsCorrectlyApplied() {
        final Iterable<Integer> infinite = () -> nonNegativeNumbersSequence().iterator();
        final List<Integer> expectedResult = nonNegativeNumbersSequence()
                .limit(1000)
                .boxed()
                .toList();
        final FiniteGenerator<Integer> generator = new FiniteGenerator<>(infinite, 1000);
        final List<Integer> result = new ArrayList<>();
        generator.forEach(result::add);
        assertThat(result, is(expectedResult));
    }

}
