package de.heinerkuecker.constraint.util.coll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Erweiterung {@link java.util.ArrayList}
 * um nützliche Methoden.
 * 
 * @author Heiner K&uuml;cker
 */
public final class XArrayList<E>
extends ArrayList<E>
implements XList<E>
{
    /**
     * Konstruktor.
     */
    public XArrayList()
    {
        super();
    }

    /**
     * Konstruktor.
     * @param c
     */
    public XArrayList( 
            final Collection<? extends E> c )
    {
        super( c );
    }

    /**
     * Konstruktor.
     * @param c
     */
    public XArrayList( 
            final E... a )
    {
        super(
                Arrays.asList(
                        a ) );
    }

    /**
     * Konstruktor.
     * @param initialCapacity
     */
    public XArrayList( 
            final int initialCapacity )
    {
        super( initialCapacity );
    }

    /**
     * @see XList#addAllFromIterable(java.lang.Iterable)
     */
    @Override
    public void addAllFromIterable(
            final Iterable<? extends E> ibl )
    {
        for ( final E e : ibl )
        {
            this.add( e );
        }
    }

    
}
