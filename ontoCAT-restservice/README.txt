To run the ontoCAT resultful service
Standalone:
 * In Eclipe, rightclick on src/uk.ac.ebi.ontocat/OntoCatServer
 * Choose Run As Java Application

On Tomcat:
 * In eclipse, rightclick your project and run -> run on server
 * Then browse to http://localhost:8080/ontocat/rest/xml/ontologies
 * You will get a text listing of ontologies.
 * This work was build with http://cxf.apache.org/docs/jax-rs.html

What to do with the service: 
Look to the file WebContent/test.html 
to see how you can interact with the service using jquery.
 
A full list of services is available at
http://localhost:8080/ontocat/rest?_wadl
 
For playing around with the server we use 
http://code.google.com/p/rest-client/

Caveat: if you use this service accross domains 
you can have cross domain errors which keep the 
services from functioning properly.