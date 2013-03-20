package _01_simple.use;

import _01_simple.SimpleConstraintCodeGenerator;
import _01_simple.constraints.RolleNiederlassungConstraint;
import _01_simple.enums.Rolle;

/**
 * Diese Klasse demonstriert die Anwendung
 * der von {@link SimpleConstraintCodeGenerator}
 * generierten Constraints.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SimpleConstraintUse
{
    /**
     * Beispiel f�r eine Methode, die von Benutzern mit den
     * Rollen Niederlassung {@link Rolle.Niederlassung} und 
     * NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
// TODO �bungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleNiederlassungAndRolleNiederlassungNord(
//            final ANDB_RolleNiederlassung_RolleNiederlassungNord_ANDE constraint )
//    {
//        doRolleNiederlassung(
//                // Kompatibilit�t Constraint �ber Konvertierungs-Methode
//                constraint.convertToRolleNiederlassungConstraint() );
//
//        doRolleNiederlassungNord(
//                // Kompatibilit�t Constraint �ber Konvertierungs-Methode
//                constraint.convertToRolleNiederlassungNordConstraint() );
//    }

    /**
     * Beispiel f�r eine Methode, die von Benutzern mit der
     * Rollen Niederlassung {@link Rolle.Niederlassung}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleNiederlassung(
            final RolleNiederlassungConstraint constraint )
    {
        
    }

    /**
     * Beispiel f�r eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
//  TODO �bungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleNiederlassungNord(
//            final RolleNiederlassungNordConstraint constraint )
//    {
//        
//    }

}