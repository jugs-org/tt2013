package de.heinerkuecker.constraint;

import java.lang.reflect.Type;

/**
 * Sichere Operation mit neu zu erzeugendem Ziel-Constraint
 * mit einem anderen Kontext-Objekt-Type als der ursprüngliche
 * Kontext-Objekt-Type.
 * 
 * @param <SCT> Type-Parameter, Klasse des Context-Objekts des ursprünglichen Constraints
 * @param <DCT> Type-Parameter, Klasse des Context-Objekts des Ziel-Constraints
 * @author Heiner K&uuml;cker
 */
public abstract class OtherContextTypeDestinationConstraintSafeOperation<SCT, DCT>
extends DestinationConstraintSafeOperation<SCT, DCT>
{
    /**
     * Konstruktor.
     * @param lineNr
     */
    public OtherContextTypeDestinationConstraintSafeOperation(
            final int lineNr )
    {
        super( lineNr );
    }

    /**
     * Zu implementierende Methode zum
     * Zurückgeben der Ziel-Constraint-Expression.
     * 
     * @param lineNr Zeilen-Nummer der definition der zurückzugebenden Ziel-Constraint-Expression
     * @param srcConstraintPredicate hier wird das vor der sicheren Operation gültige Constraint-Prädikat übergeben, damit dies beim Erzeugen des neuen Constraint-Prädikates mit in Betracht gezogen werden kann
     * @param exclude Hilfsparameter zum Anlegen von mit {@link And}, {@link Or} usw verknüpften Prädikaten
     * @return Ziel-Constraint-Expression
     */
    /*default*/ final Predicate<DCT> getDestinationConstraintExpression(
            final int lineNr ,
            final Predicate<SCT> srcConstraintPredicate ,
            final Exclude<DCT> exclude )
    {
        // TODO
        return null;
    }

    /**
     * Zu implementierende Methode zum Zurückgeben des Kontext-Objekt-Typs des Ziel-Constraint
     * @return Kontext-Objekt-Typ des Ziel-Constraints
     */
    abstract public Type getDestinationContextObjType();
}
