package de.heinerkuecker.constraint;

/**
 * Diese Klasse speichert die Definition
 * einer sicheren Operation, welche bei
 * einem bestimmten eingehaltenen
 * Constraint möglich ist und einen
 * Ergebnis-Wert liefert, der kein
 * Constraint ist.
 * <br/>
 * Ein Beispiel ist eine Operation,
 * welche für eine constraint-geprüften
 * String die Länge als int-Wert zurückgibt.
 * <br/>
 * Zur Generierungszeit wird geprüft, ob die
 * Operation sicher möglich ist.
 * <br/>
 * Die Implementierungsklasse dieser 
 * Definitionsklasse erzeugt den
 * Java-Code für die sichere Operation.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Typ
 * @author Heiner K&uuml;cker
 */
public abstract class ValueSafeOperation<CT>
extends AbstractSafeOperation<CT>
{
    /**
     * Konstruktor.
     * @param lineNr
     */
    public ValueSafeOperation(
            final int lineNr )
    {
        super( lineNr );
    }

    /**
     * Zu implementierende Methode zum
     * Erzeugen des Java-Codes, welcher
     * die sichere Operation ausführt.
     * <br/>
     * Für die Klasse {@link ValueSafeOperation}
     * muss diese Methode den Java-Code-String
     * für eine komplette Methode liefern.
     * 
     * @param otherConstraintFileName Java-Klassen-Name der eventuell(darf null sein) als Paramter der zu generierenden Methode auftauchender Constraint-Parameter
     * @param contextObjName Name des Kontext-Objektes des Constraints zum Verwenden im zu erzeugenden Java-Code-String
     * @return Java-Code als String
     */
    abstract public String toJavaCode(
            final String otherConstraintFileName ,
            final String contextObjName );

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }

}
