
# File:        ontocatdb/R/generated/java/ontocatdb/R/Investigation.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity Investigation
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for Investigation
.create.investigation <- function(data_frame, value_list, .usesession=T, .verbose=T)
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
  	  	  	
        
    return(data_frame)
}

#freely find Investigation 
find.investigation <- function( id=NULL , name=NULL , description=NULL , accession=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "ontocatdb.Investigation", mget(ls(),environment()), .verbose=.verbose)
	#use secondary key as rownames
	#rownames(result)<-result$name
	return(result)
}

#add data.frame of Investigation or each column individually
#note: each column must have the same length
add.investigation <- function(.data_frame=NULL, name=NULL, description=NULL, accession=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.investigation(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("ontocatdb.Investigation", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of Investigation or just one row using named arguments.
remove.investigation <- function( .data_frame=NULL, id=NULL, name=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.investigation(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("ontocatdb.Investigation", .data_frame, "REMOVE") )
}

use.investigation<-function(id=NULL, name=NULL)
{
	#add session parameters
    
    #retrieve the investigation by pkey or skey
    row<-F
    if(!is.null(id))
    {
    	row<-find.investigation(id=id) 
    }  
	else if( !(is.null(name)) )
	{
		row<-find.investigation(name=name)
	} 
    else
    {
    	stop('you need to provide {id} or {name}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using investigation with:\n")
    	cat("\tid=",row$id,"\n")
		.MOLGENIS$session.investigation.id<<-row$id
		cat("\tname=",row$name,"\n")
		.MOLGENIS$session.investigation.name<<-row$name
    }
    else
    {
       cat("Did not find investigation using ","id=",id,"name=",name,"\n")
    }
}