package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Diese HashSet-Erweiterung wirft eine Exception,
 * wenn ein vorher vorhandener Eintrag (Entry)
 * durch eine put-Operation verdr�ngt werden w�rde
 * (eine etwas andere Implementierung ist notwendig,
 * wenn vor der put-Operation gepr�ft werden soll).
 * 
 * @author Heiner K�cker
 */
public class NoPreviousValueLinkedHashSet<E>
extends LinkedHashSet<E>
implements Serializable
{
    /**
     * serial version id generated by eclipse
     */
    private static final long serialVersionUID = 5517689357425047328L;

    /**
     * Constructor.
     */
    public NoPreviousValueLinkedHashSet()
    {
        super();
    }

    /**
     * Constructor.
     * @param pInitialCapacity
     * @param pLoadFactor
     */
    public NoPreviousValueLinkedHashSet(
            final int pInitialCapacity, 
            final float pLoadFactor)
    {
        super( pInitialCapacity, pLoadFactor );
    }

    /**
     * Constructor.
     * @param pInitialCapacity
     */
    public NoPreviousValueLinkedHashSet(
            final int pInitialCapacity)
    {
        super( pInitialCapacity );
    }

    /**
     * Constructor.
     * @param pColl
     */
    public NoPreviousValueLinkedHashSet(
            final Collection<? extends E> pColl )
    {
        super( pColl );
    }

    /**
     * Diese HashSet-Erweiterung wirft eine Exception,
     * wenn ein vorher vorhandener Eintrag (Entry)
     * durch eine put-Operation verdr�ngt werden w�rde
     * (eine etwas andere Implementierung ist notwendig,
     * wenn vor der put-Operation gepr�ft werden soll).
     * 
     * @see java.util.HashSet#add(Object)
     * @throws IllegalStateException wenn ein vorheriger Wert verdr�ngt wurde
     */
    public boolean add(
            final E pE )
    {
        if ( this.contains( pE ) )
        {
            throw new 
            //RuntimeException( 
            IllegalStateException(
                    "previous element suppressed (verdr�ngt): " + pE );
        }
        return super.add( pE );
    }

}
