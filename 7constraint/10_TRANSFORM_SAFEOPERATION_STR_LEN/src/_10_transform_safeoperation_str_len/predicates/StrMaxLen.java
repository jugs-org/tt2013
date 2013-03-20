package _10_transform_safeoperation_str_len.predicates;

import _10_transform_safeoperation_str_len.StringConstraintUtil;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinMaxLenSafeOperation;
import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;

/**
 * Prädikat maximale Länge String.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrMaxLen
extends PrimitivPredicate<String>
implements ParametricalPredicate<String>, TransformablePredicate<String>
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
                new StrIsNotNull() );

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
     * @see ParametricalPredicate#toJavaInstanceOfStr
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
        //if ( otherPredicateToCheck instanceof StrMinLen &&
        //        this.maxLen < ( (StrMinLen) otherPredicateToCheck ).minLen )
        //{
        //    return true;
        //}
        //return false;
        final Integer otherStrMinLenIntgrObj =
                StringConstraintUtil.computeStrMinLen(
                        otherPredicateToCheck );

        if ( otherStrMinLenIntgrObj == null )
            // in otherPredicateToCheck war keine minimale Länge festgelegt
        {
            return false;
        }

        return this.maxLen < otherStrMinLenIntgrObj.intValue();
    }

    /**
     * @see TransformablePredicate#transform
     */
    @Override
    public Predicate<String> transform(
            final Predicate<String> srcConstraintPredicate ,
            final TransformDestinationConstraintSafeOperation<String> safeOperation )
    {
// TODO Übungsaufgabe Stuttgarter Testtage 2013        
//        if ( safeOperation instanceof AddStrMaxLenSafeOperation )
//        {
//            final AddStrMaxLenSafeOperation addStrMaxLenSafeOperation = (AddStrMaxLenSafeOperation) safeOperation;
//
//            // neue maximale Länge = vorherige maximale Länge + hinzukommende maximale Länge
//            return
//                    new StrMaxLen( 
//                            this.maxLen +
//                            addStrMaxLenSafeOperation.strToAddMaxLen );
//        }
//
//        if ( safeOperation instanceof AddStrMinLenSafeOperation )
//        {
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
//        }
//
//        if ( safeOperation instanceof AddStrMinMaxLenSafeOperation )
//        {
//            final AddStrMinMaxLenSafeOperation addStrMinMaxLenSafeOperation = (AddStrMinMaxLenSafeOperation) safeOperation;
//            
//            // neue maximale Länge = vorherige maximale Länge + hinzukommende maximale Länge
//            return
//                    new StrMaxLen( 
//                            this.maxLen +
//                            addStrMinMaxLenSafeOperation.strToAddMaxLen );
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }
}