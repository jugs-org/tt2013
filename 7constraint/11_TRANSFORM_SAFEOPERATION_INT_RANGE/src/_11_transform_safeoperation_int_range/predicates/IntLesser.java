package _11_transform_safeoperation_int_range.predicates;

import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Pr�dikat Integer kleiner als.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class IntLesser
extends PrimitivPredicate<Integer>
implements ParametricalPredicate<Integer>
{
    /**
     * Wert Integer kleiner als.
     */
    public final int intLtValue;

    /**
     * Konstruktor.
     * 
     * @param intLtValue Kleiner-Als-Wert
     */
    public IntLesser(
            final int intLtValue )
    {
        super( 
                //lineNr
                0 );

        this.intLtValue = intLtValue;
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
                intObj.intValue() < this.intLtValue;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
            this.getClass().getSimpleName() + 
            ( this.intLtValue < 0
                    ? "Minus"
                    : "" ) + 
            ( this.intLtValue == 0
                    ? "Zero"
                    : String.valueOf( Math.abs( this.intLtValue ) ) );
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
                this.intLtValue;
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

        return this.intLtValue == ( (IntLesser) obj ).intLtValue;
    }

    /**
     * @see ParametricalPredicate#toJavaInstanceOfStr()
     */
    @Override
    public String toJavaInstanceOfStr()
    {
        return
                "new " +
                this.getClass().getSimpleName() +
                "( " +
                this.intLtValue +
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
                ( otherPredicateToCheck instanceof IntLesser ) &&
                this.intLtValue <= ( (IntLesser) otherPredicateToCheck ).intLtValue;
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo(
            final Predicate<Integer> otherPredicateToCheck )
    {
        if ( otherPredicateToCheck instanceof IntGreater )
        {
            return ( (IntGreater) otherPredicateToCheck ).intGtValue >= this.intLtValue;
        }
        
        if ( otherPredicateToCheck instanceof IntEqual )
        {
            return ( (IntEqual) otherPredicateToCheck ).intEqValue >= this.intLtValue;
        }
        
        return false;
    }

}