package de.heinerkuecker.constraint;


/**
 * Diese Klasse speichert die Definition
 * einer sicheren Operation, welche das aktuelle
 * Constraint auf ein anderes Constraint
 * abbildet (mathematische Abbildungsvorschrift).
 * <br/>
 * Ein Beispiel ist eine Addition-plus-eins-Operation,
 * welche f�r einen constraint-gepr�ften gr��er-als-0-int-Wert
 * einen constraint-gepr�ften gr��er-als-1-int-Wert zur�ckgibt.
 * <br/>
 * Zur Generierungszeit wird gepr�ft, ob die
 * Operation sicher m�glich ist.
 * <br/>
 * Diese Definitionsklasse erzeugt den
 * Java-Code f�r die sichere Operation
 * und legt das Ergebnis-Constraint f�r die
 * sichere Operation fest.
 * <br/>
 * Dieses Constraint wird zus�tzlich zu den
 * definierten Constraints generiert, falls
 * es nicht bereits definiert ist.
 * 
 * @param <SCT> Type-Parameter, Klasse des Context-Objekts des urspr�nglichen Constraints
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
     * Falls eine �quivalente Constraint-Expression 
     * f�r das neue Ziel-Constraint bereits definiert ist, 
     * wird dieses verwendet und hier vermerkt.
     * <br/>
     * Dies ist ein tempor�rer Zwischenspeicher f�r den Code-Generator
     */
    /*default*/ Predicate<DCT> reusedOrCreatedDestinationPredicate;
    
    /**
     * Zu implementierende Methode zum
     * Erzeugen des Java-Codes, welcher
     * die sichere Operation ausf�hrt.
     * <br/>
     * F�r die Klasse {@link DestinationConstraintSafeOperation}
     * muss diese Methode den Java-Code-String
     * f�r eine komplette Methode liefern,
     * in welchen der �bergebene Klassen-Name des 
     * Ziel-Constraints als return-Typ eigestzt worden 
     * sein muss.
     * 
     * @param destinationConstraintFileName Klassen-Name des Ziel-Constraints(return-Type der zu generierenden Methode) zum Einf�gen in den erzeugten Java-Code
     * @param otherConstraintFileName Java-Klassen-Name der eventuell(darf null sein) als Paramter der zu generierenden Methode auftauchender Constraint-Parameter
     * @param contextObjName Name des Kontext-Objekts des Constraints
     * @return Java-Code als String
     */
    abstract public String toJavaCode(
            final String destinationConstraintFileName ,
            final String otherConstraintFileName ,
            final String contextObjName );

}
