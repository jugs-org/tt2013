package _10_transform_safeoperation_str_len.safeoperation;

import java.util.List;

import _10_transform_safeoperation_str_len.predicates.StrMinLen;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.DestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;

/**
 * Beispiel {@link TransformDestinationConstraintSafeOperation} für
 * die sichere Substring-Operation.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SubstringFromPosToEndSafeOperation
extends TransformDestinationConstraintSafeOperation<String>
{
    /**
     * Beginn-Position des auszuschneidenden Sub-Strings.
     */
    public final int fromPos;
    
    /**
     * Konstruktor.
     * 
     * @param fromPos Beginn-Position des auszuschneidenden Sub-Strings
     */
    public SubstringFromPosToEndSafeOperation( 
            final int fromPos )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );

        if ( fromPos < 0 )
        {
            throw new IllegalArgumentException(
                    "fromPos is lesser 0: " +
                    fromPos );
        }

        this.fromPos = fromPos;
    }

    /**
     * @see DestinationConstraintSafeOperation#getPremise
     */
    @Override
    public Predicate<String> getPremise(
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return
                new StrMinLen( this.fromPos );
    }

    /**
     * @see DestinationConstraintSafeOperation#toJavaCode
     */
    @Override
    public String toJavaCode( 
            final String destinationConstraintClassName ,
            final String otherConstraintClassName ,
            final String contextObjName )
    {
        if ( otherConstraintClassName != null )
        {
            throw new IllegalArgumentException(
                    "unexpected otherConstraintClassName: " +
                    otherConstraintClassName );
        }

        return
                "\t/**\n" +
                "\t * Sichere Sub-String-Operation.\n" +
                "\t *\n" +
                "\t * @return constraintgesicherter Sub-String\n" +
                "\t */\n" +
                "\tpublic " + destinationConstraintClassName + " substringFrom" + this.fromPos + "ToEnd()\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "( " + contextObjName+ ".substring( " + this.fromPos + " ) );\n" +
                "\t}\n\n";
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                this.getClass().getSimpleName() + " " +
                "[" +
                "fromPos=" + this.fromPos + 
                "]";
    }

    /**
     * @see AbstractSafeOperation#getOtherParameterPredicate
     */
    @Override
    public Predicate<String> getOtherParameterPredicate( 
            final int lineNr ,
            final ConstraintCodeGeneratorCache<String> cache )
    {
        // kein anderes Parameter-Prädikat erforderlich
        return null;
    }

    ///**
    // * @see NewDestinationConstraintSafeOperation#getDestinationConstraintExpression
    // */
    //@Override
    //public Predicate<String> getDestinationConstraintExpression(
    //        final int lineNr ,
    //        final Predicate<String> srcConstraintPredicate ,
    //        final Exclude<String> exclude )
    //{
    //    final int preStrMinLen =
    //            computePreStrMinLen(
    //                    srcConstraintPredicate );
    //
    //    return
    //            new StrMinLen( preStrMinLen - this.fromPos );
    //}

    ///**
    // * @see TransformDestinationConstraintSafeOperation#transformPredicate
    // */
    ////@Override TODO Methode entfernen
    //public Predicate<String> transformPredicate( 
    //        final Predicate<String> srcConstraintPredicate ,
    //        final PrimitivPredicate<String> predicateToTransform )
    //{
    //    if ( predicateToTransform instanceof StrMinLen )
    //    {
    //        final StrMinLen preStrMinLen = (StrMinLen) predicateToTransform;
    //
    //        return
    //                new StrMinLen( preStrMinLen.minLen - this.fromPos );
    //    }
    //    
    //    if ( predicateToTransform instanceof StrLastCharacter )
    //    {
    //        final Integer preStrMinLenIntgrObj =
    //                StringConstraintUtil.computeStrMinLen(
    //                        srcConstraintPredicate );
    //
    //        if ( preStrMinLenIntgrObj != null &&
    //                this.fromPos < preStrMinLenIntgrObj.intValue() - 1 )
    //            // bei SubstringFromPosToEndSafeOperation ab Pos 2 und StrMinLen > 2 soll das StrLastDigit-Constraint im Ergebnis-Constraint enthalten sein
    //        {
    //            return predicateToTransform;
    //        }
    //    }
    //    
    //    return null;
    //}

    /**
     * @see TransformDestinationConstraintSafeOperation#getPredicatesToAdd
     */
    @Override
    public List<? extends Predicate<String>> getPredicatesToAdd( 
            final int lineNr ,
            final ConstraintCodeGeneratorCache<String> cache )
    {
        // es sollen keine Prädikate hinzugefügt werden
        return null;
    }

}