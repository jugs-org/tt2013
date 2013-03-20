package _09_new_safeoperation_str_len.predicates;

import java.util.HashSet;

import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Prädikat String mit Ziffer an letzter Position.
 * 
 * @author Heiner K&uuml;cker
 */
public final class StrLastDigit
extends AbstractStrLastCharacter
implements StrLastCharacter
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

}