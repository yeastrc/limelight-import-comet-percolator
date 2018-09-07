package org.yeastrc.limelight.xml.comet_percolator.utils;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;

public class CometParsingUtils {
	
	public static CometReportedPeptide getCometReportedPeptideForString( String reportedPeptide, CometResults cometResults ) {
		
		for( CometReportedPeptide cometPeptide : cometResults.getPeptidePSMMap().keySet() ) {
			if( cometPeptide.getReportedPeptideString().equals( reportedPeptide ) ) {
				return cometPeptide;
			}
		}
		
		return null;
	}

}
