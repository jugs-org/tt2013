package de.heinerkuecker.constraint.util.lang;

import java.util.HashSet;

/**
 * Hilfsklasse zum Absichern Einmaligkeit
 * für beliebige Typen.
 * 
 * @author Heiner K&uuml;cker
 */
public final class UniqueChecker<T>
{
    /**
     * Inneres Set zur Absicherung Einmaligkeit
     */
    private final HashSet<T> set = new HashSet<T>();
    
    /**
     * Name des abzusichernden Wertes
     */
    public final String uniqueValueName;

    /**
     * Konstruktor.
     * 
     * @param uniqueValueName
     */
    public UniqueChecker( 
            final String uniqueValueName )
    {
        this.uniqueValueName = uniqueValueName;
    }
    
    /**
     * Hinzufügen Wert
     * @param elem Wert
     * @hrows IllegalStateException if value not unique
     */
    public void add(
            final T elem )
    {
        if ( set.contains( elem ) )
        {
            throw new IllegalStateException(
                    uniqueValueName + " " +
                    elem + " " +
                    "already defined" );
        }
        set.add( elem );
    }

}
