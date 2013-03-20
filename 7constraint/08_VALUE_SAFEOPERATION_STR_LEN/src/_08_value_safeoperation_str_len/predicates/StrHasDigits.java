package _08_value_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.Predicate;

/**
 * Pr�dikat String mit mindestens einer Ziffer.
 * 
 * @author Heiner K&uuml;cker
 */
public final class StrHasDigits
extends AbstractStrLastCharacter
implements StrLastCharacter
{
    /**
     * Konstruktor.
     */
    public StrHasDigits()
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
                hasDigits(
                        str );
    }

    /**
     * Pr�fen, ob der �bergebene String mindestens eine Ziffer enth�lt.
     * 
     * @param strToCheck zu pr�fender String
     * @return ob der �bergebene String mindestens eine Ziffer enth�lt
     */
    public static boolean hasDigits(
            final String strToCheck )
    {
        if ( strToCheck == null )
        {
            return false;
        }
        
        for ( int pos = 0 ; pos < strToCheck.length() ; pos++ )
        {
            if ( Character.isDigit( strToCheck.charAt( pos ) ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Z�hlen der Anzahl Ziffern im �bergebenen String.
     * 
     * @param strToCheck zu pr�fender String
     * @return Anzahl Ziffern im �bergebenen String
     */
    public static int countOfDigits(
            final String strToCheck )
    {
        int digitCount = 0;
        for ( int pos = 0 ; pos < strToCheck.length() ; pos++ )
        {
            if ( Character.isDigit( strToCheck.charAt( pos ) ) )
            {
                digitCount++;
            }
        }
        return digitCount;
    }

}