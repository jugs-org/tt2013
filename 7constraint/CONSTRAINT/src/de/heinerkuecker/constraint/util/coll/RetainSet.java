package de.heinerkuecker.constraint.util.coll;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Diese Klasse soll gegenüber
 * der Anwendung der Methode
 * {@link Set#retainAll}
 * Laufzeit sparen.
 * <br/>
 * Es gibt nur lesende Zugriffe,
 * schreibende Methoden werfen
 * {@link UnsupportedOperationException}.
 * <br/>
 * Der Laufzeit-Vorteil kommt vor allem
 * zum Tragen, wenn der {@link Iterator}
 * aus der Methode {@link Iterable}
 * nicht vollst&auml;ndig durchlaufen wird.
 * 
 * @author Heiner K&uuml;cker
 */
public final class RetainSet<E>
implements Set<E>
{
    private final ReadOnlySet<E> set1;
    
    private final ReadOnlySet<E> set2;

    /**
     * Konstruktor.
     * @param set1
     * @param set2
     */
    public RetainSet( 
            final ReadOnlySet<E> set1 ,
            final ReadOnlySet<E> set2 )
    {
        if ( set1.size() <= set2.size() )
            // das jeweils kleinere Set wird im Iterator durchlaufen
        {
            this.set1 = set1;
            this.set2 = set2;
        }
        else
        {
            this.set1 = set2;
            this.set2 = set1;
        }
    }

    /**
     * Konstruktor.
     * @param set1
     * @param set2
     */
    public RetainSet( 
            final ReadOnlySet<E> set1 ,
            final Set<E> set2 )
    {
        this(
                set1 ,
                new ReadOnlySetAdaptor<E>( set2 ) );
    }

    /**
     * @see java.util.Set#size()
     */
    @Override
    public int size()
    {
        //return 0;
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        //return false;
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#contains(java.lang.Object)
     */
    @Override
    public boolean contains( 
            final Object o )
    {
        return ( this.set1.contains( (E) o ) &&
                this.set2.contains( (E) o ) );
    }

    /**
     * @see java.util.Set#iterator()
     */
    @Override
    public Iterator<E> iterator()
    {
        return new Iterator<E>()
        {
            private final Iterator<E> set1Iterator = RetainSet.this.set1.iterator();

            private boolean hasNextElement;

            private E nextElement;

            // Initializer
            {
                skip();
            }

            @Override
            public boolean hasNext()
            {
                return this.hasNextElement;
            }

            @Override
            public E next()
            {
                if ( false == hasNext() )
                {
                    throw new NoSuchElementException();
                }
                final E tmpNextElement = this.nextElement;

                skip();

                return tmpNextElement;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }

            private void skip()
            {
                // alles auf nicht ok setzen
                this.nextElement = null;
                this.hasNextElement = false;

                while ( this.set1Iterator.hasNext() )
                    // bis zum nächsten Element, welche in beiden Sets enthalten ist, laufen
                {
                    final E tmpElem = this.set1Iterator.next();
                    if ( RetainSet.this.set2.contains( tmpElem ) )
                    {
                        // ok
                        this.nextElement = tmpElem;
                        this.hasNextElement = true;
                        return;
                    }
                }
            }
        };
    }

    /**
     * @see java.util.Set#toArray()
     */
    @Override
    public Object[] toArray()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#toArray(T[])
     */
    @Override
    public <T> T[] toArray( 
            final T[] a )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#add(java.lang.Object)
     */
    @Override
    public boolean add( 
            final E e )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#remove(java.lang.Object)
     */
    @Override
    public boolean remove( 
            final Object o )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll( 
            final Collection<?> c )
    {
        for ( Object object : c )
        {
            if ( false == this.contains( object ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @see java.util.Set#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll( 
            final Collection<? extends E> c )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll( 
            final Collection<?> c )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll( 
            final Collection<?> c )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Set#clear()
     */
    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }

}
