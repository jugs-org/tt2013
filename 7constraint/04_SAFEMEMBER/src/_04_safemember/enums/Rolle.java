package _04_safemember.enums;

import _04_safemember.enums.Ebene.Filiale;

/**
 * Diese Klasse bildet die Hierarchie
 * einer angenommenen Organisation aus
 * den Ebenen {@link Ebene}
 * {@link Zentrale},
 * {@link Niederlassung} und
 * {@link Filiale}
 * 
 * sowie den Regionen {@link Region}
 * {@link Region.Nord},
 * {@link Region.Sued},
 * {@link Region.West} und
 * {@link Region.Ost}
 * ab.
 * 
 * Aufgrund der höheren Typsicherheit
 * werden keine enums verwendet, sondern
 * nach dem Singleton-Pattern implementiert.
 * 
 * @author Heiner K&uuml;cker
 */
public abstract class Rolle
{
    /**
     * Name der Rolle
     */
    public final String name;
    
    /**
     * Ebene der Rolle
     */
    public final Ebene ebene;

    /**
     * Konstruktor.
     * 
     * @param name Name der Rolle
     * @param ebene Ebene der Rolle
     */
    protected Rolle( 
            final String name ,
            final Ebene ebene )
    {
        this.name = name;
        this.ebene = ebene;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return this.name.hashCode();
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
        
        // aufgrund Singleton müssen die Member nicht verglichen werden
        return getClass() != obj.getClass();
    }

    /**
     * Zentrale.
     */
    public static final class Zentrale
    extends Rolle
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
            super( 
                    "Zentrale" ,
                    Ebene.Zentrale.instance );
        }
    }

    /**
     * Niederlassung.
     */
    abstract public static class Niederlassung
    extends Rolle
    {
        /**
         * Region der Niederlassung
         */
        public final Region region;

        /**
         * Konstruktor.
         * 
         * @param region Region der Niederlassung
         */
        protected Niederlassung(
                final Region region )
        {
            super( 
                    "Niederlassung " +
                    region.name ,
                    Ebene.Niederlassung.instance );
            
            this.region = region;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public final int hashCode()
        {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.region.hashCode();
            return result;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public final boolean equals( Object obj )
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
        
        
    }

    /**
     * Niederlassung Nord.
     */
    public static final class NiederlassungNord
    extends Niederlassung
    {
        /**
         * Singleton-Instanz.
         */
        public static final NiederlassungNord instance = new NiederlassungNord();

        /**
         * Konstruktor.
         */
        private NiederlassungNord()
        {
            super( 
                    Region.Nord.instance );
        }
    }

    /**
     * Niederlassung Sued.
     */
    public static final class NiederlassungSued
    extends Niederlassung
    {
        /**
         * Singleton-Instanz.
         */
        public static final NiederlassungSued instance = new NiederlassungSued();

        /**
         * Konstruktor.
         */
        private NiederlassungSued()
        {
            super( 
                    Region.Sued.instance );
        }
    }

    /**
     * Niederlassung West.
     */
    public static final class NiederlassungWest
    extends Niederlassung
    {
        /**
         * Singleton-Instanz.
         */
        public static final NiederlassungWest instance = new NiederlassungWest();

        /**
         * Konstruktor.
         */
        private NiederlassungWest()
        {
            super( 
                    Region.West.instance );
        }
    }

    /**
     * Niederlassung Ost.
     */
    public static final class NiederlassungOst
    extends Niederlassung
    {
        /**
         * Singleton-Instanz.
         */
        public static final NiederlassungOst instance = new NiederlassungOst();

        /**
         * Konstruktor.
         */
        private NiederlassungOst()
        {
            super( 
                    Region.Ost.instance );
        }
    }

    /**
     * Filiale.
     */
    abstract public static class Filiale
    extends Rolle
    {
        /**
         * Niederlassung der Filiale
         */
        public final Niederlassung niederlassung;

        /**
         * Nummer der Filiale
         */
        public final int filialNr;

        /**
         * Konstruktor.
         * 
         * @param niederlassung Niederlassung Filiale
         * @param filialNr Nummer der Filiale
         */
        protected Filiale(
                final Niederlassung niederlassung ,
                final int filialNr )
        {
            super( 
                    "Filiale " +
                    niederlassung.region.name + " " +
                    filialNr ,
                    Ebene.Filiale.instance );
            
            this.niederlassung = niederlassung;
            this.filialNr = filialNr;
        }
    }

    /**
     * Filiale Nord.
     * Kein Singleton.
     */
    public static final class FilialeNord
    extends Filiale
    {
        /**
         * Konstruktor.
         * 
         * @param filialNr Nummer der Filiale
         */
        public FilialeNord(
                final int filialNr )
        {
            super(
                    NiederlassungNord.instance ,
                    filialNr );
        }
    }

    /**
     * Filiale Süd.
     * Kein Singleton.
     */
    public static final class FilialeSued
    extends Filiale
    {
        /**
         * Konstruktor.
         * 
         * @param filialNr Nummer der Filiale
         */
        public FilialeSued(
                final int filialNr )
        {
            super(
                    NiederlassungSued.instance ,
                    filialNr );
        }
    }

}