
# File:        ontocatdb/R/generated/java/ontocatdb/R/OntologyTerm.R
# Copyright:   GBIC 2000-2,011, all rights reserved
# Date:        February 8, 2011
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity OntologyTerm
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for OntologyTerm
.create.ontologyterm <- function(data_frame, value_list, .usesession=T, .verbose=T)
{
	#convert to data_frame, remove null columns
	if(!is.data.frame(data_frame))
	{
		if(is.matrix(data_frame))
		{
			data_frame <- as.data.frame(data_frame)
		}
		else if(is.list(data_frame))
		{
			data_frame <- as.data.frame(data_frame[!sapply(data_frame, is.null)])
		}
		#transform non-null values into data.frame
		else
		{
			data_frame <- as.data.frame(value_list[!sapply(value_list, is.null)])
		}
  	}	
  	  	  	
    #add missing xref values from session parameters (optional)
    if(.usesession && is.null(data_frame$investigation__id) && !is.null(.MOLGENIS$session.investigation.id))    
    {
        data_frame$investigation = .MOLGENIS$session.investigation.id
        if(.verbose) 
        {
        	cat("Using investigation (id='",.MOLGENIS$session.investigation.id,"'", sep="")
			cat(", name='",.MOLGENIS$session.investigation.name,"'", sep="")
			cat(") from session (.usession = T).\n")
		} 
    }
        
    return(data_frame)
}

#freely find OntologyTerm 
find.ontologyterm <- function( id=NULL , name=NULL , investigation_id=NULL, investigation_name=NULL , term=NULL , termdefinition=NULL , termaccession=NULL , termsource_id=NULL, termsource_name=NULL , termpath=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    if(.usesession && is.null(investigation_id) && !is.null(.MOLGENIS$session.investigation.id))    
    {
        investigation_id = .MOLGENIS$session.investigation.id
        cat("Using investigation_id (id='",.MOLGENIS$session.investigation.id,"'", sep="")
		cat(", name='",.MOLGENIS$session.investigation.name,"'", sep="")
		cat(") from session (.usession = T).\n")        
    } 
    
	result <- MOLGENIS.find( "ontocatdb.OntologyTerm", mget(ls(),environment()), .verbose=.verbose)
	#use secondary key as rownames
	#rownames(result)<-result$term
	return(result)
}

#add data.frame of OntologyTerm or each column individually
#note: each column must have the same length
add.ontologyterm <- function(.data_frame=NULL, name=NULL, investigation_id=NULL, investigation_name=NULL, term=NULL, termdefinition=NULL, termaccession=NULL, termsource_id=NULL, termsource_name=NULL, termpath=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.ontologyterm(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("ontocatdb.OntologyTerm", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of OntologyTerm or just one row using named arguments.
remove.ontologyterm <- function( .data_frame=NULL, id=NULL, term=NULL, investigation=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.ontologyterm(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("ontocatdb.OntologyTerm", .data_frame, "REMOVE") )
}

use.ontologyterm<-function(id=NULL, term=NULL, investigation=NULL)
{
	#add session parameters
    if(is.null(investigation) && !is.null(.MOLGENIS$session.investigation.id))    
    {
        investigation = .MOLGENIS$session.investigation.id
        cat("Using investigation (id='",.MOLGENIS$session.investigation.id,"'", sep="")
		cat(", name='",.MOLGENIS$session.investigation.name,"'", sep="")
		cat(") from session.\n")        
    } 
    
    #retrieve the ontologyterm by pkey or skey
    row<-F
    if(!is.null(id))
    {
    	row<-find.ontologyterm(id=id) 
    }  
	else if( !(is.null(term) ||is.null(investigation)) )
	{
		row<-find.ontologyterm(term=term,investigation=investigation)
	} 
    else
    {
    	stop('you need to provide {id} or {term and investigation}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using ontologyterm with:\n")
    	cat("\tid=",row$id,"\n")
		.MOLGENIS$session.ontologyterm.id<<-row$id
		cat("\tterm=",row$term,"\n")
		.MOLGENIS$session.ontologyterm.term<<-row$term
		cat("\tinvestigation=",row$investigation,"\n")
		.MOLGENIS$session.ontologyterm.investigation<<-row$investigation
    }
    else
    {
       cat("Did not find ontologyterm using ","id=",id,"term=",term,"investigation=",investigation,"\n")
    }
}