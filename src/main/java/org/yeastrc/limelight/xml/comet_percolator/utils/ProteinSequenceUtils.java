package org.yeastrc.limelight.xml.comet_percolator.utils;

public class ProteinSequenceUtils {

    public static String trimTrailingStar(String sequence) {
        if( sequence.endsWith("*")) {
            return sequence.substring(0, sequence.length() - 1);
        }

        return sequence;
    }

}
