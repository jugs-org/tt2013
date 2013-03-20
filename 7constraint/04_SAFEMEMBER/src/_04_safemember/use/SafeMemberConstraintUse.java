package _04_safemember.use;

import _04_safemember.constraints.RolleEbeneNiederlassungConstraint;
import _04_safemember.constraints.RolleNiederlassungConstraint;
import _04_safemember.constraints.RolleNiederlassungNordConstraint;
import _04_safemember.constraints.RolleRegionNordConstraint;
import _04_safemember.constraints.RolleRegionSuedConstraint;
import _04_safemember.enums.Ebene;
import _04_safemember.enums.Region;
import _04_safemember.enums.Rolle;

/**
 * Diese Klasse demonstriert die Anwendung
 * der von {@link SafeMemberConstraintCodeGenerator}
 * generierten Constraints.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SafeMemberConstraintUse
{
    /**
     * Beispiel f�r eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleNiederlassungNord(
            final RolleNiederlassungNordConstraint constraint )
    {
        doRolleNiederlassung(
                // Kompatibilit�t Constraint �ber Konvertierungs-Methode
                constraint.convertToRolleNiederlassungConstraint() );
        
        doRolleEbeneNiederlassung(
                // Kompatibilit�t Constraint �ber Konvertierungs-Methode
                constraint.convertToRolleEbeneNiederlassungConstraint() );

        doRolleRegionNord(
                // Kompatibilit�t Constraint �ber Konvertierungs-Methode
                constraint.convertToRolleRegionNordConstraint() );
    }

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
        final Ebene.Niederlassung niederlassungEbene = constraint.ebeneNiederlassung;
    }

    /**
     * Beispiel f�r eine Methode, die von Benutzern mit der
     * Rollen Niederlassung {@link Rolle.Niederlassung}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleEbeneNiederlassung(
            final RolleEbeneNiederlassungConstraint constraint )
    {
        final Ebene.Niederlassung niederlassungEbene = constraint.ebeneNiederlassung;
    }

    /**
     * Beispiel f�r eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.RolleRegionNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleRegionNord(
            final RolleRegionNordConstraint constraint )
    {
        final Region.Nord regionNord = constraint.regionNord;
    }

    /**
     * Beispiel f�r eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.RolleRegionSued}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleRegionSued(
            final RolleRegionSuedConstraint constraint )
    {
        final Region.Sued regionSued = constraint.regionSued;
    }

}