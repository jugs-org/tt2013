package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Interface f�r eine unver�nderliches Set.
 * 
 * @author Heiner K&uuml;cker
 */
public interface ReadOnlySet<T>
extends Iterable<T>, Serializable
{
    abstract public int size();
    
    abstract public boolean isEmpty();
    
    /**
     * @param elemToCheck element to check
     * @return contains
     */
    abstract public boolean contains(
            final T elemToCheck );

    /**
     * @param c
     * @return
     * @see java.util.Set#containsAll
     */
    abstract boolean containsAll(
            final Collection<?> c);

    /**
     * @param c
     * @return
     * @see java.util.Set#containsAll
     */
    abstract boolean containsAllFromIterable(
            final Iterable<? extends T> c);

    /**
     * Pr�fen, ob dieses Set ausschliesslich Elemente
     * aus dem �bergebenen Set enth�lt, nicht mehr.
     * 
     * @param setToCheck set to check
     * @return contains only
     */
    abstract public boolean containsOnly(
            final ReadOnlySet<? extends T> setToCheck );

    /**
     * Pr�fen, ob dieses Set mindestens ein Element
     * aus dem �bergebenen Set enth�lt.
     * 
     * @param iterableToCheck iterable to check
     * @return contains minimum one element or more
     */
    abstract public boolean containsMinOneOf(
            final Iterable<? extends T> iterableToCheck );
    
    /**
     * Pr�fen, ob dieses Set die gleichen Elemente
     * aus dem �bergebenen Set enth�lt.
     * 
     * @param setToCheck set to check
     * @return contains equal elements
     */
    abstract public boolean equalsByElements(
            final Set<? extends T> setToCheck );

    /**
     * @return {@link java.util.Set}-Set
     */
    public abstract Set<T> toJavaSet();
}
