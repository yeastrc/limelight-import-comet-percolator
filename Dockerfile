FROM amazoncorretto:11.0.17

ADD build/libs/cometPercolator2LimelightXML.jar  /usr/local/bin/cometPercolator2LimelightXML.jar
ADD entrypoint.sh /usr/local/bin/entrypoint.sh
ADD cometPercolator2LimelightXML /usr/local/bin/cometPercolator2LimelightXML

RUN chmod 755 /usr/local/bin/entrypoint.sh && chmod 755 /usr/local/bin/cometPercolator2LimelightXML
RUN yum update -y && yum install -y procps

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD []
