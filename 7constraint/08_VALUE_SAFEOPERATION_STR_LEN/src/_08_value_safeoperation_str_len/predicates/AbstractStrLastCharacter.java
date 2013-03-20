package _08_value_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Prädikat String mit einem bestimmten Zeichen an letzter Position.
 * <br/>
 * Die Methode {@link #test} bleibt in dieser Klasse
 * abstract und ist in der entsprechenden konkreten
 * Implementierungsklasse zu implementieren
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractStrLastCharacter
extends PrimitivPredicate<String>
{
    /**
     * Konstruktor.
     */
    public AbstractStrLastCharacter()
    {
        super( 
                //lineNr
                0 , 
                //includePredicateArr
                new StrIsNotNull() ,
                new StrMinLen( 1 )
                //safeMemberSet
                //null
                );
    }

}