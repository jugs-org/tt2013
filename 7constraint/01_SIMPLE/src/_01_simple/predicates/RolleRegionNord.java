package _01_simple.predicates;

import _01_simple.enums.Region;
import _01_simple.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Region Nord.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleRegionNord
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleRegionNord()
    {
        super( 
                //lineNr
                0
                //includePredicateArr
                //null ,
                //safeMemberSet
                //null
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
                ( ( rolle instanceof Rolle.Niederlassung &&
                        ( (Rolle.Niederlassung) rolle ).region.equals(
                                Region.Nord.instance ) ) ||
                  ( rolle instanceof Rolle.Filiale &&
                        ( (Rolle.Filiale) rolle ).niederlassung.region.equals(
                                Region.Nord.instance ) ) );
    }

}