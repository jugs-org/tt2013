package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.heinerkuecker.constraint.util.lang.NoRemoveIteratorAdaptor;

/**
 * Adapter-Implementierung für eine unveränderliche Liste.
 * 
 * @author Heiner K&uuml;cker
 */
public final class ReadOnlyListAdaptor<T>
implements ReadOnlyList<T>, Serializable
{
    private final List<? extends T> innerList;

    /**
     * Konstruktor.
     *
     * @param listToWrap
     */
    public ReadOnlyListAdaptor( 
            final List<? extends T> listToWrap )
    {
        if ( listToWrap == null )
        {
            throw new NullPointerException();
        }
        
        if ( listToWrap instanceof ArrayList )
        {
            // Speicher sparen (hoffentlich)
            ( (ArrayList<?>) listToWrap ).trimToSize();
        }

        this.innerList = listToWrap;
    }

    /**
     * Konstruktor.
     *
     * @param elemArr
     */
    public ReadOnlyListAdaptor( 
            final T... elemArr )
    {
        this(
                Arrays.asList( 
                        elemArr ) );
    }

    /**
     * @see ReadOnlyList#get(int)
     */
    @Override
    public T get( 
            final int index )
    {
        return this.innerList.get( index );
    }

    /**
     * @see ReadOnlyList#size()
     */
    @Override
    public int size()
    {
        return this.innerList.size();
    }

    /**
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator()
    {
        return
            new NoRemoveIteratorAdaptor<T>(
                    this.innerList.iterator() );
    }

    /**
     * @see ReadOnlyList#contains(java.lang.Object)
     */
    @Override
    public boolean contains( 
            final T elemToCheck )
    {
        return innerList.contains( elemToCheck );
    }

    /**
     * @see ReadOnlyList#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        return innerList.isEmpty();
    }

    /**
     * @see ReadOnlyList#toJavaList()
     */
    @Override
    public List<T> toJavaList()
    {
        return
            new ArrayList<T>(
                    this.innerList );
    }

    /**
     * global einmalige Instanz einer leeren Liste
     */
    private static final ReadOnlyList<?> emptyList =
        new ReadOnlyListAdaptor<Object>(
                new ArrayList<Object>() );

    /**
     * @return empty list
     */
    @SuppressWarnings("unchecked")
    public static <T> ReadOnlyListAdaptor<T> emptyList()
    {
        return (ReadOnlyListAdaptor<T>) emptyList;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return this.innerList.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( 
            final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final ReadOnlyListAdaptor<?> other = (ReadOnlyListAdaptor<?>) obj;

        return this.innerList.equals( other.innerList );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                "ReadOnlyListAdaptor[" + this.innerList + "]";
    }

}
