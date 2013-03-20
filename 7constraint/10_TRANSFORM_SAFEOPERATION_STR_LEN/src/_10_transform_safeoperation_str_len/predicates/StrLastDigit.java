package _10_transform_safeoperation_str_len.predicates;

import java.util.HashSet;

import _10_transform_safeoperation_str_len.StringConstraintUtil;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToEndSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToPosSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromStartToPosSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Prädikat String mit Ziffer an letzter Position.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrLastDigit
extends AbstractStrLastCharacter
implements StrLastCharacter, TransformablePredicate<String>
{
    /**
     * Konstruktor.
     */
    public StrLastDigit()
    {
        super();
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
                str.length() > 0 &&
                Character.isDigit(
                        str.charAt( str.length() - 1 ) );
    }

    /**
     * @see StrCharacter#getEnabledCharacters
     */
    @Override
    public ReadOnlySet<Character> getEnabledCharacters()
    {
        final HashSet<Character> result = new HashSet<Character>();

        for ( int charCode = 0 ; charCode < 256 ; charCode++ )
        {
            final char runChar = (char) charCode;

            if ( Character.isDigit( runChar ) )
            {
                result.add( runChar );
            }
        }
        return new ReadOnlySetAdaptor<Character>( result );
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
//            //final SubstringFromPosToPosSafeOperation substringFromPosToPosSafeOperation = (SubstringFromPosToPosSafeOperation) safeOperation;
//            //if ( substringFromPosToPosSafeOperation.toPos >= ??? )
//            //{
//            //    // bei Substring ab der Position 0 bleibt dieses Prädikat in der Ergebnis-Constraint-Expression enthalten
//            //    return this;
//            //}
//
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
//        }
//        
//        if ( safeOperation instanceof SubstringFromPosToEndSafeOperation )
//        {
//            final Integer preStrMinLenIntgrObj =
//                    StringConstraintUtil.computeStrMinLen(
//                            srcConstraintPredicate );
//            
//            final SubstringFromPosToEndSafeOperation substringFromPosToEndSafeOperation = (SubstringFromPosToEndSafeOperation) safeOperation;
//
//            if ( preStrMinLenIntgrObj != null &&
//                    substringFromPosToEndSafeOperation.fromPos <
//                    ( preStrMinLenIntgrObj.intValue() - 1 ) )
//                // bei SubstringFromPosToEndSafeOperation ab Pos 2 und StrMinLen > 2 soll das StrLastDigit-Constraint im Ergebnis-Constraint enthalten sein
//            {
//                return this;
//            }
//
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
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
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
//        }
//
//        if ( safeOperation instanceof AddStrMinLenSafeOperation )
//        {
//            // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//            return null;
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }

}