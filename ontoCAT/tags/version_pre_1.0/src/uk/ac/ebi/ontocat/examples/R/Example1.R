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
# Gene enrichment test by using ontoCAT R package
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

library(RCurl)
library(rjson)

# Define a function to retrieve some genes from Gene Expression Atlas (http://www.ebi.ac.uk/gxa/)
options(GXAAPIURL="http://www.ebi.ac.uk/gxa/api?")
queryGeneUp <- function(query) fromJSON(getURL(paste(getOption("GXAAPIURL"), "upIn=", query, sep = "")))

# Create a list of over-expressed genes in pancreas vs other tissues
g <- queryGeneUp("pancreas")

# g$results is a list of 100 genes, each accessible by g$results[[i]]$gene
names(g$results[[10]]$gene)

# Get GO ontology
library(ontoCAT)

# Obtain GO ontology
go <- getOntology("http://www.geneontology.org/ontology/obo_format_1_2/gene_ontology_ext.obo")

# Obtain the list of all GOIDs using biomaRt
library(biomaRt)
mart <- useMart("ensembl")
mart<-useDataset("hsapiens_gene_ensembl",mart)
genes <- getBM(attributes=c("ensembl_gene_id","go_biological_process_id"), mart=mart)
all.GOIDs <- unique(genes[,2])
all.GOIDs <- all.GOIDs[2:length(all.GOIDs)]

# Create a list of over-expressed genes
genes.of.interest <- c(sapply(1:100,function(x) g$results[[x]]$gene$ensemblGeneId),recursive = TRUE)
selection <- genes[which(genes[,1] %in% genes.of.interest),]

# Create a list of genes for each GO ID
genes.by.GOID <- sapply(1:length(all.GOIDs),function(x) genes[which(genes[,2] == all.GOIDs[x]),][1]$ensembl_gene_id)
names(genes.by.GOID)=all.GOIDs

# Create a list containing the number of genes in each category
size.by.GOID<-lapply(genes.by.GOID,length)

# Construct a list containing the overlap for each GO category with the genes of interest
#by using ontoCAT library to get all children (subclasses) of particular GO term
overlaps = lapply(names(genes.by.GOID), function(x) length(which(selection[,2] %in%
gsub("GO_*", "GO:", c(lapply(getTermAndAllChildrenById(go,toString(gsub("GO:*", "GO_",x))),function(t) {getAccession(t)}),recursive = TRUE))
)))

# Create a list of hypergeometric p-values
pvals = as.array(sapply(1:length(genes.by.GOID), function(x)
    phyper(overlaps[[x]]-1,
    size.by.GOID[[x]],
    length(all.GOIDs) - size.by.GOID[[x]],
    length(unique(selection[,1])),
    lower.tail=FALSE)))
names(pvals) =  names(genes.by.GOID)

# Get the most enriched categories:
pvals[order(pvals)][1:10]




