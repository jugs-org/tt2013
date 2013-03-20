package _10_transform_safeoperation_str_len.predicates;

import java.util.HashSet;

import _10_transform_safeoperation_str_len.safeoperation.AddStrMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToEndSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToPosSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromStartToPosSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.TransformMethodNotImplementedForSafeOperationException;
import de.heinerkuecker.constraint.TransformablePredicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Prädikat String mit Großbuchstaben an erster Position.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class StrFirstUppercaseLetter
extends PrimitivPredicate<String>
implements StrFirstCharacter, TransformablePredicate<String>
{
    /**
     * Konstruktor.
     */
    public StrFirstUppercaseLetter()
    {
        super( 
                //lineNr
                0 , 
                //includePredicateArr
                new StrIsNotNull() ,
                new StrMinLen( 1 ) );
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
                Character.isUpperCase( 
                        str.charAt( 0 ) );
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

            if ( Character.isUpperCase( runChar ) )
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
//            final SubstringFromPosToPosSafeOperation substringFromPosToPosSafeOperation = (SubstringFromPosToPosSafeOperation) safeOperation;
//
//            if ( substringFromPosToPosSafeOperation.fromPos > 0 )
//            {
//                // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//                return null;
//            }
//            else
//            {
//                // bei Substring ab der Position 0 bleibt dieses Prädikat in der Ergebnis-Constraint-Expression enthalten
//                return this;
//            }
//        }
//        
//        if ( safeOperation instanceof SubstringFromPosToEndSafeOperation )
//        {
//            final SubstringFromPosToEndSafeOperation substringFromPosToEndSafeOperation = (SubstringFromPosToEndSafeOperation) safeOperation;
//
//            if ( substringFromPosToEndSafeOperation.fromPos > 0 )
//            {
//                // dieses Prädikat soll in der Ergebnis-Constraint-Expression nicht mehr auftauchen
//                return null;
//            }
//            else
//            {
//                // bei Substring ab der Position 0 bleibt dieses Prädikat in der Ergebnis-Constraint-Expression enthalten
//                return this;
//            }
//        }
//        
//        if ( safeOperation instanceof SubstringFromStartToPosSafeOperation )
//        {
//            final SubstringFromStartToPosSafeOperation substringFromStartToPosSafeOperation = (SubstringFromStartToPosSafeOperation) safeOperation;
//            
//            if ( substringFromStartToPosSafeOperation.toPos > 0 )
//            {
//                // bei Substring ab Pos 0 und bis zu einer Position grösser 0 soll das StrFirstUppercaseLetter-Constraint im Ergebnis-Constraint enthalten sein
//                return this;
//            }
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
//            // dieses Prädikat wird unverändert übernommen
//            return this;
//        }
//
//        throw new TransformMethodNotImplementedForSafeOperationException(
//                this.lineNr ,
//                this ,
//                safeOperation );
    }

}