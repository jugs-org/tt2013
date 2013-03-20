package _05_switch.predicates;

import _05_switch.enums.Ebene;
import _05_switch.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.CollectionUtil;

/**
 * Primitives Pr�dikat Ebene Niederlassung.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleEbeneNiederlassung
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleEbeneNiederlassung()
    {
        super( 
                //lineNr
                0 , 
                //includePredicateSet
                null ,
                //safeMemberSet
                CollectionUtil.hashset(
                        RolleSafeMember.ebeneNiederlassung ) );
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final Rolle rolle )
    {
        return
                rolle != null &&
                rolle.ebene.equals(
                        Ebene.Niederlassung.instance );
    }

}