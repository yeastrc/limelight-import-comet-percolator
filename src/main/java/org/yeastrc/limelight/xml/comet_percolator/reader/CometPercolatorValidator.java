package org.yeastrc.limelight.xml.comet_percolator.reader;

import org.yeastrc.limelight.xml.comet_percolator.objects.*;

import org.yeastrc.limelight.xml.comet_percolator.objects.*;
import org.yeastrc.limelight.xml.comet_percolator.utils.CometParsingUtils;

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
	public static void validateData(CometResults cometResults, PercolatorResults percolatorResults ) throws Exception {

		for( String percolatorReportedPeptide : percolatorResults.getReportedPeptideResults().keySet() ) {

			Map<String, Map<Integer, PercolatorPSM>> percolatorPeptideDataMap = percolatorResults.getReportedPeptideResults().get( percolatorReportedPeptide ).getPercolatorPSMs();
			CometReportedPeptide cometReportedPeptide = CometParsingUtils.getCometReportedPeptideForString(percolatorReportedPeptide, cometResults);

			Map<String, Map<Integer, CometPSM>> cometMap = cometResults.getPeptidePSMMap().get( cometReportedPeptide );

			if (cometMap == null ) {
				throw new Exception("Unable to find any comet results for reported peptide: " + percolatorReportedPeptide);
			}


			for( String pepXMLFileName : percolatorPeptideDataMap.keySet() ) {

				if (!cometMap.containsKey(pepXMLFileName)) {
					throw new Exception("Unable to find any comet results in " + pepXMLFileName + " for reported peptide: " + cometReportedPeptide );
				}

				for (int scanNumber : percolatorPeptideDataMap.get( pepXMLFileName ).keySet() ) {

					if (!cometMap.get(pepXMLFileName).containsKey(scanNumber)) {
						throw new Exception("Error: Could not find PSM data in " + pepXMLFileName + " for scan number " + scanNumber );
					}

				}
			}

		}
	}
}
