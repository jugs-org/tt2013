package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Hilfs-Methoden zur Arbeit mit
 * {@link java.util.Iterator}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class IteratorUtil
{
    /**
     * Zur�ckgeben eines bestimmten Elementes
     * aus dem �bergebenen Iterator.
     * 
     * @param iterator aus diesem Iterator ist das 
     *                 gew�nschte Element zu entnehmen
     * @param index Position des gew�nschten Elements (Basis 0)
     * @return gew�nschtes Element
     * @param <T> Type-Parameter Element-Type
     * @see Iterator#next()
     */
    public static <T> T get(
            final Iterator<T> iterator ,
            final int index )
    {
        if ( index < 0 )
        {
            throw new ArrayIndexOutOfBoundsException(
                    index );
        }
        
        int i = 0;
        
        while ( i++ < index )
        {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Iterator in Liste umkopieren.
     * 
     * @param iterator
     * @return
     */
    public static <T> List<T> toList(
            final Iterator<T> iterator )
    {
        final ArrayList<T> list = new ArrayList<T>();
        
        while ( iterator.hasNext() )
        {
            list.add( iterator.next() );
        }
        
        return list;
    }

    /**
     * Iterable in Liste umkopieren.
     * 
     * @param iterable
     * @return
     */
    public static <T> List<T> toList(
            final Iterable<T> iterable )
    {
        return
                toList( iterable.iterator() );
    }
}
