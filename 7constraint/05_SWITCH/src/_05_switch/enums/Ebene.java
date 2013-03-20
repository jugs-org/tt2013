package _05_switch.enums;

/**
 * Diese Klasse bildet die Hierarchie
 * einer angenommenen Organisation aus
 * {@link Zentrale},
 * {@link Niederlassung} und
 * {@link Filiale}
 * ab.
 * 
 * Aufgrund der höheren Typsicherheit
 * werden keine enums verwendet, sondern
 * nach dem Singleton-Pattern implementiert.
 * 
 * @author Heiner K&uuml;cker
 */
public abstract class Ebene
{
    /**
     * Name der Ebene
     */
    public final String name;

    /**
     * Konstruktor.
     * 
     * @param name Name der Ebene
     */
    protected Ebene( 
            final String name )
    {
        this.name = name;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        return this.name.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals( 
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
        
        // aufgrund Singleton müssen die Member nicht verglichen werden
        return getClass() != obj.getClass();
    }

    /**
     * Zentrale.
     */
    public static final class Zentrale
    extends Ebene
    {
        /**
         * Singleton-Instanz.
         */
        public static final Zentrale instance = new Zentrale();

        /**
         * Konstruktor.
         */
        private Zentrale()
        {
            super( "Zentrale" );
        }
    }

    /**
     * Niederlassung.
     */
    public static final class Niederlassung
    extends Ebene
    {
        /**
         * Singleton-Instanz.
         */
        public static final Niederlassung instance = new Niederlassung();

        /**
         * Konstruktor.
         */
        private Niederlassung()
        {
            super( "Niederlassung" );
        }
    }

    /**
     * Filiale.
     */
    public static final class Filiale
    extends Ebene
    {
        /**
         * Singleton-Instanz.
         */
        public static final Filiale instance = new Filiale();

        /**
         * Konstruktor.
         */
        private Filiale()
        {
            super( "Filiale" );
        }
    }

}