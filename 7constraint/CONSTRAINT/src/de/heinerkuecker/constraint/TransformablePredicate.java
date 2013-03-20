package de.heinerkuecker.constraint;

/**
 * Zu implementierendes Interface
 * für Prädikate, welche durch
 * sichere Operationen
 * {@link TransformDestinationConstraintSafeOperation}
 * in ein Ergebnis-Prädikat 
 * umgewandelt werden sollen.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts der Prädikate des Constraint-Code-Generators
 * @author Heiner K&uuml;cker
 */
public interface TransformablePredicate<CT>
{
    /**
     * Zu implementierende Methode zum Transformieren
     * dieses Prädikates für die übergebene sichere Operation.
     * <br/><br/>
     * Der Aufbau des Methoden-Bodys ähnelt Methoden
     * aus dem Visitor-Pattern.
     * <br/><br/>
     * Diese Methode muss als letzte Anweisung das Werfen
     * einer TransformMethodNotImplementedForSafeOperationException
     * implementieren.
     * <br/>
     * Dies dient zum Absichern während des Generierens, dass
     * keine falsche Transformation für eine sichere Operation
     * erfolgt.
     * <br/><br/>
     * Davor ist eine if-Kaskade für die jeweilige sichere Operation
     * zu implementieren.
     * <br/><br/>
     * Für die jeweilige sichere Operation wird das ursprüngliche
     * Prädikat, ein neues verändertes Prädikat oder kein Prädikat
     * (null) zurückgegeben.
     * <br/><br/>
     * Im Code-Generator werden diese einzelnen Prädikate
     * wiederum zur Ergebnis-Constraint-Expression zusammengefügt.
     * 
     * @param srcConstraintPredicate hier wird die vor der sicheren Operation gültige Constraint-Prädikat-Expression übergeben, damit dies beim Transformieren des übergebenen einzelnen Constraint-Prädikates mit in Betracht gezogen werden kann
     * @param safeOperation sichere Operation, deren Ergebnis-Prädikat zurückgegeben werden soll
     * @return Ergebnis-Prädikat der sicheren Operation,
     *         <code>null</code>, wenn dieses Prädikat nicht in der Ergebnis-Constraint-Expression enthalten sein soll
     * @hrows TransformMethodNotImplementedForSafeOperationException wenn kein if-Zweig für die übergebene sichere Operation implementiert ist
     */
    public Predicate<CT> transform(
            final Predicate<CT> srcConstraintPredicate ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation );
}
