package _10_transform_safeoperation_str_len.safeoperation;

import java.util.Arrays;
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
public final class SubstringFromStartToPosSafeOperation
extends TransformDestinationConstraintSafeOperation<String>
{
    /**
     * Ende-Danach-Position des auszuschneidenden Sub-Strings.
     */
    public final int toPos;

    /**
     * Konstruktor.
     * 
     * @param toPos Ende-Danach-Position des auszuschneidenden Sub-Strings
     */
    public SubstringFromStartToPosSafeOperation( 
            final int toPos )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );

        if ( toPos < 0 )
        {
            throw new IllegalArgumentException(
                    "toPos is lesser 0: " +
                            toPos );
        }

        this.toPos = toPos;
    }

    /**
     * @see DestinationConstraintSafeOperation#getPremise
     */
    @Override
    public Predicate<String> getPremise(
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return
                new StrMinLen( this.toPos );        
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
                "\tpublic " + destinationConstraintClassName + " substringFromStartTo" + this.toPos + "()\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "( " + contextObjName+ ".substring( 0 , " + this.toPos + " ) );\n" +
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
                "toPos=" + this.toPos + 
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
    //    return
    //            new StrMinLen( this.toPos );
    //}

    ///**
    // * @see TransformDestinationConstraintSafeOperation#transformPredicate
    // */
    ////@Override TODO Methode entfernen
    //public Predicate<String> transformPredicate( 
    //        final Predicate<String> srcConstraintPredicate ,
    //        final PrimitivPredicate<String> predicateToTransform )
    //{
    //    if ( ( this.toPos > 0 ) && 
    //            ( predicateToTransform instanceof StrFirstCharacter ) )
    //        // bei Substring ab Pos 0 soll das StrFirstUppercaseLetter-Constraint im Ergebnis-Constraint enthalten sein
    //    {
    //        return predicateToTransform;
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
        return
                Arrays.asList(
                        new StrMinLen( this.toPos ) );
    }

}