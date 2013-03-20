package _03_excludes.use;

import _03_excludes.ExcludesConstraintCodeGenerator;
import _03_excludes.constraints.NOT_RolleEbeneFiliale;
import _03_excludes.constraints.ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE;
import _03_excludes.constraints.RolleEbeneNiederlassungConstraint;
import _03_excludes.constraints.RolleNiederlassungConstraint;
import _03_excludes.constraints.RolleNiederlassungNordConstraint;
import _03_excludes.constraints.RolleRegionNordConstraint;
import _03_excludes.enums.Rolle;

/**
 * Diese Klasse demonstriert die Anwendung
 * der von {@link ExcludesConstraintCodeGenerator}
 * generierten Constraints.
 * 
 * @author Heiner K&uuml;cker
 */
public final class ExcludesConstraintUse
{
    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleNiederlassungNord(
            final RolleNiederlassungNordConstraint constraint )
    {
        doRolleNiederlassung(
                // Kompatibilität Constraint über Konvertierungs-Methode
                constraint.convertToRolleNiederlassungConstraint() );
        
        doRolleEbeneNiederlassung(
                // Kompatibilität Constraint über Konvertierungs-Methode
                constraint.convertToRolleEbeneNiederlassungConstraint() );

        doRolleRegionNord(
                // Kompatibilität Constraint über Konvertierungs-Methode
                constraint.convertToRolleRegionNordConstraint() );
        
        doNOT_RolleEbeneFiliale(
                new NOT_RolleEbeneFiliale( constraint.rolle ) );
    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rollen Niederlassung {@link Rolle.Niederlassung}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleNiederlassung(
//            final RolleNiederlassungConstraint constraint )
//    {
//        
//    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rollen Niederlassung {@link Rolle.Niederlassung}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleEbeneNiederlassung(
//            final RolleEbeneNiederlassungConstraint constraint )
//    {
//        
//    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.RolleRegionNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doRolleRegionNord(
//            final RolleRegionNordConstraint constraint )
//    {
//        
//    }

    /**
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doNOT_RolleEbeneFiliale(
//            final NOT_RolleEbeneFiliale constraint )
//    {
//        doORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE(
//                // Konvertierung vom Not-Constraint zum Or-Constraint über closed world assumption
//                constraint.convertToORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE() );
//    }

    /**
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE(
//            final ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE constraint )
//    {
//        doNOT_RolleEbeneFiliale(
//                // Konvertierung vom Or-Constraint zum Not-Constraint über closed world assumption
//                constraint.convertToNOT_RolleEbeneFiliale() );
//    }

}