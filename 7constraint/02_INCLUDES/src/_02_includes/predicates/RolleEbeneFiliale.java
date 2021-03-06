package _02_includes.predicates;

import _02_includes.enums.Ebene;
import _02_includes.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Ebene Filiale.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen an der Klasse erkennt
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
                rolle.ebene.equals(
                        Ebene.Filiale.instance );
    }

}