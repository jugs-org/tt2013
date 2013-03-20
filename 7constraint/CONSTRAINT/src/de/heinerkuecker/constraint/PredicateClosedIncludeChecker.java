package de.heinerkuecker.constraint;

import java.util.HashMap;

import de.heinerkuecker.constraint.util.coll.HashsetHashmap;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlyMap;
import de.heinerkuecker.constraint.util.coll.ReadOnlyMapAdaptor;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Diese Klasse pr�ft und vermerkt die Information, ob ein primitives Pr�dikat in einer 
 * Expression  durch seine includierten Pr�dikate ersetzt werden kann.
 * <br/><br/>
 * Basis f�r diese Entscheidung ist die closed world assumption.
 * Ob die inkludierten Pr�dikate eines Pr�dikates die closed world assumption erf�llen,
 * wird daran festgestellt, ob es f�r die Kombination der includierten Pr�dikate ein
 * weiteres Pr�dikat gibt.
 * Gibt es nur dieses eine Pr�dikat, welches die aktuelle Kombination von inkludierten
 * Pr�dikaten hat, so erf�llt dieses die closed world assumption.
 * Falls es mehrere Pr�dikate gibt, welches die aktuelle Kombination von inkludierten
 * Pr�dikaten haben, ist in diesen Pr�dikaten implizit(ohne explizites Notieren) eine
 * weitere unterscheidende Information enthalten, die nicht von den includierten
 * Pr�dikaten abgebildet wird.
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
 * Da beide Rollen(Pr�dikate) die selben
 * Pr�dikate inkludieren, gibt es noch einen
 * nicht notierten Unterschied, in diesem
 * Falle die Einschr�nkung auf nur-lesend
 * </pre>
 * Damit dies funktioniert, m�ssen alle primitiven Pr�dikate,
 * die in den {@link Exclude}s und den add-Methoden
 * {@link AbstractConstraintCodeGenerator#add}
 * aufgetaucht sind, der Methode
 * {@link #checkAndSetIsIncludeClosed}
 * �bergeben werden.
 * 
 * @author Heiner K&uuml;cker
 */
public final class PredicateClosedIncludeChecker
{
    /**
     * Methode zum Ermitteln und Vermerken
     * cosed world assumption f�r Includes
     * primtiver Pr�dikate.
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
            // Vermerken der final (Ende der Hierarchie) inkludierten Pr�dikate in einem Set, welches als Schl�ssel dient
            final HashsetWithReadonly<PrimitivPredicate<CT>> finalIncludePredSet =
                    // nochmal umkopieren in ein HasSet, weil dies bei equals die Set-Klasse mit auf Gleichheit pr�ft, nicht nur die enthaltenen Elemente
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
            // ob es f�r die Menge der inkludierten Pr�dikate genau nur eine Auspr�gung gibt
            final boolean isIncludeFinal =
                    finalIncludePredSetToPredMap.getValueCount(
                            // nochmal umkopieren in ein HasSet, weil dies bei equals die Set-Klasse mit auf Gleichheit pr�ft, nicht nur die enthaltenen Elemente
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
