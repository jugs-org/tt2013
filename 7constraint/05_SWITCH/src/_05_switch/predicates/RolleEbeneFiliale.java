package _05_switch.predicates;

import _05_switch.enums.Ebene;
import _05_switch.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.CollectionUtil;

/**
 * Primitives Prädikat Ebene Filiale.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen an der Klasse erkennt
public final class RolleEbeneFiliale
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleEbeneFiliale()
    {
        super( 
                //lineNr
                0 , 
                //includePredicateSet
                null ,
                //safeMemberSet
                CollectionUtil.hashset(
                        RolleSafeMember.ebeneFiliale )  );
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
                        Ebene.Filiale.instance );
    }

}