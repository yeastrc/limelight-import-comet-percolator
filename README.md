Comet/Percolator to limelight XML Converter
=======================================

Use this program to convert the results of a Comet + Percolator analysis to
limelight XML suitable for import into the limelight web application. Requires
that the Percolator output be represented as XML (see -X option in Percolator).
Also requires that comet output be present as pepXML files.

How To Run
-------------
1. Download the [latest release](https://github.com/yeastrc/limelight-import-comet-percolator/releases).
2. Run the program ``java -jar cometPercolator2LimelightXML.jar`` with no arguments to see the possible parameters. Requires Java 8 or higher.

Command line documentation
---------------------------

Usage: java -jar cometPercolator2LimelightXML.jar -c path -p path -o path [-f path] [-d path]

Example: 

         java -jar cometPercolator2LimelightXML.jar -c /data/mass_spec/exp1/comet.params
                                                    -p /data/mass_spec/exp1/percolator.out.xml
                                                    -o /data/mass_spec/exp1/exp1.limelight.xml

         java -jar cometPercolator2LimelightXML.jar -c c:\data\exp1\comet.params
                                                    -p c:\data\exp1\percolator.out.xml
                                                    -o c:\data\exp1\exp1.limelight.xml
                                                    -f c:\data\fastas\yeast.fa
                                                    -d c:\data\exp1\pepxml_files

Options:
```

        -c      [Required] Path to comet .params file
        -o      [Required] Path to use for the limelight XML output file
        -p      [Required] Path to Percolator output XML file
        -f      [Optional] Path to FASTA file used in the experiment. If not present, the path will
                           be taken from the comet.params file.
        -d      [Optional] By default, this program expects the pepXML files referenced by
                           the percolator output to be in the same directory as the percolator
                           file. If this is not true, use this option to specify the directory
                           in which the pepXML file(s) may be found. This must be a directory.

```
