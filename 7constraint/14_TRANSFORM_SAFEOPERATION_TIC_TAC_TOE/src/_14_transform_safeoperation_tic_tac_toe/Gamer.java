package _14_transform_safeoperation_tic_tac_toe;

import java.io.Serializable;

/**
 * Spieler im Tic Tac Toe.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public abstract class Gamer
implements Serializable
{
    /**
     * Zu implementierende Methode
     * zum Zurückgeben des nächsten
     * Spielers (der als nächster
     * dran ist).
     * 
     * @return nächster Spieler
     */
    abstract public Gamer next();

    /**
     * Zu implementierende Methode
     * zum Zurückgeben des zu
     * setzenden Symbols für den
     * aktuellen Spieler.
     * 
     * @return welches Symbol der Spieler in eine Zelle setzt
     */
    abstract public Cell cell();

    /**
     * Spieler X (der immer X setzt).
     */
    public static final class X
    extends Gamer
    {
        /**
         * Singleton-Instanz.
         */
        public static final X instance = new X();
        
        /**
         * Konstruktor private for Singleton.
         */
        private X()
        {
            super();
        }

        /**
         * @see Gamer#next
         */
        @Override
        public Gamer next()
        {
            return O.instance;
        }

        /**
         * @see Gamer#cell
         */
        @Override
        public Cell cell()
        {
            return Cell.X.instance;
        }
    }

    /**
     * Spieler O (der immer O setzt).
     */
    public static final class O
    extends Gamer
    {
        /**
         * Singleton-Instanz.
         */
        public static final O instance = new O();

        /**
         * Konstruktor private for Singleton.
         */
        private O()
        {
            super();
        }

        /**
         * @see Gamer#next
         */
        @Override
        public Gamer next()
        {
            return X.instance;
        }

        /**
         * @see Gamer#cell
         */
        @Override
        public Cell cell()
        {
            return Cell.O.instance;
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final String canonicalClassName =
                this.getClass().getCanonicalName();
        return canonicalClassName.substring(
                canonicalClassName.lastIndexOf(
                        '.' ) + 1 );
    }

}
