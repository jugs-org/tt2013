package _08_value_safeoperation_str_len.safeoperation;

import _08_value_safeoperation_str_len.predicates.StrIsNotNull;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.ValueSafeOperation;

/**
 * Beispiel {@link ValueSafeOperation} für die
 * Ermittlung der Länge eines Strings.
 * 
 * @author Heiner K&uuml;cker
 */
public final class StrLenSafeOperation
extends ValueSafeOperation<String>
{
    /**
     * Konstruktor.
     */
    public StrLenSafeOperation()
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );
    }

    /**
     * String darf nicht <code>null</code> sein.
     */
    @Override
    public Predicate<String> getPremise(
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return new StrIsNotNull();
    }

    /**
     * @see ValueSafeOperation#toJavaCode
     */
    @Override
    public String toJavaCode(
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
                "\t * Sichere Ermittlung String-Länge.\n" +
                "\t *\n" +
                "\t * @return String-Länge als int-Wert\n" +
                "\t */\n" +
                "\tpublic int length()\n" +
                "\t{\n" +
                "\t\treturn this." + contextObjName + ".length();\n" +
                "\t}\n\n";
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

}