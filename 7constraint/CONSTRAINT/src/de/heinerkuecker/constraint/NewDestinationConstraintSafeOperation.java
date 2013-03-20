package de.heinerkuecker.constraint;

/**
 * Sichere Operation mit neu zu erzeugendem Ziel-Constraint.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts
 * @author Heiner K&uuml;cker
 */
public abstract class NewDestinationConstraintSafeOperation<CT>
extends DestinationConstraintSafeOperation<CT, CT>
{
    /**
     * Konstruktor.
     * 
     * @param lineNr
     */
    public NewDestinationConstraintSafeOperation(
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
    abstract public Predicate<CT> getDestinationConstraintExpression(
            final int lineNr ,
            final Predicate<CT> srcConstraintPredicate ,
            final Exclude<CT> exclude );
}
