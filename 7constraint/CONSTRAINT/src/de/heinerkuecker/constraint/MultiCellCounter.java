package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Zähler mit mehreren Zellen
 * (Stellen), von denen jede
 * eine andere Maximal-Anzahl
 * besitzen kann.
 * 
 * TODO eventuell bei den Zählern mit size==0 immer -1 als aktuellen Wert zurückgeben
 * 
 * @author Heiner K&uuml;cker
 */
public final class MultiCellCounter
implements Iterator<int[]>
{
    /**
     * Klasse für eine Zelle (Stelle)
     * des {@link MultiCellCounter}.
     */
    private static class CounterCell
    {
        /**
         * maximale Anzahl Werte der Zelle
         */
        final int size;
        
        /**
         * aktueller Wert der Zelle
         */
        int currIndex;

        /**
         * Konstruktor.
         *
         * @param size
         */
        CounterCell( 
                final int size )
        {
            // TODO assert size > 0
            this.size = size;
        }

        /**
         * @return current index
         */
        public int getCurrIndex()
        {
            if ( this.size <= 0 )
                // assert size > 0
            {
                return -1;
            }
            return this.currIndex;
        }
    }

    /**
     * Array mit den Maximal-Anzahlen
     * der einzelnen Zellen.
     * Die höchstwertigste Zelle
     * hat die Position 0.
     */
    private final int[] sizePerCellArr;

    /**
     * Array mit den Zähler-Zellen
     */
    final CounterCell[] counterCellArr;

    /**
     * Überlauf-Flag.
     */
    private boolean carry;
    
    /**
     * Konstruktor.
     *
     * @param newSizePerCellArr Array mit den Maximal-Anzahlen
     *                          der einzelnen Zellen.
     *                          Die höchstwertigste Zelle
     *                          hat die Position 0.
     */
    public MultiCellCounter( 
            final int[] newSizePerCellArr )
    {
        this.sizePerCellArr = newSizePerCellArr;
        
        // Initialisieren des Counter-Cell-Array
        this.counterCellArr = new CounterCell[ newSizePerCellArr.length ];
        for ( int i = 0 ; i < newSizePerCellArr.length ; i++ )
        {
            this.counterCellArr[ i ] = 
                new CounterCell(
                        newSizePerCellArr[ i ] );
        }
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext()
    {
        return ! carry;
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public int[] next()
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        /*
         * Vor dem Weiterschalten den aktuellen Zählerstand 
         * in das Result-Array kopieren
         */
        final int[] resultIntArr = new int[ counterCellArr.length ];
        for ( int i = 0 ; i < sizePerCellArr.length ; i++ )
        {
            resultIntArr[ i ] = counterCellArr[ i ].getCurrIndex();
        }

        /*
         * Weiterschalten
         */
        carry = true;
        
        // an Position 0 befindet sich die höchstwertigste CounterCell
        for ( int i = counterCellArr.length - 1 ; i >= 0 ; i-- )
        {
            counterCellArr[ i ].currIndex++;
            if ( counterCellArr[ i ].currIndex < counterCellArr[ i ].size )
            {
                carry = false;
                break;
            }
            // nächste Runde zur nächsten Cell (Stelle)
            counterCellArr[ i ].currIndex = 0;
        }

        return resultIntArr;
    }

    /**
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Test.
     * @param args unused
     */
    public static void main(
            final String[] args )
    {
        int count = 0;
        
        final MultiCellCounter multiCellCounter =
            new MultiCellCounter(
                    //new int[]{ 0 , 1 , 2 , 3 } );
                    new int[]{ 3 , 3 , 3 } );
        
        while ( multiCellCounter.hasNext() )
        {
            final int[] currentCount = 
                multiCellCounter.next();
            
            count++;

            // Umwandeln int-Array in eine anzeigbare Liste
            final ArrayList<Integer> outList = new ArrayList<Integer>();
            for ( int i = 0 ; i < currentCount.length ; i++ )
            {
                outList.add( Integer.valueOf( currentCount[ i ] ) );
            }
            System.out.println( 
                    outList );
        }
        
        System.out.println( count );
    }

}
