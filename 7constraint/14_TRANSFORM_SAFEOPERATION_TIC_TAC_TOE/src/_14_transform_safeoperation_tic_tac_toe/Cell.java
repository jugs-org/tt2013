package _14_transform_safeoperation_tic_tac_toe;

import java.io.Serializable;


/**
 * Zelle im Tic Tac Toe.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public abstract class Cell
implements Serializable
{
    /**
     * @return der Spieler, welcher diese Zelle gestzt hat
     */
    abstract public Gamer gamer();

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

    /**
     * Leere Zelle.
     */
    public static final class Empty
    extends Cell
    {
        /**
         * Singleton-Instanz.
         */
        public static final Empty instance = new Empty();
        
        /**
         * Konstruktor private for Singleton.
         */
        private Empty()
        {
            super();
        }

        /**
         * @see Cell#gamer
         */
        @Override
        public Gamer gamer()
        {
            throw new UnsupportedOperationException();
        }
        
    }

    /**
     * Zelle mit X.
     */
    public static final class X
    extends Cell
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
         * @see Cell#gamer
         */
        @Override
        public Gamer gamer()
        {
            return Gamer.X.instance;
        }
    }

    /**
     * Zelle mit O.
     */
    public static final class O
    extends Cell
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
         * @see Cell#gamer
         */
        @Override
        public Gamer gamer()
        {
            return Gamer.O.instance;
        }
    }

}
