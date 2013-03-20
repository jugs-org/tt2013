package _09_new_safeoperation_str_len.predicates;

import java.util.HashSet;

import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Pr‰dikat String mit Groﬂbuchstaben an erster Position.
 * 
 * @author Heiner K&uuml;cker
 */
public final class StrFirstUppercaseLetter
extends PrimitivPredicate<String>
implements StrFirstCharacter
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

}