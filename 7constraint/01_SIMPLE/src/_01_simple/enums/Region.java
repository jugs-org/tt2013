package _01_simple.enums;


/**
 * Diese Klasse bildet die
 * verschiedenen Regionen
 * der Hierarchie-Ebene
 * {@link Ebene.Niederlassung}
 * ab.
 * 
 * Aufgrund der höheren Typsicherheit
 * werden keine enums verwendet, sondern
 * nach dem Singleton-Pattern implementiert.
 * 
 * So hat jede Region einen eigenen Typ.
 * 
 * @author Heiner K&uuml;cker
 */
public abstract class Region
{
    /**
     * Name der Region
     */
    public final String name;

    /**
     * Konstruktor.
     * 
     * @param name Name der Region
     */
    protected Region( 
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
     * Region Nord.
     */
    public static final class Nord
    extends Region
    {
        /**
         * Singleton-Instanz.
         */
        public static final Nord instance = new Nord();

        /**
         * Konstruktor.
         */
        private Nord()
        {
            super( "Nord" );
        }
    }

    /**
     * Region Sued.
     */
    public static final class Sued
    extends Region
    {
        /**
         * Singleton-Instanz.
         */
        public static final Sued instance = new Sued();

        /**
         * Konstruktor.
         */
        private Sued()
        {
            super( "Sued" );
        }
    }

    /**
     * Region West.
     */
    public static final class West
    extends Region
    {
        /**
         * Singleton-Instanz.
         */
        public static final West instance = new West();

        /**
         * Konstruktor.
         */
        private West()
        {
            super( "West" );
        }
    }

    /**
     * Region Ost.
     */
    public static final class Ost
    extends Region
    {
        /**
         * Singleton-Instanz.
         */
        public static final Ost instance = new Ost();

        /**
         * Konstruktor.
         */
        private Ost()
        {
            super( "Ost" );
        }
    }

}