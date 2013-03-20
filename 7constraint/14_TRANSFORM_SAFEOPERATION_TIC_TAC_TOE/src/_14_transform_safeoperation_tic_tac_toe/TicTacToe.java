package _14_transform_safeoperation_tic_tac_toe;

import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * Tic tac Toe Spielfeld.
 * 
 * @author Heiner K&uuml;cker
 */
public final class TicTacToe
{
    /**
     * Anzahl Zeilen und Spalten.
     */
    public static final int ROW_COLUMN_COUNT = 3;
    
    /**
     * Welcher Spieler als nächstes dran ist.
     */
    public final Gamer nextGamer;

    /**
     * Spielfläche,
     * Array aus Zellen
     */
    private final Cell[][] cellArr =
            new Cell[ ROW_COLUMN_COUNT ][ ROW_COLUMN_COUNT ];

    /**
     * Konstruktor.
     * 
     * @param nextGamer welcher Spieler als nächstes dran ist
     */
    private TicTacToe(
            final Gamer nextGamer )
    {
        this.nextGamer = nextGamer;
    }

    /**
     * Factory-Methode für leeres Spiel.
     * 
     * @return leeres Spiel
     */
    public static TicTacToe empty()
    {
        final TicTacToe result =
                new TicTacToe(
                        Gamer.X.instance );
        
        for ( int rowIndex = 0 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            for ( int columnIndex = 0 ; columnIndex < ROW_COLUMN_COUNT ; columnIndex++ )
            {
                result.cellArr[ rowIndex ][ columnIndex ] =
                        Cell.Empty.instance;
            }
        }
        
        return result;
    }
    
    /**
     * Set-Methode,
     * die aktuelle Instanz des
     * Spiels wird nicht verändert,
     * sondern eine neue Instanz
     * zurückgegeben.
     * 
     * @param gamer aktueller Spieler
     * @param rowIndex Zeilen-Index
     * @param columnIndex Spalten-Index
     * 
     * @return neue Instanz
     */
    public TicTacToe set(
            final Gamer gamer ,
            final int rowIndex ,
            final int columnIndex )
    {
        if ( false == gamer.equals( this.nextGamer ) )
        {
            throw new IllegalArgumentException(
                    "wrong gamer: " +
                    gamer );
        }
        
        if ( false == this.cellArr[ rowIndex ][ columnIndex ].equals(
                Cell.Empty.instance ) )
        {
            throw new IllegalArgumentException(
                    "cell already set: " +
                    rowIndex + " " +
                    columnIndex );
        }
        
        final TicTacToe result =
                new TicTacToe(
                        gamer.next() );

        cloneGameArray(
                // src
                this ,
                // dst
                result );

        result.cellArr[ rowIndex ][ columnIndex ] =
                gamer.cell();
        
        return result;
    }
    
    /**
     * Zurücknehmen-Methode,
     * die aktuelle Instanz des
     * Spiels wird nicht verändert,
     * sondern eine neue Instanz
     * zurückgegeben.
     * 
     * @param gamer aktueller Spieler
     * @param rowIndex Zeilen-Index
     * @param columnIndex Spalten-Index
     * 
     * @return neue Instanz
     */
    public TicTacToe takeMoveBack(
            final Gamer gamer )
    {
        if ( false == gamer.equals( this.nextGamer ) )
        {
            throw new IllegalArgumentException(
                    "wrong gamer: " +
                    gamer );
        }
        
        final TicTacToe result =
                new TicTacToe(
                        gamer.next() );

        cloneGameArray(
                // src
                this ,
                // dst
                result );

        return result;
    }
    
    /**
     * Kopieren der gesetzten Zellen-Werte
     * von einem Spiel in das andere.
     * 
     * @param src Quelle
     * @param dst Ziel
     */
    private static void cloneGameArray(
            final TicTacToe src ,
            final TicTacToe dst )
    {
        for ( int rowIndex = 0 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            for ( int columnIndex = 0 ; columnIndex < ROW_COLUMN_COUNT ; columnIndex++ )
            {
                dst.cellArr[ rowIndex ][ columnIndex ] =
                        src.cellArr[ rowIndex ][ columnIndex ];
            }
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final SeparatedBuffer horizontalRulerBuff =
                new SeparatedBuffer(
                        "-+-+-\n" );
        
        for ( int rowIndex = 0 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            final SeparatedBuffer verticalRulerBuff =
                    new SeparatedBuffer(
                            "|" );
            
            for ( int columnIndex = 0 ; columnIndex < ROW_COLUMN_COUNT ; columnIndex++ )
            {
                if ( this.cellArr[ rowIndex ][ columnIndex ].equals(
                        Cell.Empty.instance ) )
                    // Leerzeichen statt 'Empty' ausgeben
                {
                    verticalRulerBuff.add( ' ' );
                }
                else
                {
                    verticalRulerBuff.add(
                            this.cellArr[ rowIndex ][ columnIndex ].toString() );
                }
            }

            horizontalRulerBuff.add(
                    verticalRulerBuff +
                    "\n" );
        }
        
        return 
                // führender Umbruch für korrekte Anzeige in den toString-Methoden der Constraints
                '\n' +
                horizontalRulerBuff.toString();
    }

    /**
     * @param rowIndex
     * @param columnIndex
     * @return Zellen-Inhalt
     */
    public Cell getCell(
            final int rowIndex ,
            final int columnIndex )
    {
        return this.cellArr[ rowIndex ][ columnIndex ];
    }
    
    /**
     * 
     * @return der Gewinner (Spieler, welcher gewonnen hat)
     */
    public Gamer winnerIs()
    {
        // entweder eine Zeile, eine Spalte oder eine Hauptdiagonale muss komplett besetzt sein
        for ( int rowIndex = 0 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            if ( rowCompleteSetted(
                    rowIndex ) )
            {
                return this.cellArr[ rowIndex ][ 0 ].gamer();
            }
        }
        for ( int columnIndex = 0 ; columnIndex < ROW_COLUMN_COUNT ; columnIndex++ )
        {
            if ( columnCompleteSetted(
                    columnIndex ) )
            {
                return this.cellArr[ 0 ][ columnIndex ].gamer();
            }
        }
        
        if ( diagonaleTopLeftToRightBottomompleteSetted() )
        {
            return this.cellArr[ 0 ][ 0 ].gamer();
        }
        
        if ( diagonaleTopRightToLeftBottomompleteSetted() )
        {
            return this.cellArr[ 0 ][ ROW_COLUMN_COUNT - 1 ].gamer();
        }
        
        throw new IllegalStateException(
                // Spiel nicht beendet oder unentschieden
                "game is not finished or draw" );
    }
    
    /**
     * @return ob das Spiel unentschieden ist
     */
    public boolean isDraw()
    {
        try
        {
            // Die Methode winnerIs() wirft eine Exception, wenn es keinen Gewinner gibt
            return winnerIs() == null;
        }
        catch ( final Exception e )
        {
            return true;
        }
    }

    /**
     * @param rowIndex
     * @return
     */
    private boolean rowCompleteSetted(
            final int rowIndex )
    {
        Cell expectedCell = cellArr[ rowIndex ][ 0 ];
        for( int columnIndex = 1 ; columnIndex < ROW_COLUMN_COUNT ; columnIndex++ )
        {
            if ( false == cellArr[ rowIndex ][ columnIndex ].equals( expectedCell ) )
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @param rowIndex
     * @return
     */
    private boolean columnCompleteSetted(
            final int columnIndex )
    {
        Cell expectedCell = cellArr[ 0 ][ columnIndex ];
        for( int rowIndex = 1 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            if ( false == cellArr[ rowIndex ][ columnIndex ].equals( expectedCell ) )
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @return
     */
    private boolean diagonaleTopLeftToRightBottomompleteSetted()
    {
        Cell expectedCell = cellArr[ 0 ][ 0 ];
        int columnIndex = 1;
        for( int rowIndex = 1 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            if ( false == cellArr[ rowIndex ][ columnIndex ].equals( expectedCell ) )
            {
                return false;
            }
            columnIndex++;
        }
        return true;
    }

    /**
     * @return
     */
    private boolean diagonaleTopRightToLeftBottomompleteSetted()
    {
        Cell expectedCell = cellArr[ 0 ][ ROW_COLUMN_COUNT - 1 ];
        int columnIndex = ROW_COLUMN_COUNT - 2;
        for( int rowIndex = 1 ; rowIndex < ROW_COLUMN_COUNT ; rowIndex++ )
        {
            if ( false == cellArr[ rowIndex ][ columnIndex ].equals( expectedCell ) )
            {
                return false;
            }
            columnIndex--;
        }
        return true;
    }

}
