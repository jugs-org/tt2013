package de.heinerkuecker.constraint;

import java.lang.reflect.Type;

/**
 * Sichere Operation mit neu zu erzeugendem Ziel-Constraint
 * mit einem anderen Kontext-Objekt-Type als der urspr�ngliche
 * Kontext-Objekt-Type.
 * 
 * @param <SCT> Type-Parameter, Klasse des Context-Objekts des urspr�nglichen Constraints
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
     * Zur�ckgeben der Ziel-Constraint-Expression.
     * 
     * @param lineNr Zeilen-Nummer der definition der zur�ckzugebenden Ziel-Constraint-Expression
     * @param srcConstraintPredicate hier wird das vor der sicheren Operation g�ltige Constraint-Pr�dikat �bergeben, damit dies beim Erzeugen des neuen Constraint-Pr�dikates mit in Betracht gezogen werden kann
     * @param exclude Hilfsparameter zum Anlegen von mit {@link And}, {@link Or} usw verkn�pften Pr�dikaten
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
     * Zu implementierende Methode zum Zur�ckgeben des Kontext-Objekt-Typs des Ziel-Constraint
     * @return Kontext-Objekt-Typ des Ziel-Constraints
     */
    abstract public Type getDestinationContextObjType();
}
