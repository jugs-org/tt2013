package _03_excludes.predicates;

import _03_excludes.enums.Ebene;
import _03_excludes.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Ebene Zentrale.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleEbeneZentrale
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleEbeneZentrale()
    {
        super( 
                //lineNr
                0
                //includePredicateSet
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
                        Ebene.Zentrale.instance );
    }

}