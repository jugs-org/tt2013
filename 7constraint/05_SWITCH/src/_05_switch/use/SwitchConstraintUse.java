package _05_switch.use;

import _05_switch.SwitchConstraintCodeGenerator;
import _05_switch.constraints.ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_RolleEbeneFiliale_ORE;
import _05_switch.constraints.ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE;
import _05_switch.constraints.RolleRegionNordConstraint;
import _05_switch.constraints.RolleRegionOstConstraint;
import _05_switch.constraints.RolleRegionSuedConstraint;
import _05_switch.constraints.RolleRegionWestConstraint;

/**
 * Diese Klasse demonstriert die Anwendung
 * der von {@link SwitchConstraintCodeGenerator}
 * generierten Constraints.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SwitchConstraintUse
{
    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rolle Ebene Zentrale, Niederlassung oder Filiale
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doORB_RolleEbeneZentrale_RolleEbeneNiederlassung_RolleEbeneFiliale_ORE(
            final ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_RolleEbeneFiliale_ORE constraint )
    {
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//        constraint.new EbeneSwitch()
//        {
//            @Override
//            protected void caseRolleEbeneZentrale(
//                    final RolleEbeneZentraleConstraint constraint )
//            {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            protected void caseRolleEbeneNiederlassung(
//                    final RolleEbeneNiederlassungConstraint constraint )
//            {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            protected void caseRolleEbeneFiliale(
//                    final RolleEbeneFilialeConstraint constraint )
//            {
//                // TODO Auto-generated method stub
//            }
//        }.doSwitch();
    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Region Nord, Süd, West oder Ost
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE(
            final ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE constraint )
    {
        constraint.new RegionSwitch()
        {
            @Override
            protected void caseRolleRegionNord(
                    final RolleRegionNordConstraint constraint )
            {
                // TODO Auto-generated method stub
            }

            @Override
            protected void caseRolleRegionSued(
                    final RolleRegionSuedConstraint constraint )
            {
                // TODO Auto-generated method stub
            }

            @Override
            protected void caseRolleRegionWest(
                    final RolleRegionWestConstraint constraint )
            {
                // TODO Auto-generated method stub
            }

            @Override
            protected void caseRolleRegionOst(
                    final RolleRegionOstConstraint constraint )
            {
                // TODO Auto-generated method stub
            }
        }.doSwitch();
    }

}