package de.heinerkuecker.constraint;

/**
 * Diese Klasse speichert die Definition
 * einer sicheren Operation, welche bei
 * einem bestimmten eingehaltenen
 * Constraint m�glich ist und einen
 * Ergebnis-Wert liefert, der kein
 * Constraint ist.
 * <br/>
 * Ein Beispiel ist eine Operation,
 * welche f�r eine constraint-gepr�ften
 * String die L�nge als int-Wert zur�ckgibt.
 * <br/>
 * Zur Generierungszeit wird gepr�ft, ob die
 * Operation sicher m�glich ist.
 * <br/>
 * Die Implementierungsklasse dieser 
 * Definitionsklasse erzeugt den
 * Java-Code f�r die sichere Operation.
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
     * die sichere Operation ausf�hrt.
     * <br/>
     * F�r die Klasse {@link ValueSafeOperation}
     * muss diese Methode den Java-Code-String
     * f�r eine komplette Methode liefern.
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
