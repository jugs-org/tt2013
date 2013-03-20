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
     * Beispiel für eine Methode, die von Benutzern mit den
     * Rollen Niederlassung {@link Rolle.Niederlassung} und 
     * NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleNiederlassungAndRolleNiederlassungNord(
//            final ANDB_RolleNiederlassung_RolleNiederlassungNord_ANDE constraint )
//    {
//        doRolleNiederlassung(
//                // Kompatibilität Constraint über Konvertierungs-Methode
//                constraint.convertToRolleNiederlassungConstraint() );
//
//        doRolleNiederlassungNord(
//                // Kompatibilität Constraint über Konvertierungs-Methode
//                constraint.convertToRolleNiederlassungNordConstraint() );
//    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
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
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
//  TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleNiederlassungNord(
//            final RolleNiederlassungNordConstraint constraint )
//    {
//        
//    }

}