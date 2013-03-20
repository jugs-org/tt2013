package _14_transform_safeoperation_tic_tac_toe.safeoperation;

import java.util.List;

import _14_transform_safeoperation_tic_tac_toe.Gamer;
import _14_transform_safeoperation_tic_tac_toe.TicTacToe;
import _14_transform_safeoperation_tic_tac_toe.predicates.G;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.And;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.DestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;

/**
 * Beispiel {@link TransformDestinationConstraintSafeOperation} für
 * die sichere Zurücknehmen-Operation im {@link TicTacToe}.
 * 
 * Scheinbar heisst TakeMoveBack im Beispiel
 * <a href="http://blog.vasilrem.com/tic-tac-toe-api-with-phantom-types">Tic-Tac-Toe API with phantom types</a>.
 * einfach nur, dass der Spieler auf seinen Zug verzichtet,
 * sehr seltsam.
 * 
 * @author Heiner K&uuml;cker
 */
public final class TakeMoveBack
extends TransformDestinationConstraintSafeOperation<TicTacToe>
{
    /**
     * Nächster Spieler (der als nächstes dran ist).
     */
    public final Gamer nextGamer;

    /**
     * Konstruktor.
     * 
     * @param nextGamer
     */
    public TakeMoveBack(
            final Gamer nextGamer )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );
        this.nextGamer = nextGamer;
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
                        // der aktuelle Spieler muss dran sein 
                        new G( this.nextGamer )
                        // das Spiel darf noch nicht entschieden sein, kein Gewinner
                        // TODO das klappt nicht, weil dieses Prädikat keine vom Solver erkennbare Verbindung zu anderen Prädikaten hat: new NoWinner()
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
                "\t * Sichere TicTacToe-Zurücknehmen-Operation.\n" +
                "\t *\n" +
                "\t * @return constraint-gesichertes TicTacToe\n" +
                "\t */\n" +
                "\tpublic " + destinationConstraintClassName + " takeMoveBack" + this.nextGamer + "()\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "(\n" +
                "\t\t\t\tthis." + contextObjName+ ".takeMoveBack(\n" +
                "\t\t\t\t\t\t" + Gamer.class.getName() + "." + this.nextGamer + ".instance ) );\n" +
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
                "nextGamer=" + this.nextGamer +
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