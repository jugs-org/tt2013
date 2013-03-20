package _07_int_range.predicates;

import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Prädikat Integer gleich.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class IntEqual
extends PrimitivPredicate<Integer>
implements ParametricalPredicate<Integer>
{
    /**
     * Wert Integer gleich.
     */
    public final int intEqValue;

    /**
     * Konstruktor.
     */
    public IntEqual(
            final int intEqValue )
    {
        super( 
                //lineNr
                0 );

        this.intEqValue = intEqValue;
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
                intObj.intValue() == this.intEqValue;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
            this.getClass().getSimpleName() + 
            ( this.intEqValue < 0
                    ? "Minus"
                    : "" ) + 
            ( this.intEqValue == 0
                    ? "Zero"
                    : Math.abs( this.intEqValue ) );
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
                this.intEqValue;
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

        return this.intEqValue == ( (IntEqual) obj ).intEqValue;
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
                this.intEqValue +
                " )";
    }

    /**
     * @see ParametricalPredicate#isCompatibleTo
     */
    @Override
    public boolean isCompatibleTo( 
            final PrimitivPredicate<Integer> otherPredicateToCheck )
    {
        if ( otherPredicateToCheck instanceof IntGreater )
        {
            return
                    this.intEqValue > ( (IntGreater) otherPredicateToCheck ).intGtValue;
        }
        
        if ( otherPredicateToCheck instanceof IntLesser )
        {
            return
                    this.intEqValue < ( (IntLesser) otherPredicateToCheck ).intLtValue;
        }
        
        return
                ( otherPredicateToCheck instanceof IntEqual ) &&
                ( (IntEqual) otherPredicateToCheck ).intEqValue == this.intEqValue;
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
            return ( (IntGreater) otherPredicateToCheck ).intGtValue >= this.intEqValue;
        }
        
        if ( otherPredicateToCheck instanceof IntLesser )
        {
            return ( (IntLesser) otherPredicateToCheck ).intLtValue <= this.intEqValue;
        }
        
        if ( otherPredicateToCheck instanceof IntEqual )
        {
            return ( (IntEqual) otherPredicateToCheck ).intEqValue != this.intEqValue;
        }
        
        return false;
    }

}