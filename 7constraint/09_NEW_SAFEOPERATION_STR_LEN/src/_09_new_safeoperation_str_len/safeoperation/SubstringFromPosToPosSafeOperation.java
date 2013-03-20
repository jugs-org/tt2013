package _09_new_safeoperation_str_len.safeoperation;

import _09_new_safeoperation_str_len.predicates.StrExactLen;
import _09_new_safeoperation_str_len.predicates.StrMinLen;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.DestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.NewDestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Predicate;

/**
 * Definitions-Klasse für sichere Substring-Operation
 * {@link String#substring(int, int)}
 * von der ersten spezifizierten Position bis zur
 * zweiten spezifizierten Position (rechts aussen)
 * des Strings.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SubstringFromPosToPosSafeOperation
extends NewDestinationConstraintSafeOperation<String>
{
    /**
     * Beginn-Position des auszuschneidenden Sub-Strings.
     */
    public final int fromPos;
    
    /**
     * Ende-Danach-Position des auszuschneidenden Sub-Strings.
     */
    public final int toPos;

    /**
     * Konstruktor.
     * 
     * @param fromPos Beginn-Position des auszuschneidenden Sub-Strings
     * @param toPos Ende-Danach-Position des auszuschneidenden Sub-Strings
     */
    public SubstringFromPosToPosSafeOperation( 
            final int fromPos , 
            final int toPos )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );

        if ( fromPos < 0 )
        {
            throw new IllegalArgumentException(
                    "fromPos is lesser 0: " +
                    fromPos );
        }

        if ( toPos <= fromPos )
        {
            throw new IllegalArgumentException(
                    "toPos is not greater fromPos: " +
                    fromPos + " " +
                    toPos );
        }

        this.fromPos = fromPos;
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
                "\tpublic " + destinationConstraintClassName + " substringFrom" + 
                ( this.fromPos == 0
                    ? "Zero"
                    : String.valueOf( this.fromPos ) ) + 
                "To" + this.toPos + "()\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "( " + contextObjName+ ".substring( " + this.fromPos + " , " + this.toPos + " ) );\n" +
                "\t}\n\n";
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                this.getClass().getSimpleName() + 
                "[" +
                "fromPos=" + this.fromPos + ", " +
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

    /**
     * @see NewDestinationConstraintSafeOperation#getDestinationConstraintExpression
     */
    @Override
    public Predicate<String> getDestinationConstraintExpression(
            final int lineNr ,
            final Predicate<String> srcConstraintPredicate ,
            final Exclude<String> exclude )
    {
        return 
                new StrExactLen( 
                        lineNr , 
                        this.toPos - this.fromPos );
    }

}