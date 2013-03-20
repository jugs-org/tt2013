package de.heinerkuecker.constraint;


/**
 * Diese Klasse speichert die Definition
 * einer sicheren Operation, welche das aktuelle
 * Constraint auf ein anderes Constraint
 * abbildet (mathematische Abbildungsvorschrift).
 * <br/>
 * Ein Beispiel ist eine Addition-plus-eins-Operation,
 * welche für einen constraint-geprüften größer-als-0-int-Wert
 * einen constraint-geprüften größer-als-1-int-Wert zurückgibt.
 * <br/>
 * Zur Generierungszeit wird geprüft, ob die
 * Operation sicher möglich ist.
 * <br/>
 * Diese Definitionsklasse erzeugt den
 * Java-Code für die sichere Operation
 * und legt das Ergebnis-Constraint für die
 * sichere Operation fest.
 * <br/>
 * Dieses Constraint wird zusätzlich zu den
 * definierten Constraints generiert, falls
 * es nicht bereits definiert ist.
 * 
 * @param <SCT> Type-Parameter, Klasse des Context-Objekts des ursprünglichen Constraints
 * @param <DCT> Type-Parameter, Klasse des Context-Objekts des Ziel-Constraints
 * @author Heiner K&uuml;cker
 */
abstract public class DestinationConstraintSafeOperation<SCT, DCT>
extends AbstractSafeOperation<SCT>
{
    /**
     * Konstruktor.
     * 
     * @param lineNr
     */
    public DestinationConstraintSafeOperation(
            final int lineNr )
    {
        super( lineNr );
    }

    /**
     * Falls eine äquivalente Constraint-Expression 
     * für das neue Ziel-Constraint bereits definiert ist, 
     * wird dieses verwendet und hier vermerkt.
     * <br/>
     * Dies ist ein temporärer Zwischenspeicher für den Code-Generator
     */
    /*default*/ Predicate<DCT> reusedOrCreatedDestinationPredicate;
    
    /**
     * Zu implementierende Methode zum
     * Erzeugen des Java-Codes, welcher
     * die sichere Operation ausführt.
     * <br/>
     * Für die Klasse {@link DestinationConstraintSafeOperation}
     * muss diese Methode den Java-Code-String
     * für eine komplette Methode liefern,
     * in welchen der übergebene Klassen-Name des 
     * Ziel-Constraints als return-Typ eigestzt worden 
     * sein muss.
     * 
     * @param destinationConstraintFileName Klassen-Name des Ziel-Constraints(return-Type der zu generierenden Methode) zum Einfügen in den erzeugten Java-Code
     * @param otherConstraintFileName Java-Klassen-Name der eventuell(darf null sein) als Paramter der zu generierenden Methode auftauchender Constraint-Parameter
     * @param contextObjName Name des Kontext-Objekts des Constraints
     * @return Java-Code als String
     */
    abstract public String toJavaCode(
            final String destinationConstraintFileName ,
            final String otherConstraintFileName ,
            final String contextObjName );

}
