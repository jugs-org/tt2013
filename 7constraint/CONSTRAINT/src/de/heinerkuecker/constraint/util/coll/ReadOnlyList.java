package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.List;

/**
 * Interface f�r eine unver�nderliche Liste.
 * 
 * @author Heiner K&uuml;cker
 */
public interface ReadOnlyList<T>
extends Iterable<T>, Serializable
{
    /**
     * @param index
     * @return
     * @see List#get(int)
     */
    abstract public T get(
            final int index );

    /**
     * @return
     * @see List#size
     */
    abstract public int size();

    /**
     * @return
     * @see List#isEmpty
     */
    abstract public boolean isEmpty();
    
    /**
     * TODO eigentlich sollte f�r das Pr�fen auf {@link #contains} ein Set verwendet werden
     * @param elemToCheck element to check
     * @return contains
     * @deprecated bad performance
     */
    abstract public boolean contains(
            final T elemToCheck );

    /**
     * @return {@link java.util.List}-List
     */
    public abstract List<T> toJavaList();
}
