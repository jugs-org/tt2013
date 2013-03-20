package _08_value_safeoperation_str_len.safeoperation;

import _08_value_safeoperation_str_len.predicates.StrHasDigits;
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
public final class StrDigitCountSafeOperation
extends ValueSafeOperation<String>
{
    /**
     * Konstruktor.
     */
    public StrDigitCountSafeOperation()
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );
    }

    /**
     * String muss Ziffern enthalten.
     */
    @Override
    public Predicate<String> getPremise(
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return new StrHasDigits();
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

        //return
        //        "\t/**\n" +
        //        "\t * Sichere Ermittlung Anzahl Ziffern im String.\n" +
        //        "\t *\n" +
        //        "\t * @return Anzahl Ziffern im String als int-Wert\n" +
        //        "\t */\n" +
        //        "\tpublic int countOfDigits()\n" +
        //        "\t{\n" +
        //        "\t\tint digitCount = 0;\n" +
        //        "\t\tfor ( int pos = 0 ; pos < " + contextObjName + ".length() ; pos++ )\n" +
        //        "\t\t{\n" +
        //        "\t\t\tif ( Character.isDigit( " + contextObjName + ".charAt( pos ) ) )\n" +
        //        "\t\t\t{\n" +
        //        "\t\t\t\tdigitCount++;\n" +
        //        "\t\t\t}\n" +
        //        "\t\t}\n" +
        //        "\t\treturn digitCount;\n" +
        //        "\t}\n\n";
        return
                "\t/**\n" +
                "\t * Sichere Ermittlung Anzahl Ziffern im String.\n" +
                "\t *\n" +
                "\t * @return Anzahl Ziffern im String als int-Wert\n" +
                "\t */\n" +
                "\tpublic int countOfDigits()\n" +
                "\t{\n" +
                "\t\treturn " + StrHasDigits.class.getName() + ".countOfDigits( this." + contextObjName + " );\n" +
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