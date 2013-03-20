package _09_new_safeoperation_str_len;

import java.util.List;

import _09_new_safeoperation_str_len.predicates.StrExactLen;
import _09_new_safeoperation_str_len.predicates.StrMaxLen;
import _09_new_safeoperation_str_len.predicates.StrMinLen;

import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * @author Heiner K&uuml;cker
 */
public final class StringConstraintUtil
{
    /**
     * Konstruktor private,
     * Instanziieren verboten.
     */
    private StringConstraintUtil()
    {
        super();
    }

    /**
     * Ermittlung der vorherigen Mindest-String-Länge
     * im übergebenen(ursprünglichen) Constraint.
     * 
     * @param predicateToCheck vor der sicheren Operation gültiges Constraint
     * @return vor der sicheren Operation gültige Mindest-String-Länge
     */
    public static Integer computeStrMinLen( 
            final Predicate<String> predicateToCheck )
    {
        // Prüfen, ob es vor der sicheren Operation eine festgelegte Mindestlänge des Strings gab
        final List<PrimitivPredicate<String>> strMinLenPredicateList =
                predicateToCheck.getPredicateListFromAndNotNegated(
                        //predicateClass
                        StrMinLen.class );
    
        final Integer preStrMinLenIntgrObj;
        if ( strMinLenPredicateList == null ||
                strMinLenPredicateList.isEmpty() )
            // es gab vor der sicheren Operation keine festgelegte Mindestlänge des Strings
        {
            preStrMinLenIntgrObj = null;
        }
        else
        {
            // maximale vor der sicheren Operation festgelegte Mindestlänge des Strings ermitteln
            int maxMinLen = 0;
            
            for ( PrimitivPredicate<String> primitivPredicate : strMinLenPredicateList )
            {
                final StrMinLen strMinLenPredicate =
                        (StrMinLen) primitivPredicate;
                
                if ( strMinLenPredicate.minLen > maxMinLen )
                {
                    maxMinLen = strMinLenPredicate.minLen;
                }
            }
            
            preStrMinLenIntgrObj = maxMinLen;
        }
    
        return preStrMinLenIntgrObj;
    }

    /**
     * @param predicateToCheck vor der sicheren Operation gültiges Constraint
     * @return vor der sicheren Operation gültige exakte String-Länge
     */
    public static Integer computeStrExactLen( 
            final Predicate<String> predicateToCheck )
    {
        // Prüfen, ob es vor der sicheren Operation eine festgelegte exakte Länge des Strings gab
        final List<PrimitivPredicate<String>> strExactLenPredicateList =
                predicateToCheck.getPredicateListFromAndNotNegated(
                        //predicateClass
                        StrExactLen.class );
    
        final Integer preStrExactLen;
        if ( strExactLenPredicateList == null ||
                strExactLenPredicateList.isEmpty() )
            // es gab vor der sicheren Operation keine festgelegte exakte Länge des Strings
        {
            preStrExactLen = null;
        }
        else if ( strExactLenPredicateList.size() > 1 )
            // scheinbar gab es mehr als eine exakte Länge
        {
            preStrExactLen = null;
        }
        else
        {
            // vor der sicheren Operation festgelegte exakte Länge des Strings ermitteln
            preStrExactLen = ( (StrExactLen) strExactLenPredicateList.get( 0 ) ).exactLen;
        }
    
        return preStrExactLen;
    }

    /**
     * @param predicateToCheck vor der sicheren Operation gültiges Constraint
     * @return vor der sicheren Operation gültige Maximal-String-Länge
     */
    public static Integer computeStrMaxLen( 
            final Predicate<String> predicateToCheck )
    {
        // Prüfen, ob es vor der sicheren Operation eine festgelegte Mindestlänge des Strings gab
        final List<PrimitivPredicate<String>> strMaxLenPredicateList =
                predicateToCheck.getPredicateListFromAndNotNegated(
                        //predicateClass
                        StrMaxLen.class );
    
        final Integer preStrMaxLen;
        if ( strMaxLenPredicateList == null ||
                strMaxLenPredicateList.isEmpty() )
            // es gab vor der sicheren Operation keine festgelegte Höchstlänge des Strings
        {
            preStrMaxLen = null;
        }
        else
        {
            // minimale vor der sicheren Operation festgelegte Höchstlänge des Strings ermitteln
            int minMaxLen = Integer.MAX_VALUE;
            
            for ( PrimitivPredicate<String> primitivPredicate : strMaxLenPredicateList )
            {
                final StrMaxLen strMaxLenPredicate =
                        (StrMaxLen) primitivPredicate;
                
                if ( strMaxLenPredicate.maxLen < minMaxLen )
                {
                    minMaxLen = strMaxLenPredicate.maxLen;
                }
            }
            
            preStrMaxLen = minMaxLen;
        }
    
        return preStrMaxLen;
    }

}