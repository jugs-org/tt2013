package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.heinerkuecker.constraint.util.lang.NoRemoveIteratorAdaptor;

/**
 * Adapter-Implementierung für eine unveränderliche Liste.
 * 
 * @author Heiner K&uuml;cker
 */
public final class ReadOnlySetAdaptor<T>
implements ReadOnlySet<T>, Serializable
{
    private final Set<? extends T> innerSet;

    /**
     * Konstruktor.
     *
     * @param setToWrap
     */
    public ReadOnlySetAdaptor( 
            final Set<? extends T> setToWrap )
    {
        if ( setToWrap == null )
        {
            throw new NullPointerException();
        }
        
        this.innerSet = setToWrap;
    }

    /**
     * Konstruktor.
     *
     * @param elemArr
     */
    public ReadOnlySetAdaptor( 
            final T... elemArr )
    {
        this(
                new HashSet<T>( 
                        Arrays.asList(
                                elemArr ) ) );
    }

    /**
     * @see ReadOnlySet#size()
     */
    @Override
    public int size()
    {
        return this.innerSet.size();
    }

    /**
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator()
    {
        return
            new NoRemoveIteratorAdaptor<T>(
                    this.innerSet.iterator() );
    }

    /**
     * @see ReadOnlySet#contains
     */
    @Override
    public boolean contains( 
            final T elemToCheck )
    {
        return innerSet.contains( elemToCheck );
    }

    /**
     * @see ReadOnlySet#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        return innerSet.isEmpty();
    }

    /**
     * @see ReadOnlySet#toJavaSet()
     */
    @Override
    public Set<T> toJavaSet()
    {
        if ( this.innerSet instanceof SortedSet )
        {
            return
                    new TreeSet<T>(
                            this.innerSet );
        }
        else
        {
            return
                    new HashSet<T>(
                            this.innerSet );
            
        }
    }

    /**
     * global einmalige Instanz einer leeren menge
     */
    private static final ReadOnlySet<?> emptySet =
        new ReadOnlySetAdaptor<Object>(
                new HashSet<Object>() );

    /**
     * @return empty set
     */
    @SuppressWarnings("unchecked")
    public static <T> ReadOnlySetAdaptor<T> emptySet()
    {
        return (ReadOnlySetAdaptor<T>) emptySet;
    }

    /**
     * Cache für {@link #hashCode}
     */
    private int hashCodeCache;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        if ( this.hashCodeCache == 0 )
        {
            this.hashCodeCache = this.innerSet.hashCode();
        }
        
        return this.hashCodeCache;
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
        final ReadOnlySetAdaptor<?> other = (ReadOnlySetAdaptor<?>) obj;

        return this.innerSet.equals( other.innerSet );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return
                "ReadOnlySetAdaptor" +this.innerSet;
    }

    /**
     * @see ReadOnlySet#containsOnly
     */
    @Override
    public boolean containsOnly(
            final ReadOnlySet<? extends T> setToCheck )
    {
        if ( this.size() > setToCheck.size() )
        {
            return false;
        }

        for ( final T eToCheck : setToCheck )
        {
            if ( false == this.contains( eToCheck ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * @see ReadOnlySet#containsMinOneOf
     */
    @Override
    public boolean containsMinOneOf(
            final Iterable<? extends T> iterableToCheck )
    {
        for ( final T eToCheck : iterableToCheck )
        {
            if ( this.contains( eToCheck ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @see ReadOnlySet#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll( 
            final Collection<?> c )
    {
        return this.innerSet.containsAll( c );
    }

    /**
     * @see ReadOnlySet#containsAllFromIterable
     */
    @Override
    public boolean containsAllFromIterable( 
            final Iterable<? extends T> c )
    {
        for ( final T eToCheck : c )
        {
            if ( false == this.contains( eToCheck ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @see ReadOnlySet#equalsByElements
     */
    @Override
    public boolean equalsByElements( 
            final Set<? extends T> setToCheck )
    {
        if ( this.size() != setToCheck.size() )
        {
            return false;
        }
        return this.containsAll( setToCheck );
    }

}
