package _14_transform_safeoperation_tic_tac_toe.safeoperation;

import java.util.List;

import _14_transform_safeoperation_tic_tac_toe.Cell;
import _14_transform_safeoperation_tic_tac_toe.Gamer;
import _14_transform_safeoperation_tic_tac_toe.TicTacToe;
import _14_transform_safeoperation_tic_tac_toe.predicates.C;
import _14_transform_safeoperation_tic_tac_toe.predicates.G;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.And;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.DestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;

/**
 * Beispiel {@link TransformDestinationConstraintSafeOperation} f�r
 * die sichere Set-Operation im {@link TicTacToe}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SetCell
extends TransformDestinationConstraintSafeOperation<TicTacToe>
{
    /**
     * N�chster Spieler (der als n�chstes dran ist).
     */
    public final Gamer nextGamer;

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
     * @param nextGamer
     * @param rowIndex
     * @param columnIndex
     */
    public SetCell(
            final Gamer nextGamer ,
            final int rowIndex ,
            final int columnIndex )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );
        this.nextGamer = nextGamer;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * @see DestinationConstraintSafeOperation#getPremise
     */
    @Override
    public Predicate<TicTacToe> getPremise(
            final ConstraintCodeGeneratorCache<TicTacToe> cache )
    {
        return
                And.instanceOf(
                        this.lineNr ,
                        cache ,
                        // Zelle muss noch frei sein
                        C.instanceOf(
                                Cell.Empty.instance ,
                                this.rowIndex ,
                                this.columnIndex ) ,
                        // der aktuelle Spieler muss dran sein 
                        new G( this.nextGamer )
                        // das Spiel darf noch nicht entschieden sein, kein Gewinner
                        // TODO das klappt nicht, weil dieses Pr�dikat keine vom Solver erkennbare Verbindung zu anderen Pr�dikaten hat: new NoWinner()
                        );
    }

    /**
     * @see DestinationConstraintSafeOperation#toJavaCode
     */
    @Override
    public String toJavaCode( 
            final String destinationConstraintClassName ,
            final String otherConstraintClassName ,
            final String contextObjName )
    {
        return
                "\t/**\n" +
                "\t * Sichere TicTacToe-Set-Operation.\n" +
                "\t *\n" +
                "\t * @return constraint-gesichertes TicTacToe\n" +
                "\t */\n" +
                "\tpublic " + destinationConstraintClassName + " set" + this.nextGamer + this.rowIndex + this.columnIndex + "()\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "(\n" +
                "\t\t\t\tthis." + contextObjName+ ".set(\n" +
                "\t\t\t\t\t\t" + Gamer.class.getName() + "." + this.nextGamer + ".instance ,\n" +
                "\t\t\t\t\t\t" + this.rowIndex + " ,\n" +
                "\t\t\t\t\t\t" + this.columnIndex + " ) );\n" +
                "\t}\n\n";
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                this.getClass().getSimpleName() +
                "[" +
                "nextGamer=" + this.nextGamer + ", " +
                "rowIndex=" + this.rowIndex + ", " +
                "columnIndex=" + this.columnIndex + 
                "]";
    }

    /**
     * @see TransformDestinationConstraintSafeOperation#getPredicatesToAdd
     */
    @Override
    public List<? extends Predicate<TicTacToe>> getPredicatesToAdd(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<TicTacToe> cache )
    {
        return null;
    }

    /**
     * @see AbstractSafeOperation#getOtherParameterPredicate
     */
    @Override
    public Predicate<TicTacToe> getOtherParameterPredicate( 
            final int lineNr ,
            final ConstraintCodeGeneratorCache<TicTacToe> cache )
    {
        return null;
    }

}