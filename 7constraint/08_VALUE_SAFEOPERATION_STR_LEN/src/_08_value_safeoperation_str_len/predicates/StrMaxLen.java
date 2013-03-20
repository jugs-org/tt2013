package _08_value_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Prädikat maximale Länge String.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrMaxLen
extends PrimitivPredicate<String>
implements ParametricalPredicate<String>
{
    /**
     * maximale Länge des Strings.
     */
    public final int maxLen;

    /**
     * Konstruktor.
     * 
     * @param maxLen maximale Länge des Strings
     */
    public StrMaxLen(
            final int maxLen )
    {
        super( 
                //lineNr
                0 , 
                //includePredicateArr
                new StrIsNotNull()
                //safeMemberSet
                //null
                );

        this.maxLen = maxLen;
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
                str.length() <= this.maxLen;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
            this.getClass().getSimpleName() + 
            this.maxLen;
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
        return this.getClass().hashCode() + this.maxLen;
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

        return this.maxLen == ( (StrMaxLen) obj ).maxLen;
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
                this.maxLen +
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
                ( otherPredicateToCheck instanceof StrMaxLen ) &&
                this.maxLen <= ( (StrMaxLen) otherPredicateToCheck ).maxLen;
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo( 
            final Predicate<String> otherPredicateToCheck )
    {
        if ( otherPredicateToCheck instanceof StrMinLen &&
                this.maxLen < ( (StrMinLen) otherPredicateToCheck ).minLen )
        {
            return true;
        }
        return false;
    }

}