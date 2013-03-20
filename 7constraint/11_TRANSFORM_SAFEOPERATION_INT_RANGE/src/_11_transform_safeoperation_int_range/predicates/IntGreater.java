package _11_transform_safeoperation_int_range.predicates;

import _11_transform_safeoperation_int_range.safeoperation.AddIntSafeOperation;
import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;

/**
 * Prädikat Integer grösser als.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class IntGreater
extends PrimitivPredicate<Integer>
implements ParametricalPredicate<Integer>, TransformablePredicate<Integer>
{
    /**
     * Wert Integer grösser als.
     */
    public final int intGtValue;

    /**
     * Konstruktor.
     * 
     * @param intGtValue Grösser-Als-Wert
     */
    public IntGreater(
            final int intGtValue )
    {
        super( 
                //lineNr
                0 );

        this.intGtValue = intGtValue;
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final Integer intObj )
    {
        return
                intObj != null &&
                intObj.intValue() > this.intGtValue;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
            this.getClass().getSimpleName() + 
            ( this.intGtValue < 0
                    ? "Minus"
                    : "" ) + 
            ( this.intGtValue == 0
                    ? "Zero"
                    : String.valueOf( Math.abs( this.intGtValue ) ) );
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
     * @see Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return
                this.getClass().hashCode() + 
                this.intGtValue;
    }

    /**
     * @see Object#equals
     */
    @Override
    public boolean equals( 
            final Object obj )
    {
        if ( false == super.equals( obj ) )
        {
            return false;
        }

        return this.intGtValue == ( (IntGreater) obj ).intGtValue;
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
                this.intGtValue +
                " )";
    }

    /**
     * @see ParametricalPredicate#isCompatibleTo
     */
    @Override
    public boolean isCompatibleTo( 
            final PrimitivPredicate<Integer> otherPredicateToCheck )
    {
        return
                ( otherPredicateToCheck instanceof IntGreater ) &&
                this.intGtValue >= ( (IntGreater) otherPredicateToCheck ).intGtValue;
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo( 
            final Predicate<Integer> otherPredicateToCheck )
    {
        if ( otherPredicateToCheck instanceof IntLesser )
        {
            return ( (IntLesser) otherPredicateToCheck ).intLtValue <= this.intGtValue;
        }
        
        if ( otherPredicateToCheck instanceof IntEqual )
        {
            return ( (IntEqual) otherPredicateToCheck ).intEqValue <= this.intGtValue;
        }
        
        return false;
    }

    /**
     * @see TransformablePredicate#transform
     */
    @Override
    public Predicate<Integer> transform(
            final Predicate<Integer> srcConstraintPredicate ,
            final TransformDestinationConstraintSafeOperation<Integer> safeOperation )
    {
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//        if ( safeOperation instanceof AddIntSafeOperation )
//        {
//            final AddIntSafeOperation addIntSafeOperation = (AddIntSafeOperation) safeOperation;
//            
//            // neuer grösser-als-Wert ist alter grösser-als-Wert plus zu addierender konstanter int-Wert
//            return
//                    new IntGreater(
//                            this.intGtValue +
//                            addIntSafeOperation.intToAddValue );
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }

}