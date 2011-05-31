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

######################################################
# Search and re-annotation of free-text to ontology
######################################################

# Table of results of experiments concering morphogenesis, where terms are in free-text form
results<-data.frame(term=c("sorocarp","endocardium","paraxial mesoderm","embryonic arm","post-embryonic medial fin","mesonephric glomerulus","mesonephric mesenchyme","membranous septum","post-embryonic hindlimb","bronchiole"),value=rnorm(10))

# Obtain GO slim for biological processes
library(ontoCAT)
go <- getOntology("http://www.ontocat.org/browser/trunk/ontoCAT/src/uk/ac/ebi/ontocat/examples/R/resources/goslim_bp.obo")

# Re-annotation example using GO ontology
annotations<-c(lapply(results$term,function(x) searchTerm(go, paste(x, "morphogenesis", sep=" "))),recursive = TRUE)

results$GOID <- annotations