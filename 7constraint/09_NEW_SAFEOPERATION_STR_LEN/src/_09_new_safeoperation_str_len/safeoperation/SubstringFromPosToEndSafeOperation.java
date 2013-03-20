package _09_new_safeoperation_str_len.safeoperation;

import _09_new_safeoperation_str_len.StringConstraintUtil;
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
 * {@link String#substring(int)}
 * von der spezifizierten Position bis zum Ende
 * des Strings.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SubstringFromPosToEndSafeOperation
extends NewDestinationConstraintSafeOperation<String>
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
                this.getClass().getSimpleName() + 
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

    /**
     * @see NewDestinationConstraintSafeOperation#getDestinationConstraintExpression
     */
    @Override
    public Predicate<String> getDestinationConstraintExpression(
            final int lineNr ,
            final Predicate<String> srcConstraintPredicate ,
            final Exclude<String> exclude )
    {
        // Ermittlung der vorherigen Mindest-String-Länge
        // die Ausnahme bestätigt die Regel, hier wird auf das ursprüngliche Constraint zurückgegriffen
        // bei der TransformDestinationConstraintSafeOperation sehen wir dafür eine bessere Lösung
        final Integer preStrMinLenIntgrObj =
                StringConstraintUtil.computeStrMinLen(
                        srcConstraintPredicate );
    
        return
                new StrMinLen( preStrMinLenIntgrObj - this.fromPos );
    }

}