package org.yeastrc.limelight.xml.comet_percolator.utils;

import org.yeastrc.limelight.xml.comet_percolator.constants.CometConstants;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometPSM;

import java.math.BigDecimal;

public class MassUtils {

    /**
     * Get the calculated m/z for a precursor for a psm that is (neutral mass + charge * hydrogen mass) / charge
     *
     * @param psm
     * @return
     */
    public static BigDecimal getObservedMoverZForPsm(CometPSM psm) {

        final double charge = psm.getCharge();
        final double neutralMass = psm.getPrecursorNeutralMass().doubleValue();
        final double observedMoverZ = (CometConstants.COMET_MASS_HYDROGEN_MONO.doubleValue() * charge + neutralMass) / charge;

        return BigDecimal.valueOf(observedMoverZ);
    }

}
