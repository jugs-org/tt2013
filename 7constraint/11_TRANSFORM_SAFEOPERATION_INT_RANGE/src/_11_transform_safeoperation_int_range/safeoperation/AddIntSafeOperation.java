package _11_transform_safeoperation_int_range.safeoperation;

import java.util.List;

import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.DestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;

/**
 * Beispiel {@link TransformDestinationConstraintSafeOperation} für
 * die sichere Add-Int-Operation.
 * 
 * @author Heiner K&uuml;cker
 */
public final class AddIntSafeOperation
extends TransformDestinationConstraintSafeOperation<Integer>
implements TransformablePredicate<Integer>
{
    /**
     * Zu addierender int-Wert.
     */
    public final int intToAddValue;

    /**
     * Konstruktor.
     * 
     * @param intToAddValue zu addierender konstanter int-Wert
     */
    public AddIntSafeOperation( 
            final int intToAddValue )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );

        this.intToAddValue = intToAddValue;
    }

    /**
     * @see DestinationConstraintSafeOperation#getPremise
     */
    @Override
    public Predicate<Integer> getPremise(
            final ConstraintCodeGeneratorCache<Integer> cache )
    {
        return
                //TODO new IntIsNotNull()
                null;
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
                "\t * Sichere Int-Add-Operation.\n" +
                "\t *\n" +
                "\t * @return constraint-gesicherter Int-Wert\n" +
                "\t */\n" +
                "\tpublic " + destinationConstraintClassName + " add" + this.intToAddValue + "()\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "( Integer.valueOf( this." + contextObjName+ ".intValue() + " + this.intToAddValue + " ) );\n" +
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
                "intToAddValue=" + this.intToAddValue +
                "]";
    }

    /**
     * @see TransformDestinationConstraintSafeOperation#getPredicatesToAdd
     */
    @Override
    public List<? extends Predicate<Integer>> getPredicatesToAdd(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<Integer> cache )
    {
        return null;
    }

    /**
     * @see AbstractSafeOperation#getOtherParameterPredicate
     */
    @Override
    public Predicate<Integer> getOtherParameterPredicate( 
            final int lineNr ,
            final ConstraintCodeGeneratorCache<Integer> cache )
    {
        return null;
    }

    /**
     * @see TransformablePredicate#transform
     */
    @Override
    public Predicate<Integer> transform(
            final Predicate<Integer> srcConstraintPredicate ,
            final TransformDestinationConstraintSafeOperation<Integer> safeOperation )
    {

        throw new TransformMethodNotImplementedForSafeOperationException(
                this.lineNr ,
                this ,
                safeOperation );
    }

}