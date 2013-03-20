package de.heinerkuecker.constraint;

import java.util.HashMap;

import de.heinerkuecker.constraint.util.coll.HashsetHashmap;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlyMap;
import de.heinerkuecker.constraint.util.coll.ReadOnlyMapAdaptor;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Diese Klasse prüft und vermerkt die Information, ob ein primitives Prädikat in einer 
 * Expression  durch seine includierten Prädikate ersetzt werden kann.
 * <br/><br/>
 * Basis für diese Entscheidung ist die closed world assumption.
 * Ob die inkludierten Prädikate eines Prädikates die closed world assumption erfüllen,
 * wird daran festgestellt, ob es für die Kombination der includierten Prädikate ein
 * weiteres Prädikat gibt.
 * Gibt es nur dieses eine Prädikat, welches die aktuelle Kombination von inkludierten
 * Prädikaten hat, so erfüllt dieses die closed world assumption.
 * Falls es mehrere Prädikate gibt, welches die aktuelle Kombination von inkludierten
 * Prädikaten haben, ist in diesen Prädikaten implizit(ohne explizites Notieren) eine
 * weitere unterscheidende Information enthalten, die nicht von den includierten
 * Prädikaten abgebildet wird.
 * <pre>
 * Beispiel:
 * 
 *  Rolle J2 (JobCenter-SGB2)
 *      inkludiert:
 *              Rechtskreis-SGB2
 *              Ebene JobCenter
 *              
 *  Rolle JL2 (JobCenter-SGB2-lesend)
 *      inkludiert:
 *              Rechtskreis-SGB2
 *              Ebene JobCenter
 * 
 * Da beide Rollen(Prädikate) die selben
 * Prädikate inkludieren, gibt es noch einen
 * nicht notierten Unterschied, in diesem
 * Falle die Einschränkung auf nur-lesend
 * </pre>
 * Damit dies funktioniert, müssen alle primitiven Prädikate,
 * die in den {@link Exclude}s und den add-Methoden
 * {@link AbstractConstraintCodeGenerator#add}
 * aufgetaucht sind, der Methode
 * {@link #checkAndSetIsIncludeClosed}
 * übergeben werden.
 * 
 * @author Heiner K&uuml;cker
 */
public final class PredicateClosedIncludeChecker
{
    /**
     * Methode zum Ermitteln und Vermerken
     * cosed world assumption für Includes
     * primtiver Prädikate.
     * 
     * @param predicateToCheckSet
     */
    /*default*/ static <CT> ReadOnlyMap<PrimitivPredicate<CT>, Boolean> checkAndSetIsIncludeClosed(
            final ReadOnlySet<PrimitivPredicate<CT>> predicateToCheckSet )
    {
        final HashsetHashmap<HashsetWithReadonly<PrimitivPredicate<CT>>, PrimitivPredicate<CT>> finalIncludePredSetToPredMap =
                new HashsetHashmap<HashsetWithReadonly<PrimitivPredicate<CT>>, PrimitivPredicate<CT>>();
        
        for ( PrimitivPredicate<CT> predicateToCheck : predicateToCheckSet )
        {
            // Vermerken der final (Ende der Hierarchie) inkludierten Prädikate in einem Set, welches als Schlüssel dient
            final HashsetWithReadonly<PrimitivPredicate<CT>> finalIncludePredSet =
                    // nochmal umkopieren in ein HasSet, weil dies bei equals die Set-Klasse mit auf Gleichheit prüft, nicht nur die enthaltenen Elemente
                    new HashsetWithReadonly<PrimitivPredicate<CT>>(
                            predicateToCheck.getFinalIncludedPredicateSet() );

            finalIncludePredSetToPredMap.put(
                    finalIncludePredSet ,
                    predicateToCheck );
        }

        final HashMap<PrimitivPredicate<CT>, Boolean> result =
                new HashMap<PrimitivPredicate<CT>, Boolean>();

        for ( PrimitivPredicate<CT> predicateToCheck : predicateToCheckSet )
        {
            // ob es für die Menge der inkludierten Prädikate genau nur eine Ausprägung gibt
            final boolean isIncludeFinal =
                    finalIncludePredSetToPredMap.getValueCount(
                            // nochmal umkopieren in ein HasSet, weil dies bei equals die Set-Klasse mit auf Gleichheit prüft, nicht nur die enthaltenen Elemente
                            new HashsetWithReadonly<PrimitivPredicate<CT>>(
                                    predicateToCheck.getFinalIncludedPredicateSet() ) ) < 2;

            result.put(
                    predicateToCheck ,
                    isIncludeFinal );
        }
        
        return new ReadOnlyMapAdaptor<PrimitivPredicate<CT>, Boolean>(
                result );
    }

}
