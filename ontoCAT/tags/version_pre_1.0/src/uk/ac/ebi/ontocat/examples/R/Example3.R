#   Copyright (c) 2010 - 2011 European Molecular Biology Laboratory
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

##########################################################
# Operations with relationships
# Please use the full version of ontoCAT R package: https://sourceforge.net/projects/ontocat/files/ontoCAT/ontoCAT_R/ontoCAT_1.2.1.tar.gz
##########################################################

#Java Heap size needed to reason over GO ontology (more than 20 MB in size) is 512MB.
#Here are the instructions how to increase Java Heap Size in R:

library(rJava)
options(java.parameters="-Xmx512")
.jinit()

#To check the result:
.jcall(.jnew("java/lang/Runtime"), "J", "maxMemory")
#Now it is possible to work with large ontologies like GO

library(ontoCAT)

#Example with OBO ontology

# Obtain GO ontology without reasoning over it
go_wr <- getOntologyNoReasoning("http://www.geneontology.org/ontology/obo_format_1_2/gene_ontology_ext.obo")

getOntologyRelationNames(go_wr)

# Obtain GO ontology with reasoning over it
go <- getOntology("http://www.geneontology.org/ontology/obo_format_1_2/gene_ontology_ext.obo")

getOntologyRelationNames(go)


#Example with OWL ontology

# Obtain EFO ontology with reasoning over it

efo_wr <- getOntologyNoReasoning("http://rest.bioontology.org/bioportal/ontologies/download/45781?applicationid=4ea81d74-8960-4525-810b-fa1baab576ff");

getOntologyRelationNames(efo_wr)

# Obtain EFO ontology with reasoning over it
efo <- getOntology("http://rest.bioontology.org/bioportal/ontologies/download/45781?applicationid=4ea81d74-8960-4525-810b-fa1baab576ff")

getOntologyRelationNames(efo)

term <- getTermById(efo,"EFO_0000815")
getTermRelations(efo,term,"has_part")