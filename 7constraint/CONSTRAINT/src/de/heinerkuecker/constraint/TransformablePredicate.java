package de.heinerkuecker.constraint;

/**
 * Zu implementierendes Interface
 * f�r Pr�dikate, welche durch
 * sichere Operationen
 * {@link TransformDestinationConstraintSafeOperation}
 * in ein Ergebnis-Pr�dikat 
 * umgewandelt werden sollen.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts der Pr�dikate des Constraint-Code-Generators
 * @author Heiner K&uuml;cker
 */
public interface TransformablePredicate<CT>
{
    /**
     * Zu implementierende Methode zum Transformieren
     * dieses Pr�dikates f�r die �bergebene sichere Operation.
     * <br/><br/>
     * Der Aufbau des Methoden-Bodys �hnelt Methoden
     * aus dem Visitor-Pattern.
     * <br/><br/>
     * Diese Methode muss als letzte Anweisung das Werfen
     * einer TransformMethodNotImplementedForSafeOperationException
     * implementieren.
     * <br/>
     * Dies dient zum Absichern w�hrend des Generierens, dass
     * keine falsche Transformation f�r eine sichere Operation
     * erfolgt.
     * <br/><br/>
     * Davor ist eine if-Kaskade f�r die jeweilige sichere Operation
     * zu implementieren.
     * <br/><br/>
     * F�r die jeweilige sichere Operation wird das urspr�ngliche
     * Pr�dikat, ein neues ver�ndertes Pr�dikat oder kein Pr�dikat
     * (null) zur�ckgegeben.
     * <br/><br/>
     * Im Code-Generator werden diese einzelnen Pr�dikate
     * wiederum zur Ergebnis-Constraint-Expression zusammengef�gt.
     * 
     * @param srcConstraintPredicate hier wird die vor der sicheren Operation g�ltige Constraint-Pr�dikat-Expression �bergeben, damit dies beim Transformieren des �bergebenen einzelnen Constraint-Pr�dikates mit in Betracht gezogen werden kann
     * @param safeOperation sichere Operation, deren Ergebnis-Pr�dikat zur�ckgegeben werden soll
     * @return Ergebnis-Pr�dikat der sicheren Operation,
     *         <code>null</code>, wenn dieses Pr�dikat nicht in der Ergebnis-Constraint-Expression enthalten sein soll
     * @hrows TransformMethodNotImplementedForSafeOperationException wenn kein if-Zweig f�r die �bergebene sichere Operation implementiert ist
     */
    public Predicate<CT> transform(
            final Predicate<CT> srcConstraintPredicate ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation );
}
