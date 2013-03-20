package de.heinerkuecker.constraint;

/**
 * Diese Klasse speichert die Definition
 * einer sicheren Operation, welche bei
 * einem bestimmten eingehaltenen
 * Constraint möglich ist.
 * <br/>
 * Zur Generierungszeit wird geprüft, ob die
 * Operation sicher möglich ist.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractSafeOperation<CT>
{
    /**
     * Zeilen-Nummer der definition dieser sicheren Operation.
     */
    public final int lineNr;

    /**
     * Konstruktor.
     * 
     * @param lineNr Zeilen-Nummer der definition dieser sicheren Operation
     */
    public AbstractSafeOperation(
            final int lineNr )
    {
        this.lineNr = lineNr;
    }

    /**
     * Falls eine äquivalente Constraint-Expression 
     * für das andere(rechte) Operanden-Constraint bereits 
     * definiert ist, wird dieses verwendet und hier vermerkt.
     * <br/>
     * Unter dem anderen(rechten) Operanden-Constraint
     * verstehe ich ein Constraint, welches eventuell
     * als Parameter der generierten Methode auftaucht:
     * <pre>
     *     Value/DestinationConstraint safeOperation(
     *             final OtherConstraint constraint ) {
     *         ...
     * </pre>
     * 
     * <br/>
     * Dies ist ein temporärer Zwischenspeicher für den Code-Generator
     */
    /*default*/ Predicate<CT> reusedOrCreatedOtherPredicate;
    
    /**
     * Zu implementierende Methode zum Zurückgeben
     * der Voraussetzung für die sichere Operation.
     * <br/>
     * Falls keine Voraussetzung erfoderlich ist,
     * darf <code>null</code> zurückgegeben werden.
     * 
     * @param cache Hilfsparameter zum Anlegen von mit {@link And}, {@link Or} usw verknüpften Prädikaten
     * @return vorausgesetztes Prädikat bzw Constraint-Expression
     */
    abstract public Predicate<CT> getPremise(
            final ConstraintCodeGeneratorCache<CT> cache );

    /**
     * Zu implementierende Methode zum Zurückgeben
     * eines anderen(rechten) (Parameter-)Prädikats
     * für die sichere Operation.
     * <br/>
     * Falls kein anderes(rechtes) (Parameter-)Prädikat 
     * erfoderlich ist, darf <code>null</code> zurückgegeben
     * werden.
     * 
     * @param cache Hilfsparameter zum Anlegen von mit {@link And}, {@link Or} usw verknüpften Prädikaten
     * @return anderes(rechtes) (Parameter-)Prädikat bzw Constraint-Expression
     */
    abstract public Predicate<CT> getOtherParameterPredicate(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<CT> cache );
}
