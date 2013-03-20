package de.heinerkuecker.constraint.util.lang;

import java.util.Collection;

/**
 * Puffer-Klasse für beliebig
 * viele Teil-Strings, die
 * unter Verwendung eines
 * bestimmten Trenn-Strings
 * {@link #separator}
 * zusammengefügt werden
 * sollen.
 * 
 * @author Heiner K&uuml;cker
 */
public class SeparatedBuffer
{
    /**
     * Trenn-String der einzelnen
     * Teil-Strings
     */
    public final String separator;
    
    /**
     * innerer Puffer
     */
    private final StringBuilder buffer =
        new StringBuilder();
    
    /**
     * Merker, ob der erste Teil-String
     * bereits eingefügt wurde.
     */
    private boolean isNotFirst = false;

    /**
     * Konstruktor.
     *
     * @param separator Trenn-String
     */
    public SeparatedBuffer( 
            final String separator )
    {
        this.separator = separator;
    }
    
    /**
     * Teil-String hinzufügen
     * @param str Teil-String
     */
    public final void add(
            final String str )
    {
        if ( this.isNotFirst )
        {
            this.buffer.append( this.separator );
        }
        else
        {
            this.isNotFirst = true;
        }
        this.buffer.append( str );
    }
    
    /**
     * @param c
     */
    public void add(
            final char c )
    {
        add( String.valueOf( c ) );
    }

    public final void addAll(
            final String... strArr )
    {
        for ( String str : strArr )
        {
            add( str );
        }
    }

    public final void addAll(
            final Collection<String> strColl )
    {
        for ( String str : strColl )
        {
            add( str );
        }
    }

    /**
     * @return zusammengefügter String
     * @see StringBuilder#toString()
     */
    @Override
    public final String toString()
    {
        return this.buffer.toString();
    }
    
    /**
     * @return ob der Puffer leer ist
     */
    public final boolean isEmpty()
    {
        return this.buffer.length() == 0;
    }

    /**
     * @return ob der Puffer nicht leer ist
     */
    public final boolean isNotEmpty()
    {
        return ! isEmpty();
    }

}
