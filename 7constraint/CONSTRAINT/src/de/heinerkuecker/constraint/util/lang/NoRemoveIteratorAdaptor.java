package de.heinerkuecker.constraint.util.lang;

import java.util.Iterator;

/**
 * NoRemoveIteratorAdaptor.
 * Ein gegen Aufruf der Methode
 * {@link Iterator#remove()}
 * zu schützender Iterator.
 * 
 * @param <E> Type-Parameter, Element-Typ
 * @author Heiner K&uuml;cker
 */
public final class NoRemoveIteratorAdaptor<E>
implements Iterator<E>
{
    /**
     * gegen Aufruf der Methode
     * {@link Iterator#remove()}
     * zu schützender Iterator
     */
    private final Iterator<? extends E> iteratorToWrap;

    /**
     * Konstruktor.
     *
     * @param iteratorToWrap zu schützender Iterator
     */
    public NoRemoveIteratorAdaptor( 
            final Iterator<? extends E> iteratorToWrap )
    {
        if ( iteratorToWrap == null )
        {
            throw new NullPointerException();
        }

        this.iteratorToWrap = iteratorToWrap;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext()
    {
        return this.iteratorToWrap.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public E next()
    {
        return this.iteratorToWrap.next();
    }

    /**
     * Unsupported operation.
     * 
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}
