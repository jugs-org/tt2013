package _14_transform_safeoperation_tic_tac_toe.predicates;

import _14_transform_safeoperation_tic_tac_toe.Gamer;
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
 * Prädikat Tic Tac Toe nächster Spieler.
 *
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class G
extends PrimitivPredicate<TicTacToe>
implements ParametricalPredicate<TicTacToe>, TransformablePredicate<TicTacToe>
{
    /**
     * Nächster Spieler (der als nächstes dran ist).
     */
    public final Gamer nextGamer;

    /**
     * Konstruktor.
     *
     * @param cell
     * @param rowIndex
     * @param columnIndex
     */
    public G(
            final Gamer nextGamer )
    {
        super(
                //lineNr
                0 );

        this.nextGamer = nextGamer;
    }


    /**
     * @see Predicate#test
     */
    @Override
    public boolean test(
            final TicTacToe game )
    {
        return
                game.nextGamer.equals(
                                this.nextGamer );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return
            this.getClass().getSimpleName() +
            this.nextGamer;
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
     * @see ParametricalPredicate#toJavaInstanceOfStr
     */
    @Override
    public String toJavaInstanceOfStr()
    {
        return
                "new " +
                this.getClass().getSimpleName() +
                "( " +
                Gamer.class.getName() + "." +
                // hier wird vorausgesetzt, dass die toString-Methode von Gamer den einfachen Klasen-Namen liefert
                this.nextGamer + ".instance )";
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
                otherPredicateToCheck instanceof G &&
                false == ( (G) otherPredicateToCheck ).nextGamer.equals( this.nextGamer );
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
//            return new G( this.nextGamer.next() );
//        }
//
//        if ( safeOperation instanceof TakeMoveBack )
//        {
//            return new G( this.nextGamer.next() );
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( this.nextGamer == null ) ? 0 : this.nextGamer.hashCode() );
        return result;
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
        G other = (G) obj;
        if ( this.nextGamer == null )
        {
            if ( other.nextGamer != null )
            {
                return false;
            }
        }
        else if ( !this.nextGamer.equals( other.nextGamer ) )
        {
            return false;
        }
        return true;
    }

}