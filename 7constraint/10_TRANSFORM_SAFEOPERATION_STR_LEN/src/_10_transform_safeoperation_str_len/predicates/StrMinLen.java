package _10_transform_safeoperation_str_len.predicates;


import _10_transform_safeoperation_str_len.StringConstraintUtil;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToEndSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToPosSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromStartToPosSafeOperation;
import de.heinerkuecker.constraint.ParametricalPredicate;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;

/**
 * Prädikat minimale Länge String.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrMinLen
extends PrimitivPredicate<String>
implements ParametricalPredicate<String>, TransformablePredicate<String>
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
                new StrIsNotNull() );

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
        //final Integer otherStrMinLenIntgrObj = 
        //        StringConstraintUtil.computeStrMinLen(
        //                otherPredicateToCheck );
        //
        //if ( otherStrMinLenIntgrObj == null )
        //    // in otherPredicateToCheck war keine minimale Länge festgelegt
        //{
        //    return false;
        //}
        //
        //return this.minLen >= otherStrMinLenIntgrObj;
    }

    /**
     * @see ParametricalPredicate#isExcludeTo
     */
    @Override
    public boolean isExcludeTo( 
            final Predicate<String> otherPredicateToCheck )
    {
        //if ( otherPredicateToCheck instanceof StrMaxLen &&
        //        this.minLen > ( (StrMaxLen) otherPredicateToCheck ).maxLen )
        //{
        //    return true;
        //}
        //return false;
        final Integer otherStrMaxLenIntgrObj =
                StringConstraintUtil.computeStrMaxLen(
                        otherPredicateToCheck );

        if ( otherStrMaxLenIntgrObj == null )
            // in otherPredicateToCheck war keine maximale Länge festgelegt
        {
            return false;
        }

        return
                this.minLen > otherStrMaxLenIntgrObj.intValue();
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
//        if ( safeOperation instanceof SubstringFromPosToPosSafeOperation )
//        {
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
//        }
//
//        if ( safeOperation instanceof SubstringFromPosToEndSafeOperation )
//        {
//            final SubstringFromPosToEndSafeOperation substringFromPosToEndSafeOperation = (SubstringFromPosToEndSafeOperation) safeOperation;
//
//            return
//                    new StrMinLen(
//                            this.minLen -
//                            substringFromPosToEndSafeOperation.fromPos );
//        }
//        
//        if ( safeOperation instanceof SubstringFromStartToPosSafeOperation )
//        {
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
//        }
//        
//        if ( safeOperation instanceof AddStrMaxLenSafeOperation )
//        {
//            // dieses Prädikat wird unverändert übernommen
//            return this;
//        }
//
//        if ( safeOperation instanceof AddStrMinLenSafeOperation )
//        {
//            final AddStrMinLenSafeOperation addStrMinLenSafeOperation = (AddStrMinLenSafeOperation) safeOperation;
//            
//            // neue minimale Länge = vorherige minimale Länge + hinzukommende minimale Länge
//            return
//                    new StrMinLen( 
//                            this.minLen +
//                            addStrMinLenSafeOperation.strToAddMinLen );
//        }
//
//        if ( safeOperation instanceof AddStrMinMaxLenSafeOperation )
//        {
//            final AddStrMinMaxLenSafeOperation addStrMinMaxLenSafeOperation = (AddStrMinMaxLenSafeOperation) safeOperation;
//            
//            // neue minimale Länge = vorherige minimale Länge + hinzukommende minimale Länge
//            return
//                    new StrMinLen( 
//                            this.minLen +
//                            addStrMinMaxLenSafeOperation.strToAddMinLen );
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }

}