/*
 * io.github.gilacc.finitegenerator.FiniteGenerator - Finite Iterables from finite and infinite sequences
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

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <p>Finite {@link Iterable}s from either finite and infinite sequences.</p>
 *
 * <p>The {@code FiniteGenerator} class is an immutable container that decorates an {@link Iterable} and imposes a limit
 * on how many elements the sequence can produce. This can be very helpful for working with {@link Iterable}s that yield
 * an indefinite amount of elements, transforming `Iterable`s into collection types and helping avoid potential
 * out-of-memory errors caused by infinite sequences.</p>
 *
 * <p>A {@code FiniteGenerator} will yield at most {@link FiniteGenerator#limit()} elements. This effective limit is set
 * by the constructor.</p>
 *
 * @param <T> the type of elements returned by the sequence
 */
public class FiniteGenerator<T> implements Iterable<T> {

    /*
     * The encapsulated Iterable that this object yields.
     */
    private final Iterable<T> iterable;

    /*
     * The effective limit that shall be imposed on the sequence.
     */
    private final int limit;

    /**
     * Constructs a {@code FiniteGenerator} for a given iterator and element number limit.
     *
     * @param iterator the iterator to encapsulate
     * @param limit the effective element number limit
     */
    public FiniteGenerator(Iterator<T> iterator, int limit) {
        this(() -> iterator, limit);
    }

    /**
     * Constructs a {@code FiniteGenerator} for a given iterable and element number limit.
     *
     * @param iterable the iterable to encapsulate
     * @param limit the effective element number limit
     */
    public FiniteGenerator(Iterable<T> iterable, int limit) {
        this.iterable = iterable;
        this.limit = limit;
    }

    /**
     * Returns an iterator for the {@link FiniteGenerator#limit()} first elements of the sequence.
     *
     * @return an iterator for the sequence, with the limit already applied
     */
    @Override
    public Iterator<T> iterator() {
        return this.finiteStream().iterator();
    }

    /**
     * Performs an action for the {@link FiniteGenerator#limit()} first elements of the sequence.
     *
     * @param action the (non-interfering) action to apply
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        this.finiteStream().forEach(action);
    }

    /**
     * Returns the spliterator for the {@link FiniteGenerator#limit()} first elements of the sequence.
     *
     * @return a spliterator for the sequence, with the limit already applied
     */
    @Override
    public Spliterator<T> spliterator() {
        return this.finiteStream().spliterator();
    }

    /**
     * Returns the maximum amount of elements that will be returned by the generator.
     *
     * @return the element limit
     */
    public int limit() {
        return this.limit;
    }

    /**
     * Returns a list with the {@link FiniteGenerator#limit()} first elements of the sequence.
     *
     * @return the encapsulated sequence, as a list
     */
    public List<T> toList() {
        return this.finiteStream().toList();
    }

    /**
     * Returns a set with the {@link FiniteGenerator#limit()} first elements of the sequence.
     *
     * @return the encapsulated sequence, as a set
     */
    public Set<T> toSet() {
        return this.finiteStream().collect(Collectors.toSet());
    }

    /*
     * Returns a stream for the elements in the encapsulated iterable, applying the given element limit.
     */
    private Stream<T> finiteStream() {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .limit(this.limit());
    }

}
