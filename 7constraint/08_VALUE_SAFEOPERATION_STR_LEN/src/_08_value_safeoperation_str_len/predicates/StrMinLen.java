package _08_value_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Prädikat minimale Länge String.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrMinLen
extends PrimitivPredicate<String>
implements ParametricalPredicate<String>
{
    /**
     * minimale Länge des Strings.
     */
    public final int minLen;

    /**
     * Konstruktor.
     */
    public StrMinLen(
            final int minLen )
    {
        super( 
                //lineNr
                0 , 
                //includePredicateArr
                new StrIsNotNull()
                //safeMemberSet
                //null
                );

        this.minLen = minLen;
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final String str )
    {
        return
                str != null &&
                str.length() >= this.minLen;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
            this.getClass().getSimpleName() + 
            this.minLen;
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
        return this.getClass().hashCode() + this.minLen;
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

        return this.minLen == ( (StrMinLen) obj ).minLen;
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
                this.minLen +
                " )";
    }

    /**
     * @see ParametricalPredicate#isCompatibleTo
     */
    @Override
    public boolean isCompatibleTo( 
            final PrimitivPredicate<String> otherPredicateToCheck )
    {
        return
                ( otherPredicateToCheck instanceof StrMinLen ) &&
                this.minLen >= ( (StrMinLen) otherPredicateToCheck ).minLen;
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo( 
            final Predicate<String> otherPredicateToCheck )
    {
        if ( otherPredicateToCheck instanceof StrMaxLen &&
                this.minLen > ( (StrMaxLen) otherPredicateToCheck ).maxLen )
        {
            return true;
        }
        return false;
    }

}