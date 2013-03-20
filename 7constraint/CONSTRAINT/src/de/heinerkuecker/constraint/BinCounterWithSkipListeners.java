package de.heinerkuecker.constraint;

import java.util.Arrays;

/**
 * Binär-Zähler mit Listenern für die Umschaltung einzelner Bits.
 * 
 * @author Heiner K&uuml;cker
 */
public final class BinCounterWithSkipListeners
{
    /**
     * inneres boolean-Array
     */
    private final boolean[] boolArr;
    
    /**
     * Merker für Überlauf.
     */
    private boolean isOverflow = false;
    
    /**
     * inneres {@link BinCounterSkipListener}-Array
     */
    private final BinCounterSkipListener[][] listenerArr;
    
    /**
     * Konstruktor.
     * @param size Anzahl Bits
     */
    public BinCounterWithSkipListeners(
            final int size )
    {
        this.boolArr = new boolean[ size ];
        this.listenerArr = new BinCounterSkipListener[ size ][ 0 ];
    }

    /**
     * Weiterzählen.
     */
    public void skip()
    {
        for ( int i = 0 ; i < boolArr.length ; i++ )
        {
            if ( boolArr[ i ] == false )
            {
                boolArr[ i ] = true;
                notifyListeners( i , true );
                return;
            }
            
            boolArr[ i ] = false;
            notifyListeners( i , false );
        }

        isOverflow = true;
    }
    
    /**
     * Diese Methode dient zum Abfragen eines bestimmten Bits in diesem Zähler.
     * @param index Index des abzufragenden Bits (Basis 0)
     * @return Wert des abgefragten Bits (<code>true</code> or <code>false</code>)
     */
    public boolean get(
            final int index )
    {
        return boolArr[ index ];
    }

    /**
     * @return the isOverflow
     */
    public boolean isOverflow()
    {
        return this.isOverflow;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "BinCounter" + Arrays.toString( this.boolArr );
    }

    /**
     * Test.
     * @param args unused
     */
    public static void main(
            final String[] args )
    {
        final BinCounterWithSkipListeners binCounter = new BinCounterWithSkipListeners( 4 );
        
        while ( binCounter.isOverflow() == false )
        {
            System.out.println( binCounter );
            binCounter.skip();
        }
        System.out.println( binCounter );
    }

    /**
     * @return size
     */
    public int size()
    {
        return this.boolArr.length;
    }

    /**
     * Add Listener.
     * 
     * @param digitIndex
     * @param listenerToAdd
     */
    public void addListener(
            final int digitIndex ,
            final BinCounterSkipListener listenerToAdd )
    {
        final BinCounterSkipListener[] oldDigitLstArr = this.listenerArr[ digitIndex ];
        final BinCounterSkipListener[] newDigitLstArr = new BinCounterSkipListener[ oldDigitLstArr.length + 1 ];
        System.arraycopy( oldDigitLstArr , 0 , newDigitLstArr , 0 , oldDigitLstArr.length );
        newDigitLstArr[ oldDigitLstArr.length ] = listenerToAdd;
        this.listenerArr[ digitIndex ] = newDigitLstArr;
    }

    /**
     * Informieren Listener.
     * 
     * @param digitIndex Index der umgeschalteten Stelle
     * @param newValue neuer Wert
     */
    private void notifyListeners(
            final int digitIndex ,
            final boolean newValue )
    {
        final BinCounterSkipListener[] digitLstArr = this.listenerArr[ digitIndex ];
        for ( int i = 0 ; i < digitLstArr.length ; i++ )
        {
            digitLstArr[ i ].notifyListener( newValue );
        }
    }
    
}
