# Search and re-annotation of free-text to ontology

# Table of results of experiments concering morphogenesis, where terms are in free-text form
results<-data.frame(term=c("sorocarp","endocardium","paraxial mesoderm","embryonic arm","post-embryonic medial fin","mesonephric glomerulus","mesonephric mesenchyme","membranous septum","post-embryonic hindlimb","bronchiole"),value=rnorm(10))

# Obtain GO slim for biological processes
library(ontoCAT)
go <- getOntology("http://www.ontocat.org/browser/trunk/ontoCAT/src/uk/ac/ebi/ontocat/examples/R/resources/goslim_bp.obo")

# Re-annotation example using GO ontology
annotations<-c(lapply(results$term,function(x) searchTerm(go, paste(x, "morphogenesis", sep=" "))),recursive = TRUE)

results$GOID <- annotations