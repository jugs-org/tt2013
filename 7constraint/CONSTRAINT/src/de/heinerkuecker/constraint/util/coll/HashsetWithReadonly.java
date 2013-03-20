package de.heinerkuecker.constraint.util.coll;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Heiner K&uuml;cker
 */
public class HashsetWithReadonly<E>
extends HashSet<E>
implements ReadOnlySet<E>
{

    /**
     * Konstruktor.
     */
    public HashsetWithReadonly()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * Konstruktor.
     * @param c
     */
    public HashsetWithReadonly( 
            final Collection<? extends E> c )
    {
        super( c );
    }

    /**
     * Konstruktor.
     * @param initialCapacity
     */
    public HashsetWithReadonly( 
            final int initialCapacity )
    {
        super( initialCapacity );
    }

    /**
     * Konstruktor.
     * @param initialCapacity
     * @param loadFactor
     */
    public HashsetWithReadonly(
            int initialCapacity ,
            float loadFactor )
    {
        super( initialCapacity , loadFactor );
    }

    /**
     * Konstruktor.
     * @param c
     */
    public HashsetWithReadonly( 
            final Iterable<? extends E> ibl )
    {
        for ( final E e : ibl )
        {
            this.add( e );
        }
    }

    /**
     * @see ReadOnlySet#toJavaSet()
     */
    @Override
    public Set<E> toJavaSet()
    {
        return this;
    }

    /**
     * @see java.util.AbstractCollection#containsAllFromIterable
     */
    public boolean containsAllFromIterable( 
            final Iterable<? extends E> i )
    {
        for ( final E e : i )
        {
            if ( false == this.contains( e ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @see java.util.AbstractCollection#addAll(java.util.Collection)
     */
    public boolean addAll( 
            final Iterable<? extends E> i )
    {
        boolean modified = false;
        for ( final E e : i )
        {
            if (add( e ) )
            {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * @see java.util.AbstractSet#removeAll(java.util.Collection)
     */
    public boolean removeAll( 
            final ReadOnlySet<E> r )
    {
        boolean modified = false;

        if (size() > r.size()) {
            for (Iterator<?> i = r.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
            for (Iterator<E> i = iterator(); i.hasNext(); ) {
                if (r.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }

    /**
     * @see java.util.AbstractSet#removeAll(java.util.Collection)
     */
    public boolean removeAll( 
            final Iterable<? extends E> ibl )
    {
        boolean modified = false;

        for (Iterator<?> i = ibl.iterator(); i.hasNext(); )
        {
            modified |= remove(i.next());
        }

        return modified;
    }

    /**
     * @see ReadOnlySet#containsOnly
     */
    @Override
    public boolean containsOnly(
            final ReadOnlySet<? extends E> setToCheck )
    {
        if ( this.size() > setToCheck.size() )
        {
            return false;
        }

        for ( final E eToCheck : this )
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
            final Iterable<? extends E> iterableToCheck )
    {
        for ( final E eToCheck : iterableToCheck )
        {
            if ( this.contains( eToCheck ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * @see ReadOnlySet#equalsByElements
     */
    @Override
    public boolean equalsByElements( 
            final Set<? extends E> setToCheck )
    {
        if ( this.size() != setToCheck.size() )
        {
            return false;
        }
        return this.containsAll( setToCheck );
    }

}
