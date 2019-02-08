package org.yeastrc.limelight.xml.comet_percolator.reader;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorPSM;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorResults;
import org.yeastrc.limelight.xml.comet_percolator.utils.CometParsingUtils;
import org.yeastrc.limelight.xml.comet_percolator.utils.ConversionUtils;

import java.util.Map;

public class CometPercolatorValidator {

	/**
	 * Validates All The Things. Ensures all percolator results are found in the expected places in the
	 * parsed comet results--including expected PSMs being present in expected pepXML files.
	 *
	 * @param cometResults
	 * @param percolatorResults
	 * @throws Exception
	 */
	public static void validateData( CometResults cometResults, PercolatorResults percolatorResults ) throws Exception {
		
		for( String percolatorReportedPeptide : percolatorResults.getReportedPeptideResults().keySet() ) {

			for (int scanNumber : percolatorResults.getReportedPeptideResults().get(percolatorReportedPeptide).getPercolatorPSMs().keySet()) {

				PercolatorPSM percPSM = percolatorResults.getReportedPeptideResults().get(percolatorReportedPeptide).getPercolatorPSMs().get( scanNumber );
				String pepXMLFileName = ConversionUtils.getPepXMLPrefixFromPsmId( percPSM.getPsmId() ) + ".pep.xml";

				CometReportedPeptide cometReportedPeptide = CometParsingUtils.getCometReportedPeptideForString(percolatorReportedPeptide, cometResults);

				if( !cometResults.getPeptidePSMMap().containsKey( cometReportedPeptide ) ) {
					throw new Exception( "Unable to find any comet results for reported peptide: " + cometReportedPeptide );
				}

				if( !cometResults.getPeptidePSMMap().get( cometReportedPeptide ).containsKey( pepXMLFileName ) ) {
					throw new Exception( "Unable to find any comet results in " + pepXMLFileName + " for PSM: " + percPSM );
				}

				if (!cometResults.getPeptidePSMMap().get(cometReportedPeptide).get( pepXMLFileName).containsKey(scanNumber)) {
					throw new Exception("Error: Could not find PSM data in " + pepXMLFileName + " for scan number " + scanNumber + " for percolator PSM: " + percPSM );
				}

			}
		}
	}
	
}
