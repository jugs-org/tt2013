package _08_value_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.Predicate;

/**
 * Prädikat String mit mindestens einer Ziffer.
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
     * Prüfen, ob der übergebene String mindestens eine Ziffer enthält.
     * 
     * @param strToCheck zu prüfender String
     * @return ob der übergebene String mindestens eine Ziffer enthält
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
     * Zählen der Anzahl Ziffern im übergebenen String.
     * 
     * @param strToCheck zu prüfender String
     * @return Anzahl Ziffern im übergebenen String
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