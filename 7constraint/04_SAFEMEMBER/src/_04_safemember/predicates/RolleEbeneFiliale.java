package _04_safemember.predicates;

import _04_safemember.enums.Ebene;
import _04_safemember.enums.Rolle;
import _04_safemember.safemember.RolleSafeMember;
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
                null
                //safeMemberSet
                // TODO Übungsaufgabe Stuttgarter Testtage 2013: CollectionUtil.hashset( RolleSafeMember.ebeneFiliale )
                );
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