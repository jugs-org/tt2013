package _09_new_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Prädikat genaue Länge String.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrExactLen
extends PrimitivPredicate<String>
implements ParametricalPredicate<String>
{
    /**
     * genaue Länge des Strings.
     */
    public final int exactLen;

    /**
     * Konstruktor.
     * 
     * @param exactLen genaue Länge des Strings
     */
    public StrExactLen(
            final int exactLen )
    {
        super( 
                //lineNr
                0 ,
                //includePredicateArr
                new StrIsNotNull() ,
                new StrMaxLen( exactLen ) ,
                new StrMinLen( exactLen )
                //safeMemberSet
                //null
                );

        this.exactLen = exactLen;
    }

    /**
     * Konstruktor.
     * 
     * @param exactLen genaue Länge des Strings
     */
    public StrExactLen(
            final int lineNr ,
            final int exactLen )
    {
        super( 
                lineNr ,
                //includePredicateArr
                new StrIsNotNull() ,
                new StrMaxLen( exactLen ) ,
                new StrMinLen( exactLen )
                //safeMemberSet
                //null
                );

        this.exactLen = exactLen;
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
                str.length() == this.exactLen;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
            this.getClass().getSimpleName() + 
            this.exactLen;
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
        return this.getClass().hashCode() + this.exactLen;
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

        return this.exactLen == ( (StrExactLen) obj ).exactLen;
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
                this.exactLen +
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
                ( otherPredicateToCheck instanceof StrExactLen ) &&
                this.exactLen == ( (StrExactLen) otherPredicateToCheck ).exactLen;
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo( 
            final Predicate<String> otherPredicateToCheck )
    {
        return 
                ( otherPredicateToCheck instanceof StrExactLen ) &&
                this.exactLen != ( (StrExactLen) otherPredicateToCheck ).exactLen;
    }

}