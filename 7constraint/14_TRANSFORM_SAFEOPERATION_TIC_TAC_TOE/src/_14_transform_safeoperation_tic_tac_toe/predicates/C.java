package _14_transform_safeoperation_tic_tac_toe.predicates;

import _14_transform_safeoperation_tic_tac_toe.Cell;
import _14_transform_safeoperation_tic_tac_toe.TicTacToe;
import _14_transform_safeoperation_tic_tac_toe.safeoperation.SetCell;
import _14_transform_safeoperation_tic_tac_toe.safeoperation.TakeMoveBack;
import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;

/**
 * Prädikat Tic Tac Toe Spielfeld Zelle.
 *
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class C
extends PrimitivPredicate<TicTacToe>
implements ParametricalPredicate<TicTacToe>, TransformablePredicate<TicTacToe>
{
    /**
     * Zellen-Zustand.
     */
    public final Cell cell;

    /**
     * Zellen-Zeilen-Index.
     */
    public final int rowIndex;

    /**
     * Zellen-Spalten-Index.
     */
    public final int columnIndex;

    /**
     * Konstruktor.
     *
     * @param cell
     * @param rowIndex
     * @param columnIndex
     */
    public C(
            final Cell cell ,
            final int rowIndex ,
            final int columnIndex )
    {
        super(
                //lineNr
                0 );

        this.cell = cell;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    private static final C _00 = new C( Cell.Empty.instance , 0 , 0 );
    private static final C _01 = new C( Cell.Empty.instance , 0 , 1 );
    private static final C _02 = new C( Cell.Empty.instance , 0 , 2 );
    private static final C _10 = new C( Cell.Empty.instance , 1 , 0 );
    private static final C _11 = new C( Cell.Empty.instance , 1 , 1 );
    private static final C _12 = new C( Cell.Empty.instance , 1 , 2 );
    private static final C _20 = new C( Cell.Empty.instance , 2 , 0 );
    private static final C _21 = new C( Cell.Empty.instance , 2 , 1 );
    private static final C _22 = new C( Cell.Empty.instance , 2 , 2 );

    private static final C X00 = new C( Cell.X.instance , 0 , 0 );
    private static final C X01 = new C( Cell.X.instance , 0 , 1 );
    private static final C X02 = new C( Cell.X.instance , 0 , 2 );
    private static final C X10 = new C( Cell.X.instance , 1 , 0 );
    private static final C X11 = new C( Cell.X.instance , 1 , 1 );
    private static final C X12 = new C( Cell.X.instance , 1 , 2 );
    private static final C X20 = new C( Cell.X.instance , 2 , 0 );
    private static final C X21 = new C( Cell.X.instance , 2 , 1 );
    private static final C X22 = new C( Cell.X.instance , 2 , 2 );

    private static final C O00 = new C( Cell.O.instance , 0 , 0 );
    private static final C O01 = new C( Cell.O.instance , 0 , 1 );
    private static final C O02 = new C( Cell.O.instance , 0 , 2 );
    private static final C O10 = new C( Cell.O.instance , 1 , 0 );
    private static final C O11 = new C( Cell.O.instance , 1 , 1 );
    private static final C O12 = new C( Cell.O.instance , 1 , 2 );
    private static final C O20 = new C( Cell.O.instance , 2 , 0 );
    private static final C O21 = new C( Cell.O.instance , 2 , 1 );
    private static final C O22 = new C( Cell.O.instance , 2 , 2 );

    /**
     * Fliegengewicht-Methode.
     *
     * @param cell
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public static C instanceOf(
            final Cell cell ,
            final int rowIndex ,
            final int columnIndex )
    {
        if ( cell.equals( Cell.Empty.instance ) )
        {
            switch ( rowIndex )
            {
                case 0:
                    switch ( columnIndex )
                    {
                        case 0:
                            return _00;
                        case 1:
                            return _01;
                        case 2:
                            return _02;
                        default:
                            throw new IllegalArgumentException();
                    }
                case 1:
                    switch ( columnIndex )
                    {
                        case 0:
                            return _10;
                        case 1:
                            return _11;
                        case 2:
                            return _12;
                        default:
                            throw new IllegalArgumentException();
                    }
                case 2:
                    switch ( columnIndex )
                    {
                        case 0:
                            return _20;
                        case 1:
                            return _21;
                        case 2:
                            return _22;
                        default:
                            throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException();
            }
        }
        else if ( cell.equals( Cell.X.instance ) )
        {
            switch ( rowIndex )
            {
                case 0:
                    switch ( columnIndex )
                    {
                        case 0:
                            return X00;
                        case 1:
                            return X01;
                        case 2:
                            return X02;
                        default:
                            throw new IllegalArgumentException();
                    }
                case 1:
                    switch ( columnIndex )
                    {
                        case 0:
                            return X10;
                        case 1:
                            return X11;
                        case 2:
                            return X12;
                        default:
                            throw new IllegalArgumentException();
                    }
                case 2:
                    switch ( columnIndex )
                    {
                        case 0:
                            return X20;
                        case 1:
                            return X21;
                        case 2:
                            return X22;
                        default:
                            throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException();
            }
        }
        else if ( cell.equals( Cell.O.instance ) )
        {
            switch ( rowIndex )
            {
                case 0:
                    switch ( columnIndex )
                    {
                        case 0:
                            return O00;
                        case 1:
                            return O01;
                        case 2:
                            return O02;
                        default:
                            throw new IllegalArgumentException();
                    }
                case 1:
                    switch ( columnIndex )
                    {
                        case 0:
                            return O10;
                        case 1:
                            return O11;
                        case 2:
                            return O12;
                        default:
                            throw new IllegalArgumentException();
                    }
                case 2:
                    switch ( columnIndex )
                    {
                        case 0:
                            return O20;
                        case 1:
                            return O21;
                        case 2:
                            return O22;
                        default:
                            throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException();
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }


    /**
     * @see Predicate#test
     */
    @Override
    public boolean test(
            final TicTacToe game )
    {
        return
                game.getCell(
                        this.rowIndex ,
                        this.columnIndex ).equals(
                                this.cell );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return
            this.getClass().getSimpleName() +
            this.cell + "" +
            this.rowIndex + "" +
            this.columnIndex;
    }

    /**
     * @see Predicate#toFileName
     */
    @Override
    public String toFileName(
            final String separatorStr )
    {
        return
            this.toString();
    }

    /**
     * Cache für {@link #hashCode()}.
     */
    private int hashCode = 0;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        if ( this.hashCode == 0 )
        {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ( ( this.cell == null ) ? 0 : this.cell.hashCode() );
            result = prime * result + this.columnIndex;
            result = prime * result + this.rowIndex;
            this.hashCode = result;
        }
        return this.hashCode;
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
        if ( !super.equals( obj ) )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        C other = (C) obj;
        if ( this.cell == null )
        {
            if ( other.cell != null )
            {
                return false;
            }
        }
        else if ( !this.cell.equals( other.cell ) )
        {
            return false;
        }
        if ( this.columnIndex != other.columnIndex )
        {
            return false;
        }
        if ( this.rowIndex != other.rowIndex )
        {
            return false;
        }
        return true;
    }

    /**
     * @see ParametricalPredicate#toJavaInstanceOfStr
     */
    @Override
    public String toJavaInstanceOfStr()
    {
        return
                "new " +
                this.getClass().getSimpleName() +
                "( " +
                Cell.class.getName() + "." +
                // hier wird vorausgesetzt, dass die toString-Methode von Cell den einfachen Klasen-Namen liefert
                this.cell + ".instance , " +
                this.rowIndex + " , " +
                this.columnIndex +
                " )";
    }

    /**
     * @see ParametricalPredicate#isCompatibleTo
     */
    @Override
    public boolean isCompatibleTo(
            final PrimitivPredicate<TicTacToe> otherPredicateToCheck )
    {
        return this.equals(
                otherPredicateToCheck );
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo(
            final Predicate<TicTacToe> otherPredicateToCheck )
    {
        return
                otherPredicateToCheck instanceof C &&
                ( (C) otherPredicateToCheck ).rowIndex == this.rowIndex &&
                ( (C) otherPredicateToCheck ).columnIndex == this.columnIndex &&
                false == ( (C) otherPredicateToCheck ).cell.equals( this.cell );
    }

    /**
     * @see TransformablePredicate#transform
     */
    @Override
    public Predicate<TicTacToe> transform(
            final Predicate<TicTacToe> srcConstraintPredicate ,
            final TransformDestinationConstraintSafeOperation<TicTacToe> safeOperation )
    {
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//        if ( safeOperation instanceof SetCell )
//        {
//            final SetCell setCell = (SetCell) safeOperation;
//
//            if ( setCell.rowIndex == this.rowIndex &&
//                    setCell.columnIndex == this.columnIndex )
//            {
//                // Prädkat betrifft die Position der sicheren Set-Operation und wird geändert
//                return C.instanceOf(
//                        setCell.nextGamer.cell() ,
//                        setCell.rowIndex ,
//                        setCell.columnIndex );
//            }
//
//            // Prädkat betrifft nicht die Position der sicheren Set-Operation und wird beibehalten
//            return this;
//        }
//
//        if ( safeOperation instanceof TakeMoveBack )
//        {
//            // das Spielfeld belibt wie es war, der Spieler verzichtet auf sein Setzen
//            return this;
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }
}